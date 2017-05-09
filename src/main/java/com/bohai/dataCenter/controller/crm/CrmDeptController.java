package com.bohai.dataCenter.controller.crm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CrmDeptController {

	//跳转到营业部信息维护页面
	@RequestMapping(value="toCrmDept")
	public String toCrmDept(){
		
		return "crm/crmDept";
	}
}
