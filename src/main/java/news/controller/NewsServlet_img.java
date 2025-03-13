package news.controller;

import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import news.model.NewsService;
import news.model.NewsVO;

@WebServlet("/uploadNewsServlet2.do")
@MultipartConfig
public class NewsServlet_img extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		  if ("list_pagination".equals(action)) {
	            String title = req.getParameter("title");
	            String categoryIdStr = req.getParameter("categoryId");
	            String statusIdStr = req.getParameter("statusId");

	            Integer categoryId = null;
	            if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
	                categoryId = Integer.parseInt(categoryIdStr);
	            }

	            Integer statusId = null;
	            if (statusIdStr != null && !statusIdStr.isEmpty()) {
	                statusId = Integer.parseInt(statusIdStr);
	            }

	            int page = 1;
	            if (req.getParameter("page") != null) {
	                page = Integer.parseInt(req.getParameter("page"));
	            }
	            int recordsPerPage = 5;

	            NewsService newsService = new NewsService();
	            List<NewsVO> list = newsService.getNewsByPage(title, categoryId, statusId, page, recordsPerPage);
	            int totalRecords = newsService.getNewsCount(title, categoryId, statusId);

	            int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

	            req.setAttribute("newsList", list);
	            req.setAttribute("currentPage", page);
	            req.setAttribute("totalPages", totalPages);
	            req.setAttribute("title", title); 
	            req.setAttribute("categoryId", categoryId); 
	            req.setAttribute("statusId", statusId);

	            req.getRequestDispatcher("news_list_img4.jsp").forward(req, res);
	            return; 
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
			
			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//			
			//Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
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
			
				Part filePart = req.getPart("newsImg"); // "newsImg" 是 input type="file" 的 name 屬性

				
				byte[] newsImg = null;
				
				if (filePart != null && filePart.getSize() > 0) {
				    InputStream inputStream = filePart.getInputStream();
				    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				    byte[] buffer = new byte[4096];
				    int bytesRead;

				    while ((bytesRead = inputStream.read(buffer)) != -1) {
				        outputStream.write(buffer, 0, bytesRead);
				    }

				   newsImg = outputStream.toByteArray();

				    // 現在，newsImg 包含了上傳圖片的二進制資料，你可以將它儲存到資料庫或其他地方
				    // ... 後續處理 ...
				} else {
				    // 沒有選擇圖片檔案
				}
				
				String newsTitleReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (newsTitle == null || newsTitle.trim().length() == 0) {
					errorMsgs.add("消息標題: 請勿空白");
				} else if(!newsTitle.trim().matches(newsTitleReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("消息標題: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
				
				if (newsCategoryId == null || newsCategoryId.trim().length() == 0) {
					errorMsgs.add("消息類別請勿空白");
				}
				
		
				if (adminId == null) {
					errorMsgs.add("管理員ID請勿空白");
				}
			
				String newsReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,100}$";
				if (newsContent == null || newsContent.trim().length() == 0) {
					errorMsgs.add("消息內容: 請勿空白");
				} else if(!newsContent.trim().matches(newsReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("消息內容: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
			
			
				
				try {
					newsStartDate = java.sql.Date.valueOf(req.getParameter("newsStartDate").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入消息開始日期");
				}
				Timestamp newsStartts = new Timestamp(newsStartDate.getTime());
						
				try {
					newsEndDate = java.sql.Date.valueOf(req.getParameter("newsEndDate").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入消息結束日期");
				}
				Timestamp newsEndts = new Timestamp(newsEndDate.getTime());
				
				if (newsStatusId == null) {
					errorMsgs.add("消息狀態請勿空白");
				}
				
				NewsVO testnewsVO = new NewsVO();
				Timestamp currentTime = new Timestamp(System.currentTimeMillis()); 
				
				testnewsVO.setNewsId(newsId);
				testnewsVO.setNewsTitle(newsTitle);
				testnewsVO.setNewsContent(newsContent);
				testnewsVO.setNewsStartDate(newsStartts);
				testnewsVO.setNewsEndDate(newsEndts);
				testnewsVO.setNewsImg(newsImg);
				testnewsVO.setCreatedTime(currentTime);
				testnewsVO.setNewsCategoryId(newsCategoryId);
				testnewsVO.setAdminId(adminId);
				testnewsVO.setNewsStatusId(newsStatusId);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					
//					String url = req.getContextPath() + "/front_end/news/news_update_img.jsp?newsId=" + newsId;
//					res.sendRedirect(url);
					req.setAttribute("newsVO", testnewsVO); // 含有輸入格式錯誤的empVO物件,也存入req
					req.setAttribute("newsId", newsId);
					System.out.println("含有輸入格式錯誤的newsVO物件訊息:"+errorMsgs);
					System.out.println("含有輸入格式錯誤的newsId物件:"+newsId);
					
					HttpSession session = req.getSession();
					//取得session物件
					session.setAttribute("newsVO", testnewsVO);
					session.setAttribute("newsId", newsId);
					session.setAttribute("errorMsgs", errorMsgs);
		
					String url = req.getContextPath() + "/backend/news/news_update_img.jsp";
					res.sendRedirect(url); // 使用重定向
//					RequestDispatcher failureView = req.getRequestDispatcher(req.getContextPath() + "/front_end/news/news_update_img.jsp");
//					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				NewsService newsSvc = new NewsService();
				
				NewsVO newsVO = newsSvc.updateNews(newsId, newsTitle, newsCategoryId, adminId,  newsContent, newsStartts,
						newsEndts, newsStatusId,newsImg);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				//req.setAttribute("newsVO", newsVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = req.getContextPath() + "/backend/news/news_list_img4.jsp";
				res.sendRedirect(url); // 使用重定向
//				
//				String url = "/front_end/news/news_list2.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
//				successView.forward(req, res);
		}

        if ("insert".equals(action)) { // 來自addNews.jsp的請求  
			
        	List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				
				String newsTitle = String.valueOf(req.getParameter("newsTitle").trim());
				String newsCategoryId = String.valueOf(req.getParameter("newsCategoryId").trim());
				String adminId = "A001"; 
				String newsContent = String.valueOf(req.getParameter("newsContent").trim()); 
				Date newsStartDate  = java.sql.Date.valueOf(req.getParameter("newsStartDate").trim()); 
				Date newsEndDate  = java.sql.Date.valueOf(req.getParameter("newsEndDate").trim());  
				Integer newsStatusId = Integer.valueOf(req.getParameter("newsStatusId").trim());
				
				Part filePart = req.getPart("newsImg"); // "newsImg" 是 input type="file" 的 name 屬性

				
				byte[] newsImg2 = null;
				
				if (filePart != null && filePart.getSize() > 0) {
				    InputStream inputStream = filePart.getInputStream();
				    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				    byte[] buffer = new byte[4096];
				    int bytesRead;

				    while ((bytesRead = inputStream.read(buffer)) != -1) {
				        outputStream.write(buffer, 0, bytesRead);
				    }

				   newsImg2 = outputStream.toByteArray();

				    // 現在，newsImg 包含了上傳圖片的二進制資料，你可以將它儲存到資料庫或其他地方
				    // ... 後續處理 ...
				} else {
				    // 沒有選擇圖片檔案
				}
				
				
				String newsTitleReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (newsTitle == null || newsTitle.trim().length() == 0) {
					errorMsgs.add("消息標題: 請勿空白");
				} else if(!newsTitle.trim().matches(newsTitleReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("消息標題: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
				
				if (newsCategoryId == null || newsCategoryId.trim().length() == 0) {
					errorMsgs.add("消息類別請勿空白");
				}
				
		
				if (adminId == null) {
					errorMsgs.add("管理員ID請勿空白");
				}
			
				String newsReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,100}$";
				if (newsContent == null || newsContent.trim().length() == 0) {
					errorMsgs.add("消息內容: 請勿空白");
				} else if(!newsContent.trim().matches(newsReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("消息內容: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
			
			
				
				try {
					newsStartDate = java.sql.Date.valueOf(req.getParameter("newsStartDate").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入消息開始日期");
				}
				Timestamp newsStartts = new Timestamp(newsStartDate.getTime());
						
				try {
					newsEndDate = java.sql.Date.valueOf(req.getParameter("newsEndDate").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入消息結束日期");
				}
				Timestamp newsEndts = new Timestamp(newsEndDate.getTime());
				
				if (newsStatusId == null) {
					errorMsgs.add("消息狀態請勿空白");
				}
				
				
				NewsVO testnewsVO = new NewsVO();
				Timestamp currentTime = new Timestamp(System.currentTimeMillis()); 
				
				testnewsVO.setNewsTitle(newsTitle);
				testnewsVO.setNewsContent(newsContent);
				testnewsVO.setNewsStartDate(newsStartts);
				testnewsVO.setNewsEndDate(newsEndts);
				testnewsVO.setNewsImg(newsImg2);
				testnewsVO.setCreatedTime(currentTime);
				testnewsVO.setNewsCategoryId(newsCategoryId);
				testnewsVO.setAdminId(adminId);
				testnewsVO.setNewsStatusId(newsStatusId);
				
				HttpSession session = req.getSession();
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					
//					String url = req.getContextPath() + "/front_end/news/news_update_img.jsp?newsId=" + newsId;
//					res.sendRedirect(url);
					req.setAttribute("newsVO", testnewsVO); // 含有輸入格式錯誤的empVO物件,也存入req
					System.out.println("含有輸入格式錯誤的newsVO物件訊息:"+errorMsgs);
					
					
					//取得session物件
					session.setAttribute("newsVO", testnewsVO);
					session.setAttribute("errorMsgs", errorMsgs);
		
					String url = req.getContextPath() + "/backend/news/news_add_img.jsp";
					res.sendRedirect(url); // 使用重定向
//					RequestDispatcher failureView = req.getRequestDispatcher(req.getContextPath() + "/front_end/news/news_update_img.jsp");
//					failureView.forward(req, res);
					return; //程式中斷
				}
				
			
				/***************************2.開始新增資料*****************************************/
				NewsService newsSvc = new NewsService();
				try {
					NewsVO newsVO = newsSvc.addNews( newsTitle, newsCategoryId, adminId,  newsContent, newsStartts,
							newsEndts, newsStatusId, newsImg2);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				/***************************3.新增完成,準備轉交(Send the Success view)*************/
				//req.setAttribute("newsVO", newsVO); // 資料庫update成功後,正確的的empVO物件,存入req
				 session.invalidate(); 
				String url_backlist = req.getContextPath() + "/backend/news/news_list_img4.jsp";
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
				String url = req.getContextPath() + "/backend/news/news_list_img4.jsp";
				res.sendRedirect(url); // 使用重定向
				
//				String url = "/emp/listAllEmp.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
		}
	}
}
