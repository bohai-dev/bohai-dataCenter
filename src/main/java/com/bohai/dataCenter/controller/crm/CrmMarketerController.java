package com.bohai.dataCenter.controller.crm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.dataCenter.entity.CrmMarketer;
import com.bohai.dataCenter.persistence.CrmMarketerMapper;
import com.bohai.dataCenter.vo.QueryCrmMarketerParamVO;

@Controller
public class CrmMarketerController {
	
	@Autowired
	private CrmMarketerMapper crmMarketerMapper;

	//跳转到营销人员信息维护页面
	@RequestMapping(value="toCrmMarketer")
	public String toCrmMarketer(){
		
		return "crm/crmMarketer";
	}
	
	@RequestMapping(value="queryCrmMarketer")
	@ResponseBody
	public List<CrmMarketer> queryCrmMarketer(QueryCrmMarketerParamVO paramVO){
		
		return crmMarketerMapper.selectByCondition(paramVO);
	}
}
