package com.bohai.dataCenter.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.TradeData;
import com.bohai.dataCenter.persistence.TradeDataMapper;
import com.bohai.dataCenter.service.TradeDataService;

@Service("tradeDataService")
public class TradeDataServiceImpl implements TradeDataService {
	
	static Logger logger = Logger.getLogger(TradeDataServiceImpl.class);
	
	@Autowired
	private TradeDataMapper tradeDataMapper;

	@Override
	public void saveOrUpdate(TradeData tradeData) throws BohaiException {

		/*String investor = tradeData.getInvestorNo();
		
		String serial = tradeData.getSerialNo();
		
		String tradeDate = tradeData.getTradeDate();
		
		String instrument = tradeData.getInstrument();
		*/
		
		//自成交查询     交易流水号相同   投资者编号相同  
		//int count = this.tradeDataMapper.countByInvestorNoAndSerial(investor, serial, tradeDate, instrument);
		
		/*if(count == 0){
			
		}*/
		
		try {
			this.tradeDataMapper.insert(tradeData);
		} catch (Exception e) {
			logger.error("保存期货交易明细失败:"+JSON.toJSONString(tradeData),e);
			throw new BohaiException("", "保存期货交易明细失败");
		}
	}

	@Override
	public void removeByDate(String dateStr) throws BohaiException {

		int count = this.tradeDataMapper.deleteByDate(dateStr);
		logger.debug("共删除"+count+"条成交明细");
	}

}
