<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
  <title>사진 보기</title>
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
  <script src="../script/photoComment.js"></script>
  <script src="../script/commentPaging.js"></script>
  <script>
  	// 수정하기
  	function goEdit() {
  		detailForm.action="/view/photo/edit";
  		detailForm.method="POST";
  		detailForm.submit();
  	}
  	
  	// 삭제하기
  	function deletePhoto() {
  		if(!confirm("삭제 하시겠습니까?"))return;
  		detailForm._method.value = "DELETE";
  		detailForm.action = "/view/photo/${photoDetail.photoBoard_id}";
  		detailForm.method="POST";
  		detailForm.submit();
  	}
  	
  	// 댓글 불러오기
  	function getCommentList(commentPage){
  		
  		$.get("/api/photo/${photoDetail.photoBoard_id}/comment?commentPage="+commentPage, function(data){
  	        var photoCommentList = data.photoCommentList;
  	        var commentPager = data.commentPager;
  			
			reCreateCommentTable(photoCommentList);// 테이블 새로 만들기.
			
			reCreatePaging(commentPager);	// 페이징 새로 만들기.
  	        
  	    });
  		
  	}
  	
  	<c:if test="${loginMember != null}"><%-- 로그인 안했으면 표시 안함. --%>
	  	// 댓글 입력
	  	function insertComment() {
	  		
	  		$.ajax({
	  	  		type:"post",	// 요청방식
	  	  		url:"/api/photo/${photoDetail.photoBoard_id}/comment",
	  	  		headers:{	// 헤더값 세팅.
	  	  		"Content-Type":"application/json",
	  	  		"X-HTTP-Method-Override":"POST"
	  	  		},
	  			dataType:"text",
	  			data:JSON.stringify({
					"content" : $("#commentContent").val(),
	       			"member_id" : ${loginMember.member_id}
	  			}),
	 	  		success:function(data){
	 	  			$("#commentContent").val("");
					var obj = JSON.parse(data);
					var photoCommentList = obj.photoCommentList;	// 댓글 리스트
					
					reCreateCommentTable(photoCommentList);// 테이블 새로 만들기.
					
	 	       	}// success
	  		});// ajax
	  		
	  	}// insertComment
  	
  		
	  	// 댓글 수정하기
	  	function updateComment(photoComment_id) {
	  		var content = $("#edit_"+photoComment_id).val();
	  		
	  		$.ajax({
	  	  		type:"PUT",	// 요청방식
	  	  		url:"/api/photo/${photoDetail.photoBoard_id}/comment/"+photoComment_id,
	  	  		headers:{	// 헤더값 세팅.
	  	  		"Content-Type":"application/json",		// 자료형
	  	  		"X-HTTP-Method-Override":"PUT"	// 요청방식
	  	  		},
	  			dataType:"text",
	  			data:JSON.stringify({
					"content" : content
	  			}),
	 	  		success:function(data){
					
			  		$("#content_"+photoComment_id).html("&nbsp;&nbsp;"+content);	// 해당 댓글만 수정.	
					
	 	       	}// success
	  		});// ajax
	  		
	  	}
	  	
	  	// 댓글 삭제하기
	  	function deleteComment(photoComment_id) {
	  		
	  		if(!confirm("삭제 하시겠습니까?"))return;
	  		
	  		$.ajax({
	  	  		type:"DELETE",	// 요청방식
	  	  		url:"/api/photo/${photoDetail.photoBoard_id}/comment/"+photoComment_id,
	  	  		headers:{	// 헤더값 세팅.
	  	  		"Content-Type":"application/json",		// 자료형
	  	  		"X-HTTP-Method-Override":"DELETE"	// 요청방식
	  	  		},
	  	  		dataType:"text",
	 	  		success:function(data){
	 	  			$("#commentContent").val("");
					var obj = JSON.parse(data);
					var commentPager = obj.commentPager;		// 페이징 객체
					var photoCommentList = obj.photoCommentList;	// 댓글 리스트
					
					reCreateCommentTable(photoCommentList);// 테이블 새로 만들기.
					reCreatePaging(commentPager);	// 페이징 새로 만들기.
					
	 	       	}// success
	  		});// ajax
	  	}
	  
	  	// 댓글 수정 안하고 수정창 닫으면 모달 내용 원래 대로 회복.
	  	function closeModal(photoComment_id, content) {
	  		$("#edit_"+photoComment_id).val(content);
	  	}
	</c:if>
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
      <h1>사진 보기</h1>
      	<div>
      		<br>
	      	<form name="detailForm">
	      		<input type="hidden" value="POST" name="_method">
	      		<input type="hidden" value="${photoDetail.photoBoard_id }" name="photoBoard_id">
	      		<input type="hidden" value="${photoDetail.member_id}" name="member_id">
	      		<input type="hidden" value="${photoDetail.thumb1 }" name="thumb1">
	      		<input type="hidden" value="${photoDetail.thumb2 }" name="thumb2">
			    <div class="form-group">
			    	<label for="writer" class="control-label">작성자 : </label>
		      		<input type="text" class="form-control" id="writer" value="${photoDetail.nickname}" name="nickname" readonly>
				</div>
			    <div class="form-group">
				    <label for="title" class="control-label">제 목 :</label>
		      		<input type="text" class="form-control" id="title" name="title" value="${photoDetail.title }" readonly>
				</div>
				<div class="form-group">
				    <label for="regdate" class="control-label">작성일 :</label>
				    <fmt:formatDate value="${photoDetail.regdate }" pattern="yyyy/MM/dd HH:mm:ss" var="regdate"/>
		      		<input type="text" class="form-control" name="regdate" id="regdate" value="${regdate }" readonly>
				</div>
				<div align="center">
					<c:set var="imgName" value="${photoDetail.saveName }"/>
					<c:set var="splitNames" value="${fn:split(imgName, \".\")}"/><%-- .을 기준으로 나눠서 문자열을 배열로 만들기. --%>
					<c:set var="imgFormat" value="${splitNames[fn:length(splitNames)-1] }"/><%-- '.'을 기준으로 만든 배열의 마지막 값을 이용해 확장자 추출. --%>
					<c:set var="filename" />
					
					<c:forEach begin="0" end="${fn:length(splitNames)-2 }" step="1" var="cnt"><%-- 배열의 마지막 값을 제외하고 모두 합쳐서 파일명 완성. --%>
						<c:set var="filename" value="${filename.concat(splitNames[cnt]) }"/>
					</c:forEach>
					
					<c:if test="${photoDetail.thumb1.equals(\"Y\") }"><%-- 저장된 썸네일이 있으면, 파일명 재정의. --%>
						<c:set var="filename" value="${filename.concat(\"_thumb1\")}" />
					</c:if>
					<fmt:formatDate value="${photoDetail.regdate }" pattern="yyyy/MM/dd" var="dateFolder"/>
					<a href='/images/photo/${dateFolder }/${imgName }' target="_blank">
						<img  id="preImg" src='/images/photo/${dateFolder }/${filename}.${imgFormat}'>
					</a>
					<input type="hidden" name="saveName" value="${photoDetail.saveName }">
				</div>
			</form>
      	</div>
      		<br>
			<div align="right">
				<c:if test="${loginMember.member_id == photoDetail.member_id }">
					<input type="button" class="btn btn-warning" value="수정하기" onClick="goEdit()">
				</c:if>
				<c:if test="${loginMember.member_id == photoDetail.member_id || loginMember.rank == 1 }">
					<input type="button" class="btn btn-danger" value="삭제하기" onClick="deletePhoto()">
				</c:if>
				<input type="button" class="btn btn-primary" value="목록보기" onClick="location.href='/view/photo'">
			</div>
