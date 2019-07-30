package cn.com.demo.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyPools {
	private List<Connection> pools = new ArrayList<Connection>();
	private int max = 10;
	private String url;
	private String username;
	private String password;

	public Connection getConnection() {
		Connection conn = null;

		if (pools.size() > 0) {
			conn = pools.remove(pools.size() - 1);
		} else {
			try {
				conn = DriverManager.getConnection(url, username, password);
				conn = new MyConnection(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return conn;
	}

	public void freeConnection(Connection conn) throws SQLException {
		if (conn != null && !conn.isClosed()) {
			this.pools.add(conn);
		}
	}
}
