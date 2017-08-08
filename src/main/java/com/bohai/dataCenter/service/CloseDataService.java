package com.bohai.dataCenter.service;

import java.util.Date;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CloseData;

public interface CloseDataService {

	/**
	 * 保存或更新平仓明细
	 * @param closeData
	 * @throws BohaiException
	 */
	public void save(CloseData closeData) throws BohaiException;
	
	/**
	 * 根据时间删除平仓明细
	 * @param date
	 * @throws BohaiException
	 */
	public void removeByDate(Date date) throws BohaiException;
}
