<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
  <script src="../script/boardComment.js"></script>
  <script src="../script/commentPaging.js"></script>
  <script>
  /* 댓글 목록 불러오기!! */
  function getCommentList(commentPage){
		
		$.get("/api/board/${detail.board_id }/comment?commentPage="+commentPage, function(data){
	        
			var commentList = data.commentList;	// 넘어온 댓글 리스트.
	        var commentPager = data.commentPager;	// 넘어온 페이징 객체.
			
	        var loginMember_id = 0;
	        var memberRank = 0;
	        
	        <c:if test="${loginMember != null }">
        		loginMember_id = ${loginMember.member_id};
        		memberRank = ${loginMember.rank};
        	</c:if>
	        
			reCreateCommentTable(commentList, loginMember_id, memberRank);// 테이블 새로 만들기.
			
			reCreatePaging(commentPager);	// 페이징 새로 만들기. 공통 사용 가능.
	        
	    });
	}
  
  <c:if test="${loginMember != null }">
  
  /* 게시물 */
  	function update(){	// 수정페이지 이동
  		detailForm.action="/view/board/${detail.board_id }";
  		detailForm.method="POST";
  		detailForm.submit();
  	}
  	
  	function reply(){	// 답글달기
  		detailForm.action = "/view/board/reply/write";
  		detailForm.method="POST";
  		detailForm.submit();
  	}
  	
  	function deleteContent(){	// 삭제
  		if(!confirm("게시물을 삭제하시겠습니까?")){
  			return;
  		}
  		detailForm._method.value="DELETE";
  		
  		detailForm.action="/view/board/${detail.board_id }";
  		detailForm.method="POST";
  		detailForm.submit();
  	}
  	
  	/* 댓글 */
  	function insertComment(){ // 댓글 삽입
  		
  		if($("#commentContent").val().trim().length <= 0){
  			alert("댓글을 입력해 주세요.");	
  			$("#commentContent").focus();
	  		return;
  		}
  		
  		$.ajax({
  			type:"post",	// 요청방식
  			url:"/api/board/${detail.board_id }/comment",
  			headers:{	// 헤더값 세팅.
  				"Content-Type":"application/json",
  				"X-HTTP-Method-Override":"POST"
  			},
  			dataType:"text",
  			data:JSON.stringify({
  					content : $("#commentContent").val(),
  			        member_id : ${loginMember.member_id }
  			}),
  			success:function(data){
  				var obj = JSON.parse(data);	// 넘어온 데이터 JSON 객체로 변환.
  				var commentList = obj.commentList;
  				var commentPager = obj.commentPager;
        		var loginMember_id = ${loginMember.member_id };
        		var memberRank = ${loginMember.rank };

        		$("#commentContent").val("");
				reCreateCommentTable(commentList, loginMember_id, memberRank);// 테이블 새로 만들기.
				
				reCreatePaging(commentPager);	// 페이징 새로 만들기. 공통 사용 가능.
  				
  			}// function	
  		});// ajax
  		
  	}// insertComment
  		
  	// 댓글 삭제
  	function deleteComment(comment_id){
  		
  		if(!confirm("댓글을 삭제 하시겠습니까?")){
  			return;
  		}
  		
  		$.ajax({
  			type:"DELETE",	// 요청방식
  			url:"/api/board/${detail.board_id }/comment/"+comment_id,
  			headers:{	// 헤더값 세팅.
  				"Content-Type":"application/json",
  				"X-HTTP-Method-Override":"DELETE"
  			},
  			dataType:"text",
  			success:function(data){
  				var obj = JSON.parse(data);	// 넘어온 데이터 JSON 객체로 변환.
  				var commentList = obj.commentList;
  				var commentPager = obj.commentPager;
        		var loginMember_id = ${loginMember.member_id};
        		var memberRank = ${loginMember.rank};

        		$("#commentContent").val("");
				reCreateCommentTable(commentList, loginMember_id, memberRank);// 테이블 새로 만들기.
				
				reCreatePaging(commentPager);	// 페이징 새로 만들기. 공통 사용 가능.
  				
  			}// function
  		});// ajax
	}
  	
 	// 댓글 수정
  	function updateComment(comment_id){
 		
 		var editContent = $("#edit_"+comment_id).val();
 		
  		$.ajax({
  			type:"put",	// 요청방식
  			url:"/api/board/${detail.board_id }/comment/"+comment_id,
  			headers:{	// 헤더값 세팅.
  				"Content-Type":"application/json",
  				"X-HTTP-Method-Override":"PUT"
  			},
  			dataType:"text",
  			data:JSON.stringify({
  					content : editContent
  			}),
  			success:function(data){
  				
  				$("#modal_"+comment_id).modal("hide");
  				$("#content_"+comment_id).html("&nbsp;&nbsp;"+editContent);
	    		
  			}// function	
  		});// ajax
  			
	}// updateComment
	
	function closeModal(comment_id, originalContent){	// 수정하기 창 닫을때.
		$("#edit_"+comment_id).val(originalContent);
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
      <h1>상세보기</h1>
	      <br>
	      	<form name="detailForm">
	      		<input type="hidden" value="${detail.member_id}" name="member_id">
	      		<input type="hidden" value="${detail.family}" name="family">
	      		<input type="hidden" value="${detail.depth}" name="depth">
	      		<input type="hidden" value="${detail.rank}" name="rank">
	      		<input type="hidden" value="${detail.board_id}" name="board_id">
	      		<input type="hidden" value="" name="_method">
	      		
			    <div class="form-group">
			    	<label for="writer" class="control-label">작성자 : </label>
		      		<input type="text" class="form-control" id="writer" value="${detail.nickname }" readonly>
				</div>
			    <div class="form-group">
				    <label for="title" class="control-label">제 목 :</label>
		      		<input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력해 주세요." value="${detail.title }" readonly>
				</div>

				<div class="panel panel-default" style="height:300px">
				  <div class="panel-body">
				    	${detail.content }
				  </div>
				</div>		
			</form>
	      	<div class="form-group" align="right">
	      		<c:if test="${loginMember != null }">
		      		<c:if test="${loginMember.member_id == detail.member_id }">
		      			<input type="button" class="btn btn-warning" value="수정" onClick="update()">
		      		</c:if>
		      		<c:if test="${loginMember.member_id == detail.member_id || loginMember.rank == 1 }">
		      			<input type="button" class="btn btn-danger" value="삭제" onClick="deleteContent()">
		      		</c:if>
		      		<input type="button" class="btn btn-primary" value="답글" onClick="reply()">
	      		</c:if>
				<a href="/view/board?page=${page }&pagesize=${pagesize}<c:if test="${searching.keyword != null }">&searchType=${searching.searchType }&keyword=${searching.keyword }</c:if>"><input type="button" class="btn btn-default" value="목록"></a>
			</div>
<!-- ///////////////////////////////////           댓글                  //////////////////////////////////////////////////////////// -->
			<div>
				
				<table class="table table table-striped table-responsive" id="commentTable">
					<c:forEach items="${commentList }" var="commentDetail">
						<tr>
							<td style="width:20%">&nbsp;<img src="/images/member/${commentDetail.levelname}.png">${commentDetail.nickname}</td>
							<td style="width:50%" id="content_${commentDetail.comment_id }">&nbsp;&nbsp;${commentDetail.content}</td>
							<td style="width:15%; text-align:center">${commentDetail.regdate}</td>
						
							<c:choose>
								<c:when test="${loginMember.member_id == commentDetail.member_id || loginMember.rank == 1}">
									<td style="width:15%; text-align:center">
										<c:if test="${loginMember.member_id == commentDetail.member_id }">
											<a data-toggle="modal" href="#modal_${commentDetail.comment_id }">수정</a>&nbsp;|
											
											<div id="modal_${commentDetail.comment_id }" class="modal fade">
											  <div class="modal-dialog">
											    <div class="modal-content">
											      <div class="modal-header">
											        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
											        <h4 class="modal-title">댓글수정</h4>
											      </div>
												      <div class="modal-body">
												        <textarea style="width:100%" class="form-control" rows="3" maxlength="50" id="edit_${commentDetail.comment_id }">${commentDetail.content }</textarea>
												      </div>
											      <div class="modal-footer">
											        <input type="button" class="btn btn-primary" value="수정하기" onClick="updateComment(${commentDetail.comment_id})">
											        <input type="button" class="btn btn-danger" data-dismiss="modal" value="닫기" onClick="closeModal(${commentDetail.comment_id},'${commentDetail.content }' )">
											      </div>
											    </div><!-- /.modal-content -->
											  </div><!-- /.modal-dialog -->
											</div><!-- /.modal -->
										</c:if>
										<a href="javascript:deleteComment(${commentDetail.comment_id })">삭제</a>
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
				<%@ include file="/include/commentPaging.jsp" %>
<!-------------------------------- 댓글 입력 ------------------------------------------------------>				
				<c:if test="${loginMember != null }">
					<textarea style="width:100%" class="form-control" rows="3" maxlength="50" placeholder="50자 이내로 입력해주세요." id="commentContent"></textarea>
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
