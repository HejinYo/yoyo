package cn.hejinyo.other.socket;

import cn.hejinyo.other.model.Jzt_Gps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBC_zt_Gps {
	//添加
	public int addJzt_Gps(Jzt_Gps jzt_gps) {
		Connection conn = DBUtil.getConnection();
		String sql = "" + "insert into jzt_gps" + "(devid,longitude,latitude,credate)"
				+ " values(?,?,?,current_date());";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1,jzt_gps.getDevid());
			stmt.setDouble(2,jzt_gps.getLongitude());
			stmt.setDouble(3,jzt_gps.getLatitude());
			stmt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
