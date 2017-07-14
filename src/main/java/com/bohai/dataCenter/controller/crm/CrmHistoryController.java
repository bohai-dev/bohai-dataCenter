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
import com.bohai.dataCenter.entity.CrmCustomer;
import com.bohai.dataCenter.entity.CrmCustomerHistory;
import com.bohai.dataCenter.entity.CrmMarketer;
import com.bohai.dataCenter.entity.CrmMarketerHistory;
import com.bohai.dataCenter.entity.CrmMediator;
import com.bohai.dataCenter.entity.CrmMediatorHistory;
import com.bohai.dataCenter.persistence.CrmCustomerHistoryMapper;
import com.bohai.dataCenter.persistence.CrmMarketerHistoryMapper;
import com.bohai.dataCenter.persistence.CrmMediatorHistoryMapper;
import com.bohai.dataCenter.vo.CrmMarketerAndCustomer;
import com.bohai.dataCenter.vo.CrmMarketerAndMediator;
import com.bohai.dataCenter.vo.CrmMediatorAndCustomer;
import com.bohai.dataCenter.vo.QueryCrmCustomerParamVO;
import com.bohai.dataCenter.vo.QueryCrmMarketerParamVO;
import com.bohai.dataCenter.vo.QueryCrmMediatorParamVO;
import com.bohai.dataCenter.vo.TableJsonResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 历史归属关系
 * @author caojia
 */
@Controller
public class CrmHistoryController {

    static Logger logger = Logger.getLogger(CrmHistoryController.class);
    
    @Autowired
    private CrmMediatorHistoryMapper crmMediatorHistoryMapper;
    
    @Autowired
    private CrmMarketerHistoryMapper crmMarketerHistoryMapper;
    
    @Autowired
    private CrmCustomerHistoryMapper crmCustomerHistoryMapper;
    
    @RequestMapping(value="toCrmHistory")
    public String toCrmHistory(){
        
        return "crm/crmHistory";
        
    }
    
    /**
     * 查询居间人历史信息
     * @param paramVO
     * @return
     */
    @RequestMapping(value="queryCrmMediatorHistory")
    @ResponseBody
    public List<CrmMediatorHistory> queryCrmMediatorHistory(@RequestBody(required = false) QueryCrmMediatorParamVO paramVO){
        
        
        return this.crmMediatorHistoryMapper.selectByCondition(paramVO);
    }
    
    /**
     * 查询营销人员历史信息
     * @param paramVO
     * @return
     */
    @RequestMapping(value="queryCrmMarketerHistory")
    @ResponseBody
    public List<CrmMarketerHistory> queryCrmMarketerHistory(@RequestBody(required = false) QueryCrmMarketerParamVO paramVO){
        
        return this.crmMarketerHistoryMapper.selectByCondition(paramVO);
    }
    
    /**
     * 查询客户历史信息
     * @param paramVO
     * @return
     */
    @RequestMapping(value="queryCrmCustomerHistory")
    @ResponseBody
    public TableJsonResponse<CrmCustomerHistory> queryCrmCustomerHistory(@RequestBody(required = false) QueryCrmCustomerParamVO paramVO){
        
        PageHelper.startPage(paramVO.getPageNumber(), paramVO.getPageSize());
        List<CrmCustomerHistory> list = this.crmCustomerHistoryMapper.selectByCondition(paramVO);
        
        Page<CrmCustomerHistory> page = (Page)list;
        TableJsonResponse<CrmCustomerHistory> response = new TableJsonResponse<CrmCustomerHistory>();
        response.setTotal(page.getTotal());
        response.setRows(list);
        
        return response;
    }
    
    
    
    /**
     * 导出居间人信息
     * @param paramVO
     * @param request
     * @param response
     * @throws BohaiException
     */
    @RequestMapping("exportCrmMediatorHistory")
    public void exportCrmMediator(QueryCrmMediatorParamVO paramVO, 
            HttpServletRequest request, HttpServletResponse response) throws BohaiException{
        
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet=wb.createSheet("居间人信息");
        
        String[] mediatorHead = {"所属营业部","居间人编号","居间人名称","归属类型","归属代码","归属名称","证件类型","证件号码","生效日期","失效日期","默认分配比例","联系电话"};
        
        XSSFRow row = sheet.createRow(0);
        //初始化表头
        for (int i = 0 ;i < mediatorHead.length ; i++) {
            row.createCell(i).setCellValue(mediatorHead[i]);
            
            sheet.setColumnWidth(i, 256*15);
        }
        
        List<CrmMediatorHistory> list = this.crmMediatorHistoryMapper.selectByCondition(paramVO);
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
                row2.createCell(11).setCellValue(list.get(i).getTelephone());
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
        List<CrmMediatorAndCustomer> mediatorlist = this.crmMediatorHistoryMapper.selectMediatorCustomerRelation(paramVO);
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
    
    /**
     * 导出营销人员信息
     * @param paramVO
     * @param request
     * @param response
     * @throws BohaiException
     */
    @RequestMapping("exportCrmMarketerHistory")
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
        List<CrmMarketerHistory> marketerList= crmMarketerHistoryMapper.selectByCondition(paramVO);
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
        List<CrmMarketerAndMediator> marketerAndMediatorList= crmMarketerHistoryMapper.selectMaketerRelation(paramVO);
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
        List<CrmMarketerAndCustomer> marketerAndCustomerList = crmMarketerHistoryMapper.selectMaketerCustomerRelation(paramVO);
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
}
