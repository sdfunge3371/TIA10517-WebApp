<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="news.NewsService"%>
<%@ page import="news.NewsVO"%>
<%@ page import="java.util.List"%>
<%@ page import="news_category.NewsCategoryService"%>
<%@ page import="news_category.NewsCategoryVO"%>
<%@ page import="news_status.NewsStatusService"%>
<%@ page import="news_status.NewsStatusVO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    NewsCategoryService newsCategorySvc = new NewsCategoryService();
    List<NewsCategoryVO> categoryList = newsCategorySvc.getAll();

    NewsStatusService newsStatusSvc = new NewsStatusService();
    List<NewsStatusVO> statusList = newsStatusSvc.getAll();

    int currentPage = 1;
    int totalPages = 1;
    List<NewsVO> newsList = null;
    String title = "";
    Integer categoryId = null;
    Integer statusId = null;

    if (request.getAttribute("currentPage") != null) {
        currentPage = (Integer) request.getAttribute("currentPage");
        totalPages = (Integer) request.getAttribute("totalPages");
        newsList = (List<NewsVO>) request.getAttribute("newsList");
        title = (String) request.getAttribute("title");
        categoryId = (Integer) request.getAttribute("categoryId");
        statusId = (Integer) request.getAttribute("statusId");
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Makaan - Real Estate HTML Template</title>
    <meta content="" title="keywords">
    <meta content="" title="description">
    <meta title="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="img/favicon.ico" rel="icon">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container-xxl bg-white p-0">
        <section class="py-5">
            <div class="container">
                <div class="row gx-5">
                    <div class="container mt-5">
                        <h2>最新消息一覽</h2>
                        <form action="${pageContext.request.contextPath}/uploadNewsServlet2.do?action=list_pagination" method="post">
                            <div class="row mb-3">
                                <div class="col-md-4">
                                    <input type="text" name="title" value="<%= (title != null) ? title : "" %>" id="searchInput" class="form-control" placeholder="搜尋消息標題">
                                </div>
                                <div class="col-md-4">
                                    <select name="categoryId" id="categoryFilter" class="form-select">
                                        <option value="">所有消息種類</option>
                                        <% for (NewsCategoryVO category : categoryList) { %>
                                            <option value="<%=category.getNewsCategoryId()%>" <%= (categoryId != null && categoryId.equals(category.getNewsCategoryId())) ? "selected" : "" %>><%=category.getNewsCategoryName()%></option>
                                        <% } %>
                                    </select>
                                </div>
                                <div class="col-md-4">
                                    <select name="statusId" id="statusFilter" class="form-select">
                                        <option value="">所有消息狀態</option>
                                        <% for (NewsStatusVO status : statusList) { %>
                                            <option value="<%=status.getNewsStatusId()%>" <%= (statusId != null && statusId.equals(status.getNewsStatusId())) ? "selected" : "" %>><%=status.getCategoryName()%></option>
                                        <% } %>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <button type="submit" id="searchButton" class="btn btn-primary">搜尋</button>
                                </div>
                            </div>
                        </form>

                        <% if (newsList != null) { %>
                            <b><font color=red>第<%=currentPage%>/<%=totalPages%>頁</font></b>
                            <table class="table table-striped" id="newsTable">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>消息標題</th>
                                        <th>消息內容</th>
                                        <th>消息種類</th>
                                        <th>消息起始日</th>
                                        <th>消息結束日</th>
                                        <th>管理員ID</th>
                                        <th>消息狀態</th>
                                        <th>消息圖片</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody id="productTableBody">
                                    <% for (NewsVO news : newsList) { %>
                                        <tr>
                                            <td><%=news.getNewsId()%></td>
                                            <td><%=news.getNewsTitle()%></td>
                                            <td><%=news.getNewsContent()%></td>
                                            <td><%=news.getNewsCategoryId()%></td>
                                            <td><%=news.getNewsStartDate()%></td>
                                            <td><%=news.getNewsEndDate()%></td>
                                            <td><%=news.getAdminId()%></td>
                                            <td><%=news.getNewsStatusId()%></td>
                                            <td>
                                                <img src="${pageContext.request.contextPath}/PhotoReader2?newsId=<%=news.getNewsId()%>" alt="圖片" width="200" height="150">
                                            </td>
                                            <td>
                                                <a href="news_update_img.jsp?newsId=<%=news.getNewsId()%>" class="btn btn-primary btn-sm">修改</a>
                                                <button onclick="confirmDelete('<%=news.getNewsId()%>')" class="btn btn-danger btn-sm">刪除</button>
                                            </td>
                                        </tr>
                                    <% } %>
                                </tbody>
                            </table>

                            <%-- 分頁導航 --%>
                            <nav aria-label="Page navigation">
                                <ul class="pagination justify-content-center" id="pagination">
                                    <% if (currentPage > 1) { %>
                                        <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/uploadNewsServlet2.do?action=list_pagination&page=<%=currentPage - 1%>&title=<%=title%>&categoryId=<%=categoryId != null ? categoryId : ""%>&statusId=<%=statusId != null ? statusId : ""%>">上一頁</a></li>
                                    <% } %>
                                    <% for (int i = 1; i <= totalPages; i++) { %>
                                        <li class="page-item <%= (i == currentPage) ? "active" : "" %>"><a class="page-link" href="/uploadNewsServlet2.do?action=list_pagination&page=<%=i%>&title=<%=title%>&categoryId=<%=categoryId != null ? categoryId : ""%>&statusId=<%=statusId != null ? statusId : ""%>"><%=i%></a></li>
                                    <% } %>
                                    <% if (currentPage < totalPages) { %>
                                        <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/uploadNewsServlet2.do?action=list_pagination&page=<%=currentPage + 1%>&title=<%=title%>&categoryId=<%=categoryId != null ? categoryId : ""%>&statusId=<%=statusId != null ? statusId : ""%>">下一頁</a></li>
                                    <% } %>
                                </ul>
                            </nav>
                        <% } %>
                    </div>
                </div>
            </div>
        </section>
    </div>
</body>
</html>