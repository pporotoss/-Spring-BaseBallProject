<%@page import="java.util.List"%>
<%@page import="org.jsoup.select.Elements"%>
<%@page import="org.jsoup.nodes.Element"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
  <script src="//code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
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
  	function getResult(){
  		
  		var param = $("#datePicker").val();
  		
  		if(param == ""){
  			alert("경기 날짜를 선택해 주세요.");
  			return;
  		}
  		
		location.href="/view/team/result/"+param.replace(/\//gi,"");	// "/" 표시 전부 없애고 이동.
  	}
  	/* 달력 설정 */
  	$.datepicker.setDefaults({
        dateFormat: 'yy/mm/dd',
        prevText: '이전 달',
        nextText: '다음 달',
        monthNames: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
        monthNamesShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
        dayNames: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
        showMonthAfterYear: true,
        showButtonPanel: true,
        changeMonth: true,
      	changeYear: true,
        autoSize: true,
        showOn: "button",
        buttonImage: "/images/team/calendar.png",
        buttonImageOnly: true,
        buttonText: "날짜 선택"
    });

    $(function() {
        $("#datePicker").datepicker();
    });
  	
  	
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
      <h1 align="center">${day } 경기 결과</h1>
	    <div class="form-group form-inline" align="center">
	  		<label for="datePicker">조회할 날짜 : </label>
	  		<input type="text" class="form-control" id="datePicker" disabled value="${day }"><br>
      		<input class="btn btn-warning" type="button" value="경기결과 조회" onClick="getResult()">
  	  	</div>
      	<div class="form-inline" align="center">
      	</div>
	     <%if(states != null){ %>
		     <table class="table table-bordered table-hover table-responsive">
			    <%for(int i = 0; i < states.size(); i++){ %>
				    <%
				    	Element state = states.get(i);
				    	String[] lft_team = lft.get(i);
				    	String[] rgt_team = rgt.get(i);
				    %>
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
			</table>
		<%}else{ %>
			<br>
			<br>
			<h1 style="text-align:center">오늘은 경기가 없는 날 입니다.</h1>
		<%} %>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
	    <div class="col-sm-1 sidenav" style="background-color:white">
	    </div>
	  </div>
	</div>
</div>

</body>
</html>
