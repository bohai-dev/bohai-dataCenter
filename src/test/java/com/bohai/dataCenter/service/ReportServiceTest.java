package com.bohai.dataCenter.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.vo.CountExchangeRebateParamVO;
import com.bohai.dataCenter.vo.CountRebatReportParamVO;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ReportServiceTest extends AbstractJUnit4SpringContextTests{

	
	@Autowired
	private ReportService reportService;
	
	@Test
	public void countExchangeRebate() throws BohaiException {


		CountExchangeRebateParamVO paramVO = new CountExchangeRebateParamVO();
		paramVO.setMonth("2017-03");
		
		this.reportService.countExchangeRebate(paramVO);
	}
	
	@Test
	public void countInvestorExchangeRebate() throws BohaiException{
	    
	    CountExchangeRebateParamVO paramVO = new CountExchangeRebateParamVO();
        paramVO.setMonth("2017-03");
	    
        this.reportService.countInvestorExchangeRebate(paramVO);
	}
	
	@Test
	public void reportSpecialReturn() throws BohaiException{
	    
	    CountExchangeRebateParamVO paramVO = new CountExchangeRebateParamVO();
        paramVO.setMonth("2017-03");
	    this.reportService.reportSpecialReturn(paramVO);
	    
	}
	
	
	@Test
	public void reportInvestorInterest() throws BohaiException{
	    CountRebatReportParamVO paramVO = new CountRebatReportParamVO();
	    paramVO.setYear("2017");
	    paramVO.setMonth("04");
	    //this.reportService.countRebatReport(paramVO);
	    this.reportService.reportInvestorInterest(paramVO);
	}

}
