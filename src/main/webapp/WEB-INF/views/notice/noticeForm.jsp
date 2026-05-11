<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" >

<script>
$(document).ready(function() {
	$("#noticeTitle").focus();
	
	$frm = $("#noticeForm");		// var frm = document.noticeForm;


		$("#noticeForm").validate({
			rules: {		// 체크리스트 ex: 숫자만 입력, 브라우저 별로 다른 동작
				noticeTitle : { required : true },
				noticeContent : "required"
			},
			messages: { 	// 보여지는 내용
				noticeTitle: "제목을 입력하세요.",
				noticeContent : "내용을 입력하세요."
			},
			submitHandler: function (frm) {
				
					noticeSubmit();	// 유효성 검사 통과
				
			},
			success : function (e) {
				// 생략가능
			}
	});	// end validate
}); 		// end document ready

function noticeSubmit() {
	var frm = document.noticeForm;
			
	<c:if test="${notice.noticeSeqNo == '0' }">
		frm.action = "noticeInsert";
	</c:if>
	
	<c:if test="${notice.noticeSeqNo != '0' }">
		frm.action = "noticeUpdate";
	</c:if>

	frm.submit();
}
</script>
<title>공지사항 작성</title>
</head>
<body>

<div class="container">
	<h1 align="center">공지사항 작성 및 수정 </h1>
	
	<form name="noticeForm" id="noticeForm" method="post" enctype="multipart/form-data">
<!-- 		<input type="hidden" name="noticeType" value="BBS"> -->
		<input type="hidden" name="noticeSeqNo" value="${notice.noticeSeqNo}">
		
		<table class="table">
			<tr>
				<td>제목</td>
				<td><input type="text" name="noticeTitle" id="noticeTitle" size="100" value="${notice.noticeTitle}"></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>
				<input type="hidden" name="noticeWriter" size="20" value="${notice.noticeWriter}">
				<input type="text" name="noticeWriterName" size="20"  value="${notice.noticeWriterName}" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="noticeContent" id="noticeContent" rows="15" cols="100">${notice.noticeContent}</textarea></td>
			</tr>

		</table>
		
		<p align="center">
			<input type="submit" value="저장" class="btn btn-primary" >
			<input type="reset" value="초기화" class="btn btn-primary">
			<input type="button" value="목록" class="btn btn-primary" onclick="location.href='noticeList'">
		</p>		
	
	</form>

</div>

</body>
</html>