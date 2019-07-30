package cn.com.demo.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import cn.com.demo.javaweb.snacks.db.DBConnection;
import cn.com.demo.javaweb.snacks.po.PicturePO;

public class TestMain {

	public static void main(String[] args) throws Exception{
//		// 容器对象
//		Context ctx = new InitialContext();
//		// 获取对象
//		Object obj = ctx.lookup("java:comp/env/jdbc/test");
		 
		for (int i = 0; i < 10; i++) {
			long time1 = System.currentTimeMillis();
			int fdId = 1;
			int picType = 9;
			List<PicturePO> picList = null;
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			DBConnection dbConn = DBConnection.getInstance();
			long time2 = System.currentTimeMillis();
			try {
				conn = dbConn.getConnection();
				long time3 = System.currentTimeMillis();
				String sql = "select * from snacks_picture where pic_fd_id=? and pic_type=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, fdId);
				pstmt.setInt(2, picType);
				rs = pstmt.executeQuery();
				if (rs != null) {
					picList = new ArrayList<PicturePO>();
					PicturePO pic = null;
					while (rs.next()) {
						pic = new PicturePO();
						pic.setPicDesc(rs.getString("pic_desc"));
						pic.setPicFdId(rs.getInt("pic_fd_id"));
						pic.setPicId(rs.getInt("pic_id"));
						pic.setPicType(rs.getInt("pic_type"));
						pic.setPicUrl(rs.getString("pic_url"));
						picList.add(pic);
					}
				}
				long time4 = System.currentTimeMillis();

				System.out.println("总耗时:" + (time4 - time1) + ",链接时间:" + (time3 - time2) + ", 执行时间:" + (time4 - time3));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dbConn.close(conn, pstmt, rs);
			}
		}
	}

}
