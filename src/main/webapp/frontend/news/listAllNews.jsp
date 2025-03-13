<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="news.NewsVO"%>
<%@ page import="news.NewsService"%>
<%@ page import="news.NewsDAO"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
    NewsService newsSvc = new NewsService();
    List<NewsVO> list = newsSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>所有新聞狀態資料 - listAllNews.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有新聞資料 - listAllNews.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="<%=request.getContextPath()%>/resources/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>消息ID</th>
		<th>消息標題</th>
		<th>消息種類ID</th>
		<th>消息內容</th>
		<th>消息圖片</th>
		<th>消息起始日期</th>
		<th>消息結束日期</th>
		<th>消息創建日期</th>
		<th>消息狀態ID</th>
		<th>管理員ID</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="newsVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${newsVO.newsId}</td>
			<td>${newsVO.newsTitle}</td>
			<td>${newsVO.newsCategoryId}</td>
			<td>${newsVO.newsContent}</td>
			<td>${newsVO.newsImg}</td>
			<td>${newsVO.newsStartDate}</td>
			<td>${newsVO.newsEndDate}</td>
			<td>${newsVO.createdTime}</td>
			<td>${newsVO.newsStatusId}</td>
			<td>${newsVO.adminId}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/news/uploadNewsServlet.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="newsId"  value="${newsVO.newsId}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/news/uploadNewsServlet.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="newsId"  value="${newsVO.newsId}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>