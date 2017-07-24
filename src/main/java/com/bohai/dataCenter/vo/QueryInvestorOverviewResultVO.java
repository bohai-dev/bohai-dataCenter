package com.bohai.dataCenter.vo;

/**
 * 客户概览返回参数
 * @author caojia
 */
public class QueryInvestorOverviewResultVO {
    
    /**
     * 投资者代码
     */
    private String investorNo;
    
    /**
     * 投资者名称
     */
    private String investorName;

    /**
     * 开户日期
     */
    private String openDate;
    
    /**
     * 电话
     */
    private String phone;
    
    /**
     * 居间人编号
     */
    private String mediatorNo;
    
    /**
     * 居间人名称
     */
    private String mediatorName;
    
    /**
     * 手续费比例
     */
    private String chargeRate;
    
    /**
     * 交返比例
     */
    private String exchangeRate;
    
    /**
     * 利息比例
     */
    private String interestRate;
    
    /**
     * 营销人员编号
     */
    private String marketerNo;
    
    /**
     * 营销人员名称
     */
    private String marketerName;
    
    /**
     * 营业部
     */
    private String depName;
    
    private String marketerRate;
    
    

    public String getMarketerRate() {
        return marketerRate;
    }

    public void setMarketerRate(String marketerRate) {
        this.marketerRate = marketerRate ==null?"无": marketerRate;
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

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone ==null?"无": phone;
    }

    public String getMediatorNo() {
        return mediatorNo;
    }

    public void setMediatorNo(String mediatorNo) {
        this.mediatorNo = mediatorNo ==null?"无": mediatorNo;
    }

    public String getMediatorName() {
        return mediatorName;
    }

    public void setMediatorName(String mediatorName) {
        this.mediatorName = mediatorName ==null?"无": mediatorName;
    }

    public String getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(String chargeRate) {
        this.chargeRate = chargeRate ==null?"无": chargeRate;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate ==null?"无": exchangeRate;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate ==null?"无": interestRate;
    }

    public String getMarketerNo() {
        return marketerNo;
    }

    public void setMarketerNo(String marketerNo) {
        this.marketerNo = marketerNo ==null?"无": marketerNo;
    }

    public String getMarketerName() {
        return marketerName;
    }

    public void setMarketerName(String marketerName) {
        this.marketerName = marketerName ==null?"无": marketerName;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName ==null?"无": depName;
    }
    
    
}
