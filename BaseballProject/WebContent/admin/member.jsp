<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
  <title>회원관리</title>
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
  	function updateLevel(){
  		// 회원 체크여부 검사
  		var chkCount = 0;
  		
  		for(var ch = 0; ch < memberForm.member_id.length; ch++){
  			if(memberForm.member_id[ch].checked){
  				chkCount++;
  			}
  		}
		
  		if(chkCount == 0){
  			alert("변경할 회원을 선택해 주세요.");
  			return;
  		}

  		// 변경 등급 선택여부 검사
  		if(memberForm.level_id.value == 0){
  			alert("변경시킬 등급을 선택해 주세요.");
  			return;
  		}
  		
  		if(!confirm("변경하시겠습니까?")){
  			return;
  		}
  		
  		memberForm._method.value="PUT";
  	  		
  		memberForm.action="/view/admin/member";
  		memberForm.method="POST";
  		memberForm.submit();
  		
  	}
  	
  	function deleteMember(){
  		if(!confirm("삭제하시겠습니까?")){
  			return;
  		}
  		memberForm._method.value="DELETE"
  		
  		memberForm.action="/view/admin/member";
  		memberForm.method="POST";
  		memberForm.submit();
  		
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
      <h1>회원관리</h1>
    	<form name="memberForm">
    	  <input type="hidden" value="" name="_method">
	      <table class="table table-hover">
		    <thead>
		      <tr>
		        <th>#</th>
		        <th>ID</th>
		        <th>닉네임</th>
		        <th>등급</th>
		      </tr>
		    </thead>
		    <tbody>
		      <c:forEach items="${memberList}" var="memberDetail" >
			      <c:if test="${loginMember.member_id != memberDetail.member_id }">
			      	<tr>
				      	<td><input type="checkbox" name="member_id" value="${memberDetail.member_id}"></td>
				        <td>${memberDetail.id}</td>
				        <td>${memberDetail.nickname}</td>
				        <td><img src="/images/member/${memberDetail.levelname }.png">${memberDetail.levelname}</td>
				      </tr>
				  </c:if>
		      </c:forEach>
		    </tbody>
		  </table>
		  <div class="form-inline">
	     	<select name="level_id" class="form-control">
	     		<option value="0">변경시킬 등급을 선택해 주세요.</option>
 				
 				<c:choose>
	 				<c:when test="${loginMember.id.equals(\"admin\")}"><!-- admin 계정이면 -->
	 					<c:forEach items="${levelList }" var="level">
	     					<option value="${level.level_id }">${level.levelname }</option>
		     			</c:forEach>	
	 				</c:when>
	 				<c:otherwise>
	 					<c:forEach items="${levelList }" var="level"><!-- admin계정 아니면 -->
	 						<c:if test="${level.rank != 1}">
	     						<option value="${level.level_id }">${level.levelname }</option>
	     					</c:if>
		     			</c:forEach>
	 				</c:otherwise>
 				</c:choose>
	     		
	     	</select>
     	</form>
     	<input type="button" class="btn btn-success" value="등급변경" onClick="updateLevel()">
     	<input type="button" class="btn btn-danger" value="회원삭제" onClick="deleteMember()">
     	</div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-3 sidenav" style="background-color:white">
    </div>
  </div>
</div>

</body>
</html>
