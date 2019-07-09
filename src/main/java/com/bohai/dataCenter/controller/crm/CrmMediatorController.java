package com.bohai.dataCenter.controller.crm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CrmMediator;
import com.bohai.dataCenter.persistence.CrmMediatorMapper;
import com.bohai.dataCenter.service.CrmMediatorService;
import com.bohai.dataCenter.vo.CrmMediatorAndCustomer;
import com.bohai.dataCenter.vo.QueryCrmMediatorParamVO;
import com.bohai.dataCenter.vo.QueryMediatorOverviewResultVO;

@Controller
public class CrmMediatorController {
    
    static Logger logger = Logger.getLogger(CrmMediatorController.class);
	
	@Autowired
	private CrmMediatorMapper crmMediatorMapper;
	
	@Autowired
	private CrmMediatorService crmMediatorService;
	

	//跳转到居间人信息维护页面
	@RequestMapping(value="toCrmMediator")
	@RequiresPermissions(value="crm:mediator:view")
	public String toCrmMediator(){
		
		return "crm/crmMediator";
	}
	
	@RequestMapping(value="queryCrmMediator")
	@ResponseBody
	public List<CrmMediator> queryCrmMediator(@RequestBody(required = false) QueryCrmMediatorParamVO paramVO) throws BohaiException{
		
		return this.crmMediatorMapper.selectByCondition(paramVO);
	}
	
	/**
	 * 保存居间人信息
	 * @param paramVO
	 * @throws BohaiException
	 */
	@RequestMapping(value="saveCrmMediator")
    @ResponseBody
	public void saveCrmMediator(@RequestBody(required = true) CrmMediator paramVO) throws BohaiException{
	    
	    this.crmMediatorMapper.insert(paramVO);
	}
	
	/**
	 * 居间人变更
	 * @param paramVO
	 * @throws BohaiException
	 */
	@RequestMapping(value="updateCrmMediator")
    @ResponseBody
    public void updateCrmMediator(@RequestBody(required = true) CrmMediator paramVO) throws BohaiException{
        
        this.crmMediatorService.modifyCrmMediator(paramVO);
    }
	
    @RequestMapping(value="removeCrmMediator")
    @ResponseBody
    public void removeCrmMediator(@RequestBody(required = true) CrmMediator paramVO) throws BohaiException{
        
        this.crmMediatorService.removeCrmMediator(paramVO);
    }
    
    @RequestMapping("generateMediatorNo")
    @ResponseBody
    public String generateMediatorNo(){
        
        return this.crmMediatorMapper.getMediatorNo();
    }
    
