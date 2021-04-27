package post.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import post.dao.*;
import post.model.*;
import jdbc.JdbcUtil;
import jdbc.connection.*;

public class WritePostService {

	private PostDao postDao = new PostDao();
	private PostContentDao contentDao = new PostContentDao();
	
	public Integer write(WriteRequest req) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Post post = toPost(req);
			Post savedPost = postDao.insert(conn, post);
			if (savedPost == null) {
				throw new RuntimeException("fail to insert post");
			}
			PostContent content = new PostContent(savedPost.getNumber(), req.getContent());
			PostContent savedContent = contentDao.insert(conn, content);
			if (savedContent == null) {
				throw new RuntimeException("fail to insert post_content");
			}
			
			conn.commit();
			
			return savedPost.getNumber();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	private Post toPost(WriteRequest req) {
		Date now = new Date();
		return new Post(null, req.getWriterEnt(), req.getTitle(), now, now, 0);
	}
}
