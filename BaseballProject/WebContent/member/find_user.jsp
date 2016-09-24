<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><!DOCTYPE html>
<html>
<head>
  <title>아이디/비밀번호찾기</title>
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
  
  <script>
  	// 아이디 찾기 탭
	function findId() {
  		
		$("#searchDIV").empty();
		$("#findPwd").removeClass("active");
  		$("#findId").addClass("active");
  		
  		var content = '<div class="form-group">';
  		content += '<label class="control-label" for="email">이메일 주소 :</label>';
  		content += '<input type="text" class="form-control" id="email" placeholder="가입하신 이메일 주소를 입력해주세요.">';
  		content += '</div>';
  		content += '<br>';
  		content += '<br>';
  		content += '<div class="form-group">';
  		content += '<input type="button" class="btn btn-success" value="아이디 찾기" onClick="searchUserId()">';
  		content += ' <input type="button" class="btn btn-danger" value="취소" onClick="history.back()">';
  		content += '</div>';
  		
  		$("#searchDIV").html(content);
	}// findId
  	
  	// 비밀번호 찾기 탭
  	function findPwd() {
		
  		$("#searchDIV").empty();
  		$("#findId").removeClass("active");
  		$("#findPwd").addClass("active");
  		
  		var content = '<div class="form-group">';
  		content += '<label class="control-label" for="searchUserId">아이디 :</label> <input type="button" class="btn btn-warning btn-xs" value="비밀번호 질문 보기" onClick="getPwdHintQuestion()">';
  		content += '<input type="text" class="form-control" id="searchUserId" placeholder="아이디를 입력해주세요.">';
  		content += '</div>';
  		content += '<div class="form-group">';
  		content += '<label class="control-label" for="pwdHintQuestion">비밀번호 질문 :</label>';
  		content += '<input type="text" class="form-control" id="pwdHintQuestion" disabled>';
  		content += '</div>';
  		content += '<div class="form-group">';
  		content += '<label class="control-label" for="pwdHintAnswer">비밀번호 답 :</label>';
  		content += '<input type="text" class="form-control" id="pwdHintAnswer" placeholder="비밀번호 답을 입력해주세요.">';
  		content += '</div>';
  		content += '<br>';
  		content += '<div class="form-group">';
  		content += '<input type="button" class="btn btn-success" value="비밀번호 찾기" onClick="searchUserPwd()">';
  		content += ' <input type="button" class="btn btn-danger" value="취소" onClick="history.back()">';
  		content += '</div>';
		
  		$("#searchDIV").html(content);
  	}
  	
  	// 아이디 찾기
   	function searchUserId(){
   		var param = $("#email").val();
   		
   		if(param.trim().length <= 0){
   			alert("이메일 주소를 입력해주세요.");
   			$("#email").focus();
   			return;
   		}
   		
   		if(!isEmail(param)){
   			alert("올바른 이메일을 입력해 주세요.");
   			return;
   		}
   		
   		$.get("/api/member/searchUserId?email="+param, function(data){
			
   			if(data == ""){
				alert("아이디가 존재하지 않습니다.");   				
   			}else{
	   			$("#searchDIV").empty();
	   			var content = '<h2>아이디 : '+data+'</h2>';
	   			content += '<br>';
	   			content += '<div class="form-group">';
	   			content += '<input type="button" class="btn btn-primary" value="로그인하러가기" onClick="goLogin()">';
	   			content += '</div>';
	   			
	   			$("#searchDIV").html(content);
   			}
   	    })
   	 	.fail(function() {	// 오류나면,
   			location.href="/error.html"
   		});
   	}
  	
  	// 비밀번호 질문 불러오기
  	function getPwdHintQuestion() {
  		
		var param = $("#searchUserId").val();
   		
		if(param.trim().length <= 0){
   			alert("아이디를 입력해주세요.");
   			$("#searchUserId").focus();
   			return;
   		}
		
		$.get("/api/member/getPwdHintQuestion?id="+param, function(data){
			
   			if(data == ""){
				alert("아이디가 존재하지 않습니다.");   				
   			}else{
   				$("#pwdHintQuestion").val(data);
   			}
   	    })
   	 	.fail(function() {	// 오류나면,
   			location.href="/error.html"
   		});
		
  	}// getPwdHintQuestion
  	
  	// 비밀번호 찾기
  	function searchUserPwd(){
  		
		var param1 = $("#searchUserId").val(); 
		var param2 = $("#pwdHintAnswer").val(); 
   		
   		if(param1.trim().length <= 0){
   			alert("아이디를 입력해주세요.");
   			$("#searchUserId").focus();
   			return;
   		}
   		
   		if(param2.trim().length <= 0){
   			alert("비밀번호 답을 입력해주세요.");
   			$("#pwdHintAnswer").focus();
   			return;
   		}
   		
   		$.get("/api/member/searchUserPwd?id="+param1+"&pwdHintAnswer="+param2, function(data){
			
   			if(data == ""){
   				alert("아이디 또는 비밀번호 답이 올바르지 않습니다.");
   			}else{
   				$("#searchDIV").empty();
   				
   				var content = '<h2>아이디 : '+param1+'</h2>';
   	   			content += '<h2>임시비밀번호 : '+data+'</h2>';
   	   			content += '<br>';
   	   			content += '<div class="form-group">';
   	   			content += '<input type="button" class="btn btn-primary" value="로그인하러가기" onClick="goLogin()">';
   	   			content += '</div>';
   	   			
   	   			$("#searchDIV").html(content);
   			}
   			
   	    })
   	    .fail(function() {	// 오류나면,
   			location.href="/error.html"
   		});
  	}
  	
  	
  	function goLogin() {
  		location.href="/view/member/loginPage";
  	}
  	
 	// 이메일 양식확인 메서드
	function isEmail(email){
		// 이메일 확인용 정규식
		var regExp = /\w+@\w+\.\w+/;
		
		// 정규식과 일치여부 반환.
		return regExp.test(email);
	}
  	
  </script>
</head>
<body>

<%@ include file="/include/topnav.jsp" %>
  
<div class="container-fluid text-center">    
  <div class="row content">
    <div class="col-sm-4 sidenav" style="background-color:white">
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-4 text-left"> 
	    <h1 align="center">아이디/비밀번호찾기</h1>
	    <ul class="nav nav-tabs">
		  <li role="presentation" class="active" id="findId"><a href="javascript:findId()">아이디찾기</a></li>
		  <li role="presentation" id="findPwd"><a href="javascript:findPwd()">비밀번호찾기</a></li>
		</ul>
		<br>
		<br>
		<div id="searchDIV">
		    <div class="form-group">
			    <label class="control-label" for="email">이메일 주소 :</label>
			    <input type="text" class="form-control" id="email" placeholder="가입하신 이메일 주소를 입력해주세요.">
	    	</div>
	    	<br>
		    <div class="form-group">
		   		<input type="button" class="btn btn-success" value="아이디 찾기" onClick="searchUserId()">
		   		<input type="button" class="btn btn-danger" value="취소" onClick="history.back()">
		    </div>
	    </div>
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-4 sidenav" style="background-color:white">
    </div>
  </div>
</div>

</body>
</html>
