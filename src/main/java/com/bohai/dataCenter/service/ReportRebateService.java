package com.bohai.dataCenter.service;

import java.util.List;
import java.util.Map;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.ReportRebate;

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
	
	List<ReportRebate> queryRebateDetail();
	
	List<Map<String,Object>> queryRebateReport();
	
}
