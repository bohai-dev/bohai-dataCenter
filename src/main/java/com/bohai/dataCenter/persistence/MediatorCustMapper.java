package com.bohai.dataCenter.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;

import com.bohai.dataCenter.entity.MediatorCust;

public interface MediatorCustMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_MEDIATOR_CUST
     *
     * @mbggenerated Fri Mar 31 14:23:30 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_MEDIATOR_CUST
     *
     * @mbggenerated Fri Mar 31 14:23:30 CST 2017
     */
    int insert(MediatorCust record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_MEDIATOR_CUST
     *
     * @mbggenerated Fri Mar 31 14:23:30 CST 2017
     */
    int insertSelective(MediatorCust record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_MEDIATOR_CUST
     *
     * @mbggenerated Fri Mar 31 14:23:30 CST 2017
     */
    MediatorCust selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_MEDIATOR_CUST
     *
     * @mbggenerated Fri Mar 31 14:23:30 CST 2017
     */
    int updateByPrimaryKeySelective(MediatorCust record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_MEDIATOR_CUST
     *
     * @mbggenerated Fri Mar 31 14:23:30 CST 2017
     */
    int updateByPrimaryKey(MediatorCust record);
    
    int batchInsert(List<MediatorCust> records);
    
    @Delete("delete from T_MEDIATOR_CUST")
    int deleteAll();
}