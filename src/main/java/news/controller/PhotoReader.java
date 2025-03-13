package news.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PhotoReader2")
public class PhotoReader extends HttpServlet {
	Connection con;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

//		 
//	        PreparedStatement pstmt = null;
//	        ResultSet rs = null;
//	        ServletOutputStream out = null;
//
//	        try {
//	            // 取得圖片 ID
//	            int imageId = Integer.parseInt(req.getParameter("newsId"));
//
//	            // 建立資料庫連線 (請替換為您的連線資訊)
//	            Class.forName("com.mysql.cj.jdbc.Driver");
//	            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pet_db?serverTimezone=Asia/Taipei", "root", "123456");
//
//	            // 執行 SQL 查詢，取得 BLOB 資料
//	            String sql = "SELECT news_img FROM news WHERE news_id = " + imageId;
//	            pstmt = con.prepareStatement(sql);
//	            pstmt.setInt(1, imageId);
//	            rs = pstmt.executeQuery();
//
//	            if (rs.next()) {
//	                // 設定回應的 Content-Type
//	                res.setContentType("image/jpeg"); // 或其他圖片類型
//
//	                // 取得 BLOB 資料
//	                InputStream inputStream = rs.getBinaryStream("news_img");
//	                out = res.getOutputStream();
//
//	                // 將 BLOB 資料寫入回應的輸出串流
//	                byte[] buffer = new byte[4096];
//	                int bytesRead = -1;
//	                while ((bytesRead = inputStream.read(buffer)) != -1) {
//	                    out.write(buffer, 0, bytesRead);
//	                }
//	                System.out.println(buffer);
//	            } else {
//	                res.sendError(HttpServletResponse.SC_NOT_FOUND);
//	            }
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        } finally {
//	            // 關閉資源
//	            try { if (out != null) out.close(); } catch (IOException e) {}
//	            try { if (rs != null) rs.close(); } catch (SQLException e) {}
//	            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
//	            try { if (con != null) con.close(); } catch (SQLException e) {}
//	        }
//		
//		
		
		
		 Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	 
		res.setContentType("image/png");
		ServletOutputStream out = res.getOutputStream();
		try {
			Statement stmt = con.createStatement();
			String newsId = req.getParameter("newsId");
			
			 String sql = "SELECT news_img FROM news WHERE news_id = ?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setString(1, newsId);
	        // rs = pstmt.executeQuery();
	            
			rs = pstmt.executeQuery();

			if (rs.next()) {
				  System.out.println("查詢到圖片資料"); // 加入偵錯訊息
				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("news_img"));
				byte[] buf = new byte[4 * 1024]; // 4K buffer
				int len;
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				in.close();
				  System.out.println("BLOB 資料讀取完成"); // 添加偵錯訊息
			} else {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	
	
	
	
	@Override
	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db01?serverTimezone=Asia/Taipei", "root", "123456");
		} catch (ClassNotFoundException e) {
			throw new UnavailableException("Couldn't load JdbcOdbcDriver");
		} catch (SQLException e) {
			throw new UnavailableException("Couldn't get db connection");
		}
	}

	@Override
	public void destroy() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
