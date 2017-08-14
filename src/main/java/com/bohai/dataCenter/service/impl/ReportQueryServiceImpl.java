package com.bohai.dataCenter.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.persistence.CrmCustomerMapper;
import com.bohai.dataCenter.persistence.CrmMarketerMapper;
import com.bohai.dataCenter.persistence.CrmMediatorMapper;
import com.bohai.dataCenter.service.ReportQueryService;
import com.bohai.dataCenter.vo.QueryInvestorProfitParamVO;
import com.bohai.dataCenter.vo.QueryMarketerProfitParamVO;
import com.bohai.dataCenter.vo.QueryMediatorProfitParamVO;

@Service("reportQueryService")
public class ReportQueryServiceImpl implements ReportQueryService {
    
    static Logger logger = Logger.getLogger(ReportQueryServiceImpl.class);
    
    @Autowired
    private CrmCustomerMapper crmCustomerMapper;
    
    @Autowired
    private CrmMediatorMapper crmMediatorMapper;
    
    @Autowired
    private CrmMarketerMapper crmMarketerMapper;

    @Override
    public List<Map<String, Object>> queryInvestorProfit(QueryInvestorProfitParamVO paramVO) throws BohaiException {
        
        List<Map<String, Object>> map = null;
        try {
            map = this.crmCustomerMapper.queryInvestorProfit(paramVO);
        } catch (Exception e) {
            logger.error("查询客户产生的利润失败",e);
            throw new BohaiException("", "查询客户产生的利润失败");
        }
        
        return map;
    }

    @Override
    public List<Map<String, Object>> queryMediatorProfit(QueryMediatorProfitParamVO paramVO) throws BohaiException {
        List<Map<String, Object>> map = null;
        try {
            map = this.crmMediatorMapper.queryMediatorProfit(paramVO);
        } catch (Exception e) {
            logger.error("查询居间人产生的利润失败",e);
            throw new BohaiException("", "查询居间人产生的利润失败");
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> queryMarketerProfit(QueryMarketerProfitParamVO paramVO) throws BohaiException {
        List<Map<String, Object>> map = null;
        
        try {
            map = this.crmMarketerMapper.queryMarketerProfit(paramVO);
        } catch (Exception e) {
            logger.error("查询营销人员产生的利润失败",e);
            throw new BohaiException("", "查询营销人员产生的利润失败");
        }
        return map;
    }

}
