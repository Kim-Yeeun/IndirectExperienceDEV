package auth.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.*;
import member.dao.*;
import member.model.*;

public class LoginEntService {

	private EntMemberDao entmemberDao = new EntMemberDao();
	
	public User loginEnt(String id, String password) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			EntMember entmember = entmemberDao.selectById(conn, id);
			if (entmember == null) {
				throw new LoginFailException();
			}
			if (!entmember.matchPassword(password)) {
				throw new LoginFailException();
			}
			return new User(entmember.getId(), entmember.getName());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
