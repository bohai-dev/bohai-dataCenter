package com.bohai.dataCenter.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

public interface ChartsMapper {
    
    @Select("select * from T_RIGHTS_RANKING order by RANKING")
    List<Map<String,Object>> selectOrderByRanking();
    
    @Select("select * from T_TRANSFER_MONEY where to_char(sysdate,'yyyy') = substr(month,0,4) order by month")
    List<Map<String, Object>> selectRights();
    
}