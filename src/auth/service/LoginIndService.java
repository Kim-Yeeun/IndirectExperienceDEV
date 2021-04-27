package auth.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.*;
import member.dao.*;
import member.model.*;

public class LoginIndService {

	private IndMemberDao indmemberDao = new IndMemberDao();
	
	public User loginInd(String id, String password) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			IndMember indmember = indmemberDao.selectById(conn, id);
			if (indmember == null) {
				throw new LoginFailException();
			}
			if (!indmember.matchPassword(password)) {
				throw new LoginFailException();
			}
			return new User(indmember.getId(), indmember.getName());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