<!-- ///////////////////////////////////           댓글                  //////////////////////////////////////////////////////////// -->
			<div>
				
				<table class="table table table-striped table-responsive" id="commentTable">
					<c:forEach items="${photoCommentList }" var="photoCommentDetail">
						<tr>
							<td style="width:20%">&nbsp;<img src="/images/member/${photoCommentDetail.levelname}.png">${photoCommentDetail.nickname}</td>
							<td style="width:50%" id="content_${photoCommentDetail.photoComment_id }">&nbsp;&nbsp;${photoCommentDetail.content}</td>
							<fmt:formatDate value="${photoCommentDetail.regdate }" pattern="yyyy/MM/dd HH:mm:ss" var="commentDate"/>
							<td style="width:15%; text-align:center">${commentDate}</td>
						
							<c:choose>
								<c:when test="${loginMember.member_id == photoCommentDetail.member_id || loginMember.rank == 1}"><%-- 글쓴이거나, 관리자면 삭제하기 활성화. --%>
									<td style="width:15%; text-align:center">
										<c:if test="${loginMember.member_id == photoCommentDetail.member_id }"><%-- 본인 쓴글이면, 수정하기랑 모달 활성화 --%>
											<a data-toggle="modal" href="#modal_${photoCommentDetail.photoComment_id }">수정</a>&nbsp;|
											<div id="modal_${photoCommentDetail.photoComment_id }" class="modal fade">
											  <div class="modal-dialog">
											    <div class="modal-content">
											      <div class="modal-header">
											        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
											        <h4 class="modal-title">댓글수정</h4>
											      </div>
												      <div class="modal-body">
												        <textarea style="width:100%" class="form-control" rows="3" maxlength="50" id="edit_${photoCommentDetail.photoComment_id }">${photoCommentDetail.content }</textarea>
												      </div>
											      <div class="modal-footer">
											        <input type="button" class="btn btn-primary" value="수정하기" onClick="updateComment(${photoCommentDetail.photoComment_id})" data-dismiss="modal">
											        <input type="button" class="btn btn-danger" data-dismiss="modal" value="닫기" onClick="closeModal(${photoCommentDetail.photoComment_id},'${photoCommentDetail.content }')">
											      </div>
											    </div><!-- /.modal-content -->
											  </div><!-- /.modal-dialog -->
											</div><!-- /.modal -->
										</c:if>
										
										<a href="javascript:deleteComment(${photoCommentDetail.photoComment_id })">삭제</a>
									</td>
								</c:when>
								<c:otherwise>
									<td style="width:15%; text-align:center"></td>
								</c:otherwise>
							</c:choose>							
						</tr>
					</c:forEach>
				</table>
