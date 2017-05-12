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
import com.bohai.dataCenter.vo.QueryCrmMediatorParamVO;

@Controller
public class CrmMediatorController {
	
	@Autowired
	private CrmMediatorMapper crmMediatorMapper;
	

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
}
