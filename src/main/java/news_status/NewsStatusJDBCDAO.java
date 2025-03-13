package news_status;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NewsStatusJDBCDAO implements NewsStatusDAO_interface{

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/db01?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";
	
	private static final String INSERT_STMT = 
		"INSERT INTO news_status (  news_status_id, category_name, created_time  )" 
				+ " VALUES (?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT  news_status_id, category_name, created_time "
		+ " FROM news_status order by news_status_id";
	private static final String GET_ONE_STMT = 
		"SELECT news_status_id, category_name, created_time"
		+ " FROM news_status where news_status_id = ?";
	private static final String DELETE = 
		"DELETE FROM news_status where news_status_id = ?";
	private static final String UPDATE = 
		"UPDATE news_status set category_name = ?, created_time = ?"
		+ " where news_status_id = ?";

	
	@Override
	public void insert(NewsStatusVO newsStatusVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, newsStatusVO.getNewsStatusId());
			pstmt.setString(2, newsStatusVO.getCategoryName());
			pstmt.setTimestamp(3, newsStatusVO.getCreatedTime());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		
	}

	@Override
	public void update(NewsStatusVO newsStatusVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			
			pstmt.setString(1, newsStatusVO.getCategoryName());
			pstmt.setTimestamp(2, newsStatusVO.getCreatedTime());
			pstmt.setInt(3, newsStatusVO.getNewsStatusId());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public void delete(Integer newsStatusId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, newsStatusId);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		
	}

	@Override
	public NewsStatusVO findByPrimaryKey(Integer newsStatusId) {

		NewsStatusVO newsStatusVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, newsStatusId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				newsStatusVO = new NewsStatusVO();
			//news_title, news_content, news_start_date, news_end_date, news_img, created_time, news_category_id_fk, admin_id_fk, news_status_id_fk	
				newsStatusVO.setNewsStatusId(rs.getInt("news_status_id"));
				newsStatusVO.setCategoryName(rs.getString("category_name"));
				newsStatusVO.setCreatedTime(rs.getTimestamp("created_time"));
			

			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return newsStatusVO;
		
	}

	@Override
	public List<NewsStatusVO> getAll() {

		List<NewsStatusVO> list = new ArrayList<NewsStatusVO>();
		NewsStatusVO newsStatusVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				newsStatusVO = new NewsStatusVO();
				newsStatusVO.setNewsStatusId(rs.getInt("news_status_id"));
				newsStatusVO.setCategoryName(rs.getString("category_name"));
				newsStatusVO.setCreatedTime(rs.getTimestamp("created_time"));
				list.add(newsStatusVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
		
	}

	
	public static void main(String[] args) {

		NewsStatusJDBCDAO dao = new NewsStatusJDBCDAO();

		// 新增
//		NewsStatusVO empVO1 = new NewsStatusVO();
//		empVO1.setNewsStatusId(4);
//		empVO1.setCategoryName("測試中");
//		
//		Timestamp in1 = new Timestamp(System.currentTimeMillis()); 
//		empVO1.setCreatedTime(in1);
//		
//		dao.insert(empVO1);

		// 修改
//		NewsStatusVO empVO2 = new NewsStatusVO();
//		empVO2.setNewsStatusId(4);
//		empVO2.setCategoryName("修改中");
//		
//		Timestamp in1 = new Timestamp(System.currentTimeMillis()); 
//		empVO2.setCreatedTime(in1);
//		
//		dao.update(empVO2);

		// 刪除
//		dao.delete(4);

		// 查詢
//		NewsStatusVO empVO3 = dao.findByPrimaryKey(1);
//		System.out.print(empVO3.getNewsStatusId() + ",");
//		System.out.print(empVO3.getCategoryName() + ",");
//		System.out.print(empVO3.getCreatedTime() + ",");
//		System.out.println("---------------------");

		// 查詢
//		List<NewsStatusVO> list = dao.getAll();
//		for (NewsStatusVO aEmp : list) {
//		System.out.print(aEmp.getNewsStatusId() + ",");
//		System.out.print(aEmp.getCategoryName() + ",");
//		System.out.print(aEmp.getCreatedTime() + ",");
//			System.out.println();
//		}
		
	}
	
	
}
