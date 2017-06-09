package com.bohai.dataCenter.controller.crm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    
    static Logger logger = Logger.getLogger(CrmMarketerController.class);
	
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
	
	/**
	 * 请求生成营销人员编号
	 * @return
	 */
    @RequestMapping("generateMarketerNo")
    @ResponseBody
    public String generateMarketerNo(){
        
        return this.crmMarketerMapper.getMarketerNo();
    }
    
    /**
     * 导出营销人员信息
     * @param paramVO
     * @param request
     * @param response
     * @throws BohaiException
     */
    @RequestMapping("exportCrmMarketer")
    public void exportCrmMarketer(QueryCrmMarketerParamVO paramVO, 
            HttpServletRequest request, HttpServletResponse response) throws BohaiException{
        
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet marketerSheet=wb.createSheet("营销人员信息");
        String[] marketerHead = {"行号","营销人员编号","营销人员名称","所属营业部代码","所属营业部","在职状态","入职日期","离职日期","身份证号","联系电话","联系地址","邮箱","备注"};
        //TODO  根据条件查询T_CRM_MARKETER
        
        
        XSSFSheet marketerMediatorSheet = wb.createSheet("营销人员与居间人关系");
        String[] marketerMediatorHead = {"行号","营销人员编号","营销人员名称","居间人编号","居间人名称","备注"};
        //TODO 根据条件查询T_CRM_MARKETER和T_CRM_MEDIATOR关联关系
        
        XSSFSheet marketerInvestorSheet = wb.createSheet("营销人员与客户关系");
        String[] marketerInvestorHead = {"行号","营销人员编号","营销人员名称","投资者编号","投资者名称","备注"};
        //TODO 根据条件查询T_CRM_MARKETER和T_CRM_CUSTOMER关联关系
        
        
        
        try {
            OutputStream output=response.getOutputStream();
            response.reset();
            response.setContentType("application/x-xls");  
            response.setCharacterEncoding("UTF-8");  
            String FileName = new String("营销人员信息".getBytes("UTF-8"),"ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+FileName+".xlsx");
            wb.write(output);  
            output.close(); 
        } catch (IOException e) {
            logger.error("导出营销人员信息失败",e);
            throw new BohaiException("", "导出营销人员信息失败");
        }
    }
}
