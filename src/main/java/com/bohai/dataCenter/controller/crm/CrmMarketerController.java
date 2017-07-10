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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CrmMarketer;
import com.bohai.dataCenter.persistence.CrmMarketerMapper;
import com.bohai.dataCenter.service.CrmMarketerService;
import com.bohai.dataCenter.vo.CrmMarketerAndCustomer;
import com.bohai.dataCenter.vo.CrmMarketerAndMediator;
import com.bohai.dataCenter.vo.QueryCrmMarketerParamVO;
import com.bohai.dataCenter.vo.QueryMarketerOverviewResultVO;

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
     * @throws BohaiException 
     */
    @RequestMapping(value="removeCrmMarketer")
    @ResponseBody
    public void removeCrmMarketer(@RequestBody(required = false) CrmMarketer paramVO) throws BohaiException{
        
        this.crmMarketerService.removeCrmMarketer(paramVO);
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
        XSSFRow row = marketerSheet.createRow(0);
        //初始化表头
        for (int i = 0 ;i < marketerHead.length ; i++) {
            row.createCell(i).setCellValue(marketerHead[i]);
            
            marketerSheet.setColumnWidth(i, 256*15);
        }
        //TODO  根据条件查询T_CRM_MARKETER
        List<CrmMarketer> marketerList= crmMarketerMapper.selectByCondition(paramVO);
        if(marketerList != null && marketerList.size() > 0){
            
            
            for (int i = 0 ; i < marketerList.size(); i++) {
                
                XSSFRow row2 = marketerSheet.createRow(i+1);
                //行号
                row2.createCell(0).setCellValue(String.valueOf(i+1));
                //营销人员编号
                row2.createCell(1).setCellValue(marketerList.get(i).getMarketerNo());
                //营销人员名称
                row2.createCell(2).setCellValue(marketerList.get(i).getMarketerName());
                //所属营业部代码
                row2.createCell(3).setCellValue(marketerList.get(i).getDepCode());
                //所属营业部
                row2.createCell(4).setCellValue(marketerList.get(i).getDepName());
                //在职状态
                if (("1").equals(marketerList.get(i).getStatus())) {
                    row2.createCell(5).setCellValue("在职");
                } else {
                    row2.createCell(5).setCellValue("离职");
                }
                //入职日期
                row2.createCell(6).setCellValue(marketerList.get(i).getEntryDate());
                //离职日期
                row2.createCell(7).setCellValue(marketerList.get(i).getLeaveDate());
                //身份证号
                row2.createCell(8).setCellValue(marketerList.get(i).getCertNo());
                //联系电话
                row2.createCell(9).setCellValue(marketerList.get(i).getTelephone());
                //联系地址
                row2.createCell(10).setCellValue(marketerList.get(i).getAddress());
                //邮箱
                row2.createCell(11).setCellValue(marketerList.get(i).getEmail());
                //备注
                row2.createCell(12).setCellValue(marketerList.get(i).getRemark());
            }
        }
        
        XSSFSheet marketerMediatorSheet = wb.createSheet("营销人员与居间人关系");
        String[] marketerMediatorHead = {"行号","营销人员编号","营销人员名称","居间人编号","居间人名称","备注"};
        //TODO 根据条件查询T_CRM_MARKETER和T_CRM_MEDIATOR关联关系
        XSSFRow row3 = marketerMediatorSheet.createRow(0);
        //初始化表头
        for (int i = 0 ;i < marketerMediatorHead.length ; i++) {
            row3.createCell(i).setCellValue(marketerMediatorHead[i]);   
            marketerMediatorSheet.setColumnWidth(i, 256*15);
        }
        List<CrmMarketerAndMediator> marketerAndMediatorList= crmMarketerMapper.selectMaketerRelation(paramVO);
        if(marketerAndMediatorList != null && marketerAndMediatorList.size() > 0){
            for (int i = 0 ; i < marketerAndMediatorList.size(); i++) {
                
                XSSFRow row2 = marketerMediatorSheet.createRow(i+1);
                //行号
                row2.createCell(0).setCellValue(String.valueOf(i+1));
                //营销人员编号
                row2.createCell(1).setCellValue(marketerAndMediatorList.get(i).getMarketerNo());
                //营销人员名称
                row2.createCell(2).setCellValue(marketerAndMediatorList.get(i).getMarketerName());
                //居间人编号
                row2.createCell(3).setCellValue(marketerAndMediatorList.get(i).getMediatorNo());
                //居间人名称
                row2.createCell(4).setCellValue(marketerAndMediatorList.get(i).getMediatorName());
                //备注
                row2.createCell(5).setCellValue(marketerAndMediatorList.get(i).getRemark());

            }
        }
        
        XSSFSheet marketerInvestorSheet = wb.createSheet("营销人员与客户关系");
        String[] marketerInvestorHead = {"行号","营销人员编号","营销人员名称","投资者编号","投资者名称","备注"};
        //TODO 根据条件查询T_CRM_MARKETER和T_CRM_CUSTOMER关联关系
        XSSFRow row4 = marketerInvestorSheet.createRow(0);
        //初始化表头
        for (int i = 0 ;i < marketerInvestorHead.length ; i++) {
            row4.createCell(i).setCellValue(marketerInvestorHead[i]);   
            marketerInvestorSheet.setColumnWidth(i, 256*15);
        }
        List<CrmMarketerAndCustomer> marketerAndCustomerList = crmMarketerMapper.selectMaketerCustomerRelation(paramVO);
           if(marketerAndCustomerList != null && marketerAndCustomerList.size() > 0){
                for (int i = 0 ; i < marketerAndCustomerList.size(); i++) {
                    
                    XSSFRow row2 = marketerInvestorSheet.createRow(i+1);
                    //行号
                    row2.createCell(0).setCellValue(String.valueOf(i+1));
                    //营销人员编号
                    row2.createCell(1).setCellValue(marketerAndCustomerList.get(i).getMarketerNo());
                    //营销人员名称
                    row2.createCell(2).setCellValue(marketerAndCustomerList.get(i).getMarketerName());
                    //投资者编号
                    row2.createCell(3).setCellValue(marketerAndCustomerList.get(i).getInvestorNo());
                    //投资者名称
                    row2.createCell(4).setCellValue(marketerAndCustomerList.get(i).getInvestorName());
                    //备注
                    row2.createCell(5).setCellValue(marketerAndCustomerList.get(i).getRemark());

                }
            }
        
        try {
            OutputStream output = response.getOutputStream();
            response.reset();
            response.setContentType("application/x-xls");
            response.setCharacterEncoding("UTF-8");
            String FileName = new String("营销人员信息".getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + FileName + ".xlsx");
            wb.write(output);
            output.close();
        } catch (IOException e) {
            logger.error("导出营销人员信息失败", e);
            throw new BohaiException("", "导出营销人员信息失败");
        }
    }
    
    @RequestMapping(value="queryMarketerOverview")
    @ResponseBody
    public QueryMarketerOverviewResultVO queryMarketerOverview(@RequestBody QueryCrmMarketerParamVO paramVO) throws BohaiException {
        
        return this.crmMarketerService.queryMarketerOverview(paramVO);
    }
}
