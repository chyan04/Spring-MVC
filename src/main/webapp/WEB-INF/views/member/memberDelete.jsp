<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<meta charset="UTF-8">
<title>회원탈퇴</title>
<script>
function doDelete() {
	var frm = document.memberDelete;

	frm.action = "memberDelete";
	frm.submit();
}
</script>

</head>
<body>
	<div>
		<form name="memberDelete" method="post">
			<table class="table table-bordered">
				<tr>
					<td width="150" align="center">비밀번호 확인</td>
					<td><input type="password" name="memPwd" size="20" value="">


					</td>
				<tr>
					<td colspan="2"><input type="button" value="삭제"
						class="btn btn-default" onclick="doDelete()">
						
						<input type="button" value="취소" class="btn btn-default"
						onclick="location.href='memberList'"> <br> 
						
						<font color="white"> 한번 삭제를 선택하면, 모든 정보가 삭제되고 회원 계정을 복구할 수 없습니다. </font> 
					<br> <font color="red" size="4"> 정말로 삭제 하시겠습니까? </font>
				</tr>

			</table>
		</form>
	</div>


</body>
</html>