package member.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jdbc.JdbcUtil;
import jdbc.connection.*;
import member.dao.*;
import member.model.*;

public class JoinEntService {

	private EntMemberDao entmemberDao = new EntMemberDao();
	
	public void join(JoinEntRequest joinentReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			EntMember member = entmemberDao.selectById(conn, joinentReq.getId());
			if (member != null) {
				JdbcUtil.rollback(conn);
				throw new DuplicateIdException();
			}
			
			entmemberDao.insert(conn, new EntMember(joinentReq.getId(), joinentReq.getName(), joinentReq.getPassword(),
					joinentReq.getEmail(), joinentReq.getEntname(), new Date()));
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
