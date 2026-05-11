<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>faqView</title>
<script>
$(document).ready(function() {
	$("#btnEdit").click(function() {
		location.href="faqForm?faqSeqNo="+${faq.faqSeqNo};
	});

	$("#btnDelete").click(function() {
		if(confirm("정말로 삭제 하시겠습니까?")) {
			location.href="faqDelete?faqSeqNo="+${faq.faqSeqNo};
		}
	});
});
</script>

</head>
<body>
<div class="container">
		<table class="table">	
			<tr>
				<td style="width: 100px; ">질문</td>
				<td>${faq.faqName}</td>
			</tr>
	
			<c:if test="${(not empty sessionScope.LOGIN_USER && sessionScope.LOGIN_USER.memType == 'A' )}" >
				<tr>
					<td><font color="red">작성자</font></td>
					<td>${faq.faqWriterName}</td>
				</tr>
				<tr>
					<td><font color="red">작성일</font></td>
					<td>${faq.faqDate}</td>
				</tr>
			</c:if>
			<tr>
				<td>조회수</td>
				<td>${faq.faqHitCnt}</td>
			</tr>
			<tr>
				<td>답변</td>
				<td>${faq.faqContent}</td>
			</tr>

			
		</table>
	
		<p align="center">
			
		<c:if test="${not empty sessionScope.LOGIN_USER && sessionScope.LOGIN_USER.memType == 'A'}">
			<input type="button" style= "color: #70ef8e" value="수정"  class="btn btn-primary" id="btnEdit" >
			<input type="button" style= "color: #70ef8e" value="삭제"  class="btn btn-primary" id="btnDelete">
		</c:if>
	
		<input type="button" value="목록" class="btn btn-primary" onclick="location.href='faqList'">
		
		</p>		
		
</div>
</body>
</html>