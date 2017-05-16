package com.bohai.dataCenter.controller.crm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CrmMarketer;
import com.bohai.dataCenter.persistence.CrmMarketerMapper;
import com.bohai.dataCenter.service.CrmMarketerService;
import com.bohai.dataCenter.vo.QueryCrmMarketerParamVO;

@Controller
public class CrmMarketerController {
	
	@Autowired
	private CrmMarketerMapper crmMarketerMapper;
	
	@Autowired
	private CrmMarketerService crmMarketerService;

	//跳转到营销人员信息维护页面
	@RequestMapping(value="toCrmMarketer")
	public String toCrmMarketer(){
		
		return "crm/crmMarketer";
	}
	
	@RequestMapping(value="queryCrmMarketer")
	@ResponseBody
	public List<CrmMarketer> queryCrmMarketer(@RequestBody(required = false) QueryCrmMarketerParamVO paramVO){
		
		return crmMarketerMapper.selectByCondition(paramVO);
	}
	
	/**
	 * 保存营销人员信息
	 * @param paramVO
	 */
	@RequestMapping(value="saveCrmMarketer")
	@ResponseBody
	public void saveCrmMarketer(@RequestBody(required = false) CrmMarketer paramVO){
	    
	    this.crmMarketerMapper.insertSelective(paramVO);
	}
	
	/**
	 * 更新营销人员信息
	 * @param paramVO
	 * @throws BohaiException 
	 */
	@RequestMapping(value="updateCrmMarketer")
    @ResponseBody
    public void updateCrmMarketer(@RequestBody(required = false) CrmMarketer paramVO) throws BohaiException{
        
        this.crmMarketerService.modifyCrmMarketer(paramVO);
    }
	
	/**
	 * 删除营销人员
	 * @param paramVO
	 */
	@RequestMapping(value="removeCrmMarketer")
	@ResponseBody
	public void removeCrmMarketer(@RequestBody(required = false) CrmMarketer paramVO){
	    //TODO
	    this.crmMarketerMapper.deleteByPrimaryKey(paramVO.getMarketerNo());
	}
	
    @RequestMapping("generateMarketerNo")
    @ResponseBody
    public String generateMarketerNo(){
        
        return this.crmMarketerMapper.getMarketerNo();
    }
}
