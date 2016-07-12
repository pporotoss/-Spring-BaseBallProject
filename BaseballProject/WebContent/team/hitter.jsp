<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
	ArrayList<String[]> battings = (ArrayList)request.getAttribute("battings");
	ArrayList<String[]> rbis = (ArrayList)request.getAttribute("rbis");
	ArrayList<String[]> homeruns = (ArrayList)request.getAttribute("homeruns");
	ArrayList<String[]> stealbases = (ArrayList)request.getAttribute("stealbases");
%>
<!DOCTYPE html>
<html>
<head>
  <title>타자순위</title>
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
    <div class="col-sm-1 sidenav" style="background-color:white">
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-10 text-left"> 
      <h1 align="center">타자 순위</h1>
      <table class="table table-hover table-responsive table-bordered">
		<thead>
			<tr>
				<th colspan="16" style="text-align:center">분야</th>
			</tr>
			<tr>
				<th colspan="4" style="text-align:center">타율</th>
				<th colspan="4" style="text-align:center">타점</th>
				<th colspan="4" style="text-align:center">홈런</th>
				<th colspan="4" style="text-align:center">도루</th>
			</tr>
		</thead>
		<tbody>
			<%for(int yy = 0; yy < battings.size(); yy++){ %>
				<tr align="center">
					<%for(int i = 0; i< battings.get(yy).length; i++){ %>
						<%
							String[] content = battings.get(yy);
						%>
						<td><%=content[i] %></td>
					<%} %>
					<%for(int i = 0; i< rbis.get(yy).length; i++){ %>
						<%
							String[] content = rbis.get(yy);
						%>
						<td><%=content[i] %></td>
					<%} %>
					<%for(int i = 0; i< homeruns.get(yy).length; i++){ %>
						<%
							String[] content = homeruns.get(yy);
						%>
						<td><%=content[i] %></td>
					<%} %>
					<%for(int i = 0; i< stealbases.get(yy).length; i++){ %>
						<%
							String[] content = stealbases.get(yy);
						%>
						<td><%=content[i] %></td>
					<%} %>
				</tr>
			<%} %>
		</tbody>
	</table>
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-1 sidenav" style="background-color:white">
    </div>
  </div>
</div>

</body>
</html>
