package com.bohai.dataCenter.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.ReportExchangeRebate;
import com.bohai.dataCenter.persistence.ReportExchangeRebateMapper;
import com.bohai.dataCenter.service.ReportExchangeRebateService;
import com.bohai.dataCenter.vo.QueryExchangeRebateParamVO;

@Service("reportExchangeRebateService")
public class ReportExchangeRebateServiceImpl implements ReportExchangeRebateService {
	
	static Logger logger = Logger.getLogger(ReportExchangeRebateServiceImpl.class);
	
	@Autowired
	private ReportExchangeRebateMapper reportExchangeRebateMapper;

	@Override
	public void saveOrUpdate(ReportExchangeRebate exchangeRebate) throws BohaiException {
		
		//统计月份
		String month = exchangeRebate.getMonth();
		
		//机构名称
		String depName = exchangeRebate.getDepName();
		
		int count = this.reportExchangeRebateMapper.countByMonthAndDep(month, depName);
		
		if(count <1){
			try {
				exchangeRebate.setCreateTime(new Date());
				this.reportExchangeRebateMapper.insert(exchangeRebate);
			} catch (Exception e) {
				logger.error("保存交易所返还统计失败",e);
				throw new BohaiException("", "保存交易所返还统计失败");
			}
		}else {
			exchangeRebate.setUpdateTime(new Date());
			try {
				this.reportExchangeRebateMapper.updateSelective(exchangeRebate);
			} catch (Exception e) {
				logger.error("更新交易所返还统计失败",e);
				throw new BohaiException("", "更新交易所返还统计失败");
			}
		}

	}

	@Override
	public void updateRebate(ReportExchangeRebate exchangeRebate) throws BohaiException {

		//统计月份
		String month = exchangeRebate.getMonth();
		
		//机构名称
		String depName = exchangeRebate.getDepName();
		
		ReportExchangeRebate rebate = null;
		try {
			rebate = this.reportExchangeRebateMapper.selectByMonthAndDep(month, depName);
		} catch (Exception e) {
			logger.error("查询返佣失败："+JSON.toJSONString(exchangeRebate),e);
			throw new BohaiException("", "查询返佣失败");
		}
		//如果不为空则更新
		if(rebate != null){
			//大连返佣
			String originalRebate = rebate.getDrebate();
			BigDecimal orgRebate = new BigDecimal("0");
			if(!StringUtils.isEmpty(originalRebate)){
				orgRebate = new BigDecimal(originalRebate);
			}
			
			BigDecimal newRebate = orgRebate.add(new BigDecimal(exchangeRebate.getDrebate()));
			exchangeRebate.setDrebate(newRebate.toString());
			exchangeRebate.setUpdateTime(new Date());
			try {
				this.reportExchangeRebateMapper.updateSelective(exchangeRebate);
			} catch (Exception e) {
				logger.error("更新大连交易所返还统计失败",e);
				throw new BohaiException("", "更新大连交易所返还统计失败");
			}
		}else {
			try {
				exchangeRebate.setCreateTime(new Date());
				this.reportExchangeRebateMapper.insert(exchangeRebate);
			} catch (Exception e) {
				logger.error("保存交易所返还统计失败",e);
				throw new BohaiException("", "保存交易所返还统计失败");
			}
		}
	}

	@Override
	public List<ReportExchangeRebate> queryExchangeRebate(QueryExchangeRebateParamVO paramVO) throws BohaiException {
		return this.reportExchangeRebateMapper.selectByCondition(paramVO);
	}

}
