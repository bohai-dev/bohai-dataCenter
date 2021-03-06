package com.bohai.dataCenter.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;

import com.bohai.dataCenter.entity.RebateList;

public interface RebateListMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REBATE_LIST
     *
     * @mbggenerated Fri Apr 07 09:45:35 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REBATE_LIST
     *
     * @mbggenerated Fri Apr 07 09:45:35 CST 2017
     */
    int insert(RebateList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REBATE_LIST
     *
     * @mbggenerated Fri Apr 07 09:45:35 CST 2017
     */
    int insertSelective(RebateList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REBATE_LIST
     *
     * @mbggenerated Fri Apr 07 09:45:35 CST 2017
     */
    RebateList selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REBATE_LIST
     *
     * @mbggenerated Fri Apr 07 09:45:35 CST 2017
     */
    int updateByPrimaryKeySelective(RebateList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REBATE_LIST
     *
     * @mbggenerated Fri Apr 07 09:45:35 CST 2017
     */
    int updateByPrimaryKey(RebateList record);
    
    @Delete("delete from T_REBATE_LIST")
    int deleteAll();
    
    
    /**
     * 查询所有数据
     * @return
     */
    List<RebateList> selectAll();
    
    RebateList selectOneByMediatorNo(String mediatorNo);
}