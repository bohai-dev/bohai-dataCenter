package com.bohai.dataCenter.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.dataCenter.entity.ReportMediator;
import com.bohai.dataCenter.persistence.ReportMediatorMapper;
import com.bohai.dataCenter.vo.QueryMediatorReportParamVO;
import com.bohai.dataCenter.vo.TableJsonResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
public class MediatorController {
    
    static Logger logger = Logger.getLogger(MediatorController.class);
    
    @Autowired
    private ReportMediatorMapper reportMediatorMapper;

    
    @RequestMapping(value="toMediatorReport")
    public String toMediatorReport(){
        
        return "report/mediator";
        
    }
    
    /**
     * 查询居间人提成
     * @param paramVO
     * @return
     */
    @RequestMapping(value="queryMediatorReport")
    @ResponseBody
    public TableJsonResponse<ReportMediator> queryMediatorReport(@RequestBody(required = false)
        QueryMediatorReportParamVO paramVO){
        
        PageHelper.startPage(paramVO.getPageNumber(), paramVO.getPageSize());
        
        List<ReportMediator> list = reportMediatorMapper.queryByCondition(paramVO);
        
        
        Page<ReportMediator> page = (Page)list;
        TableJsonResponse<ReportMediator> response = new TableJsonResponse<ReportMediator>();
        response.setTotal(page.getTotal());
        response.setRows(list);
        return response;
    }
    
    /**
     * 修改居间人提成
     * @param mediator
     */
    @RequestMapping(value="modifyMediatorReport")
    @ResponseBody
    public void modifyMediatorReport(@RequestBody ReportMediator mediator){
        
        mediator.setUpdateTime(new Date());
        this.reportMediatorMapper.updateByMediatorAndMonth(mediator);
        
    }
    
    
}
