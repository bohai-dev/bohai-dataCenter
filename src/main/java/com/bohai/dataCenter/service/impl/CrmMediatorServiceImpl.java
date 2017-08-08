package com.bohai.dataCenter.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CrmMediator;
import com.bohai.dataCenter.entity.RebateList;
import com.bohai.dataCenter.entity.SpecialReturn;
import com.bohai.dataCenter.persistence.CrmCustomerMapper;
import com.bohai.dataCenter.persistence.CrmMarketerMapper;
import com.bohai.dataCenter.persistence.CrmMediatorMapper;
import com.bohai.dataCenter.persistence.RebateListMapper;
import com.bohai.dataCenter.persistence.SpecialReturnMapper;
import com.bohai.dataCenter.service.CrmMediatorService;
import com.bohai.dataCenter.vo.QueryCrmMediatorParamVO;
import com.bohai.dataCenter.vo.QueryMediatorOverviewResultVO;

@Service("crmMediatorService")
public class CrmMediatorServiceImpl implements CrmMediatorService {
    
    static Logger logger = Logger.getLogger(CrmMediatorServiceImpl.class);
    
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
    public void modifyCrmMediator(CrmMediator mediator) throws BohaiException {
        
        try {
            this.crmCustomerMapper.updateDepByMediator(mediator);
        } catch (Exception e) {
            logger.error("更新居间人名下居间人归属部门失败");
            throw new BohaiException("", "更新居间人名下居间人归属部门失败");
        }
        
        try {
            crmMediatorMapper.updateByPrimaryKey(mediator);
        } catch (Exception e) {
            logger.error("更新居间人信息失败",e);
            throw new BohaiException("", "更新居间人信息失败");
        }

    }

    @Override
    public void removeCrmMediator(CrmMediator mediator) throws BohaiException {
        
        CrmMediator existsMediator = this.crmMediatorMapper.selectByPrimaryKey(mediator.getMediatorNo());
        
        if(existsMediator == null){
            logger.warn("居间人不存在："+mediator.getMediatorNo());
            throw new BohaiException("", "居间人不存在："+mediator.getMediatorNo());
        }
        
        //先备份再删除
        try {
            this.crmCustomerMapper.copyCustomerByMediator(existsMediator);
            this.crmMediatorMapper.copyMediatorByMediatorNo(existsMediator.getMediatorNo());
        } catch (Exception e1) {
            logger.error("保存历史归属关系失败",e1);
            throw new BohaiException("", "保存历史归属关系失败");
        }
        
        //更新居间人名下客户归属
        try {
            this.crmCustomerMapper.updateBelongByMediator(existsMediator);
        } catch (Exception e) {
            logger.error("更新居间人名下客户信息归属失败",e);
            throw new BohaiException("", "更新居间人名下客户信息归属失败");
        }
        
        this.crmMediatorMapper.deleteByPrimaryKey(existsMediator.getMediatorNo());
    }

    @Override
    public QueryMediatorOverviewResultVO queryMediatorOverview(QueryCrmMediatorParamVO paramVO) throws BohaiException {
        
        QueryMediatorOverviewResultVO resultVO = new QueryMediatorOverviewResultVO();
        
        List<CrmMediator> list = this.crmMediatorMapper.selectByCondition(paramVO);
        
        if(list != null && list.size() >0){
            CrmMediator mediator = list.get(0);
            resultVO.setMediatorNo(mediator.getMediatorNo());
            resultVO.setMediatorName(mediator.getMediatorName());
            resultVO.setDepName(mediator.getDepName());
            resultVO.setEffectDate(mediator.getEffectDate());
            resultVO.setExpireDate(mediator.getExpireDate());
            resultVO.setPhone(mediator.getTelephone());
            
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
            
            
            if("1".equals(mediator.getBelongType())){
                resultVO.setMarketerNo(mediator.getBelongTo());
                resultVO.setMarketerName(mediator.getBelongToName());
                
                String marketerRate = this.crmMarketerMapper.getMarketerRate(mediator.getBelongTo());
                resultVO.setMarketerRate(marketerRate);
            }else {
                resultVO.setMarketerNo(null);
                resultVO.setMarketerName(null);
                resultVO.setMarketerRate(null);
            }
            Long customerCount = this.crmCustomerMapper.countByMediatorNo(mediator.getMediatorNo());
            resultVO.setCustomerCount(customerCount.intValue());
            
        }
        
        
        return resultVO;
    }

}
