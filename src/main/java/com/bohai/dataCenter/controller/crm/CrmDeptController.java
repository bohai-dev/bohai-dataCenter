package com.bohai.dataCenter.controller.crm;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
	@RequiresPermissions(value="crm:dept:view")
	public String toCrmDept(){
		
		return "crm/crmDept";
	}
	
	@RequestMapping(value="queryCrmDept")
	@ResponseBody
	public List<CrmDept> queryCrmDept(QueryCrmDeptParamVO paramVO){
		
		return this.crmDeptMapper.selectByCondition(paramVO);
	}
	
	@RequestMapping(value="saveCrmDept")
	@ResponseBody
	public void saveCrmDept(@RequestBody(required=false) CrmDept paramVO){
	    
        this.crmDeptMapper.insertSelective(paramVO);
    }
	
	@RequestMapping(value="updateCrmDept")
	@ResponseBody
	public void updateCrmDept(@RequestBody(required=false) CrmDept paramVO){
	    this.crmDeptMapper.updateByPrimaryKey(paramVO);
	}
	
	@RequestMapping(value="removeCrmDept")
	@ResponseBody
	public void removeCrmDept(@RequestBody(required=false) CrmDept paramVO){
	    
	    this.crmDeptMapper.deleteByPrimaryKey(paramVO.getDeptCode());
	}
	
	@RequestMapping(value="generateDepNo")
    @ResponseBody
	public String generateDepNo(){
	    return this.crmDeptMapper.getDepNo();
	}
}
