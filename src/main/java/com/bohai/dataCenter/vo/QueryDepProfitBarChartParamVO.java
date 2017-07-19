package com.bohai.dataCenter.vo;

/**
 * 查询某营业部每个月的利润
 * @author BHQH
 *
 */
public class QueryDepProfitBarChartParamVO {
/**
 * 要查询的营业部名称
 */
private String depName;
private String year;

public String getYear() {
	return year;
}

public void setYear(String year) {
	this.year = year;
}

public String getDepName() {
	return depName;
}

public void setDepName(String depName) {
	this.depName = depName;
}	

}
