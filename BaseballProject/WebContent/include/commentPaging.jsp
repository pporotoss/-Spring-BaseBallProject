<%-- 

** 댓글 페이징 처리 전에 반드시 아래와 같이 변수 선언 필요!!	**

	<c:set var="url" value="/view/board/${게시물번호 }?commentPage=댓글페이지"/>
 
 --%>
<div align="center">
  	<nav>
	  <ul class="pagination">
	    <c:if test="${commentPager.prev }">
		    <li>
		      <a href="${url}${(commentPager.startPage-1)}" aria-label="Previous">
		        <span aria-hidden="true">&laquo;</span>
		      </a>
		    </li>
	    </c:if>
	    <c:forEach var="cnt" begin="${commentPager.startPage }" end="${commentPager.endPage }">
	    	<c:choose>
		    	<c:when test="${cnt == commentPager.page }">
		    		<c:set var="pageClass" value="active"/>
		    	</c:when>
		    	<c:otherwise>
		    		<c:set var="pageClass" value=""/>
		    	</c:otherwise>
	    	</c:choose>	
	    
	    	<li class="${pageClass }">
	    		<a href="${url}${cnt }">${cnt }</a>
	    	</li>
	    </c:forEach>
	    <c:if test="${commentPager.next }">
		    <li>
		      <a href="${url }${commentPager.endPage+1}" aria-label="Next">
		        <span aria-hidden="true">&raquo;</span>
		      </a>
		    </li>
	    </c:if>
	  </ul>
	</nav>
</div>