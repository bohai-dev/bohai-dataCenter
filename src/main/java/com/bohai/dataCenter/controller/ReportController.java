package com.bohai.dataCenter.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.bohai.dataCenter.entity.ReportSpecialReturn;
import com.bohai.dataCenter.service.ReportExchangeRebateService;
import com.bohai.dataCenter.service.ReportQueryService;
import com.bohai.dataCenter.service.ReportRebateService;
import com.bohai.dataCenter.service.ReportService;
import com.bohai.dataCenter.service.ReportSpecialReturnService;
import com.bohai.dataCenter.vo.CountExchangeRebateParamVO;
import com.bohai.dataCenter.vo.CountRebatReportParamVO;
import com.bohai.dataCenter.vo.QueryExchangeRebateParamVO;
import com.bohai.dataCenter.vo.QueryInvestorProfitParamVO;
import com.bohai.dataCenter.vo.QueryMarketerProfitParamVO;
import com.bohai.dataCenter.vo.QueryMarketerReturnReportParamVO;
import com.bohai.dataCenter.vo.QueryMediatorProfitParamVO;
import com.bohai.dataCenter.vo.QueryRebateDetailParamVO;
import com.bohai.dataCenter.vo.QueryRebateReportParamVO;
import com.bohai.dataCenter.vo.QuerySpecialReturnReportParamVO;

@Controller
public class ReportController {
	
	static Logger logger = Logger.getLogger(ReportController.class);
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ReportRebateService reportRebateService;
	
	@Autowired
	private ReportExchangeRebateService reportExchangeRebateService;
	
	@Autowired
	private ReportSpecialReturnService reportSpecialReturnService;
	
	@Autowired
	private ReportQueryService reportQueryService;
	
	
	@RequestMapping(value="toBusinessReport")
	@RequiresPermissions(value="report:business:view")
	public String toBusinessReport(){
		return "report/business";
	}
	
	@RequestMapping(value="toSoftServeReport")
	@RequiresPermissions(value="report:softServe:view")
	public String toSoftServeReport(){
		return "report/softServe";
	}
	
	@RequestMapping(value="toRebateReport")
	@RequiresPermissions(value="report:rebate:view")
	public String toRebateReport(){
		return "report/rebate";
	}
	
	@RequestMapping(value="toExchangeRebateReport")
	@RequiresPermissions(value="report:exchangeRebate:view")
	public String toExchangeRebateReport(){
		return "report/exchangeRebate";
	}
	
	@RequestMapping(value="toInvestorReport")
	@RequiresPermissions(value="report:investor:view")
	public String toInvestorReport(){
	    return "report/investor";
	}
	
	@RequestMapping(value="toRetainedReport")
	public String toRetainedReport(){
	    return "report/retained";
	}
	
	/**
	 * 查询客户贡献
	 * @param paramVO
	 * @return
	 * @throws BohaiException
	 */
	@RequestMapping(value="queryInvestorProfit")
	@ResponseBody
	public List<Map<String, Object>> queryInvestorProfit(@RequestBody QueryInvestorProfitParamVO paramVO) throws BohaiException{
	    
	    logger.debug("查询客户产生的利润入参："+JSON.toJSONString(paramVO));
	    return this.reportQueryService.queryInvestorProfit(paramVO);
	}
	
	/**
	 * 查询居间人贡献度
	 * @param paramVO
	 * @return
	 * @throws BohaiException
	 */
	@RequestMapping(value="queryMediatorProfit")
	@ResponseBody
	public List<Map<String, Object>> queryMediatorProfit(@RequestBody QueryMediatorProfitParamVO paramVO) throws BohaiException{
        
        logger.debug("查询居间人产生的利润入参："+JSON.toJSONString(paramVO));
        return this.reportQueryService.queryMediatorProfit(paramVO);
    }
	
	/**
	 * 查询营销人员贡献度
	 * @param paramVO
	 * @return
	 * @throws BohaiException
	 */
	@RequestMapping(value="queryMarketerProfit")
    @ResponseBody
    public List<Map<String, Object>> queryMarketerProfit(@RequestBody QueryMarketerProfitParamVO paramVO) throws BohaiException{
        
        logger.debug("查询营销人员产生的利润入参："+JSON.toJSONString(paramVO));
        return this.reportQueryService.queryMarketerProfit(paramVO);
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
	public List<ReportRebate> queryRebateDetail(@RequestBody(required=false) QueryRebateDetailParamVO paramVO){
		return this.reportRebateService.queryRebateDetail(paramVO);
	}
	
	/**
	 * 查询返利统计表(以客户为维度)
	 */
	@RequestMapping(value="queryRebateReport")
	@ResponseBody
	public List<Map<String, Object>> queryRebateReport(@RequestBody(required=false) QueryRebateReportParamVO paramVO){
		
		return this.reportRebateService.queryRebateReport(paramVO);
	}
	
	/**
	 * 查询返利息统计表(以营业部为维度)
	 * @param paramVO
	 * @return
	 */
	@RequestMapping(value="queryMarketRebateReport")
	@ResponseBody
	public List<Map<String, Object>> queryMarketRebateReport(@RequestBody(required=false) QueryRebateReportParamVO paramVO){
		
		return this.reportRebateService.queryMarketRebateReport(paramVO);
	}
	
	/**
     * 查询返利息统计表(以营销人员为维度)
     * @param paramVO
     * @return
     */
    @RequestMapping(value="queryMarketerRebateReport")
    @ResponseBody
	public List<Map<String,Object>> queryMarketerRebateReport(@RequestBody(required=false) QueryRebateReportParamVO paramVO){
	    return this.reportRebateService.queryMarketerRebateReport(paramVO);
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
	
	/**
	 * 查询交易所返佣特例
	 * @param paramVO
	 * @return
	 * @throws BohaiException
	 */
	@RequestMapping(value="querySpecialReturnReport")
	@ResponseBody
	public List<ReportSpecialReturn> querySpecialReturnReport(@RequestBody(required=false) QuerySpecialReturnReportParamVO paramVO) throws BohaiException{
	    
	    return this.reportSpecialReturnService.queryByCondition(paramVO);
	}
	
	/**
	 * 查询营销人员返佣
	 * @param paramVO
	 * @return
	 * @throws BohaiException
	 */
	@RequestMapping(value="queryMarketerReturnReport")
	@ResponseBody
	public List<Map<String, Object>> queryMarketerReturnReport(@RequestBody(required=false) QueryMarketerReturnReportParamVO paramVO) throws BohaiException{
	    
	    return this.reportSpecialReturnService.queryMarketerReturn(paramVO.getMonth());
	}
	
	/**
	 * 查询TB软件服务费
	 * @return
	 */
	@RequestMapping(value="querySoftCharge")
	@ResponseBody
	public List<Map<String, Object>> querySoftCharge(){
		return reportService.queryTBSoftReport();
	}

}
