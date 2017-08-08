package com.bohai.dataCenter.vo;

/**
 * 查询CRM营销人员信息
 * @author caojia
 */
public class QueryCrmMarketerParamVO {
    
    private String depCode;
	
    private String marketerNo;
    
    private String marketerName;

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getMarketerName() {
        return marketerName;
    }

    public void setMarketerName(String marketerName) {
        this.marketerName = marketerName;
    }

    public String getMarketerNo() {
        return marketerNo;
    }

    public void setMarketerNo(String marketerNo) {
        this.marketerNo = marketerNo;
    }
    
    

}
