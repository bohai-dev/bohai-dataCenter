package com.bohai.dataCenter.controller.crm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CrmCustomerController {

	//跳转到客户信息维护页面
	@RequestMapping(value="toCrmCustomer")
	public String toCrmCustomer(){
		
		return "crm/crmCustomer";
	}
}
