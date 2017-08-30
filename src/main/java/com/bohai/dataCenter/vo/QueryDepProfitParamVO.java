package com.bohai.dataCenter.vo;

/**
 * 查询营业部利润入参
 * @author caojia
 */
public class QueryDepProfitParamVO {
    
    private String depNo;

    private String depName;
    
    private String reportMonth;
    
    public String getDepNo() {
        return depNo;
    }

    public void setDepNo(String depNo) {
        this.depNo = depNo;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getReportMonth() {
        return reportMonth;
    }

    public void setReportMonth(String reportMonth) {
        this.reportMonth = reportMonth;
    }
    

}
