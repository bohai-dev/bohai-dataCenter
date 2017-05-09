package com.bohai.dataCenter.controller.crm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CrmMarketerController {

	//跳转到营销人员信息维护页面
	@RequestMapping(value="toCrmMarketer")
	public String toCrmMarketer(){
		
		return "crm/crmMarketer";
	}
}
