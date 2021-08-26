package bg.infosys.interns.vregister.core.dto;

public class PagingSorting {
	private int pageNumber;
	private int pageSize;
	private String sortBy;
	private String sortDirection;
	
	public PagingSorting(int pageNumber, int pageSize, String sortBy, String sortDirection) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.sortBy = sortBy;
		this.sortDirection = sortDirection;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public String getSortBy() {
		return sortBy;
	}
	
	public String getSortDirection() {
		return sortDirection;
	}
}
