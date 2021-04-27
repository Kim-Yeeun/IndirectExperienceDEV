package post.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import post.model.*;
import jdbc.JdbcUtil;

public class PostDao {

	// post 테이블에 데이터 삽입하는 insert 함수 
	public Post insert(Connection conn, Post post) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("insert into post "
					+ "(ent_id, ent_name, title, regdate, moddate, read_cnt) "
					+ "values (?, ?, ?, ?, ?, 0)");
			pstmt.setString(1, post.getWriterEnt().getId());
			pstmt.setString(2, post.getWriterEnt().getName());
			pstmt.setString(3, post.getTitle());
			pstmt.setTimestamp(4, toTimestamp(post.getRegDate()));
			pstmt.setTimestamp(5, toTimestamp(post.getModifiedDate()));
			int insertedCount = pstmt.executeUpdate();
			
			if (insertedCount > 0) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select last_insert_id() from post");
				if (rs.next()) {
					Integer newNum = rs.getInt(1);
					return new Post(newNum, post.getWriterEnt(), post.getTitle(),
							post.getRegDate(), post.getModifiedDate(), 0);
				}
			}
			return null;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(stmt);
		}
	}
	
	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}
	
	// post 테이블의 전체 레코드 수 반환하는 selectCount 함수 
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from post");
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	
	// 전체 게시글 개수 구하는 select 함수 
	public List<Post> select(Connection conn, int startRow, int size) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from post " +
					"order by post_no desc limit ?, ?");
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, size);
			rs = pstmt.executeQuery();
			List<Post> result = new ArrayList<>();
			while (rs.next()) {
				result.add(convertPost(rs));
			}
			return result;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	// ResultSet 에서 가져온 데이터로 Post 객체 생성하는 convertPost 함수
	private Post convertPost(ResultSet rs) throws SQLException {
		return new Post(rs.getInt("post_no"),
				new WriterEnt(
						rs.getString("ent_id"),
						rs.getString("ent_name")),
				rs.getString("title"),
				toDate(rs.getTimestamp("regdate")),
				toDate(rs.getTimestamp("moddate")),
				rs.getInt("read_cnt"));
	}
	
	private Date toDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}
	
	public Post selectById(Connection conn, int no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from post where post_no = ?");
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			Post post = null;
			if (rs.next()) {
				post = convertPost(rs);
			}
			return post;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public void increaseReadCount(Connection conn, int no) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement(
				"update post set read_cnt = read_cnt + 1 " +
				"where post_no = ?")) {
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		}
	}
	
	public int update(Connection conn, int no, String title) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement(
				"update post set title = ?, moddate = now()" + "where post_no = ?")){
			pstmt.setString(1, title);
			pstmt.setInt(2, no);
			return pstmt.executeUpdate();
		}
	}
}
