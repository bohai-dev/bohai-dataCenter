package com.bohai.dataCenter.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.dataCenter.service.ChartsService;
import com.bohai.dataCenter.vo.ChartsOptoin;

@Controller
public class ChartsReportController {
    
    @Autowired
    private ChartsService chartsService;

    @RequestMapping(value="toCharts")
    @RequiresPermissions(value="report:charts:view")
    public String toCharts(){
        
        return "report/charts";
    }
    
    /**
     * 客户成交额
     * @return
     */
    @RequestMapping(value="queryTrunoverBarChart")
    @ResponseBody
    public ChartsOptoin queryTrunoverBarChart(){
        
        return chartsService.queryTrunoverBarChart();
        
    }
    
    /**
     * 新开客户数
     * @return
     */
    @RequestMapping(value="queryInvestorIncreament")
    @ResponseBody
    public ChartsOptoin queryInvestorIncreament(){
        return this.chartsService.queryInvestorIncreament();
    }
    
    /**
     * 佣金
     * @return
     */
    @RequestMapping(value="queryInvestorCharge")
    @ResponseBody
    public ChartsOptoin queryInvestorCharge(){
        return this.chartsService.queryInvestorCharge();
    }
    
    /**
     * 总客户数和总权益
     * @return
     */
    @RequestMapping(value="queryInvestorAndRight")
    @ResponseBody
    public ChartsOptoin queryInvestorAndRight(){
        return this.chartsService.queryInvestorAndRight();
    }
    
    @RequestMapping(value="rightsRanking")
    @ResponseBody
    public List rightsRanking(){
        return this.chartsService.rightsRanking();
    }
}
