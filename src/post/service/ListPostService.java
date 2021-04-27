package post.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import post.dao.*;
import post.model.*;
import jdbc.connection.*;

public class ListPostService {

	
	private PostDao postDao = new PostDao();
	private int size = 10;
	
	public PostPage getPostPage(int pageNum) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			int total = postDao.selectCount(conn);
			List<Post> content = postDao.select(conn, (pageNum - 1) * size, size);
			return new PostPage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