<!-------------------------------- 댓글 페이징 ------------------------------------------------------>
				<div align="center" id="commentPaging">
				  	<nav>
					  <ul class="pagination">
					    <c:if test="${commentPager.prev }">
						    <li>
						      <a href="javascript:getCommentList(${(commentPager.startPage-1)})" aria-label="Previous">
						        <span aria-hidden="true">&laquo;</span>
						      </a>
						    </li>
					    </c:if>
					    <c:forEach var="cnt" begin="${commentPager.startPage }" end="${commentPager.endPage }">
					    	<c:choose>
						    	<c:when test="${cnt == commentPager.page }">
						    		<c:set var="pageClass" value="active"/>
						    	</c:when>
						    	<c:otherwise>
						    		<c:set var="pageClass" value=""/>
						    	</c:otherwise>
					    	</c:choose>	
					    
					    	<li class="${pageClass }">
					    		<a href="javascript:getCommentList(${cnt })">${cnt }</a>
					    	</li>
					    </c:forEach>
					    <c:if test="${commentPager.next }">
						    <li>
						      <a href="javascript:getCommentList(${commentPager.endPage+1})" aria-label="Next">
						        <span aria-hidden="true">&raquo;</span>
						      </a>
						    </li>
					    </c:if>
					  </ul>
					</nav>
				</div>
<!-------------------------------- 댓글 입력 ------------------------------------------------------>				
				<c:if test="${loginMember != null }">
					<form name="commentForm">
						<textarea style="width:100%" class="form-control" rows="3" maxlength="50" placeholder="50자 이내로 입력해주세요." name="content" id="commentContent"></textarea>
					</form>
					<div style="height:5px"></div>
					<div  align="right">
						<input type="button" value="등록" class="btn btn-default" onClick="insertComment()">
					</div>
				</c:if>
				<div style="height:100px"></div>
			</div>
			
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-3 sidenav" style="background-color:white">
    </div>
  </div>
</div>

</body>
</html>
