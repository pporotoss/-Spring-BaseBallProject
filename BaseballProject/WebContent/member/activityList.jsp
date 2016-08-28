<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <title>활동내역</title>
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
	  function goFreeBoard(){
		  $("li").removeClass("active");
		  $("#freeBoard").addClass("active");
	  }
	  
	  function goFreeComment(){
		  $("li").removeClass("active");
		  $("#freeComment").addClass("active");
	  }
	  
	  function goPhoto(){
		  $("li").removeClass("active");
		  $("#photo").addClass("active");
	  }
	  
	  function goPhotoComment(){
		  $("li").removeClass("active");
		  $("#photoComment").addClass("active");
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
      <h1>활동내역</h1>
      <ul class="nav nav-tabs">
		<li role="presentation" class="active" id="freeBoard"><a href="javascript:goFreeBoard()">자유게시판</a></li>
		<li role="presentation" id="freeComment"><a href="javascript:goFreeComment()">자유게시판 댓글</a></li>
		<li role="presentation" id="photo"><a href="javascript:goPhoto()">사진게시판</a></li>
		<li role="presentation" id="photoComment"><a href="javascript:goPhotoComment()">사진게시판 댓글</a></li>
	  </ul>
      <table class="table table-hover">
		    <thead>
		      <tr>
		         <tr>
			        <th width="5%" style="text-align:center">번 호</th>
			        <th width="60%" style="text-align:center" colspan="2">&nbsp;제 목</th>
			        <th width="15%">&nbsp;작성일</th>
			        <th width="10%" style="text-align:center">조회수</th>
			      </tr>
		      </tr>
		    </thead>
		    <tbody>
		      <c:forEach items="${freeBoardList }" var="boardDetail">
			      <tr>
			        <td style="text-align:center">${boardDetail.board_id }</td>
			        <td width="10%"><!-- 빈칸 --></td>
			        <td><a href="/view/board/${boardDetail.board_id }?page=${pager.page}&pagesize=${pager.pageSize}"> ${boardDetail.title }</a></td>
			        <td><fmt:formatDate value="${boardDetail.regdate }" type="both" pattern="yyyy.MM.dd hh:mm:ss"/></td>
			        <td style="text-align:center">${boardDetail.count }</td>
			      </tr>
		      </c:forEach>
		    </tbody>
		  </table>
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-3 sidenav" style="background-color:white">
    </div>
  </div>
</div>

</body>
</html>
