<%-- 

** 댓글 페이징 처리 전에 반드시 아래와 같은 자바스크립트 메서드 정의!!	**

	function getCommentList(commentPage){
		
		$.get("/api/photo/${photoDetail.photoBoard_id}/comment?commentPage="+commentPage, function(data){
	        var photoCommentList = data.photoCommentList;	// 넘어온 댓글 리스트.
	        var commentPager = data.commentPager;	// 넘어온 페이징 객체.
			
			var loginMember_id = 0;
	        var memberRank = 0;
	        
	        <c:if test="${loginMember != null}">	// 로그인 했으면,
        		loginMember_id = ${loginMember.member_id};
        		memberRank = ${loginMember.rank};
        	</c:if>
	        
			reCreateCommentTable(commentList, loginMember_id, memberRank);// 테이블 새로 만들기.
			
			reCreatePaging(commentPager);	// 페이징 새로 만들기. 공통 사용 가능.
	        
	    });
		
	}
	
 ** 페이징 객체의 이름은 commentPager로 넘겨야 한다.	**
 
 --%>
<div align="center" id="commentPaging">
  	<nav>
	  <ul class="pagination">
	    <c:if test="${commentPager.prev }">
		    <li>
		      <a href="javascript:getCommentList(${(commentPager.startPage-1)})" aria-label="Previous">
		        <span aria-hidden="true">&laquo;</span>
		      </a>
		    </li>
	    </c:if>
	    <c:forEach var="cnt" begin="${commentPager.startPage }" end="${commentPager.endPage }">
	    	<c:if test="${cnt == commentPager.page }">
	    		<input type="hidden" id="currentPage" value="${cnt }">
	    	</c:if>
	    	<li class=<c:out value="${cnt == commentPager.page ? 'active' : '' }"/>>
	    		<a href="javascript:getCommentList(${cnt })">${cnt }</a>
	    	</li>
	    </c:forEach>
	    <c:if test="${commentPager.next }">
		    <li>
		      <a href="javascript:getCommentList(${commentPager.endPage+1})" aria-label="Next">
		        <span aria-hidden="true">&raquo;</span>
		      </a>
		    </li>
	    </c:if>
	  </ul>
	</nav>
</div>