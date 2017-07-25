package com.bohai.dataCenter.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.dataCenter.entity.CrmCustomer;
import com.bohai.dataCenter.entity.ReportDep;
import com.bohai.dataCenter.persistence.CrmCustomerMapper;
import com.bohai.dataCenter.persistence.ReportDepMapper;
import com.bohai.dataCenter.vo.QueryDepProfitBarChartParamVO;
import com.bohai.dataCenter.vo.QueryDepProfitPieChartParamVO;

/**
 * 统计图表
 * @author caojia
 */
@Controller
public class ChartsController {
    
    static Logger logger = Logger.getLogger(ChartsController.class);
    
    @Autowired
    private ReportDepMapper reportDepMapper;
    
    @Autowired
    private CrmCustomerMapper crmCustomerMapper;
    /**
     * 查询营业部收益饼图
     * @return
     */
    @RequestMapping(value="queryMarketProfitPieChart")
    @ResponseBody
    public List<ReportDep> queryMarketProfitPieChart(@RequestBody(required=false) QueryDepProfitPieChartParamVO paramVO){
        
        return this.reportDepMapper.selectByMonth(paramVO);
    }
    
    /**
     * 根据营业部查询收益子饼图
     * @return
     */
    public List<Map<String,Object>> queryProfitByDep(){
        
        
        return null;
    }
    
    /**
     * 查询客户地域分布图
     * @return
     */
    @RequestMapping(value="queryCustomerDistribution")
    @ResponseBody
    public List<Map<String,Object>> queryCustomerDistribution(){
        
        return crmCustomerMapper.querySumByProvince();
    }
    /**
     * 查询某营业部每个月利润
     * @return
     */
    @RequestMapping(value="queryMonthProfitByDep")
    @ResponseBody
    public ReportDep[] queryMonthProfitByDep(@RequestBody(required=false) QueryDepProfitBarChartParamVO paramVO){
    	ReportDep[] reportDepArray=new ReportDep[12];

    	for(int i=0;i<reportDepArray.length;i++) {
    		reportDepArray[i]=new ReportDep();
    		reportDepArray[i].setAllProfit(new BigDecimal(0));
    		reportDepArray[i].setDepProfit(new BigDecimal(0));

    	}
    	List<ReportDep> listReportDep=reportDepMapper.selectByDep(paramVO);
    	
    	for (int i = 0; i < listReportDep.size(); i++) {

			if(listReportDep.get(i).getMonth().split("-")[1].equals("01")){
				reportDepArray[0]=listReportDep.get(i);
			}else if(listReportDep.get(i).getMonth().split("-")[1].equals("02")){
				reportDepArray[1]=listReportDep.get(i);
			}else if(listReportDep.get(i).getMonth().split("-")[1].equals("03")){
				reportDepArray[2]=listReportDep.get(i);
			}else if(listReportDep.get(i).getMonth().split("-")[1].equals("04")){
				reportDepArray[3]=listReportDep.get(i);
			}else if(listReportDep.get(i).getMonth().split("-")[1].equals("05")){
				reportDepArray[4]=listReportDep.get(i);
			}else if(listReportDep.get(i).getMonth().split("-")[1].equals("06")){
				reportDepArray[5]=listReportDep.get(i);
			}else if(listReportDep.get(i).getMonth().split("-")[1].equals("07")){
				reportDepArray[6]=listReportDep.get(i);
			}else if(listReportDep.get(i).getMonth().split("-")[1].equals("08")){
				reportDepArray[7]=listReportDep.get(i);
			}else if(listReportDep.get(i).getMonth().split("-")[1].equals("09")){
				reportDepArray[8]=listReportDep.get(i);
			}else if(listReportDep.get(i).getMonth().split("-")[1].equals("10")){
				reportDepArray[9]=listReportDep.get(i);
			}else if(listReportDep.get(i).getMonth().split("-")[1].equals("11")){
				reportDepArray[10]=listReportDep.get(i);
			}else if(listReportDep.get(i).getMonth().split("-")[1].equals("12")){
				reportDepArray[11]=listReportDep.get(i);
			}
			
		}

        return reportDepArray;

    }
}
