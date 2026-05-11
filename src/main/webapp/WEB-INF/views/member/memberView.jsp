<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet" />
<title>회원정보</title>
</head>
<body>
<div>
<table class="table table-bordered" >
	<tr>
		<td width ="150" align="center">회원번호</td>
		<td>${member.memSeqNo}</td>
	</tr>
	<tr>
		<td align="center">아이디</td>
		<td>${member.memId}</td>
	</tr>
	<tr>
		<td align="center">이름</td>
		<td>${member.memName}</td>
	</tr>
	<%-- <tr>
		<td align="center">비밀번호</td>
		<td>${member.memPwd}</td>
	</tr> --%>
	<tr>
		<td align="center">생년월일</td>
		<td>${member.memBirth}</td>
	</tr>
	<tr>
		<td align="center">전화번호</td>
		<td>${member.memPhone}</td>
	</tr>
	<tr>
		<td align="center">이메일</td>
		<td>${member.memEmail}</td>
	</tr>
	<tr>
		<td valign="bottom" align="center" >주소</td>
		<td>
			<p>${member.memZipcode}</p>
			<p>${member.memAddrMaster}</p>
			<p>${member.memAddrDetail}</p>
		</td>
	</tr>
<%-- 	<tr>
		<td align="center" >Permission</td>
		<td>${member.memType}</td>
	</tr> --%>
	<tr>
		<td colspan="3">
			<input type="button" value="정보수정" class="btn btn-default" 
			onclick="location.href='memberForm?seqNo=${member.memSeqNo}'" />

			<input type="button" value="회원목록" class="btn btn-default" onclick="location.href='memberList'"/>
		</td>
	</tr>
</table>
</div>
</body>
</html>