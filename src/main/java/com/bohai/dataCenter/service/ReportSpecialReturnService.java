package com.bohai.dataCenter.service;

import java.util.List;
import java.util.Map;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.ReportSpecialReturn;
import com.bohai.dataCenter.vo.QuerySpecialReturnReportParamVO;

/**
 * 交易所返佣特例
 * @author caojia
 *
 */
public interface ReportSpecialReturnService {

    /**
     * 保存或更新交易所返佣特例报表
     * @param reportSpecialReturn
     * @throws BohaiException
     */
    public void saveOrUpdate(ReportSpecialReturn reportSpecialReturn) throws BohaiException;
    
    /**
     * 查询交易所返还特例
     * @param paramVO
     * @return
     * @throws BohaiException
     */
    public List<ReportSpecialReturn> queryByCondition(QuerySpecialReturnReportParamVO paramVO) throws BohaiException;
    
    public List<Map<String, Object>> queryMarketerReturn(String month) throws BohaiException;
    
    public void removeByMonth(String month) throws BohaiException;
}
