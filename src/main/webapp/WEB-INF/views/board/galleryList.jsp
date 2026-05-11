<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

<title>Insert title here</title>
<script>
function doSearch(page){
	var frm = document.searchForm;
	
	if(frm.searchType.value != "" && frm.searchWord.value == ""){
		alert("검색어를 입력하세요");
		return;
	}
	
	frm.currentPage.value = page;
	frm.action = "galleryList";
	frm.submit();
	
}

function goWriteForm(){
	location.href="galleryForm";
}
</script>


</head>
<body>

<div>
	<h2 align="center">게시글 목록</h2>
	
	<p align="right"><input type="button" value="글쓰기" class="btn btn-primary" onclick="goWriteForm()"></p>
	
	<p align="center">
		<form name="searchForm" method="post">
			<input type="hidden" name="boType" value="GALLERY">
			
			<input type="hidden" name="currentPage" value="${param.currentPage}">
			<select name="searchType">
				<option value="">전체</option>
				<option ${param.searchType == '01' ? 'selected' : ''} value="01">제목</option>
				<option ${param.searchType == '02' ? 'selected' : ''} value="02">내용</option>
				<option ${param.searchType == '03' ? 'selected' : ''} value="03">제목+내용</option>
				<option ${param.searchType == '04' ? 'selected' : ''} value="04">작성자</option>				
			</select>	
			<input type="text" name="searchWord" size="40" value="${param.searchWord}">
			<input type="button" value="검색" onclick="doSearch(1);">			
		
		<p>
			<label>Total : ${pagingUtil.totalCount }</label>
			
			<span style="float: right;">
				<label>Page Size : </label>
				<select name="pageSize" onchange="doSearch(1)">
					<option ${param.pageSize == '10' ? 'selected' : ''} value="10">10개</option>
					<option ${param.pageSize == '20' ? 'selected' : ''} value="20">20개</option>
					<option ${param.pageSize == '50' ? 'selected' : ''} value="50">50개</option>
				</select>
			</span>
		</p>		
		
		</form>

	
	<table class="table table-bordered table-hover">

		<tbody>
			<c:if test="${not empty galleryList}">
						<c:forEach var="gallery" items="${galleryList}">
							<tr>
								<td rowspan="5" style="text-align:center; width:20%">
									<img class="img-thumbnail" style="width : 100px; height: 100px;" 
									src="${pageContext.request.contextPath}/common/display?fileSeqNo=${gallery.fileSeqNo}">
								</td>
							</tr>
							<tr>
								<td><a href="galleryView?seqNo=${gallery.boSeqNo}">${gallery.boTitle}</a></td>	
							</tr>							
							<tr>
								<td>${gallery.boWriterName}</td>
							</tr>
							
							<tr>
								<td>${gallery.regDate}</td>
							</tr>
							
							<tr>
								<td>${gallery.boHitCnt}</td>
							</tr>

						</c:forEach>
					</c:if>
					
					<c:if test="${empty galleryList }">
						<tr>
							<td colspan="5" align="center">데이터가 존재하지 않습니다.</td>
						</tr>
					</c:if>
		</tbody>
	
	</table>
	
	<!-- 페이징 -->	
	<div style="text-align: center;">
		<ul class="pagination">
			${pagingUtil.pageHtml }
		</ul>
	</div>
	
	<!-- //페이징 -->
	

</div>




</body>
</html>