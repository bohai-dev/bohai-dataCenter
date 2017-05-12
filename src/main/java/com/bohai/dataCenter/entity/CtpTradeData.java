package com.bohai.dataCenter.entity;

import java.util.Date;

public class CtpTradeData {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CTPTRADE_DATA.TRADE_DATE
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    private String tradeDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CTPTRADE_DATA.INVESTOR_NO
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    private String investorNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CTPTRADE_DATA.INVESTOR_NAME
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    private String investorName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CTPTRADE_DATA.EXCHANGE_NAME
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    private String exchangeName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CTPTRADE_DATA.INSTRUMENT
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    private String instrument;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CTPTRADE_DATA.DIRECTION
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    private String direction;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CTPTRADE_DATA.OFFSET_FLAG
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    private String offsetFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CTPTRADE_DATA.HEDGE_FLAG
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    private String hedgeFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CTPTRADE_DATA.VOLUME
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    private String volume;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CTPTRADE_DATA.DEAL_PRICE
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    private String dealPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CTPTRADE_DATA.TRUNOVER
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    private String trunover;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CTPTRADE_DATA.CHARGE
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    private String charge;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CTPTRADE_DATA.EXCHANGE_CHARGE
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    private String exchangeCharge;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CTPTRADE_DATA.CREATE_TIME
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CTPTRADE_DATA.TRADE_DATE
     *
     * @return the value of T_CTPTRADE_DATA.TRADE_DATE
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public String getTradeDate() {
        return tradeDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CTPTRADE_DATA.TRADE_DATE
     *
     * @param tradeDate the value for T_CTPTRADE_DATA.TRADE_DATE
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate == null ? null : tradeDate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CTPTRADE_DATA.INVESTOR_NO
     *
     * @return the value of T_CTPTRADE_DATA.INVESTOR_NO
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public String getInvestorNo() {
        return investorNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CTPTRADE_DATA.INVESTOR_NO
     *
     * @param investorNo the value for T_CTPTRADE_DATA.INVESTOR_NO
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public void setInvestorNo(String investorNo) {
        this.investorNo = investorNo == null ? null : investorNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CTPTRADE_DATA.INVESTOR_NAME
     *
     * @return the value of T_CTPTRADE_DATA.INVESTOR_NAME
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public String getInvestorName() {
        return investorName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CTPTRADE_DATA.INVESTOR_NAME
     *
     * @param investorName the value for T_CTPTRADE_DATA.INVESTOR_NAME
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public void setInvestorName(String investorName) {
        this.investorName = investorName == null ? null : investorName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CTPTRADE_DATA.EXCHANGE_NAME
     *
     * @return the value of T_CTPTRADE_DATA.EXCHANGE_NAME
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public String getExchangeName() {
        return exchangeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CTPTRADE_DATA.EXCHANGE_NAME
     *
     * @param exchangeName the value for T_CTPTRADE_DATA.EXCHANGE_NAME
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName == null ? null : exchangeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CTPTRADE_DATA.INSTRUMENT
     *
     * @return the value of T_CTPTRADE_DATA.INSTRUMENT
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public String getInstrument() {
        return instrument;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CTPTRADE_DATA.INSTRUMENT
     *
     * @param instrument the value for T_CTPTRADE_DATA.INSTRUMENT
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public void setInstrument(String instrument) {
        this.instrument = instrument == null ? null : instrument.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CTPTRADE_DATA.DIRECTION
     *
     * @return the value of T_CTPTRADE_DATA.DIRECTION
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public String getDirection() {
        return direction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CTPTRADE_DATA.DIRECTION
     *
     * @param direction the value for T_CTPTRADE_DATA.DIRECTION
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public void setDirection(String direction) {
        this.direction = direction == null ? null : direction.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CTPTRADE_DATA.OFFSET_FLAG
     *
     * @return the value of T_CTPTRADE_DATA.OFFSET_FLAG
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public String getOffsetFlag() {
        return offsetFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CTPTRADE_DATA.OFFSET_FLAG
     *
     * @param offsetFlag the value for T_CTPTRADE_DATA.OFFSET_FLAG
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public void setOffsetFlag(String offsetFlag) {
        this.offsetFlag = offsetFlag == null ? null : offsetFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CTPTRADE_DATA.HEDGE_FLAG
     *
     * @return the value of T_CTPTRADE_DATA.HEDGE_FLAG
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public String getHedgeFlag() {
        return hedgeFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CTPTRADE_DATA.HEDGE_FLAG
     *
     * @param hedgeFlag the value for T_CTPTRADE_DATA.HEDGE_FLAG
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public void setHedgeFlag(String hedgeFlag) {
        this.hedgeFlag = hedgeFlag == null ? null : hedgeFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CTPTRADE_DATA.VOLUME
     *
     * @return the value of T_CTPTRADE_DATA.VOLUME
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public String getVolume() {
        return volume;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CTPTRADE_DATA.VOLUME
     *
     * @param volume the value for T_CTPTRADE_DATA.VOLUME
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public void setVolume(String volume) {
        this.volume = volume == null ? null : volume.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CTPTRADE_DATA.DEAL_PRICE
     *
     * @return the value of T_CTPTRADE_DATA.DEAL_PRICE
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public String getDealPrice() {
        return dealPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CTPTRADE_DATA.DEAL_PRICE
     *
     * @param dealPrice the value for T_CTPTRADE_DATA.DEAL_PRICE
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public void setDealPrice(String dealPrice) {
        this.dealPrice = dealPrice == null ? null : dealPrice.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CTPTRADE_DATA.TRUNOVER
     *
     * @return the value of T_CTPTRADE_DATA.TRUNOVER
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public String getTrunover() {
        return trunover;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CTPTRADE_DATA.TRUNOVER
     *
     * @param trunover the value for T_CTPTRADE_DATA.TRUNOVER
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public void setTrunover(String trunover) {
        this.trunover = trunover == null ? null : trunover.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CTPTRADE_DATA.CHARGE
     *
     * @return the value of T_CTPTRADE_DATA.CHARGE
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public String getCharge() {
        return charge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CTPTRADE_DATA.CHARGE
     *
     * @param charge the value for T_CTPTRADE_DATA.CHARGE
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public void setCharge(String charge) {
        this.charge = charge == null ? null : charge.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CTPTRADE_DATA.EXCHANGE_CHARGE
     *
     * @return the value of T_CTPTRADE_DATA.EXCHANGE_CHARGE
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public String getExchangeCharge() {
        return exchangeCharge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CTPTRADE_DATA.EXCHANGE_CHARGE
     *
     * @param exchangeCharge the value for T_CTPTRADE_DATA.EXCHANGE_CHARGE
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public void setExchangeCharge(String exchangeCharge) {
        this.exchangeCharge = exchangeCharge == null ? null : exchangeCharge.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CTPTRADE_DATA.CREATE_TIME
     *
     * @return the value of T_CTPTRADE_DATA.CREATE_TIME
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CTPTRADE_DATA.CREATE_TIME
     *
     * @param createTime the value for T_CTPTRADE_DATA.CREATE_TIME
     *
     * @mbggenerated Mon May 08 15:46:02 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}