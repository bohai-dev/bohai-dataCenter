package com.bohai.dataCenter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bohai.dataCenter.entity.CrmMarketer;

@Component("crmMarketerDao")
public class CrmMarketerDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public int insert(CrmMarketer crmMarketer){
        String sql = "insert into T_CRM_MARKETER (MARKETER_NO, MARKETER_NAME, DEP_CODE, "
                + "DEP_NAME, PERSONNEL_DIVISION, STATUS, "
                + "ENTRY_DATE, LEAVE_DATE, ALLOCATION_PROPORTION, "
                + "MARKETER_LEVEL, CERT_NO, TELEPHONE, "
                + "ADDRESS, EMAIL, REMARK, "
                + "CREATE_TIME, UPDATE_TIME, CERT_TYPE, "
                + "BIRETHDAY) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return this.jdbcTemplate.update(sql, 
                crmMarketer.getMarketerNo(),
                crmMarketer.getMarketerName(),
                crmMarketer.getDepCode(),
                crmMarketer.getDepName(),
                crmMarketer.getPersonnelDivision(),
                crmMarketer.getStatus(),
                crmMarketer.getEntryDate(),
                crmMarketer.getLeaveDate(),
                crmMarketer.getAllocationProportion(),
                crmMarketer.getMarketerLevel(),
                crmMarketer.getCertNo(),
                crmMarketer.getTelephone(),
                crmMarketer.getAddress(),
                crmMarketer.getEmail(),
                crmMarketer.getRemark(),
                crmMarketer.getCreateTime(),
                crmMarketer.getUpdateTime(),
                crmMarketer.getCertType(),
                crmMarketer.getBirethday());
    }
    
    public int deleteByMarketerNo(String marketerNo){
        
        String sql = "delete from T_CRM_MARKETER where MARKETER_NO = ?";
        return this.jdbcTemplate.update(sql, marketerNo);
        
    }
    
    

}
