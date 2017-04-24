package com.bohai.dataCenter.vo;

import java.util.List;

/**
 * bootstrap table数据返回类
 * @author caojia
 * @param <T> 类型
 */
public class TableJsonResponse<T> {
	
	//总记录数
	private Long total;
	
	//数据列表
	private List<T> rows;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
}
