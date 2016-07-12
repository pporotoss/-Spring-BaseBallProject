<%@page import="com.baseball.team.model.domain.Team"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
	List<Team> teamList = (List)request.getAttribute("teamList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>회원가입</title>
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
	var dupleID;
	var dupleNick;
	
	window.onload = function() {
		dupleID = document.getElementById("dupleID");
		dupleNick = document.getElementById("dupleNick");
	};


	function goback(){	// 뒤로가기
		history.back();
	}
	
	// 회원 가입하기!!
	function regist(){
		
		// id 글자수 확인
		if(registForm.id.value.length < 4 || registForm.id.value.length > 15){
			alert("ID글자수를 확인해주세요.");
			registForm.id.focus();
			return;
		}
		
		// id 중복검사여부 확인
		if(dupleID.value == "true"){
			alert("ID 중복검사를 해주세요.");
			registForm.id.focus();
			return;
		}
		
		
		// password 글자수 확인
		if(registForm.pwd.value.length < 4 || registForm.pwd.value.length > 15){
			alert("비밀번호 글자수를 확인해주세요.");
			registForm.pwd.focus();
			return;
		}
		
		// password 일치여부 확인
		if(registForm.pwd.value != registForm.repwd.value){
			alert("비밀번호가 일치하지 않습니다.");
			registForm.repwd.focus();
			return;
		}
		
		// 이름 공백여부확인
		if(registForm.username.value.length < 1){
			alert("이름을 입력해주세요.");
			registForm.username.focus();
			return;
		}
		
		// 별명 입력여부 확인
		if(registForm.nickname.value.length < 1){
			alert("별명을 입력해주세요.");
			registForm.nickname.focus();
			return;
		}
		
		// 별명 중복검사여부 확인
		if(dupleNick.value == "true"){
			alert("별명 중복검사를 해주세요.");
			registForm.nickname.focus();
			return;
		}
		
		// 이메일 입력확인
		if(registForm.email.value.length < 1){
			alert("이메일을 입력해주세요.");
			registForm.email.focus();
			return;
		}
		
		// 이메일 양식확인
		if(!isEmail(registForm.email.value)){
			alert("올바른 이메일을 입력해 주세요.");
			registForm.email.focus();
			return;
		}
		
		// 팀 선택여부 확인
		if(registForm.team_id.value == 0){
			alert("응원하는 팀을 선택해 주세요.");
			registForm.team_id.focus();
			return;
		}
		
		registForm.action="/view/member/regist";
		registForm.method="POST";
		registForm.submit();
		
	}// 폼전송.
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	// 이메일 양식확인 메서드
	function isEmail(email){
		// 이메일 확인용 정규식
		var regExp = /\w+@\w+\.\w+/;
		
		// 정규식과 일치여부 반환.
		return regExp.test(email);
	}
	
	// 아이디 중복 확인
	function idChk(){
		
		// id 글자수 확인
		if(registForm.id.value.length < 4 || registForm.id.value.length > 15){
			alert("ID글자수를 확인해주세요.");
			registForm.id.focus();
			return;
		}
		
		$.getJSON("/api/member/chkid/"+registForm.id.value, function(result){
			alert(result.msg);
			dupleID.value=result.result;
	    });
	}
	
	// 별명 중복 확인
	function nickChk(){
		// 별명 입력여부 확인
		if(registForm.nickname.value.length < 1){
			alert("별명을 입력해주세요.");
			registForm.nickname.focus();
			return;
		}
		
		$.getJSON("/api/member/chknick/"+registForm.nickname.value, function(result){
			alert(result.msg);
			dupleNick.value=result.result;
	    });		
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
	      <h1>회원가입</h1>
	      	<input type="hidden" id="dupleID" value="true">
	      	<input type="hidden" id="dupleNick" value="true">
	      	<form name="registForm">
		      	<div class="form-group">
				  <label for="usr">ID:</label>
				  <div class="form-inline">
					  <input type="text" class="form-control" id="usr" maxlength="15" placeholder="아이디는 4~15자 이내로 입력해주세요." name="id" style="width:85%">
					  <input type="button" class="btn" value="ID중복확인" onClick="idChk()">
				  </div>
				</div>
				<div class="form-group">
				  <label for="pwd">Password:</label>
				  <input type="password" class="form-control" id="pwd" maxlength="15" placeholder="패스워드는 4~15자 이내로 입력해주세요." name="pwd">
				</div>
				<div class="form-group">
				  <label for="repwd">Password 확인:</label>
				  <input type="password" class="form-control" id="repwd" maxlength="15" placeholder="패스워드를 다시한번 입력해주세요." name="repwd">
				</div>
		      	<div class="form-group">
				  <label for="username">이 름:</label>
				  <input type="text" class="form-control" id="username" maxlength="10" name="username">
				</div>
		      	<div class="form-group">
				  <label for="nickname">별 명:</label>
				  <div class="form-inline">
					  <input type="text" class="form-control" id="name" maxlength="10" name="nickname" placeholder="별명은 10자 이내로 입력해주세요." style="width:85%">
					  <input type="button" class="btn" value="별명중복확인" onClick="nickChk()">
				  </div>
				</div>
		      	<div class="form-group">
				  <label for="email">E-mail:</label>
				  <input type="text" class="form-control" id="email" maxlength="40" placeholder="example : abcd@abc.com" name="email">
				</div>
		      	<div class="form-group">
				  <label for="team_id">응원팀:</label>
				  <select class="form-control" id="team_id" name="team_id">
				    <option value="0">팀선택</option>
				    <%for(int tt = 0; tt < teamList.size(); tt++){ %>
				    	<%Team team = teamList.get(tt); %>
				    	<option value="<%=team.getTeam_id()%>"><%=team.getName() %></option>
				    <%} %>
				  </select>
				</div>
			</form>
	      	<div class="form-group">
			  <label></label>
			  <input type="button" class="btn btn-primary" value="회원가입" onClick="regist()">
			  <input type="button" class="btn btn-danger" value="취소" onClick="goback()">
			</div>
			
	    </div>
	<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
	    <div class="col-sm-3 sidenav" style="background-color:white">
	    </div>
	  </div>
	</div>

</body>
</html>
