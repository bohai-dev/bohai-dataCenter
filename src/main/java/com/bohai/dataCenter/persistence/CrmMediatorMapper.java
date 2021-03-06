package com.bohai.dataCenter.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bohai.dataCenter.entity.CrmMarketer;
import com.bohai.dataCenter.entity.CrmMediator;
import com.bohai.dataCenter.vo.CrmMediatorAndCustomer;
import com.bohai.dataCenter.vo.QueryCrmMediatorParamVO;
import com.bohai.dataCenter.vo.QueryMediatorProfitParamVO;

public interface CrmMediatorMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_MEDIATOR
     *
     * @mbggenerated Fri May 12 09:06:10 CST 2017
     */
    int deleteByPrimaryKey(String mediatorNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_MEDIATOR
     *
     * @mbggenerated Fri May 12 09:06:10 CST 2017
     */
    int insert(CrmMediator record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_MEDIATOR
     *
     * @mbggenerated Fri May 12 09:06:10 CST 2017
     */
    int insertSelective(CrmMediator record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_MEDIATOR
     *
     * @mbggenerated Fri May 12 09:06:10 CST 2017
     */
    CrmMediator selectByPrimaryKey(String mediatorNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_MEDIATOR
     *
     * @mbggenerated Fri May 12 09:06:10 CST 2017
     */
    int updateByPrimaryKeySelective(CrmMediator record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_MEDIATOR
     *
     * @mbggenerated Fri May 12 09:06:10 CST 2017
     */
    int updateByPrimaryKey(CrmMediator record);
    
    List<CrmMediator> selectByCondition(QueryCrmMediatorParamVO paramVO);
    
    @Update("update T_CRM_MEDIATOR "
            + "set DEP_CODE = #{depCode},"
            + "DEP_NAME = #{depName} "
            + "where BELONG_TYPE = '1' "
            + "and BELONG_TO = #{marketerNo}")
    int updateDepByMarketerNo(CrmMarketer marketer);
    
    @Update("update T_CRM_MEDIATOR "
            + "set BELONG_TYPE = '0', "
            + "BELONG_TO = #{depCode} "
            + "where BELONG_TYPE = '1' "
            + "and BELONG_TO = #{marketerNo}")
    int updateBelongByMarketer(CrmMarketer marketer);
    
    /**
     * 备份居间人信息到历史表
     * @param mediatorNo
     * @return
     */
    int copyMediatorByMediatorNo(String mediatorNo);
    
    int copyMediatorByMarketer(CrmMarketer marketer);
    
    List<CrmMediator> selectByMarketer(String marketerNo);
    
    //XUSHEN ADD START
    List<CrmMediatorAndCustomer> selectMediatorCustomerRelation(QueryCrmMediatorParamVO paramVO);
    //XUSHEN ADD END
    
    @Select(" SELECT to_char(sysdate,'yymm')||'2'||lpad(SEQ_CRM_MEDIATORNO.nextval,5,'0') from dual")
    String getMediatorNo();
    
    List<Map<String, Object>> queryMediatorProfit(QueryMediatorProfitParamVO paramVO);
    
    @Select("select count(1) from T_CRM_MEDIATOR where belong_type = '1' and belong_to = #{0}")
    Long countByMarketerNo(String marketerNo);
}