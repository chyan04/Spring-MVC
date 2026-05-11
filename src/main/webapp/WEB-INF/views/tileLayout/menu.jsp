<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>menu</title>
</head>
<body>
<div class="container-fluid">
	<ul class="nav navbar-nav">
		<li class="active"><a  style="color: green" href="${pageContext.request.contextPath}/">HOME</a></li>
		<li class="active" ><a href="${pageContext.request.contextPath}/notice/noticeList">공지사항</a></li>		
		<li class="active"><a href="${pageContext.request.contextPath}/board/boardList?boType=BBS">게시판</a></li>
		<li class="active"><a href="${pageContext.request.contextPath}/gallery/galleryList?boType=GALLERY">갤러리</a></li>
		<li class="active"><a href="${pageContext.request.contextPath}/faq/faqList">FAQ</a></li>
		<li>
		<c:if test="${sessionScope.LOGIN_USER != null && sessionScope.LOGIN_USER.memType == 'A' }">
			<a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color: red">ADMIN
				<span class="caret"></span>
			</a>
				<ul class="dropdown-menu">
					<li><a href="${pageContext.request.contextPath}/member/memberList">회원관리</a></li>
					<li><a href="#">허거덩</a></li>
					<li><a href="#">집가고싶다</a></li>
				</ul>
		</c:if>
		</li>
	</ul>
	
	<c:if test="${sessionScope.LOGIN_USER == null}">
		<ul class="nav navbar-nav navbar-right">
			<li><a href="${pageContext.request.contextPath}/login/loginForm" style="color: aqua">LOGIN</a></li>
		</ul>
	</c:if>
	
	<c:if test="${sessionScope.LOGIN_USER != null}">
		<ul class="nav navbar-nav navbar-right">
			<li><a href="${pageContext.request.contextPath}/login/logout" style="color: blue">LOGOUT</a></li>
		</ul>
	</c:if>
	
</div>
</body>
</html>