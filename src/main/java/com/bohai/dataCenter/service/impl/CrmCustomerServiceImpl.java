package com.bohai.dataCenter.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CrmCustomer;
import com.bohai.dataCenter.entity.CrmMarketer;
import com.bohai.dataCenter.entity.CrmMediator;
import com.bohai.dataCenter.entity.RebateList;
import com.bohai.dataCenter.entity.SpecialReturn;
import com.bohai.dataCenter.persistence.CrmCustomerMapper;
import com.bohai.dataCenter.persistence.CrmMarketerMapper;
import com.bohai.dataCenter.persistence.CrmMediatorMapper;
import com.bohai.dataCenter.persistence.RebateListMapper;
import com.bohai.dataCenter.persistence.SpecialReturnMapper;
import com.bohai.dataCenter.service.CrmCustomerService;
import com.bohai.dataCenter.vo.QueryCrmCustomerParamVO;
import com.bohai.dataCenter.vo.QueryInvestorOverviewResultVO;

@Service("crmCustomerService")
public class CrmCustomerServiceImpl implements CrmCustomerService {

    static Logger logger = Logger.getLogger(CrmCustomerServiceImpl.class);
    
    @Autowired
    private CrmCustomerMapper crmCustomerMapper;
    
    @Autowired
    private CrmMediatorMapper crmMediatorMapper;
    
    @Autowired
    private CrmMarketerMapper crmMarketerMapper;
    
    @Autowired
    private RebateListMapper rebateListMapper;
    
    @Autowired
    private SpecialReturnMapper specialReturnMapper;
    
    
    @Override
    public QueryInvestorOverviewResultVO queryInvestorOverview(QueryCrmCustomerParamVO paramVO) throws BohaiException {
        
        QueryInvestorOverviewResultVO resultVO = new QueryInvestorOverviewResultVO();
        
        List<CrmCustomer> list = this.crmCustomerMapper.selectByCondition(paramVO);
        
        if(list != null && list.size()>0){
            CrmCustomer customer = list.get(0);
            resultVO.setInvestorNo(customer.getInvestorNo());
            resultVO.setInvestorName(customer.getInvestorName());
            resultVO.setDepName(customer.getDeptName());
            resultVO.setOpenDate(customer.getOpenDate());
            resultVO.setPhone(customer.getMobilePhone());
            if("1".equals(customer.getBelongType())){
                //属于营销人员
                resultVO.setMarketerNo(customer.getBelongTo());
                resultVO.setMarketerName(customer.getBelongToName());
                String marketerRate = this.crmMarketerMapper.getMarketerRate(customer.getBelongTo());
                resultVO.setMarketerRate(marketerRate);
                
                resultVO.setMediatorNo(null);
                resultVO.setMediatorName(null);
                resultVO.setInterestRate(null);
                resultVO.setExchangeRate(null);
                resultVO.setChargeRate(null);
            }else if ("2".equals(customer.getBelongType())) {
                //属于居间人
                resultVO.setMediatorNo(customer.getBelongTo());
                resultVO.setMediatorName(customer.getBelongToName());
                
                CrmMediator mediator = this.crmMediatorMapper.selectByPrimaryKey(customer.getBelongTo());
                if(mediator != null){
                    
                    resultVO.setChargeRate(mediator.getAllocationProportion()==null?"浮动":mediator.getAllocationProportion());
                    
                    //查询返利息比例
                    RebateList rebateList = this.rebateListMapper.selectOneByMediatorNo(mediator.getMediatorNo());
                    if(rebateList != null){
                        resultVO.setInterestRate(rebateList.getFixProportion()==null?"浮动":rebateList.getFixProportion());
                    }else {
                        resultVO.setInterestRate(null);
                    }
                    //查询交返比例
                    SpecialReturn specialReturn = this.specialReturnMapper.selectOneByMediatorNo(mediator.getMediatorNo());
                    if(specialReturn != null){
                        resultVO.setExchangeRate(specialReturn.getFixProportion()==null?"浮动":specialReturn.getFixProportion());
                    }else {
                        resultVO.setExchangeRate(null);
                    }
                    
                }
                
                if(mediator != null && "1".equals(mediator.getBelongType())){
                    CrmMarketer marketer = this.crmMarketerMapper.selectByPrimaryKey(mediator.getBelongTo());
                    if(marketer != null){
                        resultVO.setMarketerNo(marketer.getMarketerNo());
                        resultVO.setMarketerName(marketer.getMarketerName());
                        String marketerRate = this.crmMarketerMapper.getMarketerRate(marketer.getMarketerNo());
                        resultVO.setMarketerRate(marketerRate);
                    }else {
                        resultVO.setMarketerNo(null);
                        resultVO.setMarketerName(null);
                        resultVO.setMarketerRate(null);
                    }
                }else {
                    resultVO.setMarketerNo(null);
                    resultVO.setMarketerName(null);
                    resultVO.setMarketerRate(null);
                }
            }else {
                resultVO.setMediatorNo(null);
                resultVO.setMediatorName(null);
                resultVO.setInterestRate(null);
                resultVO.setExchangeRate(null);
                resultVO.setChargeRate(null);
                resultVO.setMarketerNo(null);
                resultVO.setMarketerName(null);
                resultVO.setMarketerRate(null);
            }
        }
        
        return resultVO;
    }

}
