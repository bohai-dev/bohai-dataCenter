package com.bohai.dataCenter.entity;

import java.math.BigDecimal;

public class CloseData {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CLOSE_DATA.DAY
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    private String day;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CLOSE_DATA.INVESTOR_NO
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    private String investorNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CLOSE_DATA.INSTRUMENT
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    private String instrument;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CLOSE_DATA.DEAL_SERIAL
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    private String dealSerial;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CLOSE_DATA.DIRECTION
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    private String direction;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CLOSE_DATA.DEAL_PRICE
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    private BigDecimal dealPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CLOSE_DATA.OPEN_PRICE
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    private BigDecimal openPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CLOSE_DATA.VOLUME
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    private Long volume;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CLOSE_DATA.YSTATEMENT
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    private BigDecimal ystatement;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CLOSE_DATA.TSTATEMENT
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    private BigDecimal tstatement;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CLOSE_DATA.DAILY_MARKET_WIN
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    private BigDecimal dailyMarketWin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CLOSE_DATA.HEDGE_WIN
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    private BigDecimal hedgeWin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CLOSE_DATA.ORGINIAL_SERIAL
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    private String orginialSerial;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CLOSE_DATA.TRADE_CODE
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    private String tradeCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CLOSE_DATA.CURRENCY
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    private String currency;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CLOSE_DATA.TRADE_DATE
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    private String tradeDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CLOSE_DATA.DAY
     *
     * @return the value of T_CLOSE_DATA.DAY
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public String getDay() {
        return day;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CLOSE_DATA.DAY
     *
     * @param day the value for T_CLOSE_DATA.DAY
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CLOSE_DATA.INVESTOR_NO
     *
     * @return the value of T_CLOSE_DATA.INVESTOR_NO
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public String getInvestorNo() {
        return investorNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CLOSE_DATA.INVESTOR_NO
     *
     * @param investorNo the value for T_CLOSE_DATA.INVESTOR_NO
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public void setInvestorNo(String investorNo) {
        this.investorNo = investorNo == null ? null : investorNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CLOSE_DATA.INSTRUMENT
     *
     * @return the value of T_CLOSE_DATA.INSTRUMENT
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public String getInstrument() {
        return instrument;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CLOSE_DATA.INSTRUMENT
     *
     * @param instrument the value for T_CLOSE_DATA.INSTRUMENT
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public void setInstrument(String instrument) {
        this.instrument = instrument == null ? null : instrument.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CLOSE_DATA.DEAL_SERIAL
     *
     * @return the value of T_CLOSE_DATA.DEAL_SERIAL
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public String getDealSerial() {
        return dealSerial;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CLOSE_DATA.DEAL_SERIAL
     *
     * @param dealSerial the value for T_CLOSE_DATA.DEAL_SERIAL
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public void setDealSerial(String dealSerial) {
        this.dealSerial = dealSerial == null ? null : dealSerial.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CLOSE_DATA.DIRECTION
     *
     * @return the value of T_CLOSE_DATA.DIRECTION
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public String getDirection() {
        return direction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CLOSE_DATA.DIRECTION
     *
     * @param direction the value for T_CLOSE_DATA.DIRECTION
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public void setDirection(String direction) {
        this.direction = direction == null ? null : direction.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CLOSE_DATA.DEAL_PRICE
     *
     * @return the value of T_CLOSE_DATA.DEAL_PRICE
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public BigDecimal getDealPrice() {
        return dealPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CLOSE_DATA.DEAL_PRICE
     *
     * @param dealPrice the value for T_CLOSE_DATA.DEAL_PRICE
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public void setDealPrice(BigDecimal dealPrice) {
        this.dealPrice = dealPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CLOSE_DATA.OPEN_PRICE
     *
     * @return the value of T_CLOSE_DATA.OPEN_PRICE
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CLOSE_DATA.OPEN_PRICE
     *
     * @param openPrice the value for T_CLOSE_DATA.OPEN_PRICE
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CLOSE_DATA.VOLUME
     *
     * @return the value of T_CLOSE_DATA.VOLUME
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public Long getVolume() {
        return volume;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CLOSE_DATA.VOLUME
     *
     * @param volume the value for T_CLOSE_DATA.VOLUME
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public void setVolume(Long volume) {
        this.volume = volume;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CLOSE_DATA.YSTATEMENT
     *
     * @return the value of T_CLOSE_DATA.YSTATEMENT
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public BigDecimal getYstatement() {
        return ystatement;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CLOSE_DATA.YSTATEMENT
     *
     * @param ystatement the value for T_CLOSE_DATA.YSTATEMENT
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public void setYstatement(BigDecimal ystatement) {
        this.ystatement = ystatement;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CLOSE_DATA.TSTATEMENT
     *
     * @return the value of T_CLOSE_DATA.TSTATEMENT
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public BigDecimal getTstatement() {
        return tstatement;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CLOSE_DATA.TSTATEMENT
     *
     * @param tstatement the value for T_CLOSE_DATA.TSTATEMENT
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public void setTstatement(BigDecimal tstatement) {
        this.tstatement = tstatement;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CLOSE_DATA.DAILY_MARKET_WIN
     *
     * @return the value of T_CLOSE_DATA.DAILY_MARKET_WIN
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public BigDecimal getDailyMarketWin() {
        return dailyMarketWin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CLOSE_DATA.DAILY_MARKET_WIN
     *
     * @param dailyMarketWin the value for T_CLOSE_DATA.DAILY_MARKET_WIN
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public void setDailyMarketWin(BigDecimal dailyMarketWin) {
        this.dailyMarketWin = dailyMarketWin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CLOSE_DATA.HEDGE_WIN
     *
     * @return the value of T_CLOSE_DATA.HEDGE_WIN
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public BigDecimal getHedgeWin() {
        return hedgeWin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CLOSE_DATA.HEDGE_WIN
     *
     * @param hedgeWin the value for T_CLOSE_DATA.HEDGE_WIN
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public void setHedgeWin(BigDecimal hedgeWin) {
        this.hedgeWin = hedgeWin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CLOSE_DATA.ORGINIAL_SERIAL
     *
     * @return the value of T_CLOSE_DATA.ORGINIAL_SERIAL
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public String getOrginialSerial() {
        return orginialSerial;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CLOSE_DATA.ORGINIAL_SERIAL
     *
     * @param orginialSerial the value for T_CLOSE_DATA.ORGINIAL_SERIAL
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public void setOrginialSerial(String orginialSerial) {
        this.orginialSerial = orginialSerial == null ? null : orginialSerial.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CLOSE_DATA.TRADE_CODE
     *
     * @return the value of T_CLOSE_DATA.TRADE_CODE
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public String getTradeCode() {
        return tradeCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CLOSE_DATA.TRADE_CODE
     *
     * @param tradeCode the value for T_CLOSE_DATA.TRADE_CODE
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode == null ? null : tradeCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CLOSE_DATA.CURRENCY
     *
     * @return the value of T_CLOSE_DATA.CURRENCY
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CLOSE_DATA.CURRENCY
     *
     * @param currency the value for T_CLOSE_DATA.CURRENCY
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CLOSE_DATA.TRADE_DATE
     *
     * @return the value of T_CLOSE_DATA.TRADE_DATE
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public String getTradeDate() {
        return tradeDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CLOSE_DATA.TRADE_DATE
     *
     * @param tradeDate the value for T_CLOSE_DATA.TRADE_DATE
     *
     * @mbggenerated Mon Apr 17 16:27:40 CST 2017
     */
    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate == null ? null : tradeDate.trim();
    }
}