package com.bohai.dataCenter.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.ReporteInvestorRebate;
import com.bohai.dataCenter.persistence.ReporteInvestorRebateMapper;
import com.bohai.dataCenter.service.ReportInvestorRebateService;

@Service("reportInvestorRebateService")
public class ReportInvestorRebateServiceImpl implements ReportInvestorRebateService {
    
    static Logger logger = Logger.getLogger(ReportInvestorRebateServiceImpl.class);
    
    @Autowired
    private ReporteInvestorRebateMapper reporteInvestorRebateMapper;

    @Override
    public void saveOrUpdate(ReporteInvestorRebate investorRebate) throws BohaiException {
        
        //统计年月
        String month = investorRebate.getMonth();
        
        //投资者账号
        String investorNo = investorRebate.getInvestorNo();
        
        

    }

}
