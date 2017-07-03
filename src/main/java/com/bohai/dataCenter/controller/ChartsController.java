package com.bohai.dataCenter.controller;

import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.bohai.dataCenter.vo.QueryMarketProfitPieChartParamVO;

/**
 * 统计图表
 * @author caojia
 */
@Controller
public class ChartsController {

    /**
     * 查询营业部收益饼图
     * @return
     */
    public JSONObject queryMarketProfitPieChart(QueryMarketProfitPieChartParamVO paramVO){
        
        JSONObject json = new JSONObject(true);
        
        
        
        
        return json;
    }
    
    /**
     * 根据营业部查询收益子饼图
     * @return
     */
    public JSONObject queryProfitByDep(){
        
        JSONObject json = new JSONObject(true);
        
        return json;
    }
}
