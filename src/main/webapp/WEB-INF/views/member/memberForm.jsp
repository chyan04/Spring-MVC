<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.css"
	rel="stylesheet" />
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

<title>회원가입</title>
<script>
// 아이디 중복 체크 변수

var isDuplicateCheck = false;

$(function() {
	$ ("#btn_idCheck").click(function() {
		idCheck();
	});
	
	function idCheck() {

		var frm = document.memberForm;
		var params = {"memId" : frm.memId.value};
		
		if(frm.memId.value == ""){
			alert("아이디를 입력하세요.");
			return;
		}

		$.ajax({
			type : 'post',
			url : "memberExists",
			data : params,
			success : function(data, status) {
				isDuplicateCheck = data.result;
				
				if(data.result == "true"){
					$("#lbl_check").text(" 해당 아이디는 사용중 입니다.");
					isDuplicateCheck = false;
				}else{
					$("#lbl_check").text(" 해당 아이디는 사용 가능 합니다.");
					isDuplicateCheck = true;
				}
			},
			error : function(error) {
				console.log(error);
				console.log(error.status);
			}
		
		});	// ajax end
		
	}
			
	
	
	
}); // function end

	function doSubmit(type) {
		// type 1 = 가입, 2 = 수정, 3 = 삭제
		if (type == 2 || type == 3) {
			isDuplicateCheck = true;
		}
			
		// validation 체크
		if (!validate()) {
			return false;
		}

		var frm = document.memberForm;

		if (type == 1) {
			frm.action = "memberInsert";
		} else if (type == 2) {
			frm.action = "memberUpdate";
		} else if (type == 3) {
			frm.action = "memberDelete";
		}
		
		frm.submit();
	}
	
	function validate() {
		// 유효성 검사
		var frm = document.memberForm;

		if (frm.memName.value == "") {
			alert("이름을 입력하세요");
			frm.memName.focus();
			return false;
		}

		if (frm.memId.value == "") {
			alert("아이디를 입력하세요");
			frm.memId.focus();
			return false;
		}
		
		if (!isDuplicateCheck) {
			alert("아이디 중복 체크를 해주세요");
			return false;
		}

		if (frm.memPwd.value == "") {
			alert("새로운 비밀번호를 입력하세요");
			frm.memPwd.focus();
			return false;
		} else 
			if (frm.memPwdConfirm.value == "") {
				alert("새로운 비밀번호 확인을 입력하세요");
				frm.memPwdConfirm.focus();
				return false;
			} else 
				if (frm.memPwd.value != frm.memPwdConfirm.value) {
					alert("비밀번호가 일치하지 않습니다");
					return false;
				}
		if (frm.memBirth.value == "") {
			alert("생년월일을 입력하세요");
			frm.memBirth.focus();
			return false;
		}
		if (frm.memPhone.value == "") {
			alert("전화번호를 입력하세요");
			frm.memPhone.focus();
			return false;
		}
		if (frm.memEmail.value == "") {
			alert("이메일을 입력하세요");
			frm.memEmail.focus();
			return false;
		}
		return true;
	}
</script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    //document.getElementById("sample6_extraAddress").value = extraAddr;
                
                } else {
                    //document.getElementById("sample6_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('memZipcode').value = data.zonecode;
                document.getElementById("memAddrMaster").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("memAddrDetail").focus();
            }
        }).open();
    }
</script>

</head>
<body>
	<div>
		<form name="memberForm" method="post">
			<input type="hidden" name="memSeqNo" value="${member.memSeqNo}" />
			<table class="table table-bordered">
				<tr>
					<td width="150" align="center">이름</td>
					<td><input type="text" name="memName" size="20"
						value="${member.memName}"
						${member.memId == null ? '' : 'readonly'}> 이름을 입력해주세요.</td>
				</tr>
				<tr>
					<td align="center">아이디</td>
					<td><input type="text" name="memId" size="20"
						value="${member.memId}" ${member.memId == null ? '' : 'readonly'}>
						<c:if test="${member.memId == null}">
							<button type="button" class="btn btn-default" id="btn_idCheck">ID
								중복검사</button>
						 아이디을 입력해주세요. 8~20자리 숫자와 영문 조합<br>
						<label id="lbl_check"></label>
						</c:if></td>
				</tr>
				<tr>
					<td align="center">비밀번호</td>
					<td><input type="password" name="memPwd" size="20" value="">
						8~20자리 숫자와 영문 조합</td>
				</tr>
				<tr>
					<td align="center">비밀번호 확인</td>
					<td><input type="password" name="memPwdConfirm" size="20"
						value=""></td>
				</tr>

				<tr>
					<td align="center">생년월일</td>
					<td><input type="text" name="memBirth" size="20"
						value="${member.memBirth}"></td>
				</tr>
				<tr>
					<td align="center">전화번호</td>
					<td><input type="text" name="memPhone" size="20"
						value="${member.memPhone}"></td>
				</tr>
				<tr>
					<td align="center">이메일</td>
					<td><input type="text" name="memEmail" size="20"
						value="${member.memEmail}"></td>
				</tr>
				<tr>
					<td align="center">주소</td>
					<td>
						<p>
							<input type="text" name="memZipcode" id="memZipcode" size="5"
								value="${member.memZipcode}" readonly="readonly">
							<button type="button" class="btn-default"
								onclick="execDaumPostcode()">우편번호검색</button>
						</p>

						<p>
							<input type="text" name="memAddrMaster" id="memAddrMaster"
								size="50" value="${member.memAddrMaster}" readonly="readonly">
						</p>

						<p>
							<input type="text" name="memAddrDetail" id="memAddrDetail"
								size="50" value="${member.memAddrDetail}">
						</p>
					</td>
				</tr>

				<tr>
					<td colspan="2"><c:if test="${empty member.memId}">
							<input type="button" value="가입" class="btn btn-default"
								onclick="doSubmit(1)">
								
						</c:if> <c:if test="${not empty member.memId && member.memId == sessionScope.LOGIN_USER.memId} ">
							<input type="button" value="수정" class="btn btn-default"
								onclick="doSubmit(2)">
								
						</c:if>  
							<font color="red"> 
							<input type="button" value="삭제" class="btn btn-default"
								onclick="doSubmit(3)"> </font>
								
						 <input type="reset" value="초기화" class="btn btn-default"> 
				</tr>

			</table>
		</form>
	</div>


</body>
</html>