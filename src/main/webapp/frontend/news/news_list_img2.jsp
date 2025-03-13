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
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>最新消息一覽 (分頁)</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
</head>
<body>
	<div class="container-xxl bg-white p-0">
		<section class="py-5">
			<div class="container">
				<div class="row gx-5">
					<div class="container mt-5">
						<h2>最新消息一覽</h2>

						<form
							action="<span class="math-inline">\{pageContext\.request\.contextPath\}/uploadNewsServlet2\.do"
							method\="post" class\="row mb\-3"\>
							<input type\="hidden" name\="action" value\="list\_pagination"\>
							<div class\="col\-md\-3"\>
								<input type\="text" name\="title" class\="form\-control" placeholder\="搜尋消息標題"
									value\="</span>{title}">
							</div>
							<div class="col-md-3">
								<select name="categoryId" class="form-select">
									<option value="">所有消息種類</option>
									<c:forEach var="category" items="<%=categoryList%>">
										<option value="${category.newsCategoryId}"
											${categoryId == category.newsCategoryId ? 'selected' : ''}>
											<span class="math-inline">\{category\.newsCategoryName\} </option\>
										</c\:forEach\>
								</select\>
							</div\>
							<div class\="col\-md\-3"\>
								<select name\="statusId" class\="form\-select"\>
									<option value\=""\>所有消息狀態</option\>
									<c \:forEach var\="status"
										items\="<%\=statusList%\>"\>
<option value\="</span>{status.newsStatusId}" ${statusId == status.newsStatusId ? 'selected' : ''}>
                                            <span class="math-inline">\{status\.newsStatusName\}
</option\>
</c\:forEach\>
</select\>
</div\>
<div class\="col\-md\-3"\>
<button type\="submit" class\="btn btn\-primary"\>搜尋</button\>
</div\>
</form\>
<table class\="table table\-striped" id\="newsTable"\>
<thead\>
<tr\>
<th\>\#</th\>
<th\>消息標題</th\>
<th\>消息內容</th\>
<th\>消息種類</th\>
<th\>消息起始日</th\>
<th\>消息結束日</th\>
<th\>管理員ID</th\>
<th\>消息狀態</th\>
<th\>消息圖片</th\>
<th\>操作</th\>
</tr\>
</thead\>
<tbody id\="productTableBody"\>
<c\:forEach var\="news" items\="</span>{newsList}">
                                    <tr>
                                        <td><span class="math-inline">\{news\.newsId\}</td\>
<td\></span>{news.newsTitle}</td>
                                        <td><span class="math-inline">\{news\.newsContent\}</td\>
<td\>
<%
NewsCategoryService categorySvc \= new NewsCategoryService\(\);
NewsCategoryVO category \= categorySvc\.getOneNewsCategory\(Integer\.parseInt\(news\.newsCategoryId\)\);
String categoryName \= \(category \!\= null\) ? category\.getNewsCategoryName\(\) \: "未知種類";
%\>
<%\=categoryName%\>
</td\>
<td\></span>{news.newsStartDate}</td>
                                        <td><span class="math-inline">\{news\.newsEndDate\}</td\>
<td\></span>{news.adminId}</td>
                                        <td>
                                            <%
                                                NewsStatusService statusSvc = new NewsStatusService();
                                                NewsStatusVO status = statusSvc.getOneNewsStatus(news.newsStatusId);
                                                String statusName = (status != null) ? status.getNewsStatusName() : "未知狀態";
                                            %>
                                            <%=statusName%>
                                        </td>
                                        <td>
                                            <img src="<span class="math-inline">\{pageContext\.request\.contextPath\}/PhotoReader2?newsId\=</span>{news.newsId}" alt="圖片" width="200" height="150">
                                        </td>
                                        <td>
                                            <a href="news_update_img.jsp?newsId=<span class="math-inline">\{news\.newsId\}" class\="btnbtn\-primarybtn\-sm"\>修改</a\>
									<button onclick\="confirmDelete\('</span>{news.newsId}')" class="btn btn-danger btn-sm">刪除</button>
									</td>
									</tr>
									</c:forEach>
									</tbody>
									</table>

									<nav aria-label="Page navigation">
										<ul class="pagination justify-content-center" id="pagination">
											<c:if test="<span class="math-inline">\{currentPage \> 1\}"\>
												<li class\="page\-item"\>
													<a class\="page\-link"
														href\="</span>{pageContext.request.contextPath}/uploadNewsServlet2.do?action=list_pagination&page=<span class="math-inline">\{currentPage \- 1\}&title\=</span>{title}&categoryId=<span class="math-inline">\{categoryId\}&statusId\=</span>{statusId}"
														aria-label="Previous">
														<span aria-hidden="true">&laquo;</span>
													</a>
												</li>
											</c:if>
											<c:forEach var="i" begin="1" end="${totalPages}">
												<li class="page-item <span class="math-inline">\{currentPage \=\= i ? 'active' \: ''\}"\>
													<a class\="page\-link"
														href\="</span>{pageContext.request.contextPath}/uploadNewsServlet2.do?action=list_pagination&page=<span class="math-inline">\{i\}&title\=</span>{title}&categoryId=<span class="math-inline">\{categoryId\}&statusId\=</span>{statusId}">
														<span class="math-inline">\{i\} 
													</a\>
												</li\>
									</c\:forEach\>
									<c \:if test\="</span>{currentPage < totalPages}">
									<li class="page-item">
										<a class="page-link"
											href="<span class="math-inline">\{pageContext\.request\.contextPath\}/uploadNewsServlet2\.do?action\=list\_pagination&page\=</span>{currentPage + 1}&title=<span class="math-inline">\{title\}&categoryId\=</span>{categoryId}&statusId=<span class="math-inline">\{statusId\}"
											aria\-label\="Next"\>
											<span aria\-hidden\="true"\>&raquo;</span\>
										</a>
									</li>
									</c:if>
									</ul>
									</nav>
							</div>
					</div>
				</div>
		</section>
		<div class="container-fluid bg-light text-black-50 footer pt-5 mt-5 wow fadeIn"
			data-wow-delay="0.1s"></div>
		<a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top">
			<i class="bi bi-arrow-up"></i>
		</a>
	</div>
	<script src\="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="lib/wow/wow.min.js"></script>
	<script src="lib/easing/easing\.min\.js"></script>
	<script src="lib/waypoints/waypoints\.min\.js"></script>
	<script src="lib/owlcarousel/owl.carousel.min.js"></script>
	<script src="js/main.js"></script>
	<script>
		function confirmDelete(newsId) {
		if (confirm("確定刪除這則消息嗎？")) {
		const form = document.createElement("form");
			form.setAttribute("method", "post");
			form.setAttribute("action", "{pageContext.request.contextPath}/uploadNewsServlet2.do");

         const actionInput = document.createElement("input");
               actionInput.setAttribute("type", "hidden);
               actionInput.setAttribute("name", "action");
               actionInput.setAttribute("value", "delete");

                const newsIdInput = document.createElement("input");
                newsIdInput.setAttribute("type", "hidden");
                newsIdInput.setAttribute("name", "newsId");
                newsIdInput.setAttribute("value", newsId);

                form.appendChild(actionInput);
                form.appendChild(newsIdInput);
                document.body.appendChild(form);
                form.submit();
            }
        }
    </script>
</body>
</html>