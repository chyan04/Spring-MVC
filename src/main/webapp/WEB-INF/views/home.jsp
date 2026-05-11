<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>home</title>
</head>
<body>
<div align="center">
<h3>WELCOME BACK</h3>
돌아오신 것을 환영합니다.<br>

<c:if test="${sessionScope.LOGIN_USER != null }" >
<div align="center">
${LOGIN_USER.memName} (${LOGIN_USER.memId}) <a href="memberForm?seqNo=${LOGIN_USER.memSeqNo}'">마이페이지</a>
</div>
</c:if><br><br>
지금 시각 : ${serverTime}
</div>
</body>
</html>