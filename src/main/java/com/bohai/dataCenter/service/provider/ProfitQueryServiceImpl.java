package com.bohai.dataCenter.service.provider;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.bohai.dataCenter.api.ProfitQueryService;
import com.bohai.dataCenter.api.exception.BohaiException;
import com.bohai.dataCenter.api.vo.InvestorProfitParamVO;
import com.bohai.dataCenter.api.vo.InvestorProfitResultVO;
import com.bohai.dataCenter.api.vo.MarketerProfitParamVO;
import com.bohai.dataCenter.api.vo.MarketerProfitResultVO;
import com.bohai.dataCenter.api.vo.MediatorProfitParamVO;
import com.bohai.dataCenter.api.vo.MediatorProfitResultVO;
import com.bohai.dataCenter.persistence.CrmCustomerMapper;
import com.bohai.dataCenter.persistence.CrmMarketerMapper;
import com.bohai.dataCenter.persistence.CrmMediatorMapper;

/**
 * 查询利润实现类
 * @author caojia
 *
 */
public class ProfitQueryServiceImpl implements ProfitQueryService {
    
    static Logger logger = Logger.getLogger(ProfitQueryServiceImpl.class);
    
    @Autowired
    private CrmCustomerMapper crmCustomerMapper;
    
    @Autowired
    private CrmMediatorMapper crmMediatorMapper;
    
    @Autowired
    private CrmMarketerMapper crmMarketerMapper;

    @Override
    public List<InvestorProfitResultVO> queryInvestorProfit(InvestorProfitParamVO paramVO) throws BohaiException {

        logger.debug("查询投资者产生的利润入参："+JSON.toJSONString(paramVO));
        List<InvestorProfitResultVO> list = this.crmCustomerMapper.queryInvestorProfitByMarketer(paramVO);
        logger.debug("查询投资者产生的利润出参："+JSON.toJSONString(list));
        return list;
        
    }

    @Override
    public List<MediatorProfitResultVO> queryMediatorProfit(MediatorProfitParamVO paramVO) throws BohaiException {

        return this.crmMediatorMapper.queryMediatorProfitByMarketer(paramVO);
    }

    @Override
    public List<MarketerProfitResultVO> queryMarketerProfit(MarketerProfitParamVO paramVO) throws BohaiException {
        
        return this.crmMarketerMapper.queryMarketerSelfProfit(paramVO);
    }

}
