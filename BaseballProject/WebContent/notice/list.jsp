<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
  
</head>
<body>

<%@ include file="/include/topnav.jsp" %>
  
<div class="container-fluid text-center">    
  <div class="row content">
    <div class="col-sm-2 sidenav" style="background-color:white">
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-8 text-left"> 
      <h1>&nbsp;&nbsp;&nbsp;공지사항</h1>
      <table class="table table-hover table-responsive">
	    <thead>
	      <tr>
	        <th width="5%" style="text-align:center">번 호</th>
	        <th width="60%" style="text-align:center" colspan="2">&nbsp;제 목</th>
	        <th width="15%">&nbsp;글쓴이</th>
	        <th width="15%">&nbsp;작성일</th>
	        <th width="10%" style="text-align:center">조회수</th>
	      </tr>
	    </thead>
	    <tbody>
			<c:forEach items="${noticeList}" var="notice">
				<tr>
					<td style="text-align:center">${notice.notice_id }</td>
					<td width="5%"><!-- 빈칸 --></td>
					<td><a href="/view/notice/${notice.notice_id }">${notice.title }</a></td>
					<td>&nbsp;<img src="/images/member/관리자.png">${notice.writer }</td>
					<td>&nbsp;${notice.regdate }</td>
					<td style="text-align:center">${notice.hit }</td>
				</tr>
			</c:forEach>
			
	    </tbody>
	  </table>
	  	<c:if test="${loginMember.rank == 1 }">
	  		<div align="right">
		  	<a href="/view/notice/write"><input type="button" value="글쓰기" class="btn btn-info"></a>
		  </div>
	  	</c:if>
	  	<!-----------------  페이징 ------------------------------------ -->
	  	<c:set var="url" value="/view/notice?page="/>
 		<c:set var="url1" value="&pagesize=${pager.pageSize}"/>
 		
 		<%@ include file="/include/paging.jsp" %>
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-2 sidenav" style="background-color:white">
    </div>
  </div>
</div>

</body>
</html>
