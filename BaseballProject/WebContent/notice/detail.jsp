<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>공지사항</title>
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
  	function update(){
  		detailForm.action="/view/notice/${notice.notice_id}";
  		detailForm.method="POST";
  		detailForm.submit();
  	}
  	
  	
  	function del(){
  		detailForm._method.value="DELETE";
  		
  		detailForm.action="/view/notice/${notice.notice_id}";
  		detailForm.method="POST";
  		detailForm.submit();
  	}
  </script>
</head>
<body>

<%@ include file="/include/topnav.jsp" %>
  
<div class="container-fluid text-center">    
  <div class="row content">
    <div class="col-sm-3 sidenav" style="background-color:white">
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-6 text-left"> 
      <h1>상세보기</h1>
	      <br>
	      	<form name="detailForm">
	      		<input type="hidden" value="${notice.notice_id}" name="notice_id">
	      		<input type="hidden" value="" name="_method">
	      		
			    <div class="form-group">
			    	<label for="writer" class="control-label">작성자 : </label>
		      		<input type="text" class="form-control" id="writer" value="${notice.writer }" readonly>
				</div>
			    <div class="form-group">
				    <label for="title" class="control-label">제 목 :</label>
		      		<input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력해 주세요." value="${notice.title }" readonly>
				</div>

				<div class="panel panel-default" style="height:300px">
				  <div class="panel-body">
				    	${notice.content }
				  </div>
				</div>		
			</form>
	      	<div class="form-group" align="right">
	      		<c:if test="${loginMember.level_id == 4 }">
	      			<input type="button" class="btn btn-warning" value="수정" onClick="update()">
		  			<input type="button" class="btn btn-danger" value="삭제" onClick="del()">
	      		</c:if>
			  <a href="/view/notice"><input type="button" class="btn btn-default" value="목록"></a>
			</div>
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-3 sidenav" style="background-color:white">
    </div>
  </div>
</div>

</body>
</html>
