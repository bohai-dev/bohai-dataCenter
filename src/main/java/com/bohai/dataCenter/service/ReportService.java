package com.bohai.dataCenter.service;

import java.util.List;
import java.util.Map;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.vo.CountExchangeRebateParamVO;
import com.bohai.dataCenter.vo.CountRebatReportParamVO;

/**
 * 统计接口
 * @author caojia
 */
public interface ReportService {

	/**
	 * TB软件服务费
	 * @return
	 */
	List<Map<String, Object>> queryTBSoftReport();
	
	/**
	 * 统计返利息报表
	 * @throws BohaiException 
	 */
	void countRebatReport(CountRebatReportParamVO paramVO) throws BohaiException;
	
	/**
	 * 统计交易所返还
	 * @param paramVO
	 * @throws BohaiException
	 */
	void countExchangeRebate(CountExchangeRebateParamVO paramVO) throws BohaiException;
}
