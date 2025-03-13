package news_category;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class NewsCategoryDAO implements NewsCategoryDAO_interface {

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

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

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, newsCategoryVO.getNewsCategoryId());
			pstmt.setString(2, newsCategoryVO.getNewsCategoryName());
			pstmt.setTimestamp(3, newsCategoryVO.getCreatedTime());

			pstmt.executeUpdate();

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

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, newsCategoryVO.getNewsCategoryName());
			pstmt.setTimestamp(2, newsCategoryVO.getCreatedTime());
			pstmt.setString(3, newsCategoryVO.getNewsCategoryId());

			pstmt.executeUpdate();

			// Handle any driver errors
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, newsCategoryId);

			pstmt.executeUpdate();

			// Handle any driver errors
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

			con = ds.getConnection();
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

			con = ds.getConnection();
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
}