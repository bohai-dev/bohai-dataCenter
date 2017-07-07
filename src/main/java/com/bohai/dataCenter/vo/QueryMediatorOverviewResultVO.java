package com.bohai.dataCenter.vo;

/**
 * 查询居间人简介
 * @author caojia
 *
 */
public class QueryMediatorOverviewResultVO {
    
    /**
     * 居间人编号
     */
    private String mediatorNo;
    
    /**
     * 居间人名称
     */
    private String mediatorName;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 生效日期
     */
    private String effectDate;
    
    /**
     * 失效日期
     */
    private String expireDate;
    
    /**
     * 客户数量
     */
    private Integer customerCount;
    
    /**
     * 营业部
     */
    private String depName;
    
    /**
     * 营销人员编号
     */
    private String marketerNo;
    
    /**
     * 营销人员名称
     */
    private String marketerName;
    
    /**
     * 手续费比例
     */
    private String chargeRate;
    
    /**
     * 利息比例
     */
    private String interestRate;
    
    /**
     * 交返比例
     */
    private String exchangeRate;
    
    private String marketerRate;
    
    

    public String getMarketerRate() {
        return marketerRate;
    }

    public void setMarketerRate(String marketerRate) {
        this.marketerRate = marketerRate ==null?"无": marketerRate;
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

    public String getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(String effectDate) {
        this.effectDate = effectDate ==null?"无": effectDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate ==null?"无": expireDate;
    }

    public Integer getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(Integer customerCount) {
        this.customerCount = customerCount;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone ==null?"无": phone;
    }

    public String getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(String chargeRate) {
        this.chargeRate = chargeRate;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate ==null?"无": interestRate;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate ==null?"无": exchangeRate;
    }
    

}
