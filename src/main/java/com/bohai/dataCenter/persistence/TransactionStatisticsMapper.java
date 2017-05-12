package com.bohai.dataCenter.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.bohai.dataCenter.entity.TransactionStatistics;

public interface TransactionStatisticsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_TRANSACTION_STATISTICS
     *
     * @mbggenerated Wed Apr 05 13:50:46 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_TRANSACTION_STATISTICS
     *
     * @mbggenerated Wed Apr 05 13:50:46 CST 2017
     */
    int insert(TransactionStatistics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_TRANSACTION_STATISTICS
     *
     * @mbggenerated Wed Apr 05 13:50:46 CST 2017
     */
    int insertSelective(TransactionStatistics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_TRANSACTION_STATISTICS
     *
     * @mbggenerated Wed Apr 05 13:50:46 CST 2017
     */
    TransactionStatistics selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_TRANSACTION_STATISTICS
     *
     * @mbggenerated Wed Apr 05 13:50:46 CST 2017
     */
    int updateByPrimaryKeySelective(TransactionStatistics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_TRANSACTION_STATISTICS
     *
     * @mbggenerated Wed Apr 05 13:50:46 CST 2017
     */
    int updateByPrimaryKey(TransactionStatistics record);
    
    @Delete("delete from T_TRANSACTION_STATISTICS")
    int deleteAll();
    
    @Select("select t.INVESTOR_NO,t.INVESTOR_NAME,t1.DEPT,t.CHARGE,t.ONHAND_CHARGE,"
    		+ "ROUND(case when t.ONHAND_CHARGE = 0 then 0 else t.ONHAND_CHARGE * t1.EXPENSE_STANDARD /100 *(1-t.TURNOVER*7/10000000/t.RETENTION_CHARGE) end,2) as SOFT_CHARGE,t.BATCH_NO "
    		+ "from T_TRANSACTION_STATISTICS t,T_OPEN_RECORD t1 where t.INVESTOR_NO = t1.CAPITAL_ACCOUNT")
    List<Map<String,Object>> selectSoftCommission();
}