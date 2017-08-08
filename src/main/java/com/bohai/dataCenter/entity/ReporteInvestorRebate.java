package com.bohai.dataCenter.entity;

import java.util.Date;

/**
 * 交易所返还到投资者统计报表
 * @author BHQH-CXYWB
 *
 */
public class ReporteInvestorRebate {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORTE_INVESTOR_REBATE.MONTH
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    private String month;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORTE_INVESTOR_REBATE.INVESTOR_NO
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    private String investorNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORTE_INVESTOR_REBATE.INVESTOR_NAME
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    private String investorName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORTE_INVESTOR_REBATE.SREBATE
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    private String srebate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORTE_INVESTOR_REBATE.ZREBATE
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    private String zrebate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORTE_INVESTOR_REBATE.JREBATE
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    private String jrebate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORTE_INVESTOR_REBATE.DREBATE
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    private String drebate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORTE_INVESTOR_REBATE.CREATE_TIME
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_REPORTE_INVESTOR_REBATE.UPDATE_TIME
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORTE_INVESTOR_REBATE.MONTH
     *
     * @return the value of T_REPORTE_INVESTOR_REBATE.MONTH
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public String getMonth() {
        return month;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORTE_INVESTOR_REBATE.MONTH
     *
     * @param month the value for T_REPORTE_INVESTOR_REBATE.MONTH
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORTE_INVESTOR_REBATE.INVESTOR_NO
     *
     * @return the value of T_REPORTE_INVESTOR_REBATE.INVESTOR_NO
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public String getInvestorNo() {
        return investorNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORTE_INVESTOR_REBATE.INVESTOR_NO
     *
     * @param investorNo the value for T_REPORTE_INVESTOR_REBATE.INVESTOR_NO
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public void setInvestorNo(String investorNo) {
        this.investorNo = investorNo == null ? null : investorNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORTE_INVESTOR_REBATE.INVESTOR_NAME
     *
     * @return the value of T_REPORTE_INVESTOR_REBATE.INVESTOR_NAME
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public String getInvestorName() {
        return investorName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORTE_INVESTOR_REBATE.INVESTOR_NAME
     *
     * @param investorName the value for T_REPORTE_INVESTOR_REBATE.INVESTOR_NAME
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public void setInvestorName(String investorName) {
        this.investorName = investorName == null ? null : investorName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORTE_INVESTOR_REBATE.SREBATE
     *
     * @return the value of T_REPORTE_INVESTOR_REBATE.SREBATE
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public String getSrebate() {
        return srebate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORTE_INVESTOR_REBATE.SREBATE
     *
     * @param srebate the value for T_REPORTE_INVESTOR_REBATE.SREBATE
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public void setSrebate(String srebate) {
        this.srebate = srebate == null ? null : srebate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORTE_INVESTOR_REBATE.ZREBATE
     *
     * @return the value of T_REPORTE_INVESTOR_REBATE.ZREBATE
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public String getZrebate() {
        return zrebate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORTE_INVESTOR_REBATE.ZREBATE
     *
     * @param zrebate the value for T_REPORTE_INVESTOR_REBATE.ZREBATE
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public void setZrebate(String zrebate) {
        this.zrebate = zrebate == null ? null : zrebate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORTE_INVESTOR_REBATE.JREBATE
     *
     * @return the value of T_REPORTE_INVESTOR_REBATE.JREBATE
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public String getJrebate() {
        return jrebate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORTE_INVESTOR_REBATE.JREBATE
     *
     * @param jrebate the value for T_REPORTE_INVESTOR_REBATE.JREBATE
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public void setJrebate(String jrebate) {
        this.jrebate = jrebate == null ? null : jrebate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORTE_INVESTOR_REBATE.DREBATE
     *
     * @return the value of T_REPORTE_INVESTOR_REBATE.DREBATE
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public String getDrebate() {
        return drebate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORTE_INVESTOR_REBATE.DREBATE
     *
     * @param drebate the value for T_REPORTE_INVESTOR_REBATE.DREBATE
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public void setDrebate(String drebate) {
        this.drebate = drebate == null ? null : drebate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORTE_INVESTOR_REBATE.CREATE_TIME
     *
     * @return the value of T_REPORTE_INVESTOR_REBATE.CREATE_TIME
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORTE_INVESTOR_REBATE.CREATE_TIME
     *
     * @param createTime the value for T_REPORTE_INVESTOR_REBATE.CREATE_TIME
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_REPORTE_INVESTOR_REBATE.UPDATE_TIME
     *
     * @return the value of T_REPORTE_INVESTOR_REBATE.UPDATE_TIME
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_REPORTE_INVESTOR_REBATE.UPDATE_TIME
     *
     * @param updateTime the value for T_REPORTE_INVESTOR_REBATE.UPDATE_TIME
     *
     * @mbggenerated Mon May 22 16:38:06 CST 2017
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}