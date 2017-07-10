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
import com.bohai.dataCenter.vo.QueryCrmMarketerParamVO;
import com.bohai.dataCenter.vo.QueryMarketerOverviewResultVO;

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
        
        
        //更新营销人员名下居间人部门
        int mediatorCount = 0;
        
        try {
            mediatorCount = this.crmMediatorMapper.updateDepByMarketerNo(marketer);
            logger.debug("共更新"+mediatorCount+"条营销人员名下居间人信息");
        } catch (Exception e) {
            logger.error("更新营销人员名下居间人归属部门失败");
            throw new BohaiException("", "更新营销人员名下居间人归属部门失败");
        }
        
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
        //删除营销人员，把名下的居间人和客户归属直接划转到营业部
        CrmMarketer crmMarketer = this.crmMarketerMapper.selectByPrimaryKey(marketer.getMarketerNo());
        if(crmMarketer == null){
            logger.warn("营销人员不存在："+marketer.getMarketerNo());
            throw new BohaiException("", "营销人员不存在："+marketer.getMarketerNo());
        }
        
        //把营销人员直属客户的归属改为该营销人员所属的营业部
        try {
            this.crmCustomerMapper.updateBelongByMarketer(crmMarketer);
        } catch (Exception e) {
            logger.error("更新营销人员直属客户归属失败",e);
            throw new BohaiException("", "更新营销人员直属客户归属失败");
        }
        
        //把营销人员名下的居间人归属改为营销人员所属的营业部
        try {
            this.crmMediatorMapper.updateBelongByMarketer(crmMarketer);
        } catch (Exception e) {
            logger.error("更新营销人员名下居间人归属失败",e);
            throw new BohaiException("", "更新营销人员名下居间人归属失败");
        }
        
        try {
            this.crmMarketerMapper.deleteByPrimaryKey(crmMarketer.getMarketerNo());
        } catch (Exception e) {
            logger.error("删除营销人员失败",e);
            throw new BohaiException("", "删除营销人员失败");
        }
    }

    @Override
    public QueryMarketerOverviewResultVO queryMarketerOverview(QueryCrmMarketerParamVO paramVO) throws BohaiException {

        QueryMarketerOverviewResultVO resultVO = new QueryMarketerOverviewResultVO();
        
        List<CrmMarketer> list = this.crmMarketerMapper.selectByCondition(paramVO);
        if(list != null && list.size() > 0){
            CrmMarketer marketer = list.get(0);
            resultVO.setDepName(marketer.getDepName());
            resultVO.setMarketerNo(marketer.getMarketerNo());
            resultVO.setMarketerName(marketer.getMarketerName());
            resultVO.setEntryDate(marketer.getEntryDate());
            resultVO.setPhone(marketer.getTelephone());
            
            Long directCustomerCount = this.crmCustomerMapper.countByMarketerNo(marketer.getMarketerNo());
            resultVO.setDirectCustomerCount(directCustomerCount);
            Long mediatorCount = this.crmMediatorMapper.countByMarketerNo(marketer.getMarketerNo());
            resultVO.setMediatorCount(mediatorCount);
            
            String marketerRate = this.crmMarketerMapper.getMarketerRate(marketer.getMarketerNo());
            resultVO.setMarketerRate(marketerRate);
        }
        
        return resultVO;
    }
}
