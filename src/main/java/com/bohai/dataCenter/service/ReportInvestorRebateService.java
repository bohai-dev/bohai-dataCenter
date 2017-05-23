package com.bohai.dataCenter.service;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.ReporteInvestorRebate;

/**
 * 交易所返还到客户
 * @author caojia
 *
 */
public interface ReportInvestorRebateService {

    /**
     * 保存或更新
     * @param investorRebate
     * @throws BohaiException
     */
    public void saveOrUpdate(ReporteInvestorRebate investorRebate) throws BohaiException;
    
    /**
     * 更新客户大连交易所手续费
     * @param investorRebate
     * @throws BohaiException
     */
    public void updateRebate(ReporteInvestorRebate investorRebate) throws BohaiException;
    
    /**
     * 根据月份删除统计数据
     * @param month
     * @throws BohaiException
     */
    public void removeByMonth(String month) throws BohaiException;
}
