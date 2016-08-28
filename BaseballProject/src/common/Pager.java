package common;

public class Pager {
	
	private int page;	// 현재 페이지
	private int startPage;	// 페이징 블럭 시작페이지 번호
	private int endPage;	// 페이징 블럭 마지막 페이지 번호
	private boolean prev;	// 이전 페이징 블럭 존재 유무
	private boolean next;	// 다음 페이징 블럭 존재 유무
	private int totalPage;	// 총 페이지 갯수
	private int pageSize;	// 화면당 표시될 게시물 수
	private int blockSize;	// 화면당 표시될 페이징 번호 수
	private int startContent;	// 화면당 첫페이지 번호
	private int endContent;	// 화면당 마지막 페이지 번호
	private int totalContents;	// 총 게시물 수
	
	public Pager(int page, int pageSize, int totalContents, int blockSize) {
		this.page = page;
		this.pageSize = pageSize;
		this.totalContents = totalContents;
		
		// DB 불러올 값 설정
		endContent = page * pageSize;	// 페이지당 마지막 게시물 번호
		startContent = endContent - (pageSize-1);	// 페이지당 시작 게시물 번호
		
		// 페이징처리
		totalPage = (int)Math.ceil(totalContents/(double)pageSize);	
		endPage = (int) Math.ceil(page/(double)blockSize)*blockSize;	
		startPage = endPage - (blockSize - 1);	
		
		if(endPage >= totalPage){
			endPage = totalPage;
		}
		
		if((startPage-1) != 0){
			prev = true;
		}
		if((endPage+1) <= totalPage){
			next = true;
		}
	}

	public int getTotalContents() {
		return totalContents;
	}

	public void setTotalContents(int totalContents) {
		this.totalContents = totalContents;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {// 이전페이지 존재 여부
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {// 다음 페이지 존재여부
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getStartContent() {
		return startContent;
	}

	public void setStartContent(int startContent) {
		this.startContent = startContent;
	}

	public int getEndContent() {
		return endContent;
	}

	public void setEndContent(int endContent) {
		this.endContent = endContent;
	}
	
}
