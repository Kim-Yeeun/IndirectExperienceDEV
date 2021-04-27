package member.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jdbc.JdbcUtil;
import jdbc.connection.*;
import member.dao.*;
import member.model.*;

public class JoinIndService {

	private IndMemberDao indmemberDao = new IndMemberDao();
	
	public void join(JoinIndRequest joinIndReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			IndMember indmember = indmemberDao.selectById(conn, joinIndReq.getId());
			if (indmember != null) {
				JdbcUtil.rollback(conn);
				throw new DuplicateIdException();
			}
			
			indmemberDao.insert(conn, new IndMember(joinIndReq.getId(), joinIndReq.getName(), joinIndReq.getPassword(),
					joinIndReq.getEmail(), new Date()));
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
