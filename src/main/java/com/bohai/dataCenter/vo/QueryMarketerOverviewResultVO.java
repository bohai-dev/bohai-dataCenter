package com.bohai.dataCenter.vo;

/**
 * 查询营销人员简介返回
 * @author caojia
 */
public class QueryMarketerOverviewResultVO {

    private String marketerNo;
    
    private String marketerName;
    
    private String phone;
    
    private String entryDate;
    
    private Long mediatorCount;
    
    private Long directCustomerCount;
    
    private String depName;
    
    private String marketerRate;
    
    

    public String getMarketerRate() {
        return marketerRate;
    }

    public void setMarketerRate(String marketerRate) {
        this.marketerRate = marketerRate ==null?"无": marketerRate;
    }

    public String getMarketerNo() {
        return marketerNo;
    }

    public void setMarketerNo(String marketerNo) {
        this.marketerNo = marketerNo==null?"无":marketerNo;
    }

    public String getMarketerName() {
        return marketerName;
    }

    public void setMarketerName(String marketerName) {
        this.marketerName = marketerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone ==null?"无":phone;
    }

    public Long getMediatorCount() {
        return mediatorCount;
    }

    public void setMediatorCount(Long mediatorCount) {
        this.mediatorCount = mediatorCount;
    }

    public Long getDirectCustomerCount() {
        return directCustomerCount;
    }

    public void setDirectCustomerCount(Long directCustomerCount) {
        this.directCustomerCount = directCustomerCount;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate ==null?"无": entryDate;
    }
    
    
}
