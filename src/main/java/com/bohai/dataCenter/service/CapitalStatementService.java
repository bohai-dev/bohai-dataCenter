package com.bohai.dataCenter.service;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CapitalStatement;

/**
 * 资金对账信息
 * @author caojia
 */
public interface CapitalStatementService {

	/**
	 * 保存或更新资金对账数据
	 * @param capitalStatement
	 * @return
	 * @throws BohaiException
	 */
	void saveOrUpdate(CapitalStatement capitalStatement) throws BohaiException;
}
