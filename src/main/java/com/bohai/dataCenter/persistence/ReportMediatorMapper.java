package com.bohai.dataCenter.persistence;

import java.util.List;

import com.bohai.dataCenter.entity.ReportMediator;
import com.bohai.dataCenter.vo.QueryMediatorReportParamVO;

public interface ReportMediatorMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REPORT_MEDIATOR
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    int insert(ReportMediator record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REPORT_MEDIATOR
     *
     * @mbggenerated Tue Aug 01 14:00:39 CST 2017
     */
    int insertSelective(ReportMediator record);
    
    List<ReportMediator> queryByCondition(QueryMediatorReportParamVO paramVO);
}