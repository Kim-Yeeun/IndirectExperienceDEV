package post.service;

import java.sql.Connection;
import java.sql.SQLException;

import post.dao.*;
import post.model.*;
import post.service.*;
import jdbc.JdbcUtil;
import jdbc.connection.*;

public class ModifyPostService {

	private PostDao postDao = new PostDao();
	private PostContentDao contentDao = new PostContentDao();
	
	public void modify(ModifyRequest modReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Post post = postDao.selectById(conn, modReq.getPostNumber());
			if (post == null) {
				throw new PostNotFoundException();
			}
			if (!canModify(modReq.getUserId(), post)) {
				throw new PermissionDeniedException();
			}
			postDao.update(conn, modReq.getPostNumber(), modReq.getTitle());
			contentDao.update(conn, modReq.getPostNumber(), modReq.getContent());
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (PermissionDeniedException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	private boolean canModify(String modifyingUserId, Post post) {
		return post.getWriterEnt().getId().equals(modifyingUserId);
	}
}
