/**
 	댓글 ajax로 페이징 새로 만들기.
 */

function reCreatePaging(commentPager){
	
	
	$("#commentPaging").empty();
	
	var nav = '<nav>';
	nav += '<ul class="pagination">';
	if(commentPager.prev){
		nav += '<li>';
		nav += '<a href="javascript:getCommentList('+(commentPager.startPage-1)+')" aria-label="Previous">';
		nav += '<span aria-hidden="true">&laquo;</span>';
		nav += '</a>';
		nav += '</li>';
	}
	
	for(var i = commentPager.startPage; i <= commentPager.endPage; i++){
		if(commentPager.page == i){
			nav += '<input type="hidden" id="currentPage" value="'+i+'">';
			nav += '<li class="active">';
		}else{
			nav += '<li>';
		}
		nav += '<a href="javascript:getCommentList('+i+')">'+i+'</a>';
		nav += '</li>';
	}// for commentPager.startPage
	
	if(commentPager.next){
		nav += '<li>';
		nav += '<a href="javascript:getCommentList('+commentPager.endPage+1+')" aria-label="Next">';
		nav += '<span aria-hidden="true">&raquo;</span>';
		nav += '</a>';
		nav += '</li>';
	}
	nav += '</ul>';
	nav += '</nav>';
	  
	$("#commentPaging").append(nav);	// 페이징 추가.
}