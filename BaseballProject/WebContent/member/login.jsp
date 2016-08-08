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
	 <c:if test="${msg != null }">
		alert("${msg}");
	 </c:if>

   	 function login(){
  		
  		// 빈칸검사
  		if(loginForm.id.value.length < 1){
  			alert("아이디를 입력해주세요.");
  			loginForm.id.focus();
  			return;
  		}
  		if(loginForm.pwd.value.length < 1){
  			alert("비밀번호를 입력해주세요.");
  			loginForm.pwd.focus();
  			return;
  		}
  		
  		loginForm.action="/view/member/login";
  		loginForm.method="POST";
  		loginForm.submit();
  	}
  	
  	function back(){
  		history.back();
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
			    <label class="control-label" for="id">ID :</label>
			    <input type="text" class="form-control" id="id" placeholder="아이디를 입력해주세요." name="id">
	    	</div>
		    <div class="form-group">
			    <label class="control-label" for="pwd">비밀번호 :</label>
			    <input type="password" class="form-control" id="pwd" placeholder="비밀번호를 입력해주세요." name="pwd">
	    	</div>
		    <div class="form-group">
		    	<label class="control-label"></label>        
		   		<input type="button" class="btn btn-success" value="로그인" onClick="login()">
		   		<input type="button" class="btn btn-danger" value="취소" onClick="back()">
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
