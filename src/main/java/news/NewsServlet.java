package news;

import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.emp.model.*;

@WebServlet("/uploadNewsServlet.do")
@MultipartConfig
public class NewsServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);

				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("empno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.put("empno","請輸入員工編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/emp/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer empno = null;
				try {
					empno = Integer.valueOf(str);
				} catch (Exception e) {
					errorMsgs.put("empno","員工編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/emp/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(empno);
				if (empVO == null) {
					errorMsgs.put("empno","查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/emp/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("empVO", empVO); // 資料庫取出的empVO物件,存入req
				String url = "/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
				/***************************1.接收請求參數****************************************/
				String newsId = String.valueOf(req.getParameter("newsId"));
				
				/***************************2.開始查詢資料****************************************/
				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.getOneNews(newsId);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				String param = "?newsId="  +newsVO.getNewsId()+
						       "&newsTitle="  +newsVO.getNewsTitle()+
						       "&newsCategoryId="    +newsVO.getNewsCategoryId()+
						       "&adminId="+newsVO.getAdminId()+
						       "&newsContent="    +newsVO.getNewsContent()+
						       "&newsStartDate="   +newsVO.getNewsStartDate()+
						       "&newsEndDate=" +newsVO.getNewsEndDate()+
						       "&createdTime=" +newsVO.getCreatedTime()+
						       "&newsStatusId=" +newsVO.getNewsStatusId()+
						       "&newsImg=" +newsVO.getNewsImg();
				String url = "/backend/news/update_news_input.jsp"+param;
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String newsId = String.valueOf(req.getParameter("newsId").trim());
				String newsTitle = String.valueOf(req.getParameter("newsTitle").trim());
				String newsCategoryId = String.valueOf(req.getParameter("newsCategoryId").trim());
				String adminId = "A001"; 
				String newsContent = String.valueOf(req.getParameter("newsContent").trim()); 
				Date newsStartDate  = java.sql.Date.valueOf(req.getParameter("newsStartDate").trim()); 
				Date newsEndDate  = java.sql.Date.valueOf(req.getParameter("newsEndDate").trim());  
				Integer newsStatusId = Integer.valueOf(req.getParameter("newsStatusId").trim());
				String newsImg = String.valueOf(req.getParameter("newsImg").trim()); 
								
				String newsTitleReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (newsTitle == null || newsTitle.trim().length() == 0) {
					errorMsgs.put("newsTitle","消息標題: 請勿空白");
				} else if(!newsTitle.trim().matches(newsTitleReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("newsTitle","消息標題: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
				
				if (newsCategoryId == null || newsCategoryId.trim().length() == 0) {
					errorMsgs.put("newsCategoryId","消息類別請勿空白");
				}
				
		
				if (adminId == null) {
					errorMsgs.put("adminId","管理員ID請勿空白");
				}
			
				String newsReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,100}$";
				if (newsContent == null || newsContent.trim().length() == 0) {
					errorMsgs.put("newsTitle","消息內容: 請勿空白");
				} else if(!newsContent.trim().matches(newsReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("newsTitle","消息內容: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
			
			
				
				try {
					newsStartDate = java.sql.Date.valueOf(req.getParameter("newsStartDate").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.put("newsStartDate","請輸入消息開始日期");
				}
				Timestamp newsStartts = new Timestamp(newsStartDate.getTime());
						
				try {
					newsEndDate = java.sql.Date.valueOf(req.getParameter("newsEndDate").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.put("newsEndDate","請輸入消息結束日期");
				}
				Timestamp newsEndts = new Timestamp(newsEndDate.getTime());
				
				if (newsStatusId == null) {
					errorMsgs.put("newsStatusId","消息狀態請勿空白");
				}
				
				
				if (newsImg == null || newsImg.trim().length() == 0) {
					errorMsgs.put("newsImg","圖片請勿空白");
				}
				
			
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/news/news_update.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.updateNews(newsId, newsTitle, newsCategoryId, adminId,  newsContent, newsStartts,
						newsEndts, newsStatusId,newsImg);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				//req.setAttribute("newsVO", newsVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = req.getContextPath() + "/front_end/news/news_list2.jsp";
				res.sendRedirect(url); // 使用重定向
//				
//				String url = "/front_end/news/news_list2.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
//				successView.forward(req, res);
		}

        if ("insert".equals(action)) { // 來自addNews.jsp的請求  
			
        	Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				
				String newsTitle = String.valueOf(req.getParameter("newsTitle").trim());
				String newsCategoryId = String.valueOf(req.getParameter("newsCategoryId").trim());
				String adminId = "A001"; 
				String newsContent = String.valueOf(req.getParameter("newsContent").trim()); 
				Date newsStartDate  = java.sql.Date.valueOf(req.getParameter("newsStartDate").trim()); 
				Date newsEndDate  = java.sql.Date.valueOf(req.getParameter("newsEndDate").trim());  
				Integer newsStatusId = Integer.valueOf(req.getParameter("newsStatusId").trim());
				String newsImg = String.valueOf(req.getParameter("newsImg").trim()); 
								
				String newsTitleReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (newsTitle == null || newsTitle.trim().length() == 0) {
					errorMsgs.put("newsTitle","消息標題: 請勿空白");
				} else if(!newsTitle.trim().matches(newsTitleReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("newsTitle","消息標題: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
				
				if (newsCategoryId == null || newsCategoryId.trim().length() == 0) {
					errorMsgs.put("newsCategoryId","消息類別請勿空白");
				}
				
		
				if (adminId == null) {
					errorMsgs.put("adminId","管理員ID請勿空白");
				}
			
				String newsReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,100}$";
				if (newsContent == null || newsContent.trim().length() == 0) {
					errorMsgs.put("newsTitle","消息內容: 請勿空白");
				} else if(!newsContent.trim().matches(newsReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("newsTitle","消息內容: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
			
			
				
				try {
					newsStartDate = java.sql.Date.valueOf(req.getParameter("newsStartDate").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.put("newsStartDate","請輸入消息開始日期");
				}
				Timestamp newsStartts = new Timestamp(newsStartDate.getTime());
						
				try {
					newsEndDate = java.sql.Date.valueOf(req.getParameter("newsEndDate").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.put("newsEndDate","請輸入消息結束日期");
				}
				Timestamp newsEndts = new Timestamp(newsEndDate.getTime());
				
				if (newsStatusId == null) {
					errorMsgs.put("newsStatusId","消息狀態請勿空白");
				}
				
				
				if (newsImg == null || newsImg.trim().length() == 0) {
					errorMsgs.put("newsImg","圖片請勿空白");
				}
				
			
				// Send the use back to the form, if there were errors
				String url_add = req.getContextPath() + "/front_end/news/news_add.jsp";
				if (!errorMsgs.isEmpty()) {
					
				       req.setAttribute("newsContent", newsContent);
				       req.setAttribute("newsImg", newsImg);
				        // ... 其他输入值得保存 ...
//
				       RequestDispatcher failureView = req
				                .getRequestDispatcher(url_add);
				       failureView.forward(req, res);
				        
//					req.setAttribute("newsContent", newsContent);
//					req.setAttribute("newsImg", newsImg);
//					    // 回傳錯誤訊息
//					req.setAttribute("errorMsgs", errorMsgs);
//					RequestDispatcher failureView = req
//							.getRequestDispatcher(url_add);
//					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始新增資料*****************************************/
				NewsService newsSvc = new NewsService();
				try {
					NewsVO newsVO = newsSvc.addNews( newsTitle, newsCategoryId, adminId,  newsContent, newsStartts,
							newsEndts, newsStatusId,newsImg);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				/***************************3.新增完成,準備轉交(Send the Success view)*************/
				//req.setAttribute("newsVO", newsVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url_backlist = req.getContextPath() + "/front_end/news/news_list2.jsp";
				res.sendRedirect(url_backlist); // 使用重定向			
		}
		
		
		if ("delete".equals(action)) { 

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
				/***************************1.接收請求參數***************************************/
				String newsId = String.valueOf(req.getParameter("newsId"));
				
				/***************************2.開始刪除資料***************************************/
				NewsService newsSvc = new NewsService();
				newsSvc.deleteNews(newsId);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = req.getContextPath() + "/front_end/news/news_list2.jsp";
				res.sendRedirect(url); // 使用重定向
				
//				String url = "/emp/listAllEmp.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
		}
	}
}
