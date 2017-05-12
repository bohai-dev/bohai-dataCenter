package com.bohai.dataCenter.controller.crm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.dataCenter.entity.CrmDept;
import com.bohai.dataCenter.persistence.CrmDeptMapper;
import com.bohai.dataCenter.vo.QueryCrmDeptParamVO;

@Controller
public class CrmDeptController {
	
	@Autowired
	private CrmDeptMapper crmDeptMapper;

	//跳转到营业部信息维护页面
	@RequestMapping(value="toCrmDept")
	public String toCrmDept(){
		
		return "crm/crmDept";
	}
	
	@RequestMapping(value="queryCrmDept")
	@ResponseBody
	public List<CrmDept> queryCrmDept(QueryCrmDeptParamVO paramVO){
		
		return this.crmDeptMapper.selectByCondition(paramVO);
	}
}
