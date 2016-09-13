<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
  <script>
  	function photoSearch(){
  		if(searchForm.keyword.value.trim().length <= 0){
  			alert("검색어를 입력해 주세요.");
  			searchForm.keyword.focus();
  			return;
  		}
  		searchForm.action="/view/photo?searchType="+searchForm.searchType+"&keyword="+searchForm.keyword;
  		searchForm.method="GET";
  		searchForm.submit();
  	}
  </script>
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
      		<c:forEach var="photoDetail" items="${photoBoardList}">
	      		<div class="col-sm-3">
					<a href="/view/photo/${photoDetail.photoBoard_id }" class="thumbnail">
						<div class="panel panel-default">
							<div class="panel-body">
								${photoDetail.title }&nbsp;[${photoDetail.count }]
							</div>
						</div>
						<c:set var="imgName" value="${photoDetail.saveName }"/>
						<c:set var="splitNames" value="${fn:split(imgName,\".\") }"/>
						<c:set var="ext" value="${splitNames[fn:length(splitNames)-1] }"/>
						<c:set var="filename" value=""/>
						<c:forEach begin="0" end="${fn:length(splitNames)-2 }" step="1" var="cnt">
							<c:set var="filename" value="${filename.concat(splitNames[cnt]) }"/>
						</c:forEach>
						<fmt:formatDate value="${photoDetail.regdate }" pattern="yyyy/MM/dd" var="datePath"/>
						<c:if test="${photoDetail.thumb2.equals(\"Y\") }">
							<c:set var="filename" value="${filename.concat(\"_thumb2\") }"/>
						</c:if>
						<div align="center" style="width:100%;height:150px">
							<img src="/images/photo/${datePath }/${filename}.${ext}">
						</div>
					</a>
					<div class="panel panel-default">
							<div class="panel-body">
								<table>
									<tr>
										<fmt:formatDate value="${photoDetail.regdate }" pattern="yyyy/MM/dd HH:mm:ss" var="regdate"/>
										<td>작성일 : ${regdate }</td>
									</tr>
									<tr>
										<td>작성자 : ${photoDetail.nickname }</td>
									</tr>
									<tr>
										<td>조회수 : ${photoDetail.hit }</td>
									</tr>
								</table>
						</div>
					</div>
	      		</div>
      		</c:forEach>
      		
    	</div>
<!------------------------ 페이징 --------------------------------->
		<c:set var="url" value="/view/photo?page="/>
		<c:set var="url1" value=""/>
		<c:if test="${searching.keyword != null }">
			<c:set var="url1" value="&searchType=${searching.searchType}&keyword=${searching.keyword}"/>
		</c:if>
	    <%@ include file="/include/paging.jsp" %>
		<!------- 검 색 ------------>
		
		<form name="searchForm">
			<div class="row">
			  <div class="col-xs-2">
				<select class="form-control" name="searchType">
				   <option value="title">제 목</option>
				   <option value="writer" <c:if test="${searching.searchType.equals('writer') }">selected</c:if>>글쓴이</option>
				</select>
			  </div>
			  <div class="col-xs-5">
			    <input type="text" class="form-control" placeholder="검색어를 입력해 주세요." <c:if test="${searching.keyword !=null}">value="${searching.keyword}"</c:if> name="keyword">
			  </div>
			  <input class="btn btn-success" type="button" value="검색" onClick="photoSearch()"> <input class="btn btn-warning" type="button" value="전체보기" onClick="location.href='/view/photo'">
			</div>
		</form>    
    	<br>
    	<br>
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-2 sidenav" style="background-color:white">
    </div>
  </div>
</div>

</body>
</html>
