package com.bohai.dataCenter.service;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.TradeData;

/**
 * 期货交易明细接口
 * @author caojia
 */
public interface TradeDataService {
	
	/**
	 * 保存或更新期货交易明细
	 * @param tradeData
	 * @throws BohaiException
	 */
	public void saveOrUpdate(TradeData tradeData) throws BohaiException;

}
