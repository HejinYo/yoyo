package cn.hejinyo.other.socket;

import java.sql.*;

public class DBUtil {
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/ssm?useUnicode=true&characterEncoding=UTF-8";
	private static final String username = "root";
	private static final String password = "redhat";

	private static Connection conn = null;

	static {
		try {
			Class.forName(driver);//加载JDBC驱动程序
		} catch (Exception e) {
			System.out.println("驱动异常！");
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {
			if (conn == null) {
				conn = DriverManager.getConnection(url, username, password);//创建数据库的连接
				return conn;
			}

			return conn;
		} catch (SQLException e) {
			System.out.println("连接数据库异常！！");
			e.printStackTrace();
			return conn;
		}

	}

	// 单例测试
	public static void main(String[] args) {

		try {
			Connection conn = DBUtil.getConnection();
			if (conn != null) {

				System.out.println("数据库连接正常！");
				Connection connn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;

				String sql = "select * from jzt_gps;"; // SQL语句
				stmt = conn.prepareStatement(sql);//PreparedStatement声明一个SQL语句对象
				rs = stmt.executeQuery();//ResultSet对象rs获得一个查询结果
				while (rs.next()) {
					System.out.println(rs.getString("devid"));
				}
				System.out.println("数据库连接正常！");
				rs.close();
				rs = null;
				stmt.close();
				stmt = null;

			} else {
				System.out.println("数据库连接异常！");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
