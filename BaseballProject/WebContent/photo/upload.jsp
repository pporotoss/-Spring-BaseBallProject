<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>사진 올리기</title>
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
  	<c:if test="${loginMember == null}">	// 로그인 안하고 접근시.
		location.href = "/member/login.jsp";
	</c:if>
  
  	$(document).ready(function(){
  		
  		$("#uploadFile").change(function(){	// 파일 선택하면,
  			if($("#uploadFile")[0].files[0].size >= 10 * 1024 * 1024){	// 파일 크기 검사
				alert("10M 이하의 파일만 업로드 해주세요.");
  				$("#uploadFile").val("");
  				return;
  			}
  			
  			imgPreview();
  			
  		});	// uploadFile change
  		
  	});	// document ready
  	
  	function imgPreview(){	// 이미지 미리보기
	  	var reader = new FileReader();
	
		reader.onload = function (e) {
			
			/* reader가 다 읽으면 preImg에 뿌리기  */
			var image = new Image();
			image.src = e.target.result;
			var scaleArray = imgScale(image.width, image.height); 
			
			$("#preImg").attr("src", e.target.result);
			$("#preImg").attr("style", "width:"+scaleArray[0]+"px;height:"+scaleArray[1]+"px");
		}
	
		/* input file에 있는 파일 하나를 읽어온다. */
		reader.readAsDataURL($("#uploadFile")[0].files[0]);
  	}
  	
  	function imgScale(width, height){	// 이미지 비율구하기
  		var referenceValue = 800;	// 사진 넓이 최대치 기준값
  		var xScale;	// x 비율
  		var yScale;	// y 비율
  		var reX = width;	// 리사이즈 x크기
  		var reY = height;	// 리사이즈 y크기
  		
  		if(width - height == 0){	// 가로와 세로가 같으면,
			if(reX > referenceValue){
	  			reX = referenceValue;
	  			reY = referenceValue;
			}
  			
  		}else{	// 다르면,
			
  			var GCD = calcGCD(width, height);
  			xScale = width / GCD;
  			yScale = height / GCD;
			
  			if(xScale > yScale){	// 가로가 더 크면,
  				
  				if(reX > referenceValue){
	  				reX = referenceValue;
	  				reY = (reX*yScale)/xScale;
  				}
  				
  			}else{	// 세로가 더 크면,
  				
  				if(reY > referenceValue){
	  				reY = referenceValue;
	  				reX = (reY * xScale)/yScale;
  				}
  				
  			}	// if xScale > yScale
  		
  		}	// if width-height
  		
  		return [reX, reY];
  	}
  	
  	function calcGCD(first, second){	// 최대공약수 구하기
  		var GCD;
  		
  		if(first > second){	// 첫번째 수가 두번째 수보다 크면,
			var big = first;
			var small = second;
		}else{
			var big = second;
			var small = first;
		}// if first > second
		
		while(true){
			if(big % small == 0){
				GCD = small;
				break;
			}else{
				var remainder = big % small;
				big = small;
				small = remainder;
			}// if big%small
		}// while
		
		return GCD;
  	}// cacGCD
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
      <h1>사진 올리기</h1>
      	<div>
      		<br>
	      	<form name="uploadForm">
	      		<input type="hidden" value="${loginMember.member_id}" name="member_id">
			    <div class="form-group">
			    	<label for="writer" class="control-label">작성자 : </label>
		      		<input type="text" class="form-control" id="writer" value="${loginMember.nickname}" readonly>
				</div>
			    <div class="form-group">
				    <label for="title" class="control-label">제 목 :</label>
		      		<input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력해 주세요.">
				</div>
				<div align="center">
				
					<img  id="preImg" src="">
					
				</div>
				<div class="form-group">
			    <label for="uploadFile">파일 업로드</label>
			    <input type="file" id="uploadFile" name="uploadFile" accept=".gif, .jpg, .png, .jpeg"><!-- 해당 확장자만 업로드 가능 -->
			    <p class="help-block">10M이하 파일만 업로드 해주세요.</p>
			  </div>
			</form>
			<div align="right">
				<input type="button" class="btn btn-primary" value="올리기">
			</div>
      	</div>
    </div>
<!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->    
    <div class="col-sm-3 sidenav" style="background-color:white">
    </div>
  </div>
</div>

</body>
</html>
