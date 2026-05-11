<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>noticeView</title>
<script>
$(document).ready(function() {
	$("#btnEdit").click(function() {
		location.href="noticeForm?noticeSeqNo="+${notice.noticeSeqNo};
	});

	$("#btnDelete").click(function() {
		if(confirm("정말로 삭제 하시겠습니까?")) {
			location.href="noticeDelete?noticeSeqNo="+${notice.noticeSeqNo};
		}
	});
});
</script>

</head>
<body>
<div class="container">
		<table class="table">	
			<tr>
				<td>공지사항 제목</td>
				<td>${notice.noticeTitle}</td>
			</tr>

			<tr>
				<td>작성자</td>
				<td>${notice.noticeWriterName}</td>
			</tr>
			<tr>
				<td>작성일</td>
				<td>${notice.noticeDate}</td>
			</tr>
			
			<tr>
				<td>조회수</td>
				<td>${notice.noticeHitCnt}</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>${notice.noticeContent}</td>
			</tr>

			
		</table>
	
		<p align="center">
			
		<c:if test="${not empty sessionScope.LOGIN_USER && sessionScope.LOGIN_USER.memType == 'A'}">
			<input type="button" style= "color: #70ef8e" value="수정"  class="btn btn-primary" id="btnEdit" >
			<input type="button" style= "color: #70ef8e" value="삭제"  class="btn btn-primary" id="btnDelete">
		</c:if>
	
		<input type="button" value="목록" class="btn btn-primary" onclick="location.href='noticeList'">
		
		</p>		
		
</div>
</body>
</html>