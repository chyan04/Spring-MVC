<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>boardView</title>
<script>
$(document).ready(function() {
	$("#btnEdit").click(function() {
		location.href="boardForm?boSeqNo="+${board.boSeqNo};
	});

	$("#btnDelete").click(function() {
		if(confirm("정말로 삭제 하시겠습니까?")) {
			location.href="boardDelete?boSeqNo="+${board.boSeqNo};
		}
	});
});
</script>

</head>
<body>
<div class="container">
		<table class="table">	
		<c:choose>
			<c:when test="${board.boOpenYn=='Y' || 
			(not empty sessionScope.LOGIN_USER && sessionScope.LOGIN_USER.memId == board.boWriter) ||
			(not empty sessionScope.LOGIN_USER && sessionScope.LOGIN_USER.memType == 'A' ) }" >
			<h1 align="center" >${board.boTitle}</h1>
			<tr>
				<td>작성자</td>
				<td>${board.boWriterName}</td>
			</tr>
			<tr>
				<td>작성일</td>
				<td>${board.regDate}</td>
			</tr>
			
			<tr>
				<td>조회수</td>
				<td>${board.boHitCnt}</td>
			</tr>
			<tr>
				<td>공개여부</td>
				<td>${board.boOpenYn == 'Y' ? '공개' : '비공개' }</td>
			</tr>

			<tr>
				<td>첨부파일</td>
				<td>
						<c:if test="${board.fileList != null}">
							<c:forEach var="fileItem" items="${board.fileList}">
							<div>
								<a href="${pageContext.request.contextPath}/common/download?fileSeqNo=${fileItem.fileSeqNo}">${fileItem.fileName}</a>${fileItem.fileFancySize}
							</div>					
							</c:forEach>
						</c:if>

						<c:if test="${empty board.fileList}">
								<font color=blue>파일이 없습니다.</font>
						</c:if>
						
				</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>${board.boContent}</td>
			</tr>
			</c:when>
			<c:otherwise>
			<h1 align="center"><font color="red">비밀게시글</font></h1><br />
			<h4 align="center">작성자와 관리자만 읽을 수 있는 게시글 입니다</h4>
			</c:otherwise>
			</c:choose>
			
		</table>
		
		<p align="center">
			
		<c:if test="${not empty sessionScope.LOGIN_USER && sessionScope.LOGIN_USER.memId == board.boWriter}">
			<input type="hidden" name="boType" value="BBS" >
			<input type="button" style= "color: #70ef8e" value="수정"  class="btn btn-primary" id="btnEdit" >
			<input type="button" style= "color: #70ef8e" value="삭제"  class="btn btn-primary" id="btnDelete">
		</c:if>
		
		<input type="button" value="목록" class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/board/boardList?boType=BBS'">
		
		</p>		
		
</div>
</body>
</html>