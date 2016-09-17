<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>회원정보</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <style>
    /* Remove the navbar's default margin-bottom and rounded borders */ 
    .navbar {
      margin-bottom: 0;
      border-radius: 0;
    }
    
    /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
    .row.content {height: 450px}
    
    /* Set gray background color and 100% height */
    .sidenav {
      padding-top: 20px;
      background-color: #f1f1f1;
      height: 100%;
    }
    
    /* Set black background color, white text and some padding */
    footer {
      background-color: #555;
      color: white;
      padding: 15px;
    }
    
    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height:auto;} 
    }
  </style>
</head>
<script>
	var pwdFlag = false;	// 비번 수정여부 확인용.
	
	$(document).ready(function(){
		$("#dupleNick").change(function(){
			$("#dupleNick").val("true");	
		});
	});

	// 수정하기!!
	function update(){
		
		// password 글자수 확인
		if(updateForm.pwd.value.trim().length < 4 || updateForm.pwd.value.trim().length > 15){
			if(!$("#pwd").attr("disabled")){
				
				alert("비밀번호 글자수를 확인해주세요.");
				updateForm.pwd.focus();
				return;
			}
		}
		
		// password 일치여부 확인
		if(updateForm.pwd.value.trim() != updateForm.repwd.value.trim()){
			if(!$("#repwd").attr("disabled")){
				alert("비밀번호가 일치하지 않습니다.");
				updateForm.repwd.focus();
				return;
			}
		}
		
		// 이름 공백여부확인
		if(updateForm.username.value.trim().length < 1){
			alert("이름을 입력해주세요.");
			updateForm.username.focus();
			return;
		}
		
		// 별명 입력여부 확인
		if(updateForm.nickname.value.trim().length < 1){
			alert("별명을 입력해주세요.");
			updateForm.nickname.focus();
			return;
		}
		
		// 별명 중복검사여부 확인
		if($("#dupleNick").val() == "true"){
			alert("별명 중복검사를 해주세요.");
			updateForm.nickname.focus();
			return;
		}
		
		// 이메일 입력확인
		if(updateForm.email.value.length < 1){
			alert("이메일을 입력해주세요.");
			updateForm.email.focus();
			return;
		}
		
		// 이메일 양식확인
		if(!isEmail(updateForm.email.value)){
			alert("올바른 이메일을 입력해 주세요.");
			updateForm.email.focus();
			return;
		}
		
		// 팀 선택여부 확인
		if(updateForm.team_id.value == 0){
			alert("응원하는 팀을 선택해 주세요.");
			updateForm.team_id.focus();
			return;
		}
		
		
		if(!confirm("수정하시겠습니까?")){
			
			return;
		}
		updateForm._method.value="PUT";
		updateForm.action="/view/member/myinfo/${memberDetail.member_id}";
		updateForm.method="POST";
		updateForm.submit();
		
	} // update()
	
	function deleteMemer(){	// 회원탈퇴
		
		if(!confirm("탈퇴하시겠습니까?")){
			return;
		}
		
	
		$.ajax({
	  		type:"DELETE",	// 요청방식
	  		url:"/api/member/myinfo/${memberDetail.member_id}",
	  		headers:{	// 헤더값 세팅.
	  		"Content-Type":"application/json",
	  		"X-HTTP-Method-Override":"DELETE"
	  		 },
  			dataType:"text",
  			success:function(data){
  			   if(data != 0){
  				   alert("탈퇴되었습니다.");
  				   location.href="/";
  			   }else{
  				   alert("실패하였습니다.");
  			   }
	       }
		});
		/* updateForm._method.value="DELETE";
		updateForm.action="/view/member/myinfo/${memberDetail.member_id}";
		updateForm.method="POST";
		updateForm.submit(); */
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	// 이메일 양식확인 메서드
	function isEmail(email){
		// 이메일 확인용 정규식
		var regExp = /\w+@\w+\.\w+/;
		
		// 정규식과 일치여부 반환.
		return regExp.test(email);
	}
	
	
	// 별명 중복 확인
	function nickChk(){
		
		// 별명 변경여부 확인
		if(updateForm.nickname.value.trim() == "${memberDetail.nickname}"){
			return;
		}
		
		// 별명 입력여부 확인
		if(updateForm.nickname.value.trim().length < 1){
			alert("별명을 입력해주세요.");
			updateForm.nickname.focus();
			return;
		}
		
		$.getJSON("/api/member/chknick/"+updateForm.nickname.value, function(result){
			alert(result.msg);
			$("#dupleNick").val(result.result);
	    });		
	}
	
	// 비번 변경 활성화
	function editPwd(){
		
		if(pwdFlag){
			
			$("#pwdBtn").val("비밀번호 변경하기");
			$("#pwd").val("");
			$("#repwd").val("");
			$("#pwd").attr("disabled", true);
			$("#repwd").attr("disabled", true);
			$("#pwd").removeAttr("placeholder");
			$("#repwd").removeAttr("placeholder");
			pwdFlag = false;
			
		}else{
			
			$("#pwdBtn").val("변경 취소");
			$("#pwd").attr("disabled", false);
			$("#repwd").attr("disabled", false);
			$("#pwd").attr("placeholder","패스워드는 4~15자 이내로 입력해주세요.");
			$("#repwd").attr("placeholder","패스워드를 다시한번 입력해주세요.");
			pwdFlag = true;
			
		}
		
	}
</script>
<body>

<%@ include file="/include/topnav.jsp" %>
  
	<div class="container-fluid text-center">    
	  <div class="row content">
	    <div class="col-sm-3 sidenav" style="background-color:white">
	    </div>
	<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
	    <div class="col-sm-6 text-left"> 
	      <h1>회원정보</h1>
	      	<form name="updateForm">
		      	<input type="hidden" name="_method" value="">
		      	<input type="hidden" id="dupleNick" value="false">
		      	<div class="form-group">
				  <label for="usr">ID:</label>
					<input type="text" class="form-control" id="usr" maxlength="15" name="id" readonly value="${memberDetail.id }">
				</div>
		      	<div class="form-group">
				  <label for="">회원등급: <img src='/images/member/${memberDetail.levelname }.png'></label>
					<input type="text" class="form-control" readonly value="${memberDetail.levelname }">
				</div>
				<div class="form-group">
				  <label for="pwd">Password:</label> <input type="button" id="pwdBtn" class="btn btn-warning btn-xs" value="비밀번호 변경하기" onClick="editPwd()">
				  <input type="password" class="form-control" id="pwd" maxlength="15" name="pwd" disabled>
				</div>
				<div class="form-group">
				  <label for="repwd">Password 확인:</label>
				  <input type="password" class="form-control" id="repwd" maxlength="15" name="repwd" disabled>
				</div>
				<div class="form-group">
				  <label for="pwdHintQuestion">비밀번호 질문:</label>
				  <input type="text" class="form-control" id="pwdHintQuestion" maxlength="20" name="pwdHintQuestion" value="${memberDetail.pwdHintQuestion }">
				</div>
		      	<div class="form-group">
				  <label for="pwdHintQuestion">비밀번호 답:</label>
				  <input type="text" class="form-control" id="pwdHintQuestion" maxlength="20" name="pwdHintAnswer" value="${memberDetail.pwdHintAnswer }">
				</div>
		      	<div class="form-group">
				  <label for="username">이 름:</label>
				  <input type="text" class="form-control" id="username" maxlength="10" name="username" value="${memberDetail.username }">
				</div>
		      	<div class="form-group">
				  <label for="nickname">별 명:</label>
				  <div class="form-inline">
					  <input type="text" class="form-control" id="name" maxlength="10" name="nickname" value="${memberDetail.nickname }" style="width:85%">
					  <input type="button" class="btn" value="별명중복확인" onClick="nickChk()">
				  </div>
				</div>
		      	<div class="form-group">
				  <label for="email">E-mail:</label>
				  <input type="text" class="form-control" id="email" maxlength="40" value="${memberDetail.email }" name="email">
				</div>
		      	<div class="form-group">
				  <label for="team_id">응원팀:</label>
				  <select class="form-control" id="team_id" name="team_id">
				    <option value="0">팀선택</option>
				    <c:forEach items="${teamList }" var="team">
				    	<option value="${team.team_id }" <c:if test="${team.name.equals(memberDetail.teamname) }">selected</c:if> >${team.name }</option>
				    </c:forEach>
				  </select>
				</div>
			</form>
	      	<div class="form-group">
			  <label></label>
			  <input type="button" class="btn btn-primary" value="즉시수정하기" onClick="update()">
			  <input type="button" class="btn btn-danger" value="탈퇴하기" onClick="deleteMemer()">
			  <input type="button" class="btn btn-default" value="취소" onClick="history.back()">
			</div>
			
	    </div>
	<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
	    <div class="col-sm-3 sidenav" style="background-color:white">
	    </div>
	  </div>
	</div>

</body>
</html>
