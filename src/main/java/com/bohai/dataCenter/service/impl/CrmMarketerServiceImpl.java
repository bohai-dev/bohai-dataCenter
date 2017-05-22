package com.bohai.dataCenter.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CrmMarketer;
import com.bohai.dataCenter.entity.CrmMediator;
import com.bohai.dataCenter.persistence.CrmCustomerMapper;
import com.bohai.dataCenter.persistence.CrmMarketerMapper;
import com.bohai.dataCenter.persistence.CrmMediatorMapper;
import com.bohai.dataCenter.service.CrmMarketerService;

@Service("crmMarketerService")
public class CrmMarketerServiceImpl implements CrmMarketerService {
    
    static Logger logger = Logger.getLogger(CrmMarketerServiceImpl.class);
    
    @Autowired
    private CrmMarketerMapper crmMarketerMapper;
    
    @Autowired
    private CrmMediatorMapper crmMediatorMapper;
    
    @Autowired
    private CrmCustomerMapper crmCustomerMapper;

    @Override
    public void modifyCrmMarketer(CrmMarketer marketer) throws BohaiException {
        
        //查询营销人员名下所有居间人信息
        List<CrmMediator> mediators = this.crmMediatorMapper.selectByMarketer(marketer.getMarketerNo());
        if(mediators != null && mediators.size() >0){
            for (CrmMediator crmMediator : mediators) {
                //更新居间人名下客户信息
                try {
                    this.crmCustomerMapper.updateDepByMediator(crmMediator);
                } catch (Exception e) {
                    logger.error("更新居间人名下居间人归属部门失败");
                    throw new BohaiException("", "更新居间人名下居间人归属部门失败");
                }
                
            }
        }
        
        
        //更新营销人员名下居间人部门
        int mediatorCount = 0;
        
        try {
            mediatorCount = this.crmMediatorMapper.updateDepByMarketerNo(marketer);
            logger.debug("共更新"+mediatorCount+"条营销人员名下居间人信息");
        } catch (Exception e) {
            logger.error("更新营销人员名下居间人归属部门失败");
            throw new BohaiException("", "更新营销人员名下居间人归属部门失败");
        }
        
        //更新营销人员名下客户信息
        int custCount = 0;
        
        try {
            custCount = this.crmCustomerMapper.updateDepByMarketer(marketer);
            logger.debug("共更新"+custCount+"条营销人员名下客户信息");
        } catch (Exception e) {
            logger.error("更新营销人员名下客户归属部门失败");
            throw new BohaiException("", "更新营销人员名下客户归属部门失败");
        }
        
        //更新营销人员信息
        try {
            this.crmMarketerMapper.updateByPrimaryKey(marketer);
        } catch (Exception e) {
            logger.error("更新营销人员信息失败");
            throw new BohaiException("", "更新营销人员信息失败");
        }

    }

    @Override
    public void removeCrmMarketer(CrmMarketer marketer) throws BohaiException {
        // TODO Auto-generated method stub
        
    }

}
