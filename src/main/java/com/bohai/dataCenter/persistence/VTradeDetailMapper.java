package com.bohai.dataCenter.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.bohai.dataCenter.entity.VTradeDetail;

public interface VTradeDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_TRADE_DETAIL
     *
     * @mbggenerated Wed Apr 19 10:36:43 CST 2017
     */
    //int insert(VTradeDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_TRADE_DETAIL
     *
     * @mbggenerated Wed Apr 19 10:36:43 CST 2017
     */
    //int insertSelective(VTradeDetail record);
    
    /**
     * 查询以营业部，合约品种，投保标志为维度的大连交易所交易统计信息
     * @param vTradeDetail
     * @return
     */
	List<Map<String,Object>> selectTradeInfo(VTradeDetail vTradeDetail);
	
	List<Map<String,Object>> selectInvestorTradeInfo(String tradeMonth);
	
	/**
	 * 查询郑商所和上期所手续费(以营业部为维度)
	 * @param month
	 * @param exchangeId
	 * @return
	 */
	//@Select("select GETDEPNAME(INVESTOR_NO) as DEP_NAME ,sum(CHARGE) as CHARGE from V_TRADE_DETAIL where substr(TRADE_DATE,0,7) = #{0} and EXCHANGE_ID = #{1} group by GETDEPNAME(INVESTOR_NO)")
	@Select("select GETDEPNAME(t.INVESTOR_NO) as DEP_NAME,sum(t.EXCHANGE_CHARGE) as CHARGE "
			+ "from T_CTPTRADE_DATA t where t.EXCHANGE_NAME = #{1}"
			+ "and substr(TRADE_DATE,0,6) = #{0}"
			+ " group by GETDEPNAME(t.INVESTOR_NO)")
	List<Map<String,Object>> selectSumCharge(String month, String exchangeId);
	
	/**
	 * 查询交易所返还手续费(以客户为维度)
	 * @param month
	 * @param exchangeId
	 * @return
	 */
	@Select("select t.INVESTOR_NO,t.INVESTOR_NAME,sum(t.EXCHANGE_CHARGE) as CHARGE from T_CTPTRADE_DATA t "
	        + "where t.EXCHANGE_NAME = #{1} "
	        + "and substr(TRADE_DATE,0,6) = #{0} "
	        + "group by t.INVESTOR_NO,t.INVESTOR_NAME")
	List<Map<String,Object>> selectInvestorCharge(String month, String exchangeId);
}