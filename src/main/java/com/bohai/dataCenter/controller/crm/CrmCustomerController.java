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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CrmCustomer;
import com.bohai.dataCenter.persistence.CrmCustomerMapper;
import com.bohai.dataCenter.persistence.CrmMarketerMapper;
import com.bohai.dataCenter.persistence.CrmMediatorMapper;
import com.bohai.dataCenter.service.CrmCustomerService;
import com.bohai.dataCenter.vo.QueryCrmCustomerParamVO;
import com.bohai.dataCenter.vo.QueryInvestorOverviewParamVO;
import com.bohai.dataCenter.vo.QueryInvestorOverviewResultVO;
import com.bohai.dataCenter.vo.TableJsonResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
public class CrmCustomerController {
	
	@Autowired
	private CrmCustomerMapper crmCustomerMapper;
	
	@Autowired
	private CrmCustomerService crmCustomerService;
	
    static Logger logger = Logger.getLogger(CrmMarketerController.class);

	//跳转到客户信息维护页面
	@RequestMapping(value="toCrmCustomer")
	@RequiresPermissions(value="crm:customer:view")
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
	
    /**
     * 导出客户信息
     * @param paramVO
     * @param request
     * @param response
     * @throws BohaiException
     */
    @RequestMapping("exportCustomer")
    public void exportCustomer(QueryCrmCustomerParamVO paramVO, 
            HttpServletRequest request, HttpServletResponse response) throws BohaiException{
        
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet=wb.createSheet("客户信息");
        
        String[] customerHead = {"所属营业部","投资者编号","投资者姓名","归属类型","归属代码","归属名称","开户日期","销户日期","证件类型","证件号码","手机","备注"};
        
        XSSFRow row = sheet.createRow(0);
        //初始化表头
        for (int i = 0 ;i < customerHead.length ; i++) {
            row.createCell(i).setCellValue(customerHead[i]);
            
            sheet.setColumnWidth(i, 256*15);
        }
        
        List<CrmCustomer> list = this.crmCustomerMapper.selectByCondition(paramVO);
        if(list != null && list.size() > 0){
            
            for (int i = 0 ; i < list.size(); i++) {
                XSSFRow row2 = sheet.createRow(i+1);
                //所属营业部
                row2.createCell(0).setCellValue(list.get(i).getDeptName());
                //投资者编号
                row2.createCell(1).setCellValue(list.get(i).getInvestorNo());
                //投资者姓名
                row2.createCell(2).setCellValue(list.get(i).getInvestorName());
                
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

                //开户日期
                row2.createCell(6).setCellValue(list.get(i).getOpenDate());
                //销户日期
                row2.createCell(7).setCellValue(list.get(i).getCancelDate());
                
                String certType = list.get(i).getCertType();
                if(!StringUtils.isEmpty(certType)){
                    if(certType.equals("0")){
                        certType = "身份证";
                    }
                }
                //证件类型
                row2.createCell(8).setCellValue(certType);
                //证件号码
                row2.createCell(9).setCellValue(list.get(i).getCertNo());
                //联系电话
                row2.createCell(10).setCellValue(list.get(i).getTelephone());
                //备注
                row2.createCell(11).setCellValue(list.get(i).getRemark());
            }
        }
        
        try {
            OutputStream output=response.getOutputStream();
            response.reset();
            response.setContentType("application/x-xls");  
            response.setCharacterEncoding("UTF-8");  
            String FileName = new String("客户信息".getBytes("UTF-8"),"ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+FileName+".xlsx");
            wb.write(output);  
            output.close(); 
        } catch (IOException e) {
            logger.error("导出客户信息失败",e);
            throw new BohaiException("", "导出客户信息失败");
        }
    }
    
    /**
     * 查询客户简介
     * @param paramVO
     * @return
     * @throws BohaiException 
     */
    @RequestMapping(value="queryInvestorOverview")
    @ResponseBody
    public QueryInvestorOverviewResultVO queryInvestorOverview(@RequestBody QueryCrmCustomerParamVO paramVO) throws BohaiException{
        
        return this.crmCustomerService.queryInvestorOverview(paramVO);
        
    }
}
