<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="news.NewsService" %>
<%@ page import="news.NewsVO" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	//NewsService newsSvc = new NewsService();
	//String updatenewsId = request.getParameter("newsId");
	//NewsVO newsVO = (NewsVO) request.getAttribute("newsVO");
	//if(newsVO == null){
	//	newsVO = newsSvc.getOneNews(updatenewsId);
	//}
	String newsId = null;
	 NewsVO newsVO = new NewsVO();
	 List<String> errorMessage = null;
  try {
    newsId = request.getParameter("newsId");
  } catch (NumberFormatException e) {
    // 處理參數轉換失敗的異常
	 
  }
  
  
  if (newsId == null) {
    // 處理 newsId 無效的情況
    //out.println("無效的消息 ID");
    try{
    	 newsVO = (NewsVO) session.getAttribute("newsVO");
    }catch(Exception e){
    	return;
    }
    newsVO = (NewsVO) session.getAttribute("newsVO");
    newsId = (String) session.getAttribute("newsId");
    errorMessage = (List<String>) session.getAttribute("errorMsgs");
    request.setAttribute("errorMessage", errorMessage);
  
  
    if (errorMessage == null) {
        System.out.println("errorMessage is null");
    }else if(errorMessage.isEmpty()){
        System.out.println("errorMessage is empty");
    }else{
    	System.out.println("jsp網頁錯誤訊息"+errorMessage);
    	System.out.println("jsp網頁id"+newsId);
    	System.out.println(errorMessage.size());
    }
    
  }

 
  
  NewsService newsSvc = new NewsService();
  newsVO = newsSvc.getOneNews(newsId);
  if (newsVO == null) {
    // 處理找不到新聞的情況
    out.println("找不到該消息");
    return;
  }
%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <title>修改最新消息</title>
  <meta content="" title="keywords">
  <meta content="" title="description">
  <meta title="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link href="img/favicon.ico" rel="icon">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Inter:wght@700;800&display=swap"
    rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
  <link href="lib/animate/animate.min.css" rel="stylesheet">
  <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
  <link href="css/bootstrap.min.css" rel="stylesheet">
  <link href="css/style.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
        <button type="button" class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
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
            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"> <i
                class="bi bi-person-circle">會員資料</i></a>
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
            <h2>修改最新消息</h2>
            <form action="<%=request.getContextPath()%>/news/uploadNewsServlet2.do" method="post" enctype="multipart/form-data">
              <input type="hidden" name="newsId" value="<%=newsVO.getNewsId()%>">
              <div class="mb-3">
                <label for="newsTitle" class="form-label">消息標題</label>
                <input type="text" class="form-control" id="newsTitle" name="newsTitle" value="<%=newsVO.getNewsTitle()%>"
                  required>
              </div>
              <div class="mb-3">
                <label for="newsContent" class="form-label">消息內容</label>
                <textarea class="form-control" id="newsContent" name="newsContent" rows="5"
                  required><%=newsVO.getNewsContent()%></textarea>
              </div>
              <div class="mb-3">
                <label for="newsCategoryId" class="form-label">消息種類</label>
                <select class="form-select" id="newsCategoryId" name="newsCategoryId" required>
                  <option value="NC001" <%= newsVO.getNewsCategoryId() == "NC001" ? "selected" : "" %>>重要公告</option>
                  <option value="NC002" <%= newsVO.getNewsCategoryId() == "NC002" ? "selected" : "" %>>活動</option>
                  <option value="NC003" <%= newsVO.getNewsCategoryId() == "NC003" ? "selected" : "" %>>空間</option>
                </select>
              </div>
              <div class="mb-3">
                <label for="newsStartDate" class="form-label">消息起始日</label>
                <input type="date" class="form-control" id="newsStartDate" name="newsStartDate"
                  value="<%=newsVO.getNewsStartDate()%>" required>
              </div>
              <div class="mb-3">
                <label for="newsEndDate" class="form-label">消息結束日</label>
                <input type="date" class="form-control" id="newsEndDate" name="newsEndDate"
                  value="<%=newsVO.getNewsEndDate()%>" required>
              </div>
              <div class="mb-3">
                <label for="newsStatusId" class="form-label">消息狀態</label>
                <select class="form-select" id="newsStatusId" name="newsStatusId" required>
                  <option value="0" <%= newsVO.getNewsStatusId() == 0 ? "selected" : "" %>>過期</option>
                  <option value="1" <%= newsVO.getNewsStatusId() == 1 ? "selected" : "" %>>上架中</option>
                  <option value="2" <%= newsVO.getNewsStatusId() == 2 ? "selected" : "" %>>即將發布</option>
                </select>
              </div>
              <div class="mb-3">
               <label for="newsImg" class="form-label">消息圖片</label>
            <%-- 資料庫有圖片，顯示目前圖片 --%>
            <img src="${pageContext.request.contextPath}/PhotoReader2?newsId=${newsVO.getNewsId()}" alt="目前無消息圖片" width="200" height="150">
            <%-- 提供上傳新圖片的欄位 --%>
            <input type="file" class="form-control" id="newsImg" name="newsImg"> 
      			</div>
              <input type="hidden" name="action" value="update">
			<input type="hidden" name="newsId" value="${param.newsId}">
              <input type="submit" class="btn btn-primary" value="提交修改"></button>
            </form>
          </div>
        </div>
    </section>
                    <%-- 錯誤表列 --%>
