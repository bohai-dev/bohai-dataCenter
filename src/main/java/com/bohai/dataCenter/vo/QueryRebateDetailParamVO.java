package com.bohai.dataCenter.vo;

public class QueryRebateDetailParamVO {
    
    /**
     * 统计开始日期yyyy-mm-dd
     */
    private String beginDate;

    /**
     * 统计结束日期yyyy-mm-dd
     */
    private String endDate;
    
    /**
     * 营业部
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
     * 居间人名称
     */
    private String mediatorName;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
