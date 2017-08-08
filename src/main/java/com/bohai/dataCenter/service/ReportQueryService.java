package com.bohai.dataCenter.service;

import java.util.List;
import java.util.Map;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.vo.QueryInvestorProfitParamVO;
import com.bohai.dataCenter.vo.QueryMarketerProfitParamVO;
import com.bohai.dataCenter.vo.QueryMediatorProfitParamVO;

/**
 * 利润查询
 * @author caojia
 *
 */
public interface ReportQueryService {
    
    /**
     * 查询客户产生的利润
     * @param paramVO
     * @return
     * @throws BohaiException
     */
    public List<Map<String, Object>> queryInvestorProfit(QueryInvestorProfitParamVO paramVO) throws BohaiException;

    /**
     * 查询居间人产生的利润
     * @param paramVO
     * @return
     * @throws BohaiException
     */
    public List<Map<String, Object>> queryMediatorProfit(QueryMediatorProfitParamVO paramVO) throws BohaiException;
    
    /**
     * 查询营销人员产生的利润
     * @param paramVO
     * @return
     * @throws BohaiException
     */
    public List<Map<String, Object>> queryMarketerProfit(QueryMarketerProfitParamVO paramVO) throws BohaiException;
}
