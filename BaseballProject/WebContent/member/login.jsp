<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><!DOCTYPE html>
<html>
<head>
  <title>로그인</title>
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
  	$(document).ready(function(){
  		$("#userId").keyup(function(event){
  			if(event.which == 13){		// 엔터키 눌렀다가 띄면,
  				login();
  			}
  		});
  		$("#pwd").keyup(function(event){
  			if(event.which == 13){		// 엔터키 눌렀다가 띄면,
  				login();
  			}
  		});
  	});

   	 function login(){
		  		
   		var param1 = $("#userId").val().trim();
   		var param2 = $("#pwd").val().trim();
   		if (!$('#rememberId').is(":checked")){
   			$("#rememberId").val("");
		}
   		var param3 = $("#rememberId").val();
   		 
  		// 빈칸검사
  		if(param1.length < 1){
  			alert("아이디를 입력해주세요.");
  			 $("#userId").focus();
  			return;
  		}
  		if(param2.length < 1){
  			alert("비밀번호를 입력해주세요.");
  			$("#pwd").focus();
  			return;
  		}
  		  		
  		$.get("/api/member/login",{id:param1, pwd:param2, rememberId:param3})
  			.done(function(data){	// 성공하면,
	  			if(data == ""){
	  	        	alert("아이디 또는 비밀번호를 확인해 주세요.");
	  	        }else{
	  	        	location.href=data;	// 아이디 비번 일치하면 이전에 보던 페이지로 이동.
	  	        }
  	    	})
  	    	.fail(function() {	// 오류나면,
  	    		location.href="/error.html"
  	    	});
  		
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
	    <h1 align="center">로그인</h1>
	    <form name="loginForm">
		    <div class="form-group">
			    <label class="control-label" for="userId">아이디 :</label>
			    <input type="text" class="form-control" id="userId" placeholder="아이디를 입력해주세요." name="id" value='<c:if test="${rememberId != null }">${rememberId }</c:if>'>
	    	</div>
		    <div class="form-group">
			    <label class="control-label" for="pwd">비밀번호 :</label>
			    <input type="password" class="form-control" id="pwd" placeholder="비밀번호를 입력해주세요." name="pwd">
	    	</div>
	    	<div class="checkbox">
				    <label>
				      <input type="checkbox" id="rememberId" name="rememberId" <c:if test="${rememberId != null }">checked</c:if> > 아이디 저장하기
				    </label>
			</div>
	    	<a href="/view/member/find_user">아이디/비밀번호 찾기</a><br>
	    	<br>
		    <div class="form-group">
		    	<label class="control-label"></label>        
		   		<input type="button" class="btn btn-success" value="로그인" onClick="login()">
		   		<input type="button" class="btn btn-danger" value="취소" onClick="history.back()">
		    </div>
	    </form>
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-4 sidenav" style="background-color:white">
    </div>
  </div>
</div>

</body>
</html>
