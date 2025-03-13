<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<%@ page import="news.model.NewsService"%>

<%@ page import="news.model.NewsVO"%>

<%@ page import="java.util.List"%>

<%@ page import="news_category.NewsCategoryService"%>

<%@ page import="news_category.NewsCategoryVO"%>

<%@ page import="news_status.NewsStatusService"%>

<%@ page import="news_status.NewsStatusVO"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<%
NewsService newsSvc = new NewsService();

List<NewsVO> list = newsSvc.getAll();

NewsCategoryService newsCategorySvc = new NewsCategoryService();

List<NewsCategoryVO> categoryList = newsCategorySvc.getAll();

NewsStatusService newsStatusSvc = new NewsStatusService();

List<NewsStatusVO> statusList = newsStatusSvc.getAll();
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



<link rel="preconnect" href="https://fonts.googleapis.com">



<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>



<link
	href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Inter:wght@700;800&display=swap"
	rel="stylesheet">



<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css"
	rel="stylesheet">



<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
	rel="stylesheet">



<link href="lib/animate/animate.min.css" rel="stylesheet">



<link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">



<link href="css/bootstrap.min.css" rel="stylesheet">



<link href="css/style.css" rel="stylesheet">



<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">



</head>







<body>



	<div class="container-xxl bg-white p-0">



		<div id="spinner"
			class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">



			<div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">



				<span class="sr-only">Loading...</span>



			</div>



		</div>







		<div class="container-fluid nav-bar bg-transparent">



			<nav class="navbar navbar-expand-lg bg-white navbar-light py-0 px-4">



				<a href="index.html" class="navbar-brand d-flex align-items-center text-center">



					<div class="icon p-2 me-2">



						<img class="img-fluid" src="img/LifeSpace.png" alt="Icon" style="width: 30px; height: 30px;">



					</div>

					<img class="img-fluid" src="img/LifeSpace2.png" alt="Icon" style="width: 200px; height: 60px;">



				</a>



				<button type="button" class="navbar-toggler" data-bs-toggle="collapse"
					data-bs-target="#navbarCollapse">



					<span class="navbar-toggler-icon"></span>



				</button>



				<div class="collapse navbar-collapse" id="navbarCollapse">



					<div class="navbar-nav ms-auto">



						<a href="index.html" class="nav-item nav-link active">Home</a>

						<a href="" class="nav-item nav-link">瀏覽空間</a>



						<div class="nav-item dropdown">



							<a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">客服中心</a>



							<div class="dropdown-menu rounded-0 m-0">



								<a href="" class="dropdown-item">常見問題</a>

								<a href="" class="dropdown-item">線上客服</a>



							</div>



						</div>



						<div class="nav-item dropdown">



							<a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">參與活動</a>



							<div class="dropdown-menu rounded-0 m-0">



								<a href="testimonial.html" class="dropdown-item">Testimonial</a>



								<a href="404.html" class="dropdown-item">404 Error</a>



							</div>



						</div>



						<a href="" class="nav-item nav-link">我的最愛</a>



					</div>



					<div class="nav-item dropdown">



						<a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">

							<i class="bi bi-person-circle">會員資料</i>

						</a>



						<div class="dropdown-menu rounded-0 m-0">



							<a href="" class="dropdown-item">我的帳號</a>

							<a href="" class="dropdown-item">訂單</a>

							<a href="" class="dropdown-item">我參與的活動</a>



							<a href="" class="dropdown-item">優惠券管理</a>

							<a href="" class="dropdown-item">我的最愛</a>

							<a href="" class="dropdown-item">常見問題</a>



							<a href="property-agent.html" class="dropdown-item">登出</a>



						</div>



					</div>



					</a>



				</div>



			</nav>



		</div>





		<section class="py-5">

			<div class="container">

				<div class="row gx-5">

					<div class="container mt-5">

						<h2>最新消息一覽</h2>



						<div class="row mb-3">

							<div class="col-md-4">

								<input type="text" id="searchInput" class="form-control" placeholder="搜尋消息標題">

							</div>

							<div class="col-md-4">

								<select id="categoryFilter" class="form-select">

									<option value="">所有消息種類</option>

									<%
									for (NewsCategoryVO category : categoryList) {
									%>

									<option value="<%=category.getNewsCategoryId()%>"><%=category.getNewsCategoryName()%></option>

									<%
									}
									%>

								</select>

							</div>

							<div class="col-md-4">

								<select id="statusFilter" class="form-select">

									<option value="">所有消息狀態</option>

									<%
									for (NewsStatusVO status : statusList) {
									%>

									<option value="<%=status.getNewsStatusId()%>"><%=status.getCategoryName()%></option>

									<%
									}
									%>

								</select>

							</div>

							<div class="col-md-3">

								<button type="button" id="searchButton" class="btn btn-primary">搜尋</button>

							</div>

						</div>



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

								<%
								for (NewsVO news : list) {
								%>

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

										<img src="${pageContext.request.contextPath}/news/controller/PhotoReader2?newsId=<%=news.getNewsId()%>"
											alt="圖片" width="200" height="150">

									</td>

									<td>

										<a href="news_update_img.jsp?newsId=<%=news.getNewsId()%>" class="btn btn-primary btn-sm">修改</a>

										<button onclick="confirmDelete('<%=news.getNewsId()%>')" class="btn btn-danger btn-sm">刪除</button>

									</td>

								</tr>

								<%
								}
								%>

							</tbody>

						</table>
						<div id="pageInfo" class="mt-3"></div>
						<nav aria-label="Page navigation">
							<ul class="pagination justify-content-center" id="pagination"></ul>
						</nav>

					</div>

				</div>

			</div>

		</section>





		<div class="container-fluid bg-light text-black-50 footer pt-5 mt-5 wow fadeIn"
			data-wow-delay="0.1s">

			<div class="container py-5">

				<div class="row g-5">

					<div class="col-lg-3 col-md-6">

						<h5 class="text-black mb-4">

							<img class="img-fluid" src="img/LifeSpace3.png" alt="Icon"
								style="width: 250px; height: 60px;">

						</h5>

						<p class="mb-2">

							<i class="fa fa-map-marker-alt me-3"></i>

							123 Street, New York, USA

						</p>

						<p class="mb-2">

							<i class="fa fa-phone-alt me-3"></i>

							+012 345 67890

						</p>

						<p class="mb-2">

							<i class="fa fa-envelope me-3"></i>

							info@example.com

						</p>

						<div class="d-flex pt-2">

							<a class="btn btn-outline-light btn-social" href="">

								<i class="fab fa-twitter"></i>

							</a>

							<a class="btn btn-outline-light btn-social" href="">

								<i class="fab fa-facebook-f"></i>

							</a>

							<a class="btn btn-outline-light btn-social" href="">

								<i class="fab fa-youtube"></i>

							</a>

							<a class="btn btn-outline-light btn-social" href="">

								<i class="fab fa-linkedin-in"></i>

							</a>

						</div>

					</div>

					<div class="col-lg-3 col-md-6">

						<h5 class="text-black mb-4">Quick Links</h5>

						<a class="btn btn-link text-black-50" href="">About Us</a>

						<a class="btn btn-link text-black-50" href="">Contact Us</a>

						<a class="btn btn-link text-black-50" href="">Our Services</a>

						<a class="btn btn-link text-black-50" href="">Privacy Policy</a>

						<a class="btn btn-link text-black-50" href="">Terms & Condition</a>

					</div>



					<div class="col-lg-3 col-md-6">



						<h5 class="text-black mb-4">Photo Gallery</h5>



						<div class="row g-2 pt-2">



							<div class="col-4">



								<img class="img-fluid rounded bg-light p-1" src="img/property-1.jpg" alt="">



							</div>



							<div class="col-4">



								<img class="img-fluid rounded bg-light p-1" src="img/property-2.jpg" alt="">



							</div>



							<div class="col-4">



								<img class="img-fluid rounded bg-light p-1" src="img/property-3.jpg" alt="">



							</div>



							<div class="col-4">



								<img class="img-fluid rounded bg-light p-1" src="img/property-4.jpg" alt="">



							</div>



							<div class="col-4">



								<img class="img-fluid rounded bg-light p-1" src="img/property-5.jpg" alt="">



							</div>



							<div class="col-4">



								<img class="img-fluid rounded bg-light p-1" src="img/property-6.jpg" alt="">



							</div>



						</div>



					</div>



					<div class="col-lg-3 col-md-6">



						<h5 class="text-black mb-4">Newsletter</h5>



						<p>Dolor amet sit justo amet elitr clita ipsum elitr est.</p>



						<div class="position-relative mx-auto" style="max-width: 400px;">



							<input class="form-control bg-transparent w-100 py-3 ps-4 pe-5" type="text"
								placeholder="Your email">



							<button type="button" class="btn btn-primary py-2 position-absolute top-0 end-0 mt-2 me-2">SignUp</button>



						</div>



					</div>



				</div>



			</div>



			<div class="container">



				<div class="copyright">



					<div class="row">



						<div class="col-md-6 text-center text-md-start mb-3 mb-md-0">



							&copy;

							<a class="border-bottom" href="#">Your Site Name</a>

							, All Right Reserved. Designed By

							<a class="border-bottom" href="https://htmlcodex.com">HTML Codex</a>



						</div>



						<div class="col-md-6 text-center text-md-end">



							<div class="footer-menu">



								<a href="">Home</a>

								<a href="">Cookies</a>

								<a href="">Help</a>

								<a href="">FQAs</a>



							</div>



						</div>



					</div>



				</div>



			</div>



		</div>







		<a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top">

			<i class="bi bi-arrow-up"></i>

		</a>



	</div>







	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>



	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>



	<script src="lib/wow/wow.min.js"></script>



	<script src="lib/easing/easing.min.js"></script>



	<script src="lib/waypoints/waypoints.min.js"></script>



	<script src="lib/owlcarousel/owl.carousel.min.js"></script>



	<script src="js/main.js"></script>





	<script>


	document.addEventListener("DOMContentLoaded", function() {
	    const searchInput = document.getElementById("searchInput");
	    const categoryFilter = document.getElementById("categoryFilter");
	    const statusFilter = document.getElementById("statusFilter");
	    const table = document.getElementById("newsTable");
	    const tbody = table.querySelector("tbody");
	    const rows = Array.from(tbody.querySelectorAll("tr"));
	    const searchButton = document.getElementById("searchButton");
	    const pagination = document.getElementById("pagination");

	    // 分頁設定
	    const rowsPerPage = 3; // 每頁顯示3筆資料
	    let currentPage = 1;
	    let filteredRows = [...rows];

	    // 初始化分頁
	    updatePagination();

	    // 搜尋與篩選
	    function filterTable() {
	        const searchTerm = searchInput.value.toLowerCase();
	        const categoryValue = categoryFilter.value;
	        const statusValue = statusFilter.value;

	        // 篩選符合條件的資料
	        filteredRows = rows.filter(row => {
	            const title = row.querySelector("td:nth-child(2)").textContent.toLowerCase();
	            const category = row.querySelector("td:nth-child(4)").textContent;
	            const status = row.querySelector("td:nth-child(8)").textContent;

	            const titleMatch = title.includes(searchTerm);
	            const categoryMatch = categoryValue === "" || category === categoryValue;
	            const statusMatch = statusValue === "" || status === statusValue;

	            return titleMatch && categoryMatch && statusMatch;
	        });

	        // 重設到第一頁
	        currentPage = 1;
	        updatePagination();
	        displayRowsForPage(currentPage);
	    }

	    // 更新分頁控制項
	    function updatePagination() {
	        const pageCount = Math.ceil(filteredRows.length / rowsPerPage);
	        pagination.innerHTML = "";

	        // 移除舊的分頁信息
	        const oldPageInfo = document.querySelector(".page-info-display");
	        if (oldPageInfo) oldPageInfo.remove();

	        // 顯示總筆數和頁數資訊
	        const pageInfoDiv = document.createElement("div");
	        pageInfoDiv.className = "page-info-display mb-2 text-center";
	        pageInfoDiv.innerHTML = '<b><font color=red>第' + currentPage + '/' + (pageCount > 0 ? pageCount : 1) + '頁</font></b>' +
	            '<br>' +
	            '<b>●符 合 查 詢 條 件 如 下 所 示: 共<font color=red>' + filteredRows.length + '</font>筆</b>';
	        
	        const tableContainer = table.parentNode;
	        tableContainer.insertBefore(pageInfoDiv, table);

	        // 如果只有一頁或沒有資料，不顯示分頁控制項
	        if (pageCount <= 1) {
	            return;
	        }

	        // 添加分頁按鈕
	        // 第一頁和上一頁按鈕
	        if (currentPage > 1) {
	            addPageLink("至第一頁", 1);
	            addPageLink("上一頁", currentPage - 1);
	        }

	        // 下一頁和最後一頁按鈕
	        if (currentPage < pageCount) {
	            addPageLink("下一頁", currentPage + 1);
	            addPageLink("至最後一頁", pageCount);
	        }

	        // 移除舊的頁數選擇器
	        const oldPageSelect = document.getElementById("pageSelectForm");
	        if (oldPageSelect) oldPageSelect.remove();

	        // 添加頁數下拉選單
	        if (pageCount > 1) {
	            const pageSelect = document.createElement("div");
	            pageSelect.className = "mt-2";
	            
	            const form = document.createElement("form");
	            form.id = "pageSelectForm";
	            form.className = "d-flex justify-content-center align-items-center";
	            
	            const select = document.createElement("select");
	            select.id = "pageSelector";
	            select.className = "form-select mx-2";
	            select.style.width = "auto";
	            
	            // 創建選項
	            for (let i = 1; i <= pageCount; i++) {
	                const option = document.createElement("option");
	                option.value = i;
	                option.textContent = "跳至第" + i + "頁";
	                if (i === currentPage) {
	                    option.selected = true;
	                }
	                select.appendChild(option);
	            }
	            
	            const button = document.createElement("button");
	            button.type = "button";
	            button.id = "goToPageBtn";
	            button.className = "btn btn-primary btn-sm";
	            button.textContent = "確定";
	            
	            form.appendChild(select);
	            form.appendChild(button);
	            pageSelect.appendChild(form);
	            
	            const paginationContainer = pagination.parentNode;
	            paginationContainer.after(pageSelect);

	            // 下拉選單頁面切換事件
	            document.getElementById("goToPageBtn").addEventListener("click", function() {
	                const selectedPage = parseInt(document.getElementById("pageSelector").value);
	                currentPage = selectedPage;
	                displayRowsForPage(currentPage);
	                updatePagination();
	            });
	        }
	    }

	    // 添加分頁連結
	    function addPageLink(text, page) {
	        const li = document.createElement("li");
	        li.className = "page-item" + (currentPage === page ? ' active' : '');
	        
	        const a = document.createElement("a");
	        a.className = "page-link";
	        a.href = "#";
	        a.textContent = text;
	        
	        a.addEventListener("click", function(e) {
	            e.preventDefault();
	            currentPage = page;
	            displayRowsForPage(currentPage);
	            updatePagination();
	        });
	        
	        li.appendChild(a);
	        pagination.appendChild(li);
	    }

	    // 顯示指定頁的資料
	    function displayRowsForPage(page) {
	        // 先隱藏所有行
	        rows.forEach(row => {
	            row.style.display = "none";
	        });

	        // 計算當前頁要顯示的資料範圍
	        const startIndex = (page - 1) * rowsPerPage;
	        const endIndex = Math.min(startIndex + rowsPerPage, filteredRows.length);

	        // 顯示當前頁的資料
	        for (let i = startIndex; i < endIndex; i++) {
	            filteredRows[i].style.display = "";
	        }
	    }

	    // 搜尋按鈕事件
	    searchButton.addEventListener("click", filterTable);

	    // 初始顯示第一頁
	    displayRowsForPage(1);
	});



    function confirmDelete(newsId) {

        if (confirm("確定刪除這則消息嗎？")) {

            const form = document.createElement("form");

            form.setAttribute("method", "post");

            form.setAttribute("action", "${pageContext.request.contextPath}/uploadNewsServlet2.do");



            const actionInput = document.createElement("input");

            actionInput.setAttribute("type", "hidden");

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