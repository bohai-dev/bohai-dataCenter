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
	
	/**
	 * 统计交易所返还（以营业部为维度）
	 * @throws BohaiException
	 */
	@Test
	public void countExchangeRebate() throws BohaiException {


		CountExchangeRebateParamVO paramVO = new CountExchangeRebateParamVO();
		paramVO.setMonth("2017-03");
		
		this.reportService.countExchangeRebate(paramVO);
	}
	
	/**-----------------------------------以下统计交返---------------------------------------**/
	
	/**
	 * 统计交易所返还（以投资者为维度）
	 * @throws BohaiException
	 */
	@Test
	public void countInvestorExchangeRebate() throws BohaiException{
	    
	    CountExchangeRebateParamVO paramVO = new CountExchangeRebateParamVO();
        paramVO.setMonth("2017-05");
	    
        this.reportService.countInvestorExchangeRebate(paramVO);
	}
	
	/**
	 * 统计交易所返还特例
	 * @throws BohaiException
	 */
	@Test
	public void reportSpecialReturn() throws BohaiException{
	    
	    CountExchangeRebateParamVO paramVO = new CountExchangeRebateParamVO();
        paramVO.setMonth("2017-05");
	    this.reportService.reportSpecialReturn(paramVO);
	    
	}
	
	/**-----------------------------------统计交返结束-------------------------------------**/
	
	
	
	
	
	
	/**-----------------------------------以下统计返利息------------------------------------**/
	/**
	 * 统计返利息特例
	 * @throws BohaiException 
	 */
	@Test
	public void countRebatReport() throws BohaiException{
	    CountRebatReportParamVO paramVO = new CountRebatReportParamVO();
	    paramVO.setYear("2017");
	    paramVO.setMonth("05");
	    this.reportService.countRebatReport(paramVO);
	}
	
	/**
	 * 统计营销人员返利息提成（除特例）
	 * @throws BohaiException
	 */
	@Test
	public void reportInvestorInterest() throws BohaiException{
	    CountRebatReportParamVO paramVO = new CountRebatReportParamVO();
	    paramVO.setYear("2017");
	    paramVO.setMonth("05");
	    //this.reportService.countRebatReport(paramVO);
	    this.reportService.reportInvestorInterest(paramVO);
	}
	
	/**-----------------------------------统计返利息结束----------------------------------**/

}
