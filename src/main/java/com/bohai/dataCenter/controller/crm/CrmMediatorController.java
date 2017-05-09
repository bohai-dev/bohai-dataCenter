package com.bohai.dataCenter.controller.crm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CrmMediatorController {

	//跳转到居间人信息维护页面
	@RequestMapping(value="toCrmMediator")
	public String toCrmMediator(){
		
		return "crm/crmMediator";
	}
}
