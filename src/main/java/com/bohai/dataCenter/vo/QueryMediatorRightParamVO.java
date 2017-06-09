package com.bohai.dataCenter.vo;

/**
 * 查询居间人名下权益入参
 * @author caojia
 *
 */
public class QueryMediatorRightParamVO {

    private String mediatorNo;
    
    private String mediatorName;
    
    private String year;
    
    private String month;

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
    
}
