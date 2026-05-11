<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" >
<script>
$(document).ready(function() {
	$("#faqName").focus();
	
	$frm = $("#faqForm");		// var frm = document.faqForm;

		$("#faqForm").validate({
			rules: {		// 체크리스트 ex: 숫자만 입력, 브라우저 별로 다른 동작
				faqName : { required : true },
				faqContent : "required"
			},
			messages: { 	// 보여지는 내용
				faqName: "제목을 입력하세요.",
				faqContent : "내용을 입력하세요."
			},
			submitHandler: function (frm) {
				
					faqSubmit();	// 유효성 검사 통과
				
			},
			success : function (e) {
				// 생략가능
			}
	});	// end validate
}); 		// end document ready

function faqSubmit() {
	var frm = document.faqForm;
			
	<c:if test="${faq.faqSeqNo == '0' }">
		frm.action = "faqInsert";
	</c:if>
	
	<c:if test="${faq.faqSeqNo != '0' }">
		frm.action = "faqUpdate";
	</c:if>

	frm.submit();
}
</script>
<title>f a q 작성</title>
</head>
<body>

<div class="container">
	<h1 align="center">FAQ 작성 및 수정 </h1>
	
	<form name="faqForm" id="faqForm" method="post" enctype="multipart/form-data">
<!-- 		<input type="hidden" name="faqType" value="BBS"> -->
		<input type="hidden" name="faqSeqNo" value="${faq.faqSeqNo}">
		
		<table class="table">
			<tr>
				<td>제목</td>
				<td><input type="text" name="faqName" id="faqName" size="100" value="${faq.faqName}"></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>
				<input type="hidden" name="faqWriter" size="20" value="${faq.faqWriter}">
				<input type="text" name="faqWriterName" size="20"  value="${faq.faqWriterName}" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="faqContent" id="faqContent" rows="15" cols="100">${faq.faqContent}</textarea></td>
			</tr>

		</table>
		
		<p align="center">
			<input type="submit" value="저장" class="btn btn-primary" >
			<input type="reset" value="초기화" class="btn btn-primary">
			<input type="button" value="목록" class="btn btn-primary" onclick="location.href='faqList'">
		</p>		
	
	</form>

</div>

</body>
</html>