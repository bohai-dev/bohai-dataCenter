package com.bohai.dataCenter.entity;

import java.util.Date;

public class ReportMediator {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORT_MEDIATOR.MEDIATOR_NO
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    private String mediatorNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORT_MEDIATOR.MEDIATOR_NAME
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    private String mediatorName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORT_MEDIATOR.DEP_NO
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    private String depNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORT_MEDIATOR.DEP_NAME
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    private String depName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORT_MEDIATOR.ORIG_INTEREST
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    private String origInterest;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORT_MEDIATOR.ORIG_EXCHANGE_REBATE
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    private String origExchangeRebate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORT_MEDIATOR.ORIG_CHARGE
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    private String origCharge;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORT_MEDIATOR.MOD_INTEREST
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    private String modInterest;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORT_MEDIATOR.MOD_EXCHANGE_REBATE
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    private String modExchangeRebate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORT_MEDIATOR.MOD_CHARGE
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    private String modCharge;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORT_MEDIATOR.REMARK
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORT_MEDIATOR.REPORT_MONTH
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    private String reportMonth;
    
    private Date updateTime;
    
    private Date createTime;
    
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORT_MEDIATOR.MEDIATOR_NO
     *
     * @return the value of T_REPORT_MEDIATOR.MEDIATOR_NO
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public String getMediatorNo() {
        return mediatorNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORT_MEDIATOR.MEDIATOR_NO
     *
     * @param mediatorNo the value for T_REPORT_MEDIATOR.MEDIATOR_NO
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public void setMediatorNo(String mediatorNo) {
        this.mediatorNo = mediatorNo == null ? null : mediatorNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORT_MEDIATOR.MEDIATOR_NAME
     *
     * @return the value of T_REPORT_MEDIATOR.MEDIATOR_NAME
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public String getMediatorName() {
        return mediatorName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORT_MEDIATOR.MEDIATOR_NAME
     *
     * @param mediatorName the value for T_REPORT_MEDIATOR.MEDIATOR_NAME
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public void setMediatorName(String mediatorName) {
        this.mediatorName = mediatorName == null ? null : mediatorName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORT_MEDIATOR.DEP_NO
     *
     * @return the value of T_REPORT_MEDIATOR.DEP_NO
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public String getDepNo() {
        return depNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORT_MEDIATOR.DEP_NO
     *
     * @param depNo the value for T_REPORT_MEDIATOR.DEP_NO
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public void setDepNo(String depNo) {
        this.depNo = depNo == null ? null : depNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORT_MEDIATOR.DEP_NAME
     *
     * @return the value of T_REPORT_MEDIATOR.DEP_NAME
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public String getDepName() {
        return depName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORT_MEDIATOR.DEP_NAME
     *
     * @param depName the value for T_REPORT_MEDIATOR.DEP_NAME
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public void setDepName(String depName) {
        this.depName = depName == null ? null : depName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORT_MEDIATOR.ORIG_INTEREST
     *
     * @return the value of T_REPORT_MEDIATOR.ORIG_INTEREST
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public String getOrigInterest() {
        return origInterest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORT_MEDIATOR.ORIG_INTEREST
     *
     * @param origInterest the value for T_REPORT_MEDIATOR.ORIG_INTEREST
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public void setOrigInterest(String origInterest) {
        this.origInterest = origInterest == null ? null : origInterest.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORT_MEDIATOR.ORIG_EXCHANGE_REBATE
     *
     * @return the value of T_REPORT_MEDIATOR.ORIG_EXCHANGE_REBATE
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public String getOrigExchangeRebate() {
        return origExchangeRebate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORT_MEDIATOR.ORIG_EXCHANGE_REBATE
     *
     * @param origExchangeRebate the value for T_REPORT_MEDIATOR.ORIG_EXCHANGE_REBATE
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public void setOrigExchangeRebate(String origExchangeRebate) {
        this.origExchangeRebate = origExchangeRebate == null ? null : origExchangeRebate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORT_MEDIATOR.ORIG_CHARGE
     *
     * @return the value of T_REPORT_MEDIATOR.ORIG_CHARGE
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public String getOrigCharge() {
        return origCharge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORT_MEDIATOR.ORIG_CHARGE
     *
     * @param origCharge the value for T_REPORT_MEDIATOR.ORIG_CHARGE
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public void setOrigCharge(String origCharge) {
        this.origCharge = origCharge == null ? null : origCharge.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORT_MEDIATOR.MOD_INTEREST
     *
     * @return the value of T_REPORT_MEDIATOR.MOD_INTEREST
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public String getModInterest() {
        return modInterest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORT_MEDIATOR.MOD_INTEREST
     *
     * @param modInterest the value for T_REPORT_MEDIATOR.MOD_INTEREST
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public void setModInterest(String modInterest) {
        this.modInterest = modInterest == null ? null : modInterest.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORT_MEDIATOR.MOD_EXCHANGE_REBATE
     *
     * @return the value of T_REPORT_MEDIATOR.MOD_EXCHANGE_REBATE
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public String getModExchangeRebate() {
        return modExchangeRebate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORT_MEDIATOR.MOD_EXCHANGE_REBATE
     *
     * @param modExchangeRebate the value for T_REPORT_MEDIATOR.MOD_EXCHANGE_REBATE
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public void setModExchangeRebate(String modExchangeRebate) {
        this.modExchangeRebate = modExchangeRebate == null ? null : modExchangeRebate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORT_MEDIATOR.MOD_CHARGE
     *
     * @return the value of T_REPORT_MEDIATOR.MOD_CHARGE
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public String getModCharge() {
        return modCharge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORT_MEDIATOR.MOD_CHARGE
     *
     * @param modCharge the value for T_REPORT_MEDIATOR.MOD_CHARGE
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public void setModCharge(String modCharge) {
        this.modCharge = modCharge == null ? null : modCharge.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORT_MEDIATOR.REMARK
     *
     * @return the value of T_REPORT_MEDIATOR.REMARK
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORT_MEDIATOR.REMARK
     *
     * @param remark the value for T_REPORT_MEDIATOR.REMARK
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORT_MEDIATOR.REPORT_MONTH
     *
     * @return the value of T_REPORT_MEDIATOR.REPORT_MONTH
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public String getReportMonth() {
        return reportMonth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORT_MEDIATOR.REPORT_MONTH
     *
     * @param reportMonth the value for T_REPORT_MEDIATOR.REPORT_MONTH
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    public void setReportMonth(String reportMonth) {
        this.reportMonth = reportMonth == null ? null : reportMonth.trim();
    }
}