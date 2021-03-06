package com.bohai.dataCenter.persistence;

import java.util.List;

import com.bohai.dataCenter.entity.CrmCustomerHistory;
import com.bohai.dataCenter.vo.QueryCrmCustomerParamVO;

public interface CrmCustomerHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_CUSTOMER_HISTORY
     *
     * @mbggenerated Mon Jul 10 16:18:59 CST 2017
     */
    int insert(CrmCustomerHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_CUSTOMER_HISTORY
     *
     * @mbggenerated Mon Jul 10 16:18:59 CST 2017
     */
    int insertSelective(CrmCustomerHistory record);
    
    List<CrmCustomerHistory> selectByCondition(QueryCrmCustomerParamVO paramVO);
}