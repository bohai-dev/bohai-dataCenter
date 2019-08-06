package com.bohai.dataCenter.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 交易所返佣特例名单
 * @author caojia
 */
public interface SpecialListMapper {
    
    @Insert("insert into T_SPECIAL_LIST(INVESTOR_NO,MEDIATOR_NO,RETURN_TYPE,IS_ALL,SHFE,DCE,CZCE,INE,CFFEX,RETURN_WAY,EFFECT_DATE,EXPIRE_DATE,REMARK) "
            + "values(#{INVESTOR_NO,jdbcType=VARCHAR},#{MEDIATOR_NO,jdbcType=VARCHAR},#{RETURN_TYPE,jdbcType=VARCHAR},#{IS_ALL,jdbcType=VARCHAR},"
            + "#{SHFE,jdbcType=VARCHAR},#{DCE,jdbcType=VARCHAR},#{CZCE,jdbcType=VARCHAR},#{INE,jdbcType=VARCHAR},#{CFFEX,jdbcType=VARCHAR},"
            + "#{RETURN_WAY,jdbcType=VARCHAR},#{EFFECT_DATE,jdbcType=VARCHAR},#{EXPIRE_DATE,jdbcType=VARCHAR},#{REMARK,jdbcType=VARCHAR})")
    int insert(Map<String, String> map);
    
    int updateById(Map<String, String> map);
    
    int updateByMediator(Map<String, String> map);
    
    List<Map<String, String>> selectByCondition(Map<String, String> map);
    
    @Update("update T_SPECIAL_LIST set STATUS = '0' where MEDIATOR_NO = #{mediatorNo,jdbcType=VARCHAR}")
    int removeByMediator(String mediatorNo);
    
    @Update("update T_SPECIAL_LIST set STATUS = '0' where ID = #{id,jdbcType=VARCHAR}")
    int removeById(String id);
    
    @Select("select DISTINCT MEDIATOR_NO from T_SPECIAL_LIST where RETURN_TYPE = '2' and IS_ALL = '1' and STATUS = '1'")
    List<String> distinctIsAll();
    
    @Select("select * from T_SPECIAL_LIST where STATUS = '1' and MEDIATOR_NO = #{mediatorNo,jdbcType=VARCHAR} and rownum = 1")
    Map<String, String> selectOneByMediatorNo(String mediatorNo);
}