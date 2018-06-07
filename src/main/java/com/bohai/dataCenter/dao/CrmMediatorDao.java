package com.bohai.dataCenter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bohai.dataCenter.entity.CrmMediator;

@Component("crmMediatorDao")
public class CrmMediatorDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public int insert(CrmMediator crmMediator){
        
        String sql = "insert into T_CRM_MEDIATOR (MEDIATOR_NO, MEDIATOR_NAME, MEDIATOR_SHORT_NAME, "
                + "STATUS, DEP_CODE, DEP_NAME, "
                + "ALLOCATION_PROPORTION, PAY_TYPE, TELEPHONE, "
                + "FAX, ADDRESS, EMAIL, "
                + "CERT_NO, REMARK, CREATE_TIME, "
                + "UPDATE_TIME, EFFECT_DATE, EXPIRE_DATE, "
                + "CERT_TYPE,BELONG_TYPE,BELONG_TO,IS_IB)"
                + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return this.jdbcTemplate.update(sql, 
                crmMediator.getMediatorNo(),
                crmMediator.getMediatorName(),
                crmMediator.getMediatorShortName(),
                crmMediator.getStatus(),
                crmMediator.getDepCode(),
                crmMediator.getDepName(),
                crmMediator.getAllocationProportion(),
                crmMediator.getPayType(),
                crmMediator.getTelephone(),
                crmMediator.getFax(),
                crmMediator.getAddress(),
                crmMediator.getEmail(),
                crmMediator.getCertNo(),
                crmMediator.getRemark(),
                crmMediator.getCreateTime(),
                crmMediator.getUpdateTime(),
                crmMediator.getEffectDate(),
                crmMediator.getExpireDate(),
                crmMediator.getCertType(),
                crmMediator.getBelongType(),
                crmMediator.getBelongTo(),
                crmMediator.getIsIb());
    }
    
    public int deleteByMediatorNo(String mediatorNo){
        
        String sql = "delete from T_CRM_MEDIATOR where MEDIATOR_NO = ?";
        return this.jdbcTemplate.update(sql, mediatorNo);
    }
    
    

}
