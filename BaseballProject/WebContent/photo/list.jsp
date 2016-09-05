<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>사진게시판</title>
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
<body>

<%@ include file="/include/topnav.jsp" %>
  
<div class="container-fluid text-center">    
  <div class="row content">
    <div class="col-sm-2 sidenav" style="background-color:white">
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-8 text-left"> 
      <h1>사진게시판  &nbsp;&nbsp;<c:if test="${loginMember != null }"><input type="button" value="사진올리기" class="btn btn-warning" onClick="location.href='/photo/upload.jsp'" ></c:if></h1> 
      <br>
      <br>
      <div class="row">
      	
      		<div class="col-sm-3">
				<a href="pulpitrock.jpg" class="thumbnail">
					<div class="panel panel-default">
						<div class="panel-body">
							제  목[댓글수]
						</div>
					</div>
					<img src="pulpitrock.jpg" alt="Pulpit Rock" style="width:150px;height:150px">
				</a>
				<div class="panel panel-default">
						<div class="panel-body">
							<table>
								<tr>
									<td>작성일</td>
								</tr>
								<tr>
									<td>작성자</td>
								</tr>
							</table>
					</div>
				</div>
      		</div>
      		
      		
    	</div>
<!------------------------ 페이징 --------------------------------->
      			
      </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-2 sidenav" style="background-color:white">
    </div>
  </div>
</div>

</body>
</html>