    /**
     * 导出居间人信息
     * @param paramVO
     * @param request
     * @param response
     * @throws BohaiException
     */
    @RequestMapping("exportCrmMediator")
    public void exportCrmMediator(QueryCrmMediatorParamVO paramVO, 
            HttpServletRequest request, HttpServletResponse response) throws BohaiException{
        
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet=wb.createSheet("居间人信息");
        
        String[] mediatorHead = {"所属营业部","居间人编号","居间人名称","归属类型","归属代码","归属名称","证件类型","证件号码","生效日期","失效日期","默认分配比例","地址","IB居间区分"};
        
        XSSFRow row = sheet.createRow(0);
        //初始化表头
        for (int i = 0 ;i < mediatorHead.length ; i++) {
            row.createCell(i).setCellValue(mediatorHead[i]);
            
            sheet.setColumnWidth(i, 256*15);
        }
        
        List<CrmMediator> list = this.crmMediatorMapper.selectByCondition(paramVO);
        if(list != null && list.size() > 0){
            
            for (int i = 0 ; i < list.size(); i++) {
                XSSFRow row2 = sheet.createRow(i+1);
                //所属营业部
                row2.createCell(0).setCellValue(list.get(i).getDepName());
                //居间人编号
                row2.createCell(1).setCellValue(list.get(i).getMediatorNo());
                //居间人名称
                row2.createCell(2).setCellValue(list.get(i).getMediatorName());
                
                String belongType = list.get(i).getBelongType();
                if(StringUtils.isEmpty(belongType)){
                    belongType = "无归属";
                }else if(belongType.equals("0")){
                    belongType = "营业部";
                }else if (belongType.equals("1")) {
                    belongType = "营销人员";
                }else if (belongType.equals("2")) {
                    belongType = "居间人";
                }
                //归属类型
                row2.createCell(3).setCellValue(belongType);
                //归属代码
                row2.createCell(4).setCellValue(list.get(i).getBelongTo());
                //归属名称
                row2.createCell(5).setCellValue(list.get(i).getBelongToName());
                
                String certType = list.get(i).getCertType();
                if(!StringUtils.isEmpty(certType)){
                    if(certType.equals("0")){
                        certType = "身份证";
                    }
                }
                //证件类型
                row2.createCell(6).setCellValue(certType);
                //证件号码
                row2.createCell(7).setCellValue(list.get(i).getCertNo());
                //生效日期
                row2.createCell(8).setCellValue(list.get(i).getEffectDate());
                //失效日期
                row2.createCell(9).setCellValue(list.get(i).getExpireDate());
                //默认分配比例
                row2.createCell(10).setCellValue(list.get(i).getAllocationProportion());
                //联系电话
                row2.createCell(11).setCellValue(list.get(i).getAddress());
                
                //IB居间区分
                String isIb=list.get(i).getIsIb();
                if(StringUtils.isEmpty(isIb)){
                	isIb = "";
                }else if(isIb.equals("0")){
                	isIb = "否";
                }else if (isIb.equals("1")) {
                	isIb = "是";
                }
                row2.createCell(12).setCellValue(isIb);
                
            }
        }
        
        
        XSSFSheet sheet1 = wb.createSheet("居间人与客户关系");
        String[] mediatorInvestorHead = {"居间人编号","居间人姓名","投资者编号","投资者名称","生效日期","失效日期","备注"};
        XSSFRow row1 = sheet1.createRow(0);
        //初始化表头
        for (int i = 0 ;i < mediatorInvestorHead.length ; i++) {
            row1.createCell(i).setCellValue(mediatorInvestorHead[i]);
            
            sheet1.setColumnWidth(i, 256*15);
        }
        
        //TODO 根据条件查询居间人与客户关系T_CRM_MEDIATOR和T_CRM_CUSTOMER关联关系
        List<CrmMediatorAndCustomer> mediatorlist = this.crmMediatorMapper.selectMediatorCustomerRelation(paramVO);
		if (mediatorlist != null && mediatorlist.size() > 0) {

			for (int i = 0; i < mediatorlist.size(); i++) {
				XSSFRow row2 = sheet1.createRow(i + 1);
				// 居间人编号
				row2.createCell(0).setCellValue(mediatorlist.get(i).getMediatorNo());
				// 居间人名称
				row2.createCell(1).setCellValue(mediatorlist.get(i).getMediatorName());
				// 投资者编号
				row2.createCell(2).setCellValue(mediatorlist.get(i).getInvestorNo());
				// 投资者名称
				row2.createCell(3).setCellValue(mediatorlist.get(i).getInvestorName());
				// 生效日期
				row2.createCell(4).setCellValue(mediatorlist.get(i).getEffectDate());
				// 失效日期
				row2.createCell(5).setCellValue(mediatorlist.get(i).getExpireDate());
				// 备注
				row2.createCell(6).setCellValue(mediatorlist.get(i).getRemark());
			}
		}
        
        try {
            OutputStream output=response.getOutputStream();
            response.reset();
            response.setContentType("application/x-xls");  
            response.setCharacterEncoding("UTF-8");  
            String FileName = new String("居间人信息".getBytes("UTF-8"),"ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+FileName+".xlsx");
            wb.write(output);  
            output.close(); 
        } catch (IOException e) {
            logger.error("导出居间人信息失败",e);
            throw new BohaiException("", "导出居间人信息失败");
        }
    }
	
    @RequestMapping(value="queryMediatorOverview")
    @ResponseBody
    public QueryMediatorOverviewResultVO queryMediatorOverview(@RequestBody QueryCrmMediatorParamVO paramVO) throws BohaiException{
        
        return this.crmMediatorService.queryMediatorOverview(paramVO);
        
    }
    
    @RequestMapping(value="publicity/{mediatorNo}")
    @ResponseBody
    public void publicity(@PathVariable String mediatorNo) throws BohaiException{
        
        this.crmMediatorService.publicityMediator(mediatorNo);
    }
    
}
