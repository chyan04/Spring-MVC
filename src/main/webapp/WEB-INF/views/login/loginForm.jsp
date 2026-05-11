<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet" />
<title>로그인</title>
<script>
$(document).ready(function() {
	$("#memId").focus();
	$("#memPwd").keydown(function(key) {
		// 키의 코드가 13번일 경우 (13=Enter)
		if(key.keyCode ==13) {
			login();
		}
	});
} );

function login() {

	var frm = document.loginForm;
	
	if(!validate()) {
		return false;
	}
	
	frm.action = "login";
	frm.submit();
}

function validate() {
	var frm = document.loginForm;
	
	if(frm.memId.value == "") {
		alert("아이디를 입력해주세요.");
		return false;
	}
	
	if(frm.memPwd.value == "") {
		alert("비밀번호를 입력해주세요.");
		return false;
	}
	return true;
}
</script>

</head>
<body>
<div class="container" align="center" >
	<h3>로그인</h3>
	<form name="loginForm" method="post">
		<table class="table table-bordered" style="width: 300px" >
			<tr>
				<td>아이디</td>
				<td>
					<input type="text" name="memId" id="memId" size="20" />
					</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td>
					<input type="password" name="memPwd" id="memPwd" size="20" />
					</td>
			</tr>
		
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="로그인" class="btn btn-default" onclick="login()" />
					<input type="button" value="회원가입" class="btn btn-default" 
					onclick="location.href='${pageContext.request.contextPath}/member/memberForm'" />
				</td>
			</tr>
		</table>
	</form>

</div>

</body>
</html>