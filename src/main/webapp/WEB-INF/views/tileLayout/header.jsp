<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>header</title>
</head>
<body>
<div align="center">
<h1>In-Between</h1>
</div>
<c:if test="${sessionScope.LOGIN_USER != null }" >
<div align="right">
${LOGIN_USER.memName} (${LOGIN_USER.memId}) <a href="${pageContext.request.contextPath }/member/memberForm?seqNo=${LOGIN_USER.memSeqNo }'">마이페이지</a>
</div>
</c:if>
</body>
</html>
