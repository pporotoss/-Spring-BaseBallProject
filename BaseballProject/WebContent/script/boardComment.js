/**
 	자유게시판 댓글 Ajax로 다시 생성할때 쓰는 것들.
 */
	
	/* 날짜 yyyy-MM-dd HH:mm:ss 형태로 재구성하기 */
	function transToDateFormat(date){
		
		var transDate = new Date(date);	// 자바스크립트 Date객체로 재변환.
		var yyyy = transDate.getFullYear();
		var MM = transDate.getMonth()+1 < 10 ? "0"+(transDate.getMonth()+1) : transDate.getMonth()+1;	// getMonth()는 0부터 반환하므로 +1 해줘야 됨.
		var dd = transDate.getDate() < 10 ? "0"+transDate.getDate() : transDate.getDate();
		var HH = transDate.getHours() < 10 ? "0"+transDate.getHours() : transDate.getHours();
		var mm = transDate.getMinutes() < 10 ? "0"+transDate.getMinutes() : transDate.getMinutes();
		var ss = transDate.getSeconds() < 10 ? "0"+transDate.getSeconds() : transDate.getSeconds();
		
		var fullDate = yyyy+"-"+MM+"-"+dd+" "+HH+":"+mm+":"+ss;
		
		return fullDate;
	}
	
	/* 테이블 재구성하기 */
	function reCreateCommentTable(commentList, loginMember_id, memberRank){
	
		$("#commentTable").empty();	// 테이블에 딸린 기존 값 모두 제거.
		// 테이블 만들기.
		for(var i = 0; i < commentList.length; i++){
			var commentDetail = commentList[i];
			var regdate = transToDateFormat(commentDetail.regdate);	// 입력일 포멧 변경.
			
			var row = '<tr>';	// 행 시작
			
			row += '<td style="width:20%">&nbsp;<img src="/images/member/'+commentDetail.levelname+'.png">'+commentDetail.nickname+'</td>';
			row += '<td style="width:50%" id="content_'+commentDetail.comment_id+'">&nbsp;&nbsp;'+commentDetail.content+'</td>';
			row += '<td style="width:15%; text-align:center">'+regdate+'</td>';
			row += '<td style="width:15%; text-align:center">';
			if(loginMember_id == commentDetail.member_id){	// 댓글 쓴 사람이면,
				row += '<a data-toggle="modal" href="#modal_'+commentDetail.comment_id+'">수정</a>&nbsp;|';	
				row += '<div id="modal_'+commentDetail.comment_id+'" class="modal fade">';	// modal 시작.
				row += '<div class="modal-dialog">';
				row += '<div class="modal-content">';
				row += '<div class="modal-header">';
				row += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
				row += '<h4 class="modal-title">댓글수정</h4>';
				row += '</div>';
				row += '<div class="modal-body">';
				row += '<textarea style="width:100%" class="form-control" rows="3" maxlength="50" id="edit_'+commentDetail.comment_id+'">'+commentDetail.content+'</textarea>';
				row += '</div>';
				row += '<div class="modal-footer">';
				row += '<input type="button" class="btn btn-primary" value="수정하기" onClick="updateComment('+commentDetail.comment_id+')">';
				row += '<input type="button" class="btn btn-danger" data-dismiss="modal" value="닫기" onClick="closeModal('+commentDetail.comment_id+',\''+commentDetail.content+'\')">';
				row += '</div>';
				row += '</div>';
				row += '</div>';
				row += '</div>';	// modal 끝.
			}
			if(loginMember_id == commentDetail.member_id || memberRank == 1){	// 댓글쓴 사람이거나 관리자면,
				row += '<a href="javascript:deleteComment('+commentDetail.comment_id+')">&nbsp;삭제</a>';
			}
			row += '</td>';
			row += '</tr>';	// 행 끝
		 
			$("#commentTable").append(row);	// 행 추가.
			
		}// for commentList
		
	}// function reCreateCommentTable