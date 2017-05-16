package com.bohai.dataCenter.controller.crm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CrmMediator;
import com.bohai.dataCenter.persistence.CrmMediatorMapper;
import com.bohai.dataCenter.service.CrmMediatorService;
import com.bohai.dataCenter.vo.QueryCrmMediatorParamVO;

@Controller
public class CrmMediatorController {
	
	@Autowired
	private CrmMediatorMapper crmMediatorMapper;
	
	@Autowired
	private CrmMediatorService crmMediatorService;
	

	//跳转到居间人信息维护页面
	@RequestMapping(value="toCrmMediator")
	public String toCrmMediator(){
		
		return "crm/crmMediator";
	}
	
	@RequestMapping(value="queryCrmMediator")
	@ResponseBody
	public List<CrmMediator> queryCrmMediator(@RequestBody(required = false) QueryCrmMediatorParamVO paramVO) throws BohaiException{
		
		return this.crmMediatorMapper.selectByCondition(paramVO);
	}
	
	/**
	 * 保存居间人信息
	 * @param paramVO
	 * @throws BohaiException
	 */
	@RequestMapping(value="saveCrmMediator")
    @ResponseBody
	public void saveCrmMediator(@RequestBody(required = true) CrmMediator paramVO) throws BohaiException{
	    
	    this.crmMediatorMapper.insert(paramVO);
	}
	
	/**
	 * 居间人变更
	 * @param paramVO
	 * @throws BohaiException
	 */
	@RequestMapping(value="updateCrmMediator")
    @ResponseBody
    public void updateCrmMediator(@RequestBody(required = true) CrmMediator paramVO) throws BohaiException{
        
        this.crmMediatorService.modifyCrmMediator(paramVO);
    }
	
    @RequestMapping(value="removeCrmMediator")
    @ResponseBody
    public void removeCrmMediator(@RequestBody(required = true) CrmMediator paramVO) throws BohaiException{
        
        this.crmMediatorService.removeCrmMediator(paramVO);
    }
    
    @RequestMapping("generateMediatorNo")
    @ResponseBody
    public String generateMediatorNo(){
        
        return this.crmMediatorMapper.getMediatorNo();
    }
	
	
}
