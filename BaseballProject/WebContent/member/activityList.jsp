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
      	<c:if test="${freeBoardList != null }">
      		<c:set var="freeBoardMenu" value="active" />
      	</c:if>
      	<c:if test="${freeCommentList != null }">
      		<c:set var="freeCommentMenu" value="active"/>
      	</c:if>
      	<c:if test="${photoBoardList != null }">
      		<c:set var="photoBoardMenu" value="active"/>
      	</c:if>
      	<c:if test="${photoCommentList != null }">
      		<c:set var="photoCommentMenu" value="active"/>
      	</c:if>
		<li role="presentation" class="${freeBoardMenu }"><a href="/view/member/activityList/${loginMember.member_id }/freeBoard">자유게시판</a></li>
		<li role="presentation" class="${freeCommentMenu }"><a href="/view/member/activityList/${loginMember.member_id }/freeComment">자유게시판 댓글</a></li>
		<li role="presentation" class="${photoBoardMenu }"><a href="/view/member/activityList/${loginMember.member_id }/photoBoard">사진게시판</a></li>
		<li role="presentation" class="${photoCommentMenu }"><a href="/view/member/activityList/${loginMember.member_id }/photoComment">사진게시판 댓글</a></li>
	  </ul>
      <table class="table table-hover">
		    <thead>
		      <tr>
		         <tr>
			        <th width="5%" style="text-align:center">번호</th>
			        <c:if test="${freeBoardList != null || photoBoardList != null}" >
			        	<c:set var="headName" value="제 목"/>
			        </c:if>
			        <c:if test="${freeCommentList != null || photoCommentList != null}" >
			        	<c:set var="headName" value="내 용"/>
			        </c:if>				        
		        	<th width="60%" style="text-align:center" colspan="2">&nbsp;${headName }</th>
			        <th width="15%">&nbsp;작성일</th>
			        
			        <c:if test="${freeBoardList != null || photoBoardList != null}" >
			        	<th width="10%" style="text-align:center">조회수</th>
			        </c:if>
			      </tr>
		      </tr>
		    </thead>
		    <tbody>
		    <!-- 게시글 내역 -->
	   			<c:if test="${freeBoardList != null }">
	   				<c:forEach items="${freeBoardList }" var="board">
				      <tr>
				        <td style="text-align:center">${board.board_id }</td>
				        <td width="10%"><!-- 빈칸 --></td>
				        <td><a href="/view/board/${board.board_id }" target="_blank"> ${board.title }</a></td>
				        <td><fmt:formatDate value="${board.regdate }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				        <td style="text-align:center">${board.hit }</td>
				      </tr>
					</c:forEach>
	   			</c:if>
	   			
	   			<c:if test="${photoBoardList != null }">
	   				<c:forEach items="${photoBoardList }" var="photo">
				      <tr>
				        <td style="text-align:center">${photo.photoBoard_id }</td>
				        <td width="10%"><!-- 빈칸 --></td>
				        <td><a href="/view/photo/${photo.photoBoard_id }" target="_blank"> ${photo.title }</a></td>
				        <td><fmt:formatDate value="${photo.regdate }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				        <td style="text-align:center">${photo.hit }</td>
				      </tr>
					</c:forEach>
	   			</c:if>
	   			
			<!-- 댓글 내역 -->					
		   		<c:if test="${freeCommentList != null}">
					<c:set var="commentNum" value="${pager.totalContents-(pager.startContent-1)  }"/>
					<c:forEach items="${freeCommentList }" var="comment">
						<tr>
							<td style="text-align:center">${commentNum }</td>
					        <td width="10%"><!-- 빈칸 --></td>
					        <td><a href="/view/board/${comment.board_id }" target="_blank">${comment.content }</a></td>
					        <td><fmt:formatDate value="${comment.regdate }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
						<c:set var="commentNum" value="${commentNum - 1 }"/>
					</c:forEach>
				</c:if>
				
		   		<c:if test="${photoCommentList != null }">
					<c:set var="commentNum" value="${pager.totalContents-(pager.startContent-1)  }"/>
					<c:forEach items="${commentList }" var="photoComment">
						<tr>
							<td style="text-align:center">${commentNum }</td>
					        <td width="10%"><!-- 빈칸 --></td>
					        <td><a href="/view/photo/${photoComment.photoBoard_id }">${photoComment.content }</a></td>
					        <td><fmt:formatDate value="${photoComment.regdate }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
						<c:set var="commentNum" value="${commentNum - 1 }"/>
					</c:forEach>
				</c:if>
		    </tbody>
		  </table>
		  <!-----------------  페이징 ------------------------------------ -->
	  	<div align="center">
	  		<c:if test="${freeBoardList != null }">
	  			<c:set var="url" value="/view/member/activityList/${loginMember.member_id }/freeBoard?page="/>
	  		</c:if>
	  		<c:if test="${photoBoardList != null }">
	  			<c:set var="url" value="/view/member/activityList/${loginMember.member_id }/photoBoard?page="/>
	  		</c:if>
	  		<c:if test="${freeCommentList != null }">
	  			<c:set var="url" value="/view/member/activityList/${loginMember.member_id }/freeComment?page="/>
	  		</c:if>
	  		<c:if test="${photoCommentList != null }">
	  			<c:set var="url" value="/view/member/activityList/${loginMember.member_id }/photoComment?page="/>
	  		</c:if>
	  		<%@ include file="/include/paging.jsp" %>
		</div>
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-3 sidenav" style="background-color:white">
    </div>
  </div>
</div>

</body>
</html>
