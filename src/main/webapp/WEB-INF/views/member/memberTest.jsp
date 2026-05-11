<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memberTest</title>
</head>
<body>

<table>
	<tr>
		<td>아이디</td>
		<td>이름</td>
		<td>이메일</td>
	
	</tr>

<c:forEach var="member" items="${memberList}">
<tr>
	<td>${member.memId}</td>
	<td>${member.memName}</td>
	<td>${member.memEmail}</td>
</tr>
</c:forEach>
</table>


</body>
</html>