package com.bohai.dataCenter.vo;

/**
 * 分页查询通用请求参数
 * @author caojia
 *
 */
public class PaginationParamVO {
	
	private Long offset;
	
	private Long pages;
	
	private String orderBy;
	
	private String sort;

	public Long getOffset() {
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}

	public Long getPages() {
		return pages;
	}

	public void setPages(Long pages) {
		this.pages = pages;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	
}
