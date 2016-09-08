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
  <script>
  	// 수정하기
  	function goEdit() {
  		detailForm.action="/view/photo/edit";
  		detailForm.method="POST";
  		detailForm.submit();
  	}
  	
  	// 삭제하기
  	function deletePhoto() {
  		
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
      <h1>사진 보기</h1>
      	<div>
      		<br>
	      	<form name="detailForm">
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
				    <fmt:formatDate value="${photoDetail.regdate }" pattern="yyyy/MM/dd hh:mm:ss" var="regdate"/>
		      		<input type="text" class="form-control" name="regdate" id="regdate" value="${regdate }" readonly>
				</div>
				<div align="center">
					<c:set var="imgName" value="${photoDetail.saveName }"/>
					<c:set var="splitNames" value="${fn:split(imgName, \".\") }"/>
					<c:set var="imgFormat" value="${splitNames[fn:length(splitNames)-1] }"/>
					<c:set var="cnt" value="0"/>
					<c:set var="filename" />
					
					<c:forEach begin="0" end="${fn:length(splitNames)-2 }" step="1">
						<c:set var="filename" value="${filename.concat(splitNames[cnt]) }"/>
						<c:set var="cnt" value="${cnt = cnt + 1 }"/>
					</c:forEach>
					
					<c:if test="${photoDetail.thumb1.equals(\"Y\") }">
						<c:set var="filename" value="${filename.concat(\"_thumb1\")}" />
					</c:if>
					<a href='/images/photo/<fmt:formatDate value="${photoDetail.regdate }" pattern="yyyy/MM/dd"/>/${imgName }' target="_blank">
						<img  id="preImg" src='/images/photo/<fmt:formatDate value="${photoDetail.regdate }" pattern="yyyy/MM/dd"/>/${filename}.${imgFormat}'>
					</a>
					<input type="hidden" name="saveName" value='/images/photo/<fmt:formatDate value="${photoDetail.regdate }" pattern="yyyy/MM/dd" />/${filename}.${imgFormat}'>					
				</div>
			</form>
      	</div>
      		<br>
			<div align="right">
				<input type="button" class="btn btn-warning" value="수정하기" onClick="goEdit()">
				<input type="button" class="btn btn-danger" value="삭제하기" onClick="deletePhoto()">
				<input type="button" class="btn btn-primary" value="목록보기" onClick="location.href='/view/photo'">
			</div>
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-3 sidenav" style="background-color:white">
    </div>
  </div>
</div>

</body>
</html>
