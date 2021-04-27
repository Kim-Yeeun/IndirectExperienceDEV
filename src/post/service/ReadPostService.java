package post.service;

import java.sql.Connection;
import java.sql.SQLException;

import post.dao.*;
import post.model.*;
import jdbc.connection.*;

public class ReadPostService {

	private PostDao postDao = new PostDao();
	private PostContentDao contentDao = new PostContentDao();
	
	public PostData getPost(int postNum, boolean increaseReadCount) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			Post post = postDao.selectById(conn, postNum);
			if (post == null) {
				throw new PostNotFoundException();
			}
			PostContent content = contentDao.selectById(conn, postNum);
			if (content == null) {
				throw new PostContentNotFoundException();
			}
			if (increaseReadCount) {
				postDao.increaseReadCount(conn, postNum);
			}
			return new PostData(post, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
