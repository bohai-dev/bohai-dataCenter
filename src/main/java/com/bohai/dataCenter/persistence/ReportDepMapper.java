package com.bohai.dataCenter.persistence;

import java.util.List;
import com.bohai.dataCenter.entity.ReportDep;
import com.bohai.dataCenter.vo.QueryDepProfitBarChartParamVO;
import com.bohai.dataCenter.vo.QueryDepProfitParamVO;
import com.bohai.dataCenter.vo.QueryDepProfitPieChartParamVO;

public interface ReportDepMapper {
    
    List<ReportDep> selectByMonth(QueryDepProfitPieChartParamVO paramVO);
    
    //查询某营业部每个月净利润和毛利润
    List<ReportDep> selectByDep(QueryDepProfitBarChartParamVO paramVO);
    
    /**
     * 根据条件查询营业部利润
     * @param paramVO
     * @return
     */
    List<ReportDep> selectByCondition(QueryDepProfitParamVO paramVO);
}