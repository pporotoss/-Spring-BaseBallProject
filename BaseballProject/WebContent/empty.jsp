<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Bootstrap Example</title>
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
      <h1>자유게시판</h1>
      <table class="table table-hover">
	    <thead>
	      <tr>
	        <th>Firstname</th>
	        <th>Lastname</th>
	        <th>Email</th>
	      </tr>
	    </thead>
	    <tbody>
	      <tr>
	        <td>John</td>
	        <td>Doe</td>
	        <td>john@example.com</td>
	      </tr>
	      <tr>
	        <td>Mary</td>
	        <td>Moe</td>
	        <td>mary@example.com</td>
	      </tr>
	      <tr>
	        <td>July</td>
	        <td>Dooley</td>
	        <td>july@example.com</td>
	      </tr>
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
