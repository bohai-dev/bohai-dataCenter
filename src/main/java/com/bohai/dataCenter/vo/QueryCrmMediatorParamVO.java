package com.bohai.dataCenter.vo;

/**
 * 查询居间人信息入参
 * @author caojia
 */
public class QueryCrmMediatorParamVO {
	
	private String deptCode;
	
	private String mediatorNo;
	
	private String mediatorName;
	
	private String belongType;
	
	private String belongTo;

    public String getBelongType() {
        return belongType;
    }

    public void setBelongType(String belongType) {
        this.belongType = belongType;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
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
