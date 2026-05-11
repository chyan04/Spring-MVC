<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script>

$(document).ready(function(){
	$("#btnEdit").click(function(){
		location.href="galleryForm?boSeqNo="+${gallery.boSeqNo};
	});
	
	$("#btnDelete").click(function(){
		if(confirm("삭제하시겠습니까?")){
			location.href="galleryDelete?boSeqNo="+${gallery.boSeqNo};
		}
	});
});


function viewImg(fileSeqNo){
	window.open
	("${pageContext.request.contextPath}/common/display?imgType=img&fileSeqNo="+fileSeqNo
			, "", "height=" + screen.height + ", width=" + screen.width +
			", location=no, menubar=no, status=no, toolbar=no, fullscreen=yes");
}

</script>

<title>Insert title here</title>
</head>
<body>

<div class="container">
		<table class="table">
			<c:choose>
				<c:when test="${gallery.boOpenYn =='Y' || 
				(not empty sessionScope.LOGIN_USER && sessionScope.LOGIN_USER.memId == gallery.boWriter) ||
					(not empty sessionScope.LOGIN_USER && sessionScope.LOGIN_USER.memType == 'A')}">
					<h1 align="center">게시글 상세보기</h1>

					<tr>
						<td>제목</td>
						<td>${gallery.boTitle }</td>
					</tr>
					<tr>
						<td>작성일</td>
						<td>${gallery.regDate }</td>
					</tr>

					<tr>
						<td>작성자</td>
						<td>${gallery.boWriterName }</td>
					</tr>
					<tr>
						<td>조회수</td>
						<td>${gallery.boHitCnt }</td>
					</tr>
					<tr>
						<td>공개여부</td>
						<td>${gallery.boOpenYn == 'Y' ? '공개' : '비공개' }</td>
					</tr>

					<!-- 첨부파일 -->
					<tr>
						<td>첨부파일</td>
						<td><c:if test="${gallery.fileList != null }">
								<c:forEach var="fileItem" items="${gallery.fileList }">
									<div>
										<a
											href="${pageContext.request.contextPath}/common/download?fileSeqNo=${fileItem.fileSeqNo}">${fileItem.fileName }</a>
										${fileItem.fileFancySize } <img
											src="${pageContext.request.contextPath }/common/display?fileSeqNo=${fileItem.fileSeqNo}"
											onclick="viewImg(${fileItem.fileSeqNo})" />
									</div>
								</c:forEach>
							</c:if> <c:if test="${empty gallery.fileList}">
						파일이 없습니다.
					</c:if></td>
					</tr>

					<tr>
						<td>내용</td>
						<td>${gallery.boContent }</td>
					</tr>

				</c:when>
				<c:otherwise>
					<h1 align="center">
						<font color="red">비밀게시글</font>
					</h1>
					<br />
					<h4 align="center">작성자와 관리자만 읽을 수 있는 게시글 입니다</h4>
				</c:otherwise>
			</c:choose>

		</table>

		<p align="center">
		
		<c:if test="${not empty sessionScope.LOGIN_USER && sessionScope.LOGIN_USER.memId == gallery.boWriter }"> 
			<input type="button" value="수정" class="btn btn-primary" id="btnEdit" />
			<input type="button" value="삭제" class="btn btn-primary" id="btnDelete" />			
		</c:if>
		
		<input type="button" value="목록" class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/gallery/galleryList?boType=GALLERY'">
		</p>		

</div>

</body>
</html>


