package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import jdbc.JdbcUtil;
import member.model.*;

public class IndMemberDao {
	
	public IndMember selectById(Connection conn, String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from indmember where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			IndMember indmember = null;
			if (rs.next()) {
				indmember = new IndMember(
						rs.getString("id"),
						rs.getString("name"),
						rs.getString("password"),
						rs.getString("email"),
						toDate(rs.getTimestamp("regDate")));
			}
			return indmember;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	private Date toDate(Timestamp date) {
		return date == null ? null : new Date(date.getTime());
	}
	
	public void insert(Connection conn, IndMember indmem) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement("insert into indmember values(?, ?, ?, ?, ?)")) {
			pstmt.setString(1, indmem.getId());
			pstmt.setString(2, indmem.getName());
			pstmt.setString(3, indmem.getPassword());
			pstmt.setString(4, indmem.getEmail());
			pstmt.setTimestamp(5, new Timestamp(indmem.getRegDate().getTime()));
			pstmt.executeUpdate();
		}
	}
	
	public void update(Connection conn, IndMember indmem) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement(
				"update indmember set name =?, password =? where id = ?")){
			pstmt.setString(1, indmem.getName());
			pstmt.setString(2, indmem.getPassword());
			pstmt.setString(3, indmem.getId());
			pstmt.executeUpdate();
		}
	}
}
