package com.bohai.dataCenter.service;

import java.util.List;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.ReportExchangeRebate;
import com.bohai.dataCenter.vo.QueryExchangeRebateParamVO;

/**
 * 交易所返还统计表
 * @author caojia
 */
public interface ReportExchangeRebateService {

	/**
	 * 保存或更新
	 * @param exchangeRebate
	 * @throws BohaiException
	 */
	public void saveOrUpdate(ReportExchangeRebate exchangeRebate) throws BohaiException;
	
	/**
	 * 更新交易所返佣金额
	 * @param exchangeRebate
	 * @throws BohaiException
	 */
	public void updateRebate(ReportExchangeRebate exchangeRebate) throws BohaiException;
	
	/**
	 * 查询交易所返佣统计表
	 * @return
	 * @throws BohaiException
	 */
	public List<ReportExchangeRebate> queryExchangeRebate(QueryExchangeRebateParamVO paramVO) throws BohaiException;
}