<c:if test="${not empty errorMessage}">
    <font style="color:red">請修正以下錯誤:</font>
    <ul>
        <c:forEach var="message" items="${errorMessage}">
            <li style="color:red"><c:out value="${message}"/></li>
        </c:forEach>
    </ul>
</c:if>

    <div class="container-fluid bg-light text-black-50 footer pt-5 mt-5 wow fadeIn" data-wow-delay="0.1s">
      <div class="container py-5">
        <div class="row g-5">
          <div class="col-lg-3 col-md-6">
            <h5 class="text-black mb-4">
              <img class="img-fluid" src="img/LifeSpace3.png" alt="Icon" style="width: 250px; height: 60px;">
            </h5>
            <p class="mb-2"><i class="fa fa-map-marker-alt me-3"></i>123 Street, New York, USA</p>
            <p class="mb-2"><i class="fa fa-phone-alt me-3"></i>+012 345 67890</p>
            <p class="mb-2"><i class="fa fa-envelope me-3"></i>info@example.com</p>
            <div class="d-flex pt-2">
              <a class="btn btn-outline-light btn-social" href=""><i class="fab fa-twitter"></i></a>
              <a class="btn btn-outline-light btn-social" href=""><i class="fab fa-facebook-f"></i></a>
              <a class="btn btn-outline-light btn-social" href=""><i class="fab fa-youtube"></i></a>
              <a class="btn btn-outline-light btn-social" href=""><i class="fab fa-linkedin-in"></i></a>
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
              <input class="form-control bg-transparent w-100 py-3 ps-4 pe-5" type="text" placeholder="Your email">
              <button type="button" class="btn btn-primary py-2 position-absolute top-0 end-0 mt-2 me-2">SignUp</button>
            </div>
          </div>
        </div>
      </div>
      <div class="container">
        <div class="copyright">
          <div class="row">
            <div class="col-md-6 text-center text-md-start mb-3 mb-md-0">
              &copy; <a class="border-bottom" href="#">Your Site Name</a>, All Right Reserved.
              Designed By <a class="border-bottom" href="https://htmlcodex.com">HTML Codex</a>
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

    <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
  </div>

  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
  <script src="lib/wow/wow.min.js"></script>
  <script src="lib/easing/easing.min.js"></script>
  <script src="lib/waypoints/waypoints.min.js"></script>
  <script src="lib/owlcarousel/owl.carousel.min.js"></script>
  <script src="js/main.js"></script>
</body>

</html>