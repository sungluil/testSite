package com.spring.board.vo;

public class PageVo {

	private int pageNo;//현재페이지
	private int pageSize;//뿌려질글 갯수
	private int totalCnt;//총갯수
	private int blockSize;//블럭갯수
	
	private int totalPage;
	private int startRow;
	private int endRow;

	private int startPage;
	private int endPage;
	private int prevPage;
	private int nextPage;
	
	private int size;

	public PageVo() {
		// TODO Auto-generated constructor stub
	}

	
	
	public PageVo(int pageNo) {
		super();
		this.pageNo = pageNo;
	}



	public PageVo(int pageNo, int pageSize, int totalCnt, int blockSize) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalCnt = totalCnt;
		this.blockSize = blockSize;
		
		calcPager();
	}

	
	private void calcPager() {
		

		totalPage=(totalCnt+pageSize-1)/pageSize;
		

		if(pageNo < 1) pageNo=1;
		if(pageNo > totalPage) pageNo=totalPage;
		

		startRow = (pageNo-1) * pageSize + 1;
		//System.out.println("startRow = "+startRow);

		endRow = pageNo * pageSize;
		//System.out.println("pageNo = "+pageNo+", pageSize = "+pageSize);
		//System.out.println(pageNo * pageSize);
		//System.out.println("endRow = "+endRow);

		if(endRow>totalCnt) {
			endRow=totalCnt;
		}
		

		startPage = (pageNo-1) / blockSize * blockSize + 1;
		

		endPage = startPage + blockSize - 1;
		

		if(endPage>totalPage) {
			endPage=totalPage;
		}
		
		prevPage=startPage-blockSize;
		nextPage=startPage+blockSize;
		
		size=pageSize;
	}


	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
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

	public int getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

}





















