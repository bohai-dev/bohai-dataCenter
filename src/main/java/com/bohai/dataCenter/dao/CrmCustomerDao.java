package com.bohai.dataCenter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bohai.dataCenter.entity.CrmCustomer;

@Component("crmCustomerDao")
public class CrmCustomerDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public int insert(CrmCustomer crmCustomer){
        
        String sql = "insert into T_CRM_CUSTOMER (INVESTOR_NO, INVESTOR_NAME, FULL_NAME,"
                + "STATUS, DEPT_CODE, OPEN_DATE, "
                + "CANCEL_DATE, OPEN_OPERATOR, CANCEL_OPERATOR, "
                + "CERT_TYPE, CERT_NO, POSTCODE, "
                + "ADDRESS, TELEPHONE, MOBILE_PHONE, "
                + "EMAIL, CONTRACT_NO, CUST_TYPE, "
                + "REMARK, EFFECT_DATE, EXPIRE_DATE, "
                + "CREATE_TIME, UPDATE_TIME, BELONG_TYPE, "
                + "BELONG_TO) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        
        return this.jdbcTemplate.update(sql, 
                crmCustomer.getInvestorNo(),
                crmCustomer.getInvestorName(),
                crmCustomer.getFullName(),
                crmCustomer.getStatus(),
                crmCustomer.getDeptCode(),
                crmCustomer.getOpenDate(),
                crmCustomer.getCancelDate(),
                crmCustomer.getOpenOperator(),
                crmCustomer.getCancelOperator(),
                crmCustomer.getCertType(),
                crmCustomer.getCertNo(),
                crmCustomer.getPostcode(),
                crmCustomer.getAddress(),
                crmCustomer.getTelephone(),
                crmCustomer.getMobilePhone(),
                crmCustomer.getEmail(),
                crmCustomer.getContractNo(),
                crmCustomer.getCustType(),
                crmCustomer.getRemark(),
                crmCustomer.getEffectDate(),
                crmCustomer.getExpireDate(),
                crmCustomer.getCreateTime(),
                crmCustomer.getUpdateTime(),
                crmCustomer.getBelongType(),
                crmCustomer.getBelongTo());
    }
    
    
    public int deleteByCustomerNo(String customerNo){
        
        String sql = "delete from T_CRM_CUSTOMER"
                + " where INVESTOR_NO = ?";
        return this.jdbcTemplate.update(sql, customerNo);
    }
    
    

}
