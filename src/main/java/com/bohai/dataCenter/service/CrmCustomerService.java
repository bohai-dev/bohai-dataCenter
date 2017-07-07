package com.bohai.dataCenter.service;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.vo.QueryCrmCustomerParamVO;
import com.bohai.dataCenter.vo.QueryInvestorOverviewResultVO;

public interface CrmCustomerService {

    /**
     * 查询客户简介
     * @param paramVO
     * @return
     * @throws BohaiException
     */
    public QueryInvestorOverviewResultVO queryInvestorOverview(QueryCrmCustomerParamVO paramVO) throws BohaiException;
    
    
}
