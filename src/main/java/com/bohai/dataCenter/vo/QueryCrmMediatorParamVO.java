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
	
	//开始失效时间
	private String expireBegin;
	
	//结束失效时间
	private String expireEnd;
	

    public String getExpireBegin() {
        return expireBegin;
    }

    public void setExpireBegin(String expireBegin) {
        this.expireBegin = expireBegin;
    }

    public String getExpireEnd() {
        return expireEnd;
    }

    public void setExpireEnd(String expireEnd) {
        this.expireEnd = expireEnd;
    }

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
