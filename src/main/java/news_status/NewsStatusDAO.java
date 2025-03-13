package news_status;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class NewsStatusDAO implements NewsStatusDAO_interface {

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

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, newsStatusVO.getNewsStatusId());
			pstmt.setString(2, newsStatusVO.getCategoryName());
			pstmt.setTimestamp(3, newsStatusVO.getCreatedTime());
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
	public void update(NewsStatusVO newsStatusVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, newsStatusVO.getCategoryName());
			pstmt.setTimestamp(2, newsStatusVO.getCreatedTime());
			pstmt.setInt(3, newsStatusVO.getNewsStatusId());
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
	public void delete(Integer newsStatusId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, newsStatusId);

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
	public NewsStatusVO findByPrimaryKey(Integer newsStatusId) {

		NewsStatusVO newsStatusVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				// empVO 也稱為 Domain objects
				newsStatusVO = new NewsStatusVO();
				newsStatusVO.setNewsStatusId(rs.getInt("news_status_id"));
				newsStatusVO.setCategoryName(rs.getString("category_name"));
				newsStatusVO.setCreatedTime(rs.getTimestamp("created_time"));
				list.add(newsStatusVO); // Store the row in the list
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