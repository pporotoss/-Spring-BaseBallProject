<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
  <title>자유게시판</title>
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
    <div class="col-sm-2 sidenav hidden-xs" style="background-color:white">
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-8 text-left"> 
      <h1>&nbsp;&nbsp;&nbsp;자유게시판</h1>
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
		     <c:forEach items="${boardDetailList }" var="boardDetail">
		     	<tr>
					<td style="text-align:center">${boardDetail.board_id}</td>
					<td width="5%"><!-- 빈칸 --></td>
					<td>
						<c:if test="${boardDetail.ishidden.equals(\"no\") }"><!-- 비밀글이 아니면, -->
							<c:choose>
								<c:when test="${boardDetail.depth == 0}"><!-- 원글이면 -->
									&nbsp;<a href="/view/board/${boardDetail.board_id }">${boardDetail.title }&nbsp;&nbsp;[${boardDetail.count }]	
								</c:when>
								<c:otherwise><!-- 답글이면  -->
									<c:forEach begin="0" end="${boardDetail.depth }">
										&nbsp;&nbsp;&nbsp;
									</c:forEach>
									<img src="/images/board/reply.png">
									<a href="/view/board/${boardDetail.board_id }">${boardDetail.title }&nbsp;&nbsp;[${boardDetail.count }]
								</c:otherwise>
							</c:choose>
							</a>
						</c:if>
						<c:if test="${boardDetail.ishidden.equals(\"yes\") }"><!-- 비밀글이면  -->
							<c:choose>
								<c:when test="${boardDetail.member_id == loginMember.member_id || loginMember.level_id == 4}"><!-- 작성자이거나 관리자이면, -->
									<c:choose>
										<c:when test="${boardDetail.depth == 0}"><!-- 원글이면 -->
											&nbsp;<img src="/images/board/lock.png">
											<a href="/view/board/${boardDetail.board_id }">${boardDetail.title }&nbsp;&nbsp;[${boardDetail.count }]	
										</c:when>
										<c:otherwise><!-- 답글이면  -->
											<c:forEach begin="0" end="${boardDetail.depth }">
												&nbsp;&nbsp;&nbsp;
											</c:forEach>
											<img src="/images/board/reply.png"><img src="/images/board/lock.png">
											<a href="/view/board/${boardDetail.board_id }">${boardDetail.title }&nbsp;&nbsp;[${boardDetail.count }]
										</c:otherwise>
									</c:choose>
									</a>
								</c:when>
								<c:otherwise><!--  작성자나 관리자가 아니면,  -->
									<c:choose>
										<c:when test="${boardDetail.depth == 0}"><!-- 원글이면 -->
											&nbsp;<img src="/images/board/lock.png">
											작성자 또는 관리자만 볼 수 있습니다.	
										</c:when>
										<c:otherwise><!-- 답글이면  -->
											<c:forEach begin="0" end="${boardDetail.depth }">
												&nbsp;&nbsp;&nbsp;
											</c:forEach>
											<img src="/images/board/reply.png">&nbsp;<img src="/images/board/lock.png">
											작성자 또는 관리자만 볼 수 있습니다.
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:if>
					</td>
					<td>&nbsp;<img src="/images/member/${boardDetail.levelname}.png">${boardDetail.nickname }</td>
					<td>&nbsp;<fmt:formatDate value="${boardDetail.regdate }" type="both" pattern="yyyy.MM.dd hh:mm:ss"/></td>
					<td style="text-align:center">${boardDetail.hit }</td>
				</tr>
		     </c:forEach>
	    </tbody>
	  </table>
	  	<c:if test="${loginMember != null }">
	  		<div align="right">
		  	<a href="/view/board/write"><input type="button" value="글쓰기" class="btn btn-info"></a>
		  </div>
	  	</c:if>
	  	<!--  페이징  -->
	  	<div align="center">
		  	<nav>
			  <ul class="pagination">
			    <c:if test="${pager.prev }">
				    <li>
				      <a href="#" aria-label="Previous">
				        <span aria-hidden="true">&laquo;</span>
				      </a>
				    </li>
			    </c:if>
			    <c:forEach var="cnt" begin="${pager.startPage }" end="${pager.endPage }">
			    	<c:choose>
				    	<c:when test="${cnt == pager.page }">
					    	<li class="active">
				    	</c:when>
				    	<c:otherwise>
				    		<li>
				    	</c:otherwise>
			    	</c:choose>
			    	<a href="/view/board?page=${cnt }&pagesize=10">${cnt }</a></li>
			    </c:forEach>
			    <c:if test="${pager.next }">
				    <li>
				      <a href="#" aria-label="Next">
				        <span aria-hidden="true">&raquo;</span>
				      </a>
				    </li>
			    </c:if>
			  </ul>
			</nav>
		</div>
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-2 sidenav hidden-xs" style="background-color:white">
    </div>
  </div>
</div>

</body>
</html>
