package com.bohai.dataCenter.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        
        
        try {
            investorRebate.setUpdateTime(new Date());
            int updateCount = reporteInvestorRebateMapper.updateSelective(investorRebate);
            if(updateCount <1){
                //不存在则插入
                investorRebate.setCreateTime(new Date());
                investorRebate.setUpdateTime(null);
                reporteInvestorRebateMapper.insert(investorRebate);
            }else {
                logger.debug("更新投资者交易所返还成功！");
            }
        } catch (Exception e) {
            logger.error("更新或保存交易所返还到投资者失败",e);
            throw new BohaiException("", "更新或保存交易所返还到投资者失败");
        }
        
    }

    @Override
    public void updateRebate(ReporteInvestorRebate investorRebate) throws BohaiException {

        String tradeMonth = investorRebate.getMonth();
        
        String investorNo = investorRebate.getInvestorNo();
        
        try {
            ReporteInvestorRebate rebate = this.reporteInvestorRebateMapper.selectByMonthAndInvestorNo(tradeMonth, investorNo);
            
            if(rebate == null){
                //不存在则插入
                investorRebate.setCreateTime(new Date());
                this.reporteInvestorRebateMapper.insertSelective(investorRebate);
            }else {
                //累加手续费
                String originalRebate = rebate.getDrebate();
                BigDecimal orgRebate = new BigDecimal("0");
                if(!StringUtils.isEmpty(originalRebate)){
                    orgRebate = new BigDecimal(originalRebate);
                }
                
                BigDecimal newRebate = orgRebate.add(new BigDecimal(investorRebate.getDrebate()));
                investorRebate.setUpdateTime(new Date());
                investorRebate.setDrebate(newRebate.toString());
                this.reporteInvestorRebateMapper.updateSelective(investorRebate);
            }
        } catch (Exception e) {
            logger.error("更新手续费失败",e);
            throw new BohaiException("", "更新手续费失败");
        }
        
        
        
    }

    @Override
    public void removeByMonth(String month) throws BohaiException {

        this.reporteInvestorRebateMapper.deleteByMonth(month);
        
    }
    
    

}
