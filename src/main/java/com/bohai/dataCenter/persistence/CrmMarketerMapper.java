package com.bohai.dataCenter.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.bohai.dataCenter.entity.CrmMarketer;
import com.bohai.dataCenter.vo.CrmMarketerAndCustomer;
import com.bohai.dataCenter.vo.CrmMarketerAndMediator;
import com.bohai.dataCenter.vo.QueryCrmMarketerParamVO;
import com.bohai.dataCenter.vo.QueryMarketerProfitParamVO;

public interface CrmMarketerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_MARKETER
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    int deleteByPrimaryKey(String marketerNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_MARKETER
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    int insert(CrmMarketer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_MARKETER
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    int insertSelective(CrmMarketer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_MARKETER
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    CrmMarketer selectByPrimaryKey(String marketerNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_MARKETER
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    int updateByPrimaryKeySelective(CrmMarketer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_MARKETER
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    int updateByPrimaryKey(CrmMarketer record);
    
    List<CrmMarketer> selectByCondition(QueryCrmMarketerParamVO paramVO);
    
    //XUSHEN ADD START
    List<CrmMarketerAndMediator> selectMaketerRelation(QueryCrmMarketerParamVO paramVO);
    
    List<CrmMarketerAndCustomer> selectMaketerCustomerRelation(QueryCrmMarketerParamVO paramVO);
    //XUSHEN ADD END
    
    @Select("SELECT to_char(sysdate,'yymm')||'1'||lpad(SEQ_CRM_MARKETERNO.nextval,5,'0') from dual")
    String getMarketerNo();
    
    List<Map<String,Object>> queryMarketerProfit(QueryMarketerProfitParamVO paramVO);
    
    String getMarketerRate(String marketerNo);
}