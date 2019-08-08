package com.bohai.dataCenter.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
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
    
    @Select("select t.INVESTOR_NO,nvl(t.SHFE,'0') as SHFE,nvl(t.DCE,'0') as DCE,nvl(t.CZCE,'0') as CZCE,nvl(t.INE,'0') as INE,"
            + "nvl(t1.SCOMMISSION,'0') as SCOMMISSION, nvl(t1.ZCOMMISSION,'0') as ZCOMMISSION, nvl(t1.DCOMMISSION,'0') as DCOMMISSION, nvl(t1.ICOMMISSION,'0') as ICOMMISSION,"
            + "t1.INVESTOR_NAME,t1.SREBATE,t1.ZREBATE,t1.DREBATE,t1.IREBATE "
            + "from T_SPECIAL_LIST t "
            + "left join T_REPORT_SPECIAL_RETURN t1 "
            + "on t.INVESTOR_NO = t1.INVESTOR_NO "
            + "where t.status = '1' "
            + "and t.RETURN_TYPE = '1' "
            + "and t.RETURN_WAY = '1' "
            + "and F_GETDEPNAME(t.INVESTOR_NO) = #{depName,jdbcType=VARCHAR} "
            + "and t1.MONTH = #{month,jdbcType=VARCHAR}")
    List<Map<String,String>> selectAccountCustomer(@Param("month")String month, @Param("depName")String depName);
    
    @Select("select t.INVESTOR_NO,nvl(t.SHFE,'0') as SHFE,nvl(t.DCE,'0') as DCE,nvl(t.CZCE,'0') as CZCE,nvl(t.INE,'0') as INE,"
            + "nvl(t1.SCOMMISSION,'0') as SCOMMISSION, nvl(t1.ZCOMMISSION,'0') as ZCOMMISSION, nvl(t1.DCOMMISSION,'0') as DCOMMISSION, nvl(t1.ICOMMISSION,'0') as ICOMMISSION,"
            + "t1.INVESTOR_NAME,t1.SREBATE,t1.ZREBATE,t1.DREBATE,t1.IREBATE "
            + "from T_SPECIAL_LIST t "
            + "left join T_REPORT_SPECIAL_RETURN t1 "
            + "on t.INVESTOR_NO = t1.INVESTOR_NO "
            + "where t.status = '1' "
            + "and t.RETURN_TYPE = '1' "
            + "and t.RETURN_WAY = '2' "
            + "and F_GETDEPNAME(t.INVESTOR_NO) = #{depName,jdbcType=VARCHAR} "
            + "and t1.MONTH = #{month,jdbcType=VARCHAR}")
    List<Map<String,String>> selectBankCustomer(@Param("month")String month, @Param("depName")String depName);
    
    @Select("select t.INVESTOR_NO,nvl(t.SHFE,'0') as SHFE,nvl(t.DCE,'0') as DCE,nvl(t.CZCE,'0') as CZCE,nvl(t.INE,'0') as INE,"
            + "nvl(t1.SCOMMISSION,'0') as SCOMMISSION, nvl(t1.ZCOMMISSION,'0') as ZCOMMISSION, nvl(t1.DCOMMISSION,'0') as DCOMMISSION, nvl(t1.ICOMMISSION,'0') as ICOMMISSION,"
            + "t1.INVESTOR_NAME,t1.SREBATE,t1.ZREBATE,t1.DREBATE,t1.IREBATE "
            + "from T_SPECIAL_LIST t "
            + "left join T_REPORT_SPECIAL_RETURN t1 "
            + "on t.INVESTOR_NO = t1.INVESTOR_NO "
            + "where t.status = '1' "
            + "and t.RETURN_TYPE = '2' "
            + "and t.MEDIATOR_NO = #{mediatorNo,jdbcType=VARCHAR} "
            + "and t1.MONTH = #{month,jdbcType=VARCHAR}")
    List<Map<String, String>> selectMediatorCustomer(@Param("month")String month, @Param("mediatorNo")String mediatorNo);
    
    @Select("select DISTINCT t.MEDIATOR_NO from T_SPECIAL_LIST t "
            + "where F_GETDEPNAME(t.INVESTOR_NO) = #{depName,jdbcType=VARCHAR} "
            + "and t.status = '1' "
            + "and t.RETURN_TYPE = '2'")
    List<String> selectMediatorByDepName(String depName);
    
    @Select("select DISTINCT F_GETDEPNAME(INVESTOR_NO) from T_SPECIAL_LIST where status = '1'")
    List<String> selectDepName();
}