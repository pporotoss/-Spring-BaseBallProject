package common;

public class Pager {
	
	private int page;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	private int totalPage;
	private int pageSize;	// ȭ��� ǥ�õ� �Խù� ��
	private int blockSize;	// ȭ��� ǥ�õ� ����¡ ��ȣ ��
	private int startContent;
	private int endContent;
	
	public Pager(int page, int pageSize, int totalContents, int blockSize) {
		this.page = page;
		this.pageSize = pageSize;
		
		// DB �ҷ��� �� ����
		endContent = page * pageSize;
		startContent = endContent - (pageSize-1);	// �������� ���۰�
		
		// ����¡ó��
		totalPage = (int)Math.ceil(totalContents/(double)pageSize);	// �� ������ ����
		endPage = (int) Math.ceil(page/(double)blockSize)*blockSize;	// ȭ�� ������ ������
		startPage = endPage - (blockSize - 1);	// ȭ�� ù������
		
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

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
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
