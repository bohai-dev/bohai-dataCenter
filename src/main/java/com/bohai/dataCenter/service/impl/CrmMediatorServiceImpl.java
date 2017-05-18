package com.bohai.dataCenter.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CrmMediator;
import com.bohai.dataCenter.persistence.CrmCustomerMapper;
import com.bohai.dataCenter.persistence.CrmMediatorMapper;
import com.bohai.dataCenter.service.CrmMediatorService;

@Service("crmMediatorService")
public class CrmMediatorServiceImpl implements CrmMediatorService {
    
    static Logger logger = Logger.getLogger(CrmMediatorServiceImpl.class);
    
    @Autowired
    private CrmCustomerMapper crmCustomerMapper;
    
    @Autowired
    private CrmMediatorMapper crmMediatorMapper;

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
        
        try {
            this.crmCustomerMapper.updateBelongByMediator(existsMediator);
        } catch (Exception e) {
            logger.error("更新居间人名下客户信息归属失败",e);
            throw new BohaiException("", "更新居间人名下客户信息归属失败");
        }
        
        this.crmMediatorMapper.deleteByPrimaryKey(existsMediator.getMediatorNo());
    }

}
