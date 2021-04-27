package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import jdbc.JdbcUtil;
import member.model.*;

public class EntMemberDao {
	
	public EntMember selectById(Connection conn, String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from entmember where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			EntMember entmember = null;
			if (rs.next()) {
				entmember = new EntMember(
						rs.getString("id"),
						rs.getString("name"),
						rs.getString("password"),
						rs.getString("email"),
						rs.getString("entname"),
						toDate(rs.getTimestamp("regDate")));
			}
			return entmember;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	private Date toDate(Timestamp date) {
		return date == null ? null : new Date(date.getTime());
	}
	
	public void insert(Connection conn, EntMember entmem) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement("insert into entmember values(?, ?, ?, ?, ?, ?)")) {
			pstmt.setString(1, entmem.getId());
			pstmt.setString(2, entmem.getName());
			pstmt.setString(3, entmem.getPassword());
			pstmt.setString(4, entmem.getEmail());
			pstmt.setString(5, entmem.getEntname());
			pstmt.setTimestamp(6, new Timestamp(entmem.getRegDate().getTime()));
			pstmt.executeUpdate();
		}
	}
	
	public void update(Connection conn, EntMember entmem) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement(
				"update entmember set name =?, password =? where id = ?")){
			pstmt.setString(1, entmem.getName());
			pstmt.setString(2, entmem.getPassword());
			pstmt.setString(3, entmem.getId());
			pstmt.executeUpdate();
		}
	}
}
