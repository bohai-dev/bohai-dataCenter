package com.bohai.dataCenter.vo;

/**
 * 查询居间人提成报表
 * @author caojia
 */
public class QueryMediatorReportParamVO extends PaginationParamVO{
    
    /**
     * 居间人编号
     */
    private String mediatorNo;
    
    /**
     * 居间人名称
     */
    private String mediatorName;
    
    /**
     * 部门编号
     */
    private String depNo;
    
    /**
     * 统计月份
     */
    private String reportMonth;
    
    /**
     * 是否修改 1：是   0：否
     */
    private String isChange;

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

    public String getDepNo() {
        return depNo;
    }

    public void setDepNo(String depNo) {
        this.depNo = depNo;
    }

    public String getReportMonth() {
        return reportMonth;
    }

    public void setReportMonth(String reportMonth) {
        this.reportMonth = reportMonth;
    }

    public String getIsChange() {
        return isChange;
    }

    public void setIsChange(String isChange) {
        this.isChange = isChange;
    }
    

}
