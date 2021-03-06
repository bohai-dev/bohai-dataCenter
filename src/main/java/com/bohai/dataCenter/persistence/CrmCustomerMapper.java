package com.bohai.dataCenter.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bohai.dataCenter.entity.CrmCustomer;
import com.bohai.dataCenter.entity.CrmMarketer;
import com.bohai.dataCenter.entity.CrmMediator;
import com.bohai.dataCenter.vo.QueryCrmCustomerParamVO;
import com.bohai.dataCenter.vo.QueryInvestorProfitParamVO;

public interface CrmCustomerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_CUSTOMER
     *
     * @mbggenerated Wed May 10 17:08:23 CST 2017
     */
    int deleteByPrimaryKey(String investorNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_CUSTOMER
     *
     * @mbggenerated Wed May 10 17:08:23 CST 2017
     */
    int insert(CrmCustomer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_CUSTOMER
     *
     * @mbggenerated Wed May 10 17:08:23 CST 2017
     */
    int insertSelective(CrmCustomer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_CUSTOMER
     *
     * @mbggenerated Wed May 10 17:08:23 CST 2017
     */
    CrmCustomer selectByPrimaryKey(String investorNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_CUSTOMER
     *
     * @mbggenerated Wed May 10 17:08:23 CST 2017
     */
    int updateByPrimaryKeySelective(CrmCustomer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_CUSTOMER
     *
     * @mbggenerated Wed May 10 17:08:23 CST 2017
     */
    int updateByPrimaryKey(CrmCustomer record);
    
    List<CrmCustomer> selectByCondition(QueryCrmCustomerParamVO paramVO);
    
    @Update("update T_CRM_CUSTOMER "
            + "set DEPT_CODE = #{depCode}"
            + "where BELONG_TYPE = '1'"
            + "and BELONG_TO = #{marketerNo}")
    int updateDepByMarketer(CrmMarketer marketer);
    
    @Update("update T_CRM_CUSTOMER "
            + "set DEPT_CODE = #{depCode} "
            + "where BELONG_TYPE = '2' "
            + "and BELONG_TO = #{mediatorNo}")
    int updateDepByMediator(CrmMediator mediator);
    
    @Update("update T_CRM_CUSTOMER "
            + "set BELONG_TYPE = #{belongType}, "
            + "BELONG_TO = #{belongTo} "
            + "where BELONG_TYPE = '2' "
            + "and BELONG_TO = #{mediatorNo}")
    int updateBelongByMediator(CrmMediator mediator);
    
    @Update("update T_CRM_CUSTOMER "
            + "set BELONG_TYPE = '0', "
            + "BELONG_TO = #{depCode} "
            + "where BELONG_TYPE = '1' "
            + "and BELONG_TO = #{marketerNo}")
    int updateBelongByMarketer(CrmMarketer marketer);
    
    /**
     * 备份数据到历史表
     * @param mediator
     * @return
     */
    int copyCustomerByMediator(CrmMediator mediator);
    
    int copyCustomerByMarketer(CrmMarketer marketer);
    
    /**
     * 查询投资者产生的利润
     * @param paramVO
     * @return
     */
    List<Map<String, Object>> queryInvestorProfit(QueryInvestorProfitParamVO paramVO);
    
    /**
     * 查询居间人客户数量
     * @param mediator
     * @return
     */
    @Select("select count(1) from T_CRM_CUSTOMER where belong_type = '2' and belong_to = #{0}")
    Long countByMediatorNo(String mediatorNo);
    
    @Select("select count(1) from T_CRM_CUSTOMER where belong_type = '1' and belong_to = #{0}")
    Long countByMarketerNo(String marketerNo);
    
    @Select("select PROVINCE AS name,COUNT(*) AS value FROM(select T_CRM_CUSTOMER.*,T_CRM_DEPT.* FROM T_CRM_CUSTOMER,  T_CRM_DEPT WHERE T_CRM_CUSTOMER.DEPT_CODE=T_CRM_DEPT.DEPT_CODE) GROUP BY PROVINCE")
    List<Map<String, Object>> querySumByProvince();
}