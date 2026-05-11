<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>FAQ목록</title>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet" />
<script>
	function doSearch(page) {
		var frm = document.searchForm;

		if(frm.searchType.value != "" && frm.searchWord.value == ""){
		alert("검색어를 입력하세요.");
		return;
	}
		
	frm.currentPage.value = page;
	frm.action = "faqList";
	frm.submit();
}  
</script>
</head>
<body>
	<div class="container">
	<h1 align="center" >F A Q</h1>
	
		<c:if test="${(not empty sessionScope.LOGIN_USER && sessionScope.LOGIN_USER.memType == 'A' )}" >
		<p align="right">
			<input type="button" value="작성" class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/faq/faqForm'">
		</p>
		</c:if>
		<p align="center">
		<form name="searchForm" method="post">
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
					<th align="center" >번호</th>
					<th>제목</th>
					<th align="center" >작성일</th>
					<th align="center" >조회수</th>
				</tr>
			</thead>

			<tbody>

				<c:if test="${not empty faqList}">
					<c:forEach var="faq" items="${faqList}">
						<tr>
							<td align="center" style="width: 50px; ">${faq.faqSeqNo}</td>
							
							<td><a href="faqView?seqNo=${faq.faqSeqNo}">${faq.faqName}</a></td>
							<td style="width: 200px; ">${faq.faqDate}</td>
							<td style="width: 80px; ">${faq.faqHitCnt}</td>
						</tr>
					<c:if test="${empty faqList}">
						<tr>
							<td colspan="5" align="center">이곳은 너무나도 조용합니다...</td>
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