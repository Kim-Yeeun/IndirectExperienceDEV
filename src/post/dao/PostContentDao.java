package post.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import post.model.*;
import jdbc.JdbcUtil;

public class PostContentDao {

	public PostContent insert(Connection conn, PostContent content) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(
					"insert into post_content " +
					"(post_no, content) values (?, ?)");
			pstmt.setLong(1, content.getNumber());
			pstmt.setString(2, content.getContent());
			int insertedCount = pstmt.executeUpdate();
			if (insertedCount > 0) {
				return content;
			} else {
				return null;
			}
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	public PostContent selectById(Connection conn, int no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"select * from post_content where post_no = ?");
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			PostContent content = null;
			if (rs.next()) {
				content = new PostContent(rs.getInt("post_no"), rs.getString("content"));
			}
			return content;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public int update(Connection conn, int no, String content) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement(
				"update post_content set content = ? where post_no = ?")) {
			pstmt.setString(1, content);
			pstmt.setInt(2, no);
			return pstmt.executeUpdate();
		}
	}
}
