package cn.com.demo.javaweb.snacks.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;

public class DBConnection {
	private static DBConnection dbCon = null;
	private String url;
	private String username;
	private String password;
	private DruidDataSource druidDataSource;
	private DBConnection() {
		try {
			this.init();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public static DBConnection getInstance() {
		if(dbCon == null)
			dbCon = new DBConnection();
		return dbCon;
	}
	
	private void init() throws Exception{
		Properties props = new Properties();
		InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties");
		props.load(in);
		this.url = props.getProperty("url");
		this.username = props.getProperty("username");
		this.password = props.getProperty("password");
		
		//Class.forName(props.getProperty("driverName"));
		// 创建对象
		this.druidDataSource = new DruidDataSource();
		// 设置连接数据库得数据
		this.druidDataSource.setUrl(this.url);
		this.druidDataSource.setUsername(username);
		this.druidDataSource.setPassword(password);
		// 调用init方法，初始化连接池
		this.druidDataSource.init();
	}
	public Connection getConnection() {
		Connection conn = null;
//		try {
//			conn = DriverManager.getConnection(url, username, password);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		// 从连接池中获取连接
		try {
			conn = this.druidDataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return conn;
	}

	public void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				// 改成：将连接释放回连接池
				conn.close();// 内部按释放实现
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
