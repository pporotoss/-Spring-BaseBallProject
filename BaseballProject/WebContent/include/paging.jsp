<%-- 

** 페이징 처리 전에 반드시 아래와 같이 변수 선언 필요!!	**

	<c:set var="url" value="/view/board?page="/> => 이동할 주소 + 페이지 번호까지 입력.
	<c:set var="url1" value="&pagesize=${pager.pageSize}"/>	=> 페이지당 보여질 게시물 갯수 지정했으면 갯수 지정. 안했으면 밑에꺼 땡겨다 쓰면 댐.
	<c:set var="url2" value=""/>	=> 검색어 관련 입력.
	<c:if test="${searching.keyword != null }">
		<c:set var="url2" value="&searchType=${searching.searchType}&keyword=${searching.keyword}"/>
	</c:if>

 --%>
<div align="center">
  	<nav>
	  <ul class="pagination">
	    <c:if test="${pager.prev }">
		    <li>
		      <a href="${url}${(pager.startPage-1)}${url1}${url2}" aria-label="Previous">
		        <span aria-hidden="true">&laquo;</span>
		      </a>
		    </li>
	    </c:if>
	    <c:forEach var="cnt" begin="${pager.startPage }" end="${pager.endPage }">
	    	<c:choose>
		    	<c:when test="${cnt == pager.page }">
		    		<c:set var="pageClass" value="active"/>
		    	</c:when>
		    	<c:otherwise>
		    		<c:set var="pageClass" value=""/>
		    	</c:otherwise>
	    	</c:choose>	
	    
	    	<li class="${pageClass }">
	    		<a href="${url}${cnt }${url1}${url2}">${cnt }</a>
	    	</li>
	    </c:forEach>
	    <c:if test="${pager.next }">
		    <li>
		      <a href="${url }${pager.endPage+1}${url1}${url2}" aria-label="Next">
		        <span aria-hidden="true">&raquo;</span>
		      </a>
		    </li>
	    </c:if>
	  </ul>
	</nav>
</div>