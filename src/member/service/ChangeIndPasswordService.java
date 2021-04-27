package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.*;
import member.dao.*;
import member.model.*;

public class ChangeIndPasswordService {

	private IndMemberDao indMemberDao = new IndMemberDao();
	
	public void changePassword(String userId, String curPwd, String newPwd) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			IndMember indmember = indMemberDao.selectById(conn, userId);
			if (indmember == null) {
				throw new MemberNotFoundException();
			}
			if (!indmember.matchPassword(curPwd)) {
				throw new InvalidPasswordException();
			}
			indmember.changePassword(newPwd);
			indMemberDao.update(conn, indmember);
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
			}
	}
}
