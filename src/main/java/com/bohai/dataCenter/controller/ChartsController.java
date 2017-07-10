package com.bohai.dataCenter.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.dataCenter.entity.ReportDep;
import com.bohai.dataCenter.persistence.ReportDepMapper;
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
}
