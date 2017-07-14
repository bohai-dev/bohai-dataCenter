package com.bohai.dataCenter.persistence;

import java.util.List;

import com.bohai.dataCenter.entity.ReportDep;
import com.bohai.dataCenter.vo.QueryDepProfitPieChartParamVO;

public interface ReportDepMapper {
    
    List<ReportDep> selectByMonth(QueryDepProfitPieChartParamVO paramVO);
    
    //查询某营业部每个月净利润和毛利润
    
}