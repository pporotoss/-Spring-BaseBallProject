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
  <script src="//cdn.ckeditor.com/4.5.9/standard/ckeditor.js"></script>
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
			// 제목 입력여부 확인
			if(editForm.title.value.length < 1){
				alert("제목을 입력해주세요.");
				editForm.title.focus();
				return;			
			}
			
			editForm.action="/view/board/${boardDetail.board_id}";
			editForm.method="POST";	// 웹브라우저는 GET,POST만 지원하므로 PUT, DELETE 사용하려면 POST로 지정해야 한다.
			editForm.submit();
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
	      <h1>수정하기</h1>
	      <br>
	      	<form name="editForm">
	      		<input type="hidden" value="PUT" name="_method"><!-- PUT형식 사용위한 hiddenmethod -->
	      		<input type="hidden" value="${loginMember.member_id}" name="member_id">
			    <div class="form-group">
			    	<label for="writer" class="control-label">작성자 : </label>
		      		<input type="text" class="form-control" id="writer" value="${loginMember.nickname}%>" readonly>
				</div>
			    <div class="form-group">
				    <label for="title" class="control-label">제 목 :</label>
		      		<input type="text" class="form-control" id="title" name="title" value="${boardDetail.title}">
				</div>
				<div class="form-group">
			    	<textarea id="content" class="form-control" name="content">${boardDetail.content}</textarea>
			    </div>
			    <script>
	                // Replace the <textarea id="editor1"> with a CKEditor
	                // instance, using default configuration.
	                CKEDITOR.replace( 'content' );
            	</script>
				<div class="form-group">
					<c:choose>
					 <c:when test="${boardDetail.ishidden.equals(\"no\") }">
					 	<label class="radio-inline">
							<input type="radio" name="ishidden" value="no" checked>전체공개
						</label>
						<label class="radio-inline">
							<input type="radio" name="ishidden" value="yes">비밀글
						</label>
					 </c:when>
					 <c:otherwise>
					 	<label class="radio-inline">
							<input type="radio" name="ishidden" value="no">전체공개
						</label>
						<label class="radio-inline">
							<input type="radio" name="ishidden" value="yes" checked>비밀글
						</label>
					 </c:otherwise>
					</c:choose>
				</div>
			</form>
	      	<div class="form-group" align="right">
			  <input type="button" class="btn btn-primary" value="수정" onClick="update()">
			  <input type="button" class="btn btn-danger" value="취소" onClick="history.back()">
			</div>
	    </div>
	<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
	    <div class="col-sm-3 sidenav" style="background-color:white">
	    </div>
	  </div>
	</div>

</body>
</html>
