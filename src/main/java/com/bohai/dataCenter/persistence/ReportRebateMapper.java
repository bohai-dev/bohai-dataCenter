package com.bohai.dataCenter.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.bohai.dataCenter.entity.ReportRebate;
import com.bohai.dataCenter.vo.QueryRebateDetailParamVO;
import com.bohai.dataCenter.vo.QueryRebateReportParamVO;

public interface ReportRebateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REPORT_REBATE
     *
     * @mbggenerated Tue Apr 11 15:16:02 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REPORT_REBATE
     *
     * @mbggenerated Tue Apr 11 15:16:02 CST 2017
     */
    int insert(ReportRebate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REPORT_REBATE
     *
     * @mbggenerated Tue Apr 11 15:16:02 CST 2017
     */
    int insertSelective(ReportRebate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REPORT_REBATE
     *
     * @mbggenerated Tue Apr 11 15:16:02 CST 2017
     */
    ReportRebate selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REPORT_REBATE
     *
     * @mbggenerated Tue Apr 11 15:16:02 CST 2017
     */
    int updateByPrimaryKeySelective(ReportRebate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REPORT_REBATE
     *
     * @mbggenerated Tue Apr 11 15:16:02 CST 2017
     */
    int updateByPrimaryKey(ReportRebate record);
    
    @Delete("delete from T_REPORT_REBATE where to_char(TRADE_DATE,'yyyymm') = #{0}")
    int deleteByMonth(String month);
    
    ReportRebate selectByTradeDateAndInvestorNo(String tradeDateStr, String investorNo);
    
    List<ReportRebate> selectAll();
    
    List<ReportRebate> selectByCondition(QueryRebateDetailParamVO paramVO);
    
    List<ReportRebate> selectDistinctInvestorByYearAndMonth(String year, String month);
    
    /*@Select("select t.INVESTOR_NO,t.INVESTOR_NAME,t.MEDIATOR_NAME,t.DEPT_NAME,sum(t.AVAILABLE_FUNDS) as AVAILABLE_FUNDS,to_char(sum(t.INTEREST_AMOUNT),'fm9999999999990.0099') as INTEREST_AMOUNT "
    		+ "from T_REPORT_REBATE t group by t.INVESTOR_NO,t.INVESTOR_NAME,t.MEDIATOR_NAME,t.DEPT_NAME")*/
    List<Map<String, Object>> selectReport(QueryRebateReportParamVO paramVO);
    
    
    List<Map<String, Object>> selectMarketReport(QueryRebateReportParamVO paramVO);
    
    List<Map<String, Object>> selectMarketerReport(QueryRebateReportParamVO paramVO);
}