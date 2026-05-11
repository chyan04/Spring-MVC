<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록</title>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet" />
<script>
	function doSearch(page) {
		var frm = document.searchForm;
		
		if(frm.searchType.value != "" && frm.searchWord.value == ""){
		alert("검색어를 입력하세요.");
		return;
	}
		
	frm.currentPage.value = page;
	frm.action = "memberList";
	frm.submit();
}
</script>


</head>
<body>
	<h1 align="center" >회원관리</h1>


	<div>
		<p align="right">
		<!--  input type="button" value="회원가입" class="btn btn-default" 
		onclick="location.href='memberForm'" -->
		</p>
	</div>
	
	
	
	<p align="center">
		<form name="searchForm" method="post" >
		<input type="hidden" name="currentPage" value="${param.currentPage }" />
	
			<select name="searchType"  >
				<option value="">전체</option>
				<option ${param.searchType == 'id' ? 'selected' : '' } value="id">아이디</option>
				<option ${param.searchType == 'name' ? 'selected' : '' } value="name">이름</option>
			</select>
			<input type="text" size="20" name="searchWord" value="${param.searchWord }"/>
			<input type="button" value="검색" onclick="doSearch(1)" />
			<p>
			 	<label style="float : left;">Total : ${pagingUtil.totalCount} </label>
			 	<span style="float:right;"><label>Page Size : </label>
			 	<select name="pageSize" onchange="doSearch(1)">
			 		<option ${param.pageSize == '10' ? 'selected' : ' '} value="10">10개</option>
			 		<option ${param.pageSize == '20' ? 'selected' : ' '} value="20">20개</option>
			 		<option ${param.pageSize == '50' ? 'selected' : ' '} value="50">50개</option>
			 	</select>
			</span>
		</form>
		<table class="table table-bordered table-hover">
			<thead>
				<tr>
					<th width ="150" align="center" >순번</th>
					<th width ="150" align="center" >아이디</th>
					<th width ="150" align="center" >이름</th>
					<th>휴대폰</th>
					<th>이메일</th>
				</tr>
			</thead>
		
			<tbody>
				<c:if test="${not empty memberList }">
					<c:forEach var="member" items="${memberList}">
						<tr>
							<td>${member.memSeqNo}</td>
							<td>${member.memId}</td>
							<td><a href="memberView?seqNo=${member.memSeqNo}">${member.memName}</a></td>
							<td>${member.memPhone}</td>
							<td>${member.memEmail}</td>
						</tr>
					</c:forEach>
				</c:if>
				
				<c:if test="${empty memberList }">
					<tr>
						<td colspan="5" align="center">데이터가 존재하지 않습니다.</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	
		<!-- 페이지 네비게이션 -->
		<div style="text-align: center;">
			<ul class="pagination">
				${pagingUtil.pageHtml}
			</ul>
		</div>
</body>
</html>