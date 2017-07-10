package com.bohai.dataCenter.persistence;

import java.util.List;

import com.bohai.dataCenter.entity.ReportDep;
import com.bohai.dataCenter.vo.QueryDepProfitPieChartParamVO;

public interface ReportDepMapper {
    
    List<ReportDep> selectByMonth(QueryDepProfitPieChartParamVO paramVO);
}