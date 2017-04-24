package com.bohai.dataCenter.vo;

/**
 * 查询返利报表入参
 * @author caojia
 */
public class QueryRebateReportParamVO {

	/**
	 * 统计年份
	 */
	private String year;
	
	/**
	 * 统计月份
	 */
	private String month;
	
	/**
	 * 营业部名称
	 */
	private String depName;
	
	/**
	 * 投资者账号
	 */
	private String investorNo;
	
	/**
	 * 投资者名称
	 */
	private String investorName;
	
	/**
	 * 居间人编号
	 */
	private String mediatorNo;
	
	/**
	 * 居间人姓名
	 */
	private String mediatorName;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getInvestorNo() {
		return investorNo;
	}

	public void setInvestorNo(String investorNo) {
		this.investorNo = investorNo;
	}

	public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}

	public String getMediatorNo() {
		return mediatorNo;
	}

	public void setMediatorNo(String mediatorNo) {
		this.mediatorNo = mediatorNo;
	}

	public String getMediatorName() {
		return mediatorName;
	}

	public void setMediatorName(String mediatorName) {
		this.mediatorName = mediatorName;
	}
	
}
