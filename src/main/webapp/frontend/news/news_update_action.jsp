<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="news.NewsService" %>
<%@ page import="news.NewsVO" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.io.File" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="java.util.List" %>

<%
    request.setCharacterEncoding("UTF-8");

    if (ServletFileUpload.isMultipartContent(request)) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            List<FileItem> items = upload.parseRequest(request);
            NewsVO news = new NewsVO();
            String uploadPath = application.getRealPath("/images/news"); // 圖片上傳路徑
            String fileName = null;

            for (FileItem item : items) {
                if (item.isFormField()) {
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString("UTF-8");

                    switch (fieldName) {
                        case "newsId":
                            news.setNewsId(Integer.parseInt(fieldValue));
                            break;
                        case "newsTitle":
                            news.setNewsTitle(fieldValue);
                            break;
                        case "newsContent":
                            news.setNewsContent(fieldValue);
                            break;
                        case "newsCategory":
                            news.setNewsCategoryId(Integer.parseInt(fieldValue));
                            break;
                        case "newsStartDate":
                            news.setNewsStartDate(Date.valueOf(fieldValue));
                            break;
                        case "newsEndDate":
                            news.setNewsEndDate(Date.valueOf(fieldValue));
                            break;
                        case "newsStatus":
                            news.setNewsStatusId(Integer.parseInt(fieldValue));
                            break;
                    }
                } else {
                    fileName = item.getName();
                    if (fileName != null && !fileName.isEmpty()) {
                        File uploadedFile = new File(uploadPath, fileName);
                        item.write(uploadedFile);
                        news.setNewsImg("images/news/" + fileName); // 設定圖片路徑
                    }
                }
            }

            NewsService newsSvc = new NewsService();
            newsSvc.updateNews(news);

            response.sendRedirect("news_list.jsp"); // 重定向到新聞列表頁面
        } catch (Exception e) {
            e.printStackTrace();
            // 處理異常
        }
    }
%>