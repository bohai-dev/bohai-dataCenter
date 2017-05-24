package com.bohai.dataCenter.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.ReportSpecialReturn;
import com.bohai.dataCenter.persistence.ReportSpecialReturnMapper;
import com.bohai.dataCenter.service.ReportSpecialReturnService;
import com.bohai.dataCenter.vo.QuerySpecialReturnReportParamVO;

@Service("reportSpecialReturnService")
public class ReportSpecialReturnServiceImpl implements ReportSpecialReturnService {
    
    static Logger logger = Logger.getLogger(ReportSpecialReturnServiceImpl.class);
    
    @Autowired
    private ReportSpecialReturnMapper reportSpecialReturnMapper;

    @Override
    public void saveOrUpdate(ReportSpecialReturn reportSpecialReturn) throws BohaiException {
        
        try {
            int count = this.reportSpecialReturnMapper.updateByPrimaryKey(reportSpecialReturn);
            
            if(count < 1){
                
                this.reportSpecialReturnMapper.insertSelective(reportSpecialReturn);
            }
        } catch (Exception e) {
            logger.error("保存或更新交易所返佣特例失败",e);
            throw new BohaiException("", "保存或更新交易所返佣特例失败");
        }
        
    }

    @Override
    public List<ReportSpecialReturn> queryByCondition(QuerySpecialReturnReportParamVO paramVO) throws BohaiException {

        return this.reportSpecialReturnMapper.selectByCondition(paramVO);
    }

    @Override
    public List<Map<String, Object>> queryMarketerReturn(String month) throws BohaiException {
        return this.reportSpecialReturnMapper.selectMarketerReturn(month);
    }

}
