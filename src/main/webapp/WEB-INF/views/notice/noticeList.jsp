<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>공지사항 목록</title>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet" />
<script>
	function doSearch(page) {
		var frm = document.searchForm;
		
		if(frm.searchType.value != "" && frm.searchWord.value == ""){
		alert("검색어를 입력하세요.");
		return;
	}
		
	frm.currentPage.value = page;
	frm.action = "noticeList";
	frm.submit();
}  
</script>
</head>
<body>
	<div class="container">
	<h1 align="center" >공지사항 목록</h1>
	
		<c:if test="${ (not empty sessionScope.LOGIN_USER && sessionScope.LOGIN_USER.memType == 'A' ) }" >
		<p align="right">
			<input type="button" value="공지작성" class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/notice/noticeForm'">
		</p>
		</c:if>

		<p align="center">
		<form name="searchForm" method="post">
	<!-- 	<input type="hidden" name="noticeType" size="40"  value="BBS">  -->
			<input type="hidden" name="currentPage" size="40"  value="${param.currentType}"> 
			<select name="searchType">
				<option value="">전체</option>
				<option ${param.searchType == '01' ? 'selected' : ' '} value="01">제목</option>
				<option ${param.searchType == '02' ? 'selected' : ' '} value="02">내용</option>
				<option ${param.searchType == '03' ? 'selected' : ' '} value="03">제목+내용</option>
				<option ${param.searchType == '04' ? 'selected' : ' '} value="04">작성자</option>
			</select> 
			<input type="text" name="searchWord" size="40" value="${param.searchWord}"> 
			<input type="button" value="검색" onclick="doSearch(1);">

			<p>
				
				<span style="float: left;"><label>Total : ${pagingUtil.totalCount} </label> </span>
				<span style="float: right;"><label>Page Size : </label> 
				<select name="pageSize" onChange="doSearch(1)">
						<option ${param.pageSize == '10' ? 'selected' : ' '} value="10">10개</option>
						<option ${param.pageSize == '20' ? 'selected' : ' '} value="20">20개</option>
						<option ${param.pageSize == '50' ? 'selected' : ' '} value="50">50개</option>
				</select>
				</span>
			</p>

		</form>

		<table class="table table-bordered table-hover">
			<thead align="center">
				<tr>
					<th style="width:5%">번호</th>
					<th>제목</th>
					<th style="width:13%">작성자</th>
					<th style="width:15%">작성일</th>
					<th style="width:6%">조회수</th>
				</tr>
			</thead>

			<tbody>

				<c:if test="${not empty noticeList }">
					<c:forEach var="notice" items="${noticeList}">
						<tr>
							<td>${notice.noticeSeqNo}</td>
							
							<td><a href="noticeView?seqNo=${notice.noticeSeqNo}">${notice.noticeTitle}</a></td>
							<td>${notice.noticeWriterName}</td>
							<td>${notice.noticeDate}</td>
							<td>${notice.noticeHitCnt}</td>
						</tr>
					<c:if test="${empty noticeList}">
						<tr>
							<td colspan="5" align="center">게시글이 존재하지 않습니다.</td>
						</tr>
					</c:if>
						
					</c:forEach>
				</c:if>
			</tbody>

		</table>

		<!-- 페이징 -->

		<div style="text-align: center;">
			<ul class="pagination">
				${pagingUtil.pageHtml}
			</ul>
		</div>

		<!-- //페이징 -->


	</div>
</body>
</html>