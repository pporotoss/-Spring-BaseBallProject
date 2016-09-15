<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="/"><span class="glyphicon glyphicon-home"></span>Home</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
     <c:choose>
     	<c:when test="${noticeList != null}">
			<c:set var="noticeboard" value="active"/>
		</c:when>
		<c:when test="${boardDetailList != null}">
			<c:set var="freeboard" value="active"/>
		</c:when>
		<c:when test="${memberList != null}">
			<c:set var="management" value="active"/>
		</c:when>
		<c:when test="${photoList != null}">
			<c:set var="photoboard" value="active"/>
		</c:when>
		
     </c:choose>
	      <li class="${noticeboard }"><a href="/view/notice">공지사항</a></li>
		  <li class="${freeboard }"><a href="/view/board">자유게시판</a></li>
		  <li class="${photoboard }"><a href="/view/photo">사진게시판</a></li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">야구<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
              <li><a href="/view/team/result/today">경기 결과</a></li>
              <li class="divider"></li>
              <li><a href="/view/team/ranking">팀 순위</a></li>
              <li class="divider"></li>
              <li><a href="/view/team/pitcher">투수 순위</a></li>
              <li class="divider"></li>
              <li><a href="/view/team/hitter">타자 순위</a></li>
            </ul>
          </li>
	      <c:if test="${loginMember.rank == 1}">
	      	<li class="${management }"><a href="/view/admin/member">회원관리</a></li>
	      </c:if>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <c:choose>
        	<c:when test="${loginMember == null }">
        		<li><a href="/view/member/regist"><span class="glyphicon glyphicon-user"></span> 회원가입</a></li>
        		<li><a href="/view/member/loginPage"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
        	</c:when>
        	<c:otherwise>
        		<li class="dropdown">
		        	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><span class="glyphicon glyphicon-user"></span>${loginMember.nickname }<span class="caret"></span></a>
		        	<ul class="dropdown-menu" role="menu">
		              <li><a href="/view/member/myinfo/${loginMember.member_id }">회원정보</a></li>
		              <li class="divider"></li>
		              <li><a href="/view/member/activityList/${loginMember.member_id }/freeBoard">활동내역</a></li>
		            </ul>
		         </li>
	        	<li><a href="/view/member/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
        	</c:otherwise>
        </c:choose>
      </ul>
    </div>
  </div>
</nav>
