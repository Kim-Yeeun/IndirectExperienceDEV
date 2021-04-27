package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.*;
import member.dao.*;
import member.model.*;

public class ChangeEntPasswordService {

	private EntMemberDao entMemberDao = new EntMemberDao();
	
	public void changePassword(String userId, String curPwd, String newPwd) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			EntMember entmember = entMemberDao.selectById(conn, userId);
			if (entmember == null) {
				throw new MemberNotFoundException();
			}
			if (!entmember.matchPassword(curPwd)) {
				throw new InvalidPasswordException();
			}
			entmember.changePassword(newPwd);
			entMemberDao.update(conn, entmember);
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
			}
	}
}
