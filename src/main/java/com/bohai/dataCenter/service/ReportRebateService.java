package com.bohai.dataCenter.service;

import java.util.List;
import java.util.Map;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.ReportRebate;
import com.bohai.dataCenter.vo.QueryRebateDetailParamVO;
import com.bohai.dataCenter.vo.QueryRebateReportParamVO;

/**
 * 返利息报表
 * @author caojia
 */
public interface ReportRebateService {

	/**
	 * 保存或更新返利息报表信息
	 * @param reportRebate
	 * @throws BohaiException
	 */
	void saveOrUpdate(ReportRebate reportRebate) throws BohaiException;
	
	/**
	 * 根据月份删除返利息特例报表
	 * @param month 月份yyyyMM
	 * @throws BohaiException
	 */
	void removeByMonth(String month) throws BohaiException;
	
	List<ReportRebate> queryRebateDetail(QueryRebateDetailParamVO paramVO);
	
	/**
	 * 查询返利息报表
	 * @param paramVO
	 * @return
	 */
	List<Map<String,Object>> queryRebateReport(QueryRebateReportParamVO paramVO);
	
	
	/**
	 * 查询返利息统计表(以营业部为维度)
	 * @param paramVO
	 * @return
	 */
	List<Map<String,Object>> queryMarketRebateReport(QueryRebateReportParamVO paramVO);
	
	/**
	 * 查询返利息统计表(以营销人员为维度)
	 * @param paramVO
	 * @return
	 */
	List<Map<String,Object>> queryMarketerRebateReport(QueryRebateReportParamVO paramVO);
	
	
	
}
