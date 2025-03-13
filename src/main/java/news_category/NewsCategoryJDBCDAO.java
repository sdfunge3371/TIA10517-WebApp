package news_category;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NewsCategoryJDBCDAO implements NewsCategoryDAO_interface{

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/db01?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";
	
	private static final String INSERT_STMT = 
		"INSERT INTO news_category (  news_category_id, category_name, created_time  )" 
				+ " VALUES (?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT  news_category_id, category_name, created_time"
		+ " FROM news_category order by news_category_id";
	private static final String GET_ONE_STMT = 
		"SELECT news_category_id, category_name, created_time"
		+ " FROM news_category where news_category_id = ?";
	private static final String DELETE = 
		"DELETE FROM news_category where news_category_id = ?";
	private static final String UPDATE = 
		"UPDATE news_category set category_name=?, created_time=?"
		+ " where news_category_id = ?";
	
	
	@Override
	public void insert(NewsCategoryVO newsCategoryVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, newsCategoryVO.getNewsCategoryId());
			pstmt.setString(2, newsCategoryVO.getNewsCategoryName());
			pstmt.setTimestamp(3, newsCategoryVO.getCreatedTime());

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
	public void update(NewsCategoryVO newsCategoryVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
		
			pstmt.setString(1, newsCategoryVO.getNewsCategoryName());
			pstmt.setTimestamp(2, newsCategoryVO.getCreatedTime());
			pstmt.setString(3, newsCategoryVO.getNewsCategoryId());

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
	public void delete(String newsCategoryId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, newsCategoryId);

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
	public NewsCategoryVO findByPrimaryKey(String newsCategoryId) {

		NewsCategoryVO newsCategoryVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, newsCategoryId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				newsCategoryVO = new NewsCategoryVO();
			//news_title, news_content, news_start_date, news_end_date, news_img, created_time, news_category_id_fk, admin_id_fk, news_status_id_fk	
				newsCategoryVO.setNewsCategoryId(rs.getString("news_category_id"));
				newsCategoryVO.setNewsCategoryName(rs.getString("category_name"));
				newsCategoryVO.setCreatedTime(rs.getTimestamp("created_time"));
			

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
		return newsCategoryVO;
		
	}

	@Override
	public List<NewsCategoryVO> getAll() {

		List<NewsCategoryVO> list = new ArrayList<NewsCategoryVO>();
		NewsCategoryVO newsCategoryVO = null;

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
				newsCategoryVO = new NewsCategoryVO();
				
				newsCategoryVO.setNewsCategoryId(rs.getString("news_category_id"));
				newsCategoryVO.setNewsCategoryName(rs.getString("category_name"));
				newsCategoryVO.setCreatedTime(rs.getTimestamp("created_time"));
			
				list.add(newsCategoryVO); // Store the row in the list
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

		NewsCategoryJDBCDAO dao = new NewsCategoryJDBCDAO();

		// 新增
//		NewsCategoryVO empVO1 = new NewsCategoryVO();
//		empVO1.setNewsCategoryId("NC004");
//		empVO1.setNewsCategoryName("測試中");
//		
//		Timestamp in1 = new Timestamp(System.currentTimeMillis()); 
//		empVO1.setCreatedTime(in1);
//		
//		dao.insert(empVO1);

		// 修改
//		NewsCategoryVO empVO2 = new NewsCategoryVO();
//		empVO2.setNewsCategoryId("NC004");
//		empVO2.setNewsCategoryName("修改中");
//		
//		Timestamp in1 = new Timestamp(System.currentTimeMillis()); 
//		empVO2.setCreatedTime(in1);
//		
//		dao.update(empVO2);

		// 刪除
//		dao.delete("NC004");

		// 查詢
//		NewsCategoryVO empVO3 = dao.findByPrimaryKey("NC001");
//	
//		System.out.print(empVO3.getNewsCategoryId() + ",");
//		System.out.print(empVO3.getNewsCategoryName() + ",");
//		System.out.print(empVO3.getCreatedTime() + ",");
//		System.out.println("---------------------");

		// 查詢
//		List<NewsCategoryVO> list = dao.getAll();
//		for (NewsCategoryVO aEmp : list) {
//			System.out.print(aEmp.getNewsCategoryId() + ",");
//			System.out.print(aEmp.getNewsCategoryName() + ",");
//			System.out.print(aEmp.getCreatedTime() + ",");
//			System.out.println();
//		}
		
	}
	

}
