package com.bohai.dataCenter.service;

import java.util.List;

import com.bohai.dataCenter.vo.ChartsOptoin;

public interface ChartsService {

    /**
     * 查询客户成交额柱状图
     * @return
     */
    public ChartsOptoin queryTrunoverBarChart();
    
    /**
     * 查询新增客户数
     * @return
     */
    public ChartsOptoin queryInvestorIncreament();
    
    /**
     * 查询日均出入金
     * @return
     */
    public ChartsOptoin queryCashInAndOut();
    
    /**
     * 查询客户手续费
     * @return
     */
    public ChartsOptoin queryInvestorCharge();
    
    /**
     * 查询客户数和总权益
     * @return
     */
    public ChartsOptoin queryInvestorAndRight();
    
    /**
     * 权益排名
     * @return
     */
    public List rightsRanking();
}
