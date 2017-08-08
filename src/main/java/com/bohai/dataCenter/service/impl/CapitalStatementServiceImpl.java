package com.bohai.dataCenter.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CapitalStatement;
import com.bohai.dataCenter.persistence.CapitalStatementMapper;
import com.bohai.dataCenter.service.CapitalStatementService;

@Service("capitalStatementService")
public class CapitalStatementServiceImpl implements CapitalStatementService {
	
	static Logger logger = Logger.getLogger(CapitalStatementServiceImpl.class);
	
	@Autowired
	private CapitalStatementMapper capitalStatementMapper;

	@Override
	public void saveOrUpdate(CapitalStatement capitalStatement) throws BohaiException {
		
		//交易日
		String tradeDate = capitalStatement.getTradeDateStr();
		//投资者代码
		String investorNo = capitalStatement.getInvestorNo();
		
		CapitalStatement statement = null;
		try {
			statement = this.capitalStatementMapper.selectByTradeDateAndInvestorNo(tradeDate, investorNo);
		} catch (Exception e) {
			logger.error("查询资金对账信息失败："+JSON.toJSONString(capitalStatement),e);
			throw new BohaiException("", "查询资金对账信息失败："+JSON.toJSONString(capitalStatement));
		}
		
		//不存在即插入
		if(statement == null || StringUtils.isEmpty(statement.getId())){
			capitalStatement.setCreateTime(new Date());
			try {
				this.capitalStatementMapper.insertSelective(capitalStatement);
			} catch (Exception e) {
				logger.error("保存资金对账信息失败："+JSON.toJSONString(capitalStatement),e);
				throw new BohaiException("", "保存资金对账信息失败："+JSON.toJSONString(capitalStatement));
			}
		}else {
			capitalStatement.setId(statement.getId());
			capitalStatement.setUpdateTime(new Date());
			try {
				this.capitalStatementMapper.updateByPrimaryKey(capitalStatement);
			} catch (Exception e) {
				logger.error("更新资金对账信息失败："+JSON.toJSONString(capitalStatement),e);
				throw new BohaiException("", "更新资金对账信息失败："+JSON.toJSONString(capitalStatement));
			}
		}
		
	}

}
