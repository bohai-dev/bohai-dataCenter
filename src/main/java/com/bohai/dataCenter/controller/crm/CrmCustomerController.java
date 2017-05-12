package com.bohai.dataCenter.controller.crm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.dataCenter.entity.CrmCustomer;
import com.bohai.dataCenter.persistence.CrmCustomerMapper;
import com.bohai.dataCenter.vo.QueryCrmCustomerParamVO;

@Controller
public class CrmCustomerController {
	
	@Autowired
	private CrmCustomerMapper crmCustomerMapper;

	//跳转到客户信息维护页面
	@RequestMapping(value="toCrmCustomer")
	public String toCrmCustomer(){
		
		return "crm/crmCustomer";
	}
	
	@RequestMapping(value="queryCrmCustomer")
	@ResponseBody
	public List<CrmCustomer> queryCrmCustomer(@RequestBody(required = false) QueryCrmCustomerParamVO paramVO){
		
		return this.crmCustomerMapper.selectByCondition(paramVO);
		
	}
}
