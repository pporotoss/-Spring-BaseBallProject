package common;

public class Pager {
	
	private int page;	// ���� ������
	private int startPage;	// ����¡ �� ���������� ��ȣ
	private int endPage;	// ����¡ �� ������ ������ ��ȣ
	private boolean prev;	// ���� ����¡ �� ���� ����
	private boolean next;	// ���� ����¡ �� ���� ����
	private int totalPage;	// �� ������ ����
	private int pageSize;	// ȭ��� ǥ�õ� �Խù� ��
	private int blockSize;	// ȭ��� ǥ�õ� ����¡ ��ȣ ��
	private int startContent;	// ȭ��� ù������ ��ȣ
	private int endContent;	// ȭ��� ������ ������ ��ȣ
	private int totalContents;	// �� �Խù� ��
	
	public Pager(int page, int pageSize, int totalContents, int blockSize) {
		this.page = page;
		this.pageSize = pageSize;
		this.totalContents = totalContents;
		
		// DB �ҷ��� �� ����
		endContent = page * pageSize;	// �������� ������ �Խù� ��ȣ
		startContent = endContent - (pageSize-1);	// �������� ���� �Խù� ��ȣ
		
		// ����¡ó��
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

	public boolean isPrev() {// ���������� ���� ����
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {// ���� ������ ���翩��
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
