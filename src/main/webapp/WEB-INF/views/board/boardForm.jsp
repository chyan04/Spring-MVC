<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" >

<script>
$(document).ready(function() {
	$("#boTitle").focus();
	
	$frm = $("#boardForm");		// var frm = document.boardForm;
	
	// insert 파일 추가
	$(".btn-new-file", $frm).click(function(){
		$("#fileList").append(
		'<div>' + 
		'<input type="file" name="uploadFiles" multiple="multiple" style="display: inline;" >' + 
		'<button type="button" class="btn btn-danger btn-xs btn-delete-file">x</button>' + 
	'</div>'
		);
		

	});
	
	// insert 파일 삭제 
	$("#fileList").on("click", ".btn-delete-file", function(){
		$(this).parent().remove();
	});
	
	// 기본 파일 삭제(수정, 삭제)
	$(".btn-delete-exist").click(function(){
		$(this).parent().html('<input type="hidden" name="delFileSeq" value="' + $(this).data("file_seq_no") + '">')
	});
	
	
	
	
	function checkFiles() {
		var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$"); 	// 정규식 추가, 안되는 확장자 체크
		var maxSize = 10485760;
		var files = $("input[name='uploadFiles']")[0].files;
		
		for(var i=0; i<files.length; i++) {
			var fileName = files[i].name;
			var fileSize = files[i].size;
			var ext = fileName.split(".").pop().toLowerCase();
			
			console.log("fileName : " + fileName);
			console.log("fileSize : " + fileSize);
			console.log("확장자 : " + ext);
			console.log("확장자 : " + (regex.test(fileName ? "ok" : "no")));
			
			if(fileSize >= maxSize) {
				alert("파일 사이즈는 10mb를 초과할 수 없습니다.");
				return false;
			}
			if(regex.test(fileName)) {
				alert("해당 확장자는 업로드 할 수 없습니다.");
				return false;
			}
			
			// 특정 확장자 체크, 주석 처리 가능
			if($.inArray(ext, ['gif' , 'png', 'jpg', 'jpeg' ]) == -1) {
				alert("gif, png, jpg, jpeg 파일만 업로드 할 수 있습니다.");
				return false;
			}
		}
		return true;
	}

		$("#boardForm").validate({
			rules: {		// 체크리스트 ex: 숫자만 입력, 브라우저 별로 다른 동작
				boTitle : { required : true },
				boContent : "required"
			},
			messages: { 	// 보여지는 내용
				boTitle: "제목을 입력하세요.",
				boContent : "내용을 입력하세요."
			},
			submitHandler: function (frm) {
				if(!checkFiles()) {	// 첨푸파일 확장자 사이즈 체크
					return false;
				}else{
				doSubmit();	// 유효성 검사 통과
				}
			},
			success : function (e) {
				// 생략가능
			}
	});	// end validate
}); 		// end document ready

function doSubmit() {
	var frm = document.boardForm;
			
	<c:if test="${board.boSeqNo == '0' }">
		frm.action = "boardInsert";
	</c:if>
	
	<c:if test="${board.boSeqNo != '0' }">
		frm.action = "boardUpdate";
	</c:if>
		
	frm.submit();
}
function golist() {
	location.href="boardList?boType=BBS";
}
			
</script>


<title>게시글 작성</title>
</head>
<body>

<div class="container">
	<h1 align="center">게시글 생성 및 수정 </h1>
	
	<form name="boardForm" id="boardForm" method="post" enctype="multipart/form-data" >
		<input type="hidden" name="boType" value="BBS">
		<input type="hidden" name="boSeqNo" value="${board.boSeqNo}">
		
		<table class="table">
			<tr>
				<td>제목</td>
				<td><input type="text" name="boTitle" id="boTitle" size="100" value="${board.boTitle}"></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>
				<input type="hidden" name="boWriter" size="20" value="${board.boWriter}">
				<input type="text" name="boWriterName" size="20"  value="${board.boWriterName}" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td>공개여부</td>
				<td><label for="openYn"><input type="checkbox" name="boOpenYn" id="openYn" value="Y" 
				${board.boOpenYn == 'N' ? '' : 'checked' } > 공개</label></td>
			</tr>
			
			<!-- 첨부 파일 관련 -->
			<tr>
				<td>첨부파일</td>
				<td>
					<!-- 업로드된 파일 목록 -->
					<p>
					<c:forEach var="fileItem" items="${board.fileList}">
						<div>
							<a href="${pageContext.request.contextPath}/common/download?fileSeqNo=${fileItem.fileSeqNo}">${fileItem.fileName}</a>${fileItem.fileFancySize}
							<button type="button" class="btn btn-danger btn-xs btn-delete-exist" data-file_seq_no="${fileItem.fileSeqNo}" >x</button>
						</div>
					</c:forEach>
					</p>
					
					<p><button type="button" class="btn btn-primary btn-xs btn-new-file">추가</button> 
					<div id="fileList"> 
						<div>
							<input type="file" name="uploadFiles" multiple="multiple" style="display: inline;" >
							<button type="button" class="btn btn-danger btn-xs btn-delete-file">X</button>
						</div>
					</div>
				</td>
			</tr>			
			<!-- //첨부 파일 관련 -->
			
			
			<tr>
				<td>내용</td>
				<td><textarea name="boContent" id="boContent" rows="15" cols="100">${board.boContent}</textarea></td>
			</tr>

		</table>
		
		<p align="center">
			
			<input type="submit" value="저장" class="btn btn-primary" >
			<input type="reset" value="취소" class="btn btn-primary">
			<input type="button" value="목록" class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/board/boardList?boType=BBS'">
		</p>		
	
	</form>

</div>

</body>
</html>