package com.bohai.dataCenter.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.ReportExchangeRebate;
import com.bohai.dataCenter.entity.ReportRebate;
import com.bohai.dataCenter.service.ReportExchangeRebateService;
import com.bohai.dataCenter.service.ReportRebateService;
import com.bohai.dataCenter.service.ReportService;
import com.bohai.dataCenter.vo.CountExchangeRebateParamVO;
import com.bohai.dataCenter.vo.CountRebatReportParamVO;
import com.bohai.dataCenter.vo.QueryExchangeRebateParamVO;

@Controller
public class ReportController {
	
	static Logger logger = Logger.getLogger(ReportController.class);
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ReportRebateService reportRebateService;
	
	@Autowired
	private ReportExchangeRebateService reportExchangeRebateService;

	
	@RequestMapping(value="toBusinessReport")
	public String toBusinessReport(){
		return "report/business";
	}
	
	@RequestMapping(value="toSoftServeReport")
	public String toSoftServeReport(){
		return "report/softServe";
	}
	
	@RequestMapping(value="toRebateReport")
	public String toRebateReport(){
		return "report/rebate";
	}
	
	@RequestMapping(value="toExchangeRebateReport")
	public String toExchangeRebateReport(){
		return "report/exchangeRebate";
	}
	
	/**
	 * 统计返利息报表
	 * @throws BohaiException 
	 */
	@RequestMapping(value="countRebatReport",method=RequestMethod.POST)
	@ResponseBody
	public void countRebatReport(@RequestBody CountRebatReportParamVO paramVO) throws BohaiException{
		logger.debug("统计返利息报表入参："+JSON.toJSONString(paramVO));
		this.reportService.countRebatReport(paramVO);
		logger.debug("统计完成");
	}
	
	@RequestMapping(value="countExchangeRebate",method=RequestMethod.POST)
	@ResponseBody
	public void countExchangeRebate(@RequestBody CountExchangeRebateParamVO paramVO) throws BohaiException{
		logger.debug("统计交易所返还报表入参："+JSON.toJSONString(paramVO));
		this.reportService.countExchangeRebate(paramVO);
		logger.debug("统计完成");
	}
	
	/**
	 * 查询返利明细
	 * @return
	 */
	@RequestMapping(value="queryRebateDetail")
	@ResponseBody
	public List<ReportRebate> queryRebateDetail(){
		return this.reportRebateService.queryRebateDetail();
	}
	
	/**
	 * 查询返利统计表
	 */
	@RequestMapping(value="queryRebateReport")
	@ResponseBody
	public List<Map<String, Object>> queryRebateReport(){
		
		return this.reportRebateService.queryRebateReport();
	}
	
	/**
	 * 查询交易所返佣统计
	 * @param paramVO
	 * @return
	 * @throws BohaiException
	 */
	@RequestMapping(value="queryExchangeRebateReport")
	@ResponseBody
	public List<ReportExchangeRebate> queryExchangeRebateReport(@RequestBody(required=false) QueryExchangeRebateParamVO paramVO) throws BohaiException{
		
		return this.reportExchangeRebateService.queryExchangeRebate(paramVO);
	}

}
