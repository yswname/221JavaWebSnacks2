package cn.com.demo.javaweb.snacks.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class TestContainerDSServlet
 */
@WebServlet("/testContainerDSServlet")
public class TestContainerDSServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 创建上下文
			Context ctx = new InitialContext();
			// 从上下文中获取连接池对象（绑定）
			Object obj = ctx.lookup("java:comp/env/jdbcab/xxx");
			DataSource ds = (DataSource) obj;
			// 获取连接
			Connection conn = ds.getConnection();
			// 打印
			System.out.println(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
