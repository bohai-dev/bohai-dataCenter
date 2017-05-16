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
import com.bohai.dataCenter.vo.TableJsonResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

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
	public TableJsonResponse<CrmCustomer> queryCrmCustomer(@RequestBody(required = false) QueryCrmCustomerParamVO paramVO){
	    
	    
	    PageHelper.startPage(paramVO.getPageNumber(), paramVO.getPageSize());
		List<CrmCustomer> list = this.crmCustomerMapper.selectByCondition(paramVO);
		
		Page<CrmCustomer> page = (Page)list;
		TableJsonResponse<CrmCustomer> response = new TableJsonResponse<CrmCustomer>();
		response.setTotal(page.getTotal());
		response.setRows(list);
		
		return response;
	}
	
	/**
	 * 保存CRM客户信息
	 * @param paramVO
	 */
	@RequestMapping(value="saveCrmCustomer")
	@ResponseBody
	public void saveCrmCustomer(@RequestBody(required = false) CrmCustomer paramVO){
		
	    this.crmCustomerMapper.insert(paramVO);
		
	}
	
	/**
	 * 更新CRM客户信息
	 * @param paramVO
	 */
	@RequestMapping(value="updateCrmCustomer")
	@ResponseBody
	public void updateCrmCustomer(@RequestBody(required = false) CrmCustomer paramVO){
		this.crmCustomerMapper.updateByPrimaryKey(paramVO);
	}
	
	@RequestMapping(value="removeCrmCustomer")
	@ResponseBody
    public void removeCrmCustomer(@RequestBody(required = false) CrmCustomer paramVO){
        this.crmCustomerMapper.deleteByPrimaryKey(paramVO.getInvestorNo());
    }
}
