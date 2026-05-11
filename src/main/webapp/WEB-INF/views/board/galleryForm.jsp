<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
<head>


<title>Insert title here</title>

<script>
$(document).ready(function(){
	$("#boTitle").focus();
	
	$frm = $("#galleryForm"); //var frm = document.galleryForm;
	
	//insert 파일 추가
	$(".btn-new-file", $frm).click(function(){
		$("#fileList").append(
			'<div>' + 
			'<input type="file" name="uploadFiles" multiple="multiple" style="display: inline;" >' +
			'<button type="button" class="btn btn-danger btn-xs btn-delete-file">x</button>' +
			'</div>'	
		);		
	});	
	
	//insert신규 파일 삭제
	$("#fileList").on("click", ".btn-delete-file", function(){
		$(this).parent().remove();
	});
	
	//기존 파일 삭제(수정, 삭제할 때)
	$(".btn-delete-exist").click(function(){
		$(this).parent().html('<input type="hidden" name="delFileSeq" value="' +
				$(this).data("file_seq_no") + '">')
	});
	
	function checkFiles(){
		var maxSize = 10485760; //약10mb, 파일의 최대 사이즈
		
		if(!$(".btn-delete-exist").length){
			if(!$("input[name='uploadFiles']").length || !$("input[name='uploadFiles']").val()){
				alert("이미지를 하나 이상 업로드 해주세요");
				return false;
			}
		}
		
		var files = $("input[name='uploadFiles']")[0].files;
		
		for(var i=0; i<files.length; i++){
			var fileName = files[i].name;
			var fileSize = files[i].size;
			var ext = fileName.split(".").pop().toLowerCase(); // .으로 잘라서 맨 마지막 값만 가져오기
			
			if(fileSize >= maxSize){
				alert("파일 사이즈는 10mb를 초과할 수 없습니다.");
				return false;
			}
			
			if($.inArray(ext, ['gif', 'png', 'jpg', 'jpeg']) == -1){
				alert("gif, png, jpg, jpeg 파일만 업로드 할 수 있습니다.");
				return false;
			}
		}	
		
		return true;		
	}
	
	$('#galleryForm').validate({
		rules : { //체크할 항목들, 정규식 지정, ex:숫자만 입력, 브라우저 별로 다른 동작 등 
			boTitle : {required : true},
			boContent : "required"
		},
		messages : { //보여주는 내용
			boTitle : "제목을 입력하세요",
			boContent : "내용을 입력하세요"
		},
		submitHandler : function(frm){
			if(!checkFiles()) { //첨부파일 확장자, 사이즈 체크
				return false;
			}else{
				doSubmit(); //유효성 검사를 통과 시 전송
			}
		},
		success : function(e){
			//생략가능
		}
		
	});//end validate
	
}); //end document ready 

function doSubmit(){
	var frm = document.galleryForm;
	
	<c:if test="${gallery.boSeqNo == '0' }">
		frm.action = "galleryInsert";
	</c:if>
	
	<c:if test="${gallery.boSeqNo != '0' }">
		frm.action = "galleryUpdate";
	</c:if>
	
	frm.submit();
}

function goList(){
	location.href="galleryList?boType=GALLERY";
}

</script>


</head>
<body>


<div class="container">
	<h2 align="center">게시글 등록</h2>
	
	<form name="galleryForm" id="galleryForm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="boType" value="GALLERY">
		<input type="hidden" name="boSeqNo" value="${gallery.boSeqNo }">
		
		<table class="table">
		
			<tr>
				<td>제목</td>
				<td><input type="text" name="boTitle" id="boTitle" size="100" value="${gallery.boTitle }"></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>
				<input type="hidden" name="boWriter" id="boWriter" size="20" value="${gallery.boWriter }">
				<input type="text" name="boWriterName" id="boWriterName" size="20" value="${gallery.boWriterName }" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td>공개여부</td>
				<td><label for="openYn"><input type="checkbox" name="boOpenYn" id="openYn" value="Y" ${gallery.boOpenYn == 'N' ? '' : 'checked' }> 공개</label></td>
			</tr>
			
			<!-- 첨부 파일 관련 -->
			<tr>
				<td>첨부파일</td>
				<td>
					<!-- 업로드된 파일 목록 -->
					<p>
					<c:forEach var="fileItem" items="${gallery.fileList }">
						<div>
							<a href="${pageContext.request.contextPath }/common/download?fileSeqNo=${fileItem.fileSeqNo}">${fileItem.fileName }</a>${fileItem.fileFancySize}
							<button type="button" class="btn btn-danger btn-xs btn-delete-exist" data-file_seq_no="${fileItem.fileSeqNo }">x</button>
						</div>					
					</c:forEach>
					</p>
					
					<p><button type="button" class="btn btn-primary btn-xs btn-new-file">추가</button>
					<div id="fileList">
						<div>
							<input type="file" name="uploadFiles" multiple="multiple" style="display: inline;" >
							<button type="button" class="btn btn-danger btn-xs btn-delete-file">x</button>
						</div>
					</div>

					
				</td>
			</tr>			
			<!-- //첨부 파일 관련 -->
			
			<tr>
				<td>내용</td>
				<td><textarea name="boContent" id="boContent" rows="15" cols="100">${gallery.boContent}</textarea></td>
			</tr>
		
		</table>
		
		<p align="center">
			
			<input type="submit" value="저장" class="btn btn-primary" >
				
			<input type="reset" value="취소" class="btn btn-primary">
			<input type="button" value="목록" class="btn btn-primary" onclick="goList();">
		</p>		
	
	</form>

</div>

</body>
</html>