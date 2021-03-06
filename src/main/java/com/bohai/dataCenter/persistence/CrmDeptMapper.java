package com.bohai.dataCenter.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.bohai.dataCenter.entity.CrmDept;
import com.bohai.dataCenter.vo.QueryCrmDeptParamVO;

public interface CrmDeptMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_DEPT
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    int deleteByPrimaryKey(String deptCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_DEPT
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    int insert(CrmDept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_DEPT
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    int insertSelective(CrmDept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_DEPT
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    CrmDept selectByPrimaryKey(String deptCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_DEPT
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    int updateByPrimaryKeySelective(CrmDept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CRM_DEPT
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    int updateByPrimaryKey(CrmDept record);
    
    List<CrmDept> selectByCondition(QueryCrmDeptParamVO paramVO);
    
    //@Select(" SELECT SEQ_CRM_DEPNO.nextval from dual")
    @Select(" select max(DEPT_CODE)+1 from T_CRM_DEPT")
    String getDepNo();
}