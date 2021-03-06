package com.bohai.dataCenter.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.ReportRebate;
import com.bohai.dataCenter.persistence.ReportRebateMapper;
import com.bohai.dataCenter.service.ReportRebateService;
import com.bohai.dataCenter.vo.QueryRebateDetailParamVO;
import com.bohai.dataCenter.vo.QueryRebateReportParamVO;

@Service("reportRebateService")
public class ReportRebateServiceImpl implements ReportRebateService {
	
	static Logger logger = Logger.getLogger(ReportRebateServiceImpl.class);
	
	@Autowired
	private ReportRebateMapper reportRebateMapper;

	@Override
	public void saveOrUpdate(ReportRebate reportRebate) throws BohaiException {

		//交易日
		String tradeDateStr = reportRebate.getTradeDateStr();
		//投资者编号
		String investorNo = reportRebate.getInvestorNo();
		
		ReportRebate report = null;
		
		try {
			report = this.reportRebateMapper.selectByTradeDateAndInvestorNo(tradeDateStr, investorNo);
		} catch (Exception e) {
			logger.error("查询投资者返利息明细失败:"+JSON.toJSONString(reportRebate),e);
			throw new BohaiException("", "查询投资者返利息明细失败");
		}
		
		if(report == null || StringUtils.isEmpty(report.getId())){
			//不存在则插入
			reportRebate.setCreateTime(new Date());
			
			try {
				this.reportRebateMapper.insertSelective(reportRebate);
			} catch (Exception e) {
				logger.error("保存投资者返利息明细失败："+JSON.toJSONString(reportRebate),e);
				throw new BohaiException("", "保存投资者返利息明细失败");
			}
		}else {
			
			reportRebate.setId(report.getId());
			reportRebate.setUpdateTime(new Date());
			reportRebate.setCreateTime(report.getCreateTime());
			
			try {
				this.reportRebateMapper.updateByPrimaryKey(reportRebate);
			} catch (Exception e) {
				logger.error("更新投资者返利息明细失败："+JSON.toJSONString(reportRebate),e);
				throw new BohaiException("", "更新投资者返利息明细失败");
			}
		}
	}
	
	@Override
	public List<ReportRebate> queryRebateDetail(QueryRebateDetailParamVO paramVO){
		
	    //return this.reportRebateMapper.selectAll();
		return this.reportRebateMapper.selectByCondition(paramVO);
	}

	@Override
	public List<Map<String, Object>> queryRebateReport(QueryRebateReportParamVO paramVO) {
		
		return this.reportRebateMapper.selectReport(paramVO);
	}

	@Override
	public List<Map<String, Object>> queryMarketRebateReport(QueryRebateReportParamVO paramVO) {
		
		return this.reportRebateMapper.selectMarketReport(paramVO);
	}
	
	public List<Map<String, Object>> queryMarketerRebateReport(QueryRebateReportParamVO paramVO) {
        
        return this.reportRebateMapper.selectMarketerReport(paramVO);
    }

    @Override
    public void removeByMonth(String month) throws BohaiException {
        
        try {
            this.reportRebateMapper.deleteByMonth(month);
        } catch (Exception e) {
            logger.error("删除返利息报表失败",e);
            throw new BohaiException("", "删除返利息报表失败");
        }
    }

}
