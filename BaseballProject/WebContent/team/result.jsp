<%@page import="java.util.List"%>
<%@page import="org.jsoup.select.Elements"%>
<%@page import="org.jsoup.nodes.Element"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
	String today = (String)request.getAttribute("today");
	Elements states = (Elements)request.getAttribute("states");
	List<String[]> lft = (List)request.getAttribute("lft");
	List<String[]> rgt = (List)request.getAttribute("rgt");
%>

<!DOCTYPE html>
<html>
<head>
  <title>경기결과</title>
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
  	function getResult(day){
		location.href="/view/team/result/"+day;
  	}
  </script>
</head>
<body>

<%@ include file="/include/topnav.jsp" %>
  
<div class="container-fluid text-center">    
  <div class="row content">
    <div class="col-sm-1 sidenav" style="background-color:white">
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-10 text-left"> 
      <h1 align="center"><%=today %> 경기</h1>
      <input type="hidden" value="<%=today%>">
      	<div class="form-inline" align="center">
      		<input class="btn btn-warning" type="button" value="어제" onClick="getResult('yesterday')">
      		<input class="btn btn-success" type="button" value="오늘" onClick="getResult('today')">
      		<input class="btn btn-warning" type="button" value="내일" onClick="getResult('tomorrow')">
      	</div>
	     <%if(states != null){ %>
		     <table class="table table-bordered table-hover table-responsive">
			    <%for(int i = 0; i < states.size(); i++){ %>
			    <%
			    	Element state = states.get(i);
			    	String[] lft_team = lft.get(i);
			    	String[] rgt_team = rgt.get(i);
			    %>
			    <thead>
			    </thead>
			    <tbody>
				    <!-- 상태 -->
				    <tr align="center">
				      <td colspan="2" ><%=state.text() %></td>
				    </tr>
				    <!--  팀  -->
				    <%for(int tt = 0; tt < lft_team.length; tt++){ %>
					    <tr align="center">
					      <td><%=lft_team[tt] %></td>
					      <td><%=rgt_team[tt] %></td>
					    </tr>
				    <%} %>
				<%} %>
				</tbody>
			</table>
		<%}else{ %>
		<h1>경기가 없는 날 입니다.</h1>
		<%} %>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
	    <div class="col-sm-1 sidenav" style="background-color:white">
	    </div>
	  </div>
	</div>
</div>

</body>
</html>
