package com.bohai.dataCenter.service.impl;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CrmCustomer;
import com.bohai.dataCenter.entity.CrmMediator;
import com.bohai.dataCenter.persistence.CrmCustomerMapper;
import com.bohai.dataCenter.persistence.CrmMediatorMapper;
import com.bohai.dataCenter.persistence.SpecialListMapper;
import com.bohai.dataCenter.util.ZipUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class SpecialListServiceImpl {

    static Logger logger = Logger.getLogger(SpecialListServiceImpl.class);
    
    @Autowired
    private SpecialListMapper specialListMapper;
    
    @Autowired
    private CrmCustomerMapper customerMapper;
    
    @Autowired
    private CrmMediatorMapper mediatorMapper;
    
    public void saveSpecial(Map<String, String> param) throws BohaiException{
        
        if(StringUtils.isBlank(param.get("RETURN_TYPE"))){
            throw new BohaiException("00001", "返还类型不能为空");
        }
        if(param.get("RETURN_TYPE").equals("1")){
            if(StringUtils.isBlank(param.get("INVESTOR_NO"))){
                throw new BohaiException("00001", "投资者代码不能为空");
            }
        }else if(param.get("RETURN_TYPE").equals("2")){
            if(StringUtils.isBlank(param.get("MEDIATOR_NO"))){
                throw new BohaiException("00001", "居间人编号不能为空");
            }
        }else {
            throw new BohaiException("00001", "返还类型错误");
        }
        
        String id = param.get("ID");
        if(StringUtils.isBlank(id)){
            //新增
            if(param.get("RETURN_TYPE").equals("2") && "1".equals(param.get("IS_ALL"))){
                //居间人名下所有客户
                List<CrmCustomer> customers = this.customerMapper.selectByMediator(param.get("MEDIATOR_NO"));
                for (CrmCustomer crmCustomer : customers) {
                    param.put("INVESTOR_NO", crmCustomer.getInvestorNo());
                    this.specialListMapper.insert(param);
                }
            }else {
                this.specialListMapper.insert(param);
            }
        }else {
            //更新
            if("1".equals(param.get("UPDATE_ALL"))){
                this.specialListMapper.updateByMediator(param);
            }else {
                this.specialListMapper.updateById(param);
            }
        }
    }
    
    public PageInfo<Map<String,String>> querySpecial(Map<String, String> param, int pageNum, int pageSize){
        
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, String>> list = this.specialListMapper.selectByCondition(param);
        PageInfo<Map<String,String>> pageInfo = new PageInfo<>(list);
        
        return pageInfo;
    }
    
    public void remove(String id, String removeAll, String mediatorNo){
        
        if ("1".equals(removeAll)) {
            this.specialListMapper.removeByMediator(mediatorNo);
        }else {
            this.specialListMapper.removeById(id);
        }
        
    }
    
    public XSSFWorkbook exportSpecial(){
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet=wb.createSheet("交返特例名单");
        
        String[] customerHead = {"营业部","返还类型","是否名下所有客户","居间人编号","居间人名称","投资者编号","投资者名称","上期返还比例","大商返还比例","郑商返还比例","能源返还比例","中金返还比例","返还方式","生效日期","失效日期","备注"};
        
        XSSFRow row = sheet.createRow(0);
        //初始化表头
        for (int i = 0 ;i < customerHead.length ; i++) {
            row.createCell(i).setCellValue(customerHead[i]);
            
            sheet.setColumnWidth(i, 256*15);
        }
        
        List<Map<String, String>> list = this.specialListMapper.selectByCondition(new HashMap<String, String>());

        if(list != null && list.size() > 0){
            
            for (int i = 0 ; i < list.size(); i++) {
                XSSFRow row2 = sheet.createRow(i+1);
                
                row2.createCell(0).setCellValue(list.get(i).get("DEP_NAME"));
                //返还类型
                String returnType = list.get(i).get("RETURN_TYPE");
                if("1".equals(returnType)){
                    returnType = "客户";
                }else {
                    returnType = "居间人";
                }
                row2.createCell(1).setCellValue(returnType);
                //是否名下所有客户
                String isAll = list.get(i).get("IS_ALL");
                if("0".equals(isAll)){
                    isAll = "否";
                }else if("1".equals(isAll)){
                    isAll = "是";
                }
                row2.createCell(2).setCellValue(isAll);
                //居间人编号
                row2.createCell(3).setCellValue(list.get(i).get("MEDIATOR_NO"));
                //居间人名称
                row2.createCell(4).setCellValue(list.get(i).get("MEDIATOR_NAME"));
                //投资者编号
                row2.createCell(5).setCellValue(list.get(i).get("INVESTOR_NO"));
                //投资者名称
                row2.createCell(6).setCellValue(list.get(i).get("INVESTOR_NAME"));
                //上期返还比例
                row2.createCell(7).setCellValue(list.get(i).get("SHFE"));
                //大商返还比例
                row2.createCell(8).setCellValue(list.get(i).get("DCE"));
                //郑商返还比例
                row2.createCell(9).setCellValue(list.get(i).get("CZCE"));
                //能源返还比例
                row2.createCell(10).setCellValue(list.get(i).get("INE"));
                //中金返还比例
                row2.createCell(11).setCellValue(list.get(i).get("CFFEX"));
                //返还方式
                String returnWay = list.get(i).get("RETURN_WAY");
                if("1".equals(returnWay)){
                    returnWay = "加帐户";
                }else if("2".equals(returnWay)){
                    returnWay = "客户银行卡";
                }
                row2.createCell(12).setCellValue(returnWay);
                //生效日期
                row2.createCell(13).setCellValue(list.get(i).get("EFFECT_DATE"));
                //失效日期
                row2.createCell(14).setCellValue(list.get(i).get("EXPIRE_DATE"));
                //备注
                row2.createCell(15).setCellValue(list.get(i).get("REMARK"));
            }
        }
        
        return wb;
        
    }
    
    public void syncCustomer(){
        //先查询所有客户的居间人
        List<String> mediators = this.specialListMapper.distinctIsAll();
        for (String string : mediators) {
            //查询居间人属性
            Map<String, String> map = this.specialListMapper.selectOneByMediatorNo(string);
            //查询居间人名下客户
            List<CrmCustomer> customers = this.customerMapper.selectByMediator(string);
            for (CrmCustomer crmCustomer : customers) {
                map.put("INVESTOR_NO", crmCustomer.getInvestorNo());
                this.specialListMapper.insert(map);
            }
        }
    }
    
    public void exportZip(String exportMonth, HttpServletResponse response) throws FileNotFoundException, IOException{
        
        List<String> depNames = this.specialListMapper.selectDepName();
        List<File> fileList=new  ArrayList<File>();
        for (String depName : depNames) {
            XSSFWorkbook workbook = this.exportDepartment(exportMonth, depName);
            File file = new File(depName+".xlsx");
            workbook.write(new FileOutputStream(file));
            fileList.add(file);
        }
        
        //汇总
        {
            File file = new File("汇总.xlsx");
            XSSFWorkbook workbook = this.exportSummary(exportMonth, depNames);
            workbook.write(new FileOutputStream(file));
            fileList.add(file);
        }
        
        //加帐户汇总
        
        
        OutputStream output=response.getOutputStream();
        response.reset();
        response.setContentType("application/zip");  
        response.setCharacterEncoding("UTF-8");  
        File zipFile=new File(exportMonth+"营业部交返.zip");
        System.out.println(zipFile.getAbsoluteFile());
        ZipUtil.zipFiles(fileList, zipFile);
        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(exportMonth+"营业部交返", "UTF-8")+".zip");
        FileInputStream inputStream = new FileInputStream(zipFile);
        int length = 0;
        byte[] buf = new byte[1024];
        while ((length = inputStream.read(buf)) > 0) {
                output.write(buf, 0, length);
                
            }
        inputStream.close();
        output.flush();
        output.close();
        
    }
    
    public XSSFWorkbook exportDepartment(String month,String depName) throws FileNotFoundException, IOException{
        String[] head= {"客户号","客户名","返还方式","比例","上交手续费","交易所返还","客户返还","剔税返还6%","营业税风险金","净得"};//10
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet SHFESheet=wb.createSheet("上期");
        XSSFSheet CZCESheet=wb.createSheet("郑商");
        XSSFSheet DCESheet=wb.createSheet("大商");
        XSSFSheet INESheet=wb.createSheet("能源");
        //标题样式
        XSSFCellStyle labelStyle = wb.createCellStyle();
        XSSFFont font = wb.createFont(); 
        font.setBold(true);//粗体显示    
        font.setFontHeightInPoints((short) 14);//设置字体大小
        labelStyle.setFont(font);
        labelStyle.setAlignment(HorizontalAlignment.CENTER);
        
        //在sheet里增加合并单元格  
        CellRangeAddress cra=new CellRangeAddress(0, 0, 0, 9);   //合并第一行所有的列
        SHFESheet.addMergedRegion(cra);
        CZCESheet.addMergedRegion(cra);
        DCESheet.addMergedRegion(cra);
        INESheet.addMergedRegion(cra);
        
        //表头样式
        XSSFCellStyle headStyle = wb.createCellStyle();
        XSSFFont font2 = wb.createFont(); 
        font2.setBold(true);//粗体显示    
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setFont(font2);
        setStyle(headStyle);
        
        //填充样式
        XSSFCellStyle yellowStyle = wb.createCellStyle();
        yellowStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        yellowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        setStyle(yellowStyle);
        
        //上期sheet
        {
            XSSFRow row = SHFESheet.createRow(0);
            XSSFCell cell = row.createCell(0, CellType.STRING);
            cell.setCellStyle(labelStyle);
            cell.setCellValue(depName+month+"上期所返还明细表");
            //设置表头
            Row headRow = SHFESheet.createRow(1);
            for(int i=0;i<10;i++) {
                Cell cell1=headRow.createCell(i);
                cell1.setCellValue(head[i]);
                cell1.setCellStyle(headStyle);
            }
            SHFESheet.setColumnWidth(1, 256*15);
            SHFESheet.setColumnWidth(2, 256*15);
            SHFESheet.setColumnWidth(4, 256*15);
            SHFESheet.setColumnWidth(5, 256*12);
            SHFESheet.setColumnWidth(6, 256*12);
            SHFESheet.setColumnWidth(7, 256*12);
            SHFESheet.setColumnWidth(8, 256*12);
            SHFESheet.setColumnWidth(9, 256*12);
        }
        //郑商sheet
        {
            XSSFRow row = CZCESheet.createRow(0);
            XSSFCell cell = row.createCell(0, CellType.STRING);
            cell.setCellStyle(labelStyle);
            cell.setCellValue(depName+month+"郑商所返还明细表");
            //设置表头
            Row headRow = CZCESheet.createRow(1);
            for(int i=0;i<10;i++) {
                Cell cell1=headRow.createCell(i);
                cell1.setCellValue(head[i]);
                cell1.setCellStyle(headStyle);
            }
            CZCESheet.setColumnWidth(1, 256*15);
            CZCESheet.setColumnWidth(2, 256*15);
            CZCESheet.setColumnWidth(4, 256*15);
            CZCESheet.setColumnWidth(5, 256*12);
            CZCESheet.setColumnWidth(6, 256*12);
            CZCESheet.setColumnWidth(7, 256*12);
            CZCESheet.setColumnWidth(8, 256*12);
            CZCESheet.setColumnWidth(9, 256*12);
        }
        //大商sheet
        {
            XSSFRow row = DCESheet.createRow(0);
            XSSFCell cell = row.createCell(0, CellType.STRING);
            cell.setCellStyle(labelStyle);
            //String previousMonth = this.specialListMapper.selectPreviousMonth(month);
            cell.setCellValue(depName+month+"大商所返还明细表");
            //设置表头
            Row headRow = DCESheet.createRow(1);
            for(int i=0;i<10;i++) {
                Cell cell1=headRow.createCell(i);
                cell1.setCellValue(head[i]);
                cell1.setCellStyle(headStyle);
            }
            DCESheet.setColumnWidth(1, 256*15);
            DCESheet.setColumnWidth(2, 256*15);
            DCESheet.setColumnWidth(4, 256*15);
            DCESheet.setColumnWidth(5, 256*12);
            DCESheet.setColumnWidth(6, 256*12);
            DCESheet.setColumnWidth(7, 256*12);
            DCESheet.setColumnWidth(8, 256*12);
            DCESheet.setColumnWidth(9, 256*12);
        }
        //能源sheet
        {
            XSSFRow row = INESheet.createRow(0);
            XSSFCell cell = row.createCell(0, CellType.STRING);
            cell.setCellStyle(labelStyle);
            cell.setCellValue(depName+month+"能源中心返还明细表");
            //设置表头
            Row headRow = INESheet.createRow(1);
            for(int i=0;i<10;i++) {
                Cell cell1=headRow.createCell(i);
                cell1.setCellValue(head[i]);
                cell1.setCellStyle(headStyle);
            }
            INESheet.setColumnWidth(1, 256*15);
            INESheet.setColumnWidth(2, 256*15);
            INESheet.setColumnWidth(4, 256*15);
            INESheet.setColumnWidth(5, 256*12);
            INESheet.setColumnWidth(6, 256*12);
            INESheet.setColumnWidth(7, 256*12);
            INESheet.setColumnWidth(8, 256*12);
            INESheet.setColumnWidth(9, 256*12);
        }
        //先查询加帐户客户
        {
            List<Map<String, String>> list = this.specialListMapper.selectAccountCustomer(month,depName);
            if(list.size()>0){
                
                //上期sheet
                {
                    BigDecimal sjsxf = BigDecimal.ZERO;//上交手续费
                    BigDecimal sqsfh = BigDecimal.ZERO;//上期所返还
                    BigDecimal khfh = BigDecimal.ZERO;//客户返还
                    BigDecimal tsfh = BigDecimal.ZERO;//剔税返还
                    BigDecimal yysfxj = BigDecimal.ZERO;//营业税风险金
                    BigDecimal jd = BigDecimal.ZERO;//净得
                    
                    for (int j = 0; j < list.size() ; j++) {
                        BigDecimal rebate = new BigDecimal(list.get(j).get("SREBATE")).multiply(new BigDecimal(list.get(j).get("SHFE"))).setScale(2, RoundingMode.HALF_UP);
                        if(rebate.compareTo(BigDecimal.ZERO) > 0){
                            
                            Row sheetRow= SHFESheet.createRow(SHFESheet.getLastRowNum()+1);
                            sheetRow.createCell(0, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NO"));
                            sheetRow.createCell(1, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NAME"));
                            sheetRow.createCell(2, CellType.STRING).setCellValue("加帐户");
                            sheetRow.createCell(3, CellType.NUMERIC).setCellValue(list.get(j).get("SHFE"));
                            sheetRow.createCell(4, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("SCOMMISSION")).doubleValue());
                            sheetRow.createCell(5, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("SREBATE")).doubleValue());
                            //客户返还
                            sheetRow.createCell(6, CellType.NUMERIC).setCellValue(rebate.doubleValue());
                            //剔税
                            BigDecimal tax = rebate.divide(new BigDecimal("1.06"), RoundingMode.HALF_UP);
                            sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tax.doubleValue());
                            //营业税风险金
                            BigDecimal risk = tax.multiply(new BigDecimal("0.1172")).setScale(2, RoundingMode.HALF_UP);
                            sheetRow.createCell(8, CellType.NUMERIC).setCellValue(risk.doubleValue());
                            //净得 = 客户返还-营业税风险金
                            BigDecimal net = rebate.subtract(risk);
                            sheetRow.createCell(9, CellType.NUMERIC).setCellValue(net.doubleValue());
                            
                            sjsxf = sjsxf.add(new BigDecimal(list.get(j).get("SCOMMISSION")));
                            sqsfh = sqsfh.add(new BigDecimal(list.get(j).get("SREBATE")));
                            khfh = khfh.add(rebate);
                            tsfh = tsfh.add(tax);
                            yysfxj = yysfxj.add(risk);
                            jd = jd.add(net);
                        }
                    }
                    //加帐户合计
                    if(khfh.compareTo(BigDecimal.ZERO) > 0){
                        Row sheetRow= SHFESheet.createRow(SHFESheet.getLastRowNum()+1);
                        sheetRow.createCell(0, CellType.STRING).setCellValue("加帐户");
                        sheetRow.createCell(1, CellType.STRING);
                        sheetRow.createCell(2, CellType.STRING);
                        sheetRow.createCell(3, CellType.STRING);
                        sheetRow.createCell(4, CellType.NUMERIC).setCellValue(sjsxf.doubleValue());
                        sheetRow.createCell(5, CellType.NUMERIC).setCellValue(sqsfh.doubleValue());
                        sheetRow.createCell(6, CellType.NUMERIC).setCellValue(khfh.doubleValue());
                        sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tsfh.doubleValue());
                        sheetRow.createCell(8, CellType.NUMERIC).setCellValue(yysfxj.doubleValue());
                        sheetRow.createCell(9, CellType.NUMERIC).setCellValue(jd.doubleValue());
                    }
                }
                
                //郑商sheet
                {
                    BigDecimal sjsxf = BigDecimal.ZERO;//上交手续费
                    BigDecimal sqsfh = BigDecimal.ZERO;//上期所返还
                    BigDecimal khfh = BigDecimal.ZERO;//客户返还
                    BigDecimal tsfh = BigDecimal.ZERO;//剔税返还
                    BigDecimal yysfxj = BigDecimal.ZERO;//营业税风险金
                    BigDecimal jd = BigDecimal.ZERO;//净得
                    
                    for (int j = 0; j < list.size() ; j++) {
                        BigDecimal rebate = new BigDecimal(list.get(j).get("ZREBATE")).multiply(new BigDecimal(list.get(j).get("CZCE"))).setScale(2, RoundingMode.HALF_UP);
                        if(rebate.compareTo(BigDecimal.ZERO) > 0){
                            
                            Row sheetRow= CZCESheet.createRow(CZCESheet.getLastRowNum()+1);
                            sheetRow.createCell(0, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NO"));
                            sheetRow.createCell(1, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NAME"));
                            sheetRow.createCell(2, CellType.STRING).setCellValue("加帐户");
                            sheetRow.createCell(3, CellType.NUMERIC).setCellValue(list.get(j).get("CZCE"));
                            sheetRow.createCell(4, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("ZCOMMISSION")).doubleValue());
                            sheetRow.createCell(5, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("ZREBATE")).doubleValue());
                            //客户返还
                            sheetRow.createCell(6, CellType.NUMERIC).setCellValue(rebate.doubleValue());
                            //剔税
                            BigDecimal tax = rebate.divide(new BigDecimal("1.06"), RoundingMode.HALF_UP);
                            sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tax.doubleValue());
                            //营业税风险金
                            BigDecimal risk = tax.multiply(new BigDecimal("0.1172")).setScale(2, RoundingMode.HALF_UP);
                            sheetRow.createCell(8, CellType.NUMERIC).setCellValue(risk.doubleValue());
                            //净得 = 客户返还-营业税风险金
                            BigDecimal net = rebate.subtract(risk);
                            sheetRow.createCell(9, CellType.NUMERIC).setCellValue(net.doubleValue());
                            
                            
                            sjsxf = sjsxf.add(new BigDecimal(list.get(j).get("ZCOMMISSION")));
                            sqsfh = sqsfh.add(new BigDecimal(list.get(j).get("ZREBATE")));
                            khfh = khfh.add(rebate);
                            tsfh = tsfh.add(tax);
                            yysfxj = yysfxj.add(risk);
                            jd = jd.add(net);
                        }
                    }
                    //加帐户合计
                    if(khfh.compareTo(BigDecimal.ZERO) > 0){
                        Row sheetRow= CZCESheet.createRow(CZCESheet.getLastRowNum()+1);
                        sheetRow.createCell(0, CellType.STRING).setCellValue("加帐户");
                        sheetRow.createCell(1, CellType.STRING);
                        sheetRow.createCell(2, CellType.STRING);
                        sheetRow.createCell(3, CellType.STRING);
                        sheetRow.createCell(4, CellType.NUMERIC).setCellValue(sjsxf.doubleValue());
                        sheetRow.createCell(5, CellType.NUMERIC).setCellValue(sqsfh.doubleValue());
                        sheetRow.createCell(6, CellType.NUMERIC).setCellValue(khfh.doubleValue());
                        sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tsfh.doubleValue());
                        sheetRow.createCell(8, CellType.NUMERIC).setCellValue(yysfxj.doubleValue());
                        sheetRow.createCell(9, CellType.NUMERIC).setCellValue(jd.doubleValue());
                    }
                }
                
                //大商sheet
                {
                    BigDecimal sjsxf = BigDecimal.ZERO;//上交手续费
                    BigDecimal sqsfh = BigDecimal.ZERO;//上期所返还
                    BigDecimal khfh = BigDecimal.ZERO;//客户返还
                    BigDecimal tsfh = BigDecimal.ZERO;//剔税返还
                    BigDecimal yysfxj = BigDecimal.ZERO;//营业税风险金
                    BigDecimal jd = BigDecimal.ZERO;//净得
                    
                    for (int j = 0; j < list.size() ; j++) {
                        BigDecimal rebate = new BigDecimal(list.get(j).get("DREBATE")).multiply(new BigDecimal(list.get(j).get("DCE"))).setScale(2, RoundingMode.HALF_UP);
                        if(rebate.compareTo(BigDecimal.ZERO) > 0){
                            
                            Row sheetRow= DCESheet.createRow(DCESheet.getLastRowNum()+1);
                            sheetRow.createCell(0, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NO"));
                            sheetRow.createCell(1, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NAME"));
                            sheetRow.createCell(2, CellType.STRING).setCellValue("加帐户");
                            sheetRow.createCell(3, CellType.NUMERIC).setCellValue(list.get(j).get("DCE"));
                            sheetRow.createCell(4, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("DCOMMISSION")).doubleValue());
                            sheetRow.createCell(5, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("DREBATE")).doubleValue());
                            //客户返还
                            sheetRow.createCell(6, CellType.NUMERIC).setCellValue(rebate.doubleValue());
                            //剔税
                            BigDecimal tax = rebate.divide(new BigDecimal("1.06"), RoundingMode.HALF_UP);
                            sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tax.doubleValue());
                            //营业税风险金
                            BigDecimal risk = tax.multiply(new BigDecimal("0.1172")).setScale(2, RoundingMode.HALF_UP);
                            sheetRow.createCell(8, CellType.NUMERIC).setCellValue(risk.doubleValue());
                            //净得 = 客户返还-营业税风险金
                            BigDecimal net = rebate.subtract(risk);
                            sheetRow.createCell(9, CellType.NUMERIC).setCellValue(net.doubleValue());
                            
                            sjsxf = sjsxf.add(new BigDecimal(list.get(j).get("DCOMMISSION")));
                            sqsfh = sqsfh.add(new BigDecimal(list.get(j).get("DREBATE")));
                            khfh = khfh.add(rebate);
                            tsfh = tsfh.add(tax);
                            yysfxj = yysfxj.add(risk);
                            jd = jd.add(net);
                        }
                    }
                    //加帐户合计
                    if(khfh.compareTo(BigDecimal.ZERO) > 0){
                        Row sheetRow= DCESheet.createRow(DCESheet.getLastRowNum()+1);
                        sheetRow.createCell(0, CellType.STRING).setCellValue("加帐户");
                        sheetRow.createCell(1, CellType.STRING);
                        sheetRow.createCell(2, CellType.STRING);
                        sheetRow.createCell(3, CellType.STRING);
                        sheetRow.createCell(4, CellType.NUMERIC).setCellValue(sjsxf.doubleValue());
                        sheetRow.createCell(5, CellType.NUMERIC).setCellValue(sqsfh.doubleValue());
                        sheetRow.createCell(6, CellType.NUMERIC).setCellValue(khfh.doubleValue());
                        sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tsfh.doubleValue());
                        sheetRow.createCell(8, CellType.NUMERIC).setCellValue(yysfxj.doubleValue());
                        sheetRow.createCell(9, CellType.NUMERIC).setCellValue(jd.doubleValue());
                    }
                }
                
                //能源sheet
                {
                    BigDecimal sjsxf = BigDecimal.ZERO;//上交手续费
                    BigDecimal sqsfh = BigDecimal.ZERO;//上期所返还
                    BigDecimal khfh = BigDecimal.ZERO;//客户返还
                    BigDecimal tsfh = BigDecimal.ZERO;//剔税返还
                    BigDecimal yysfxj = BigDecimal.ZERO;//营业税风险金
                    BigDecimal jd = BigDecimal.ZERO;//净得
                    
                    for (int j = 0; j < list.size() ; j++) {
                        BigDecimal rebate = new BigDecimal(list.get(j).get("IREBATE")).multiply(new BigDecimal(list.get(j).get("INE"))).setScale(2, RoundingMode.HALF_UP);
                        if(rebate.compareTo(BigDecimal.ZERO) > 0){
                            
                            Row sheetRow= INESheet.createRow(INESheet.getLastRowNum()+1);
                            sheetRow.createCell(0, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NO"));
                            sheetRow.createCell(1, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NAME"));
                            sheetRow.createCell(2, CellType.STRING).setCellValue("加帐户");
                            sheetRow.createCell(3, CellType.NUMERIC).setCellValue(list.get(j).get("INE"));
                            sheetRow.createCell(4, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("ICOMMISSION")).doubleValue());
                            sheetRow.createCell(5, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("IREBATE")).doubleValue());
                            //客户返还
                            sheetRow.createCell(6, CellType.NUMERIC).setCellValue(rebate.doubleValue());
                            //剔税
                            BigDecimal tax = rebate.divide(new BigDecimal("1.06"), RoundingMode.HALF_UP);
                            sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tax.doubleValue());
                            //营业税风险金
                            BigDecimal risk = tax.multiply(new BigDecimal("0.1172")).setScale(2, RoundingMode.HALF_UP);
                            sheetRow.createCell(8, CellType.NUMERIC).setCellValue(risk.doubleValue());
                            //净得 = 客户返还-营业税风险金
                            BigDecimal net = rebate.subtract(risk);
                            sheetRow.createCell(9, CellType.NUMERIC).setCellValue(net.doubleValue());
                            
                            sjsxf = sjsxf.add(new BigDecimal(list.get(j).get("ICOMMISSION")));
                            sqsfh = sqsfh.add(new BigDecimal(list.get(j).get("IREBATE")));
                            khfh = khfh.add(rebate);
                            tsfh = tsfh.add(tax);
                            yysfxj = yysfxj.add(risk);
                            jd = jd.add(net);
                        }
                    }
                    //加帐户合计
                    if(khfh.compareTo(BigDecimal.ZERO) > 0){
                        Row sheetRow= INESheet.createRow(INESheet.getLastRowNum()+1);
                        sheetRow.createCell(0, CellType.STRING).setCellValue("加帐户");
                        sheetRow.createCell(1, CellType.STRING);
                        sheetRow.createCell(2, CellType.STRING);
                        sheetRow.createCell(3, CellType.STRING);
                        sheetRow.createCell(4, CellType.NUMERIC).setCellValue(sjsxf.doubleValue());
                        sheetRow.createCell(5, CellType.NUMERIC).setCellValue(sqsfh.doubleValue());
                        sheetRow.createCell(6, CellType.NUMERIC).setCellValue(khfh.doubleValue());
                        sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tsfh.doubleValue());
                        sheetRow.createCell(8, CellType.NUMERIC).setCellValue(yysfxj.doubleValue());
                        sheetRow.createCell(9, CellType.NUMERIC).setCellValue(jd.doubleValue());
                    }
                }
            }
        }
        
        //再查客户银行卡客户
        {
            List<Map<String, String>> list = this.specialListMapper.selectBankCustomer(month,depName);
            if(list.size()>0){
                
                //上期sheet
                {
                    BigDecimal sjsxf = BigDecimal.ZERO;//上交手续费
                    BigDecimal sqsfh = BigDecimal.ZERO;//上期所返还
                    BigDecimal khfh = BigDecimal.ZERO;//客户返还
                    BigDecimal tsfh = BigDecimal.ZERO;//剔税返还
                    BigDecimal yysfxj = BigDecimal.ZERO;//营业税风险金
                    BigDecimal jd = BigDecimal.ZERO;//净得
                    
                    for (int j = 0; j < list.size() ; j++) {
                        BigDecimal rebate = new BigDecimal(list.get(j).get("SREBATE")).multiply(new BigDecimal(list.get(j).get("SHFE"))).setScale(2, RoundingMode.HALF_UP);
                        if(rebate.compareTo(BigDecimal.ZERO) > 0){
                            
                            Row sheetRow= SHFESheet.createRow(SHFESheet.getLastRowNum()+1);
                            sheetRow.createCell(0, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NO"));
                            sheetRow.createCell(1, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NAME"));
                            sheetRow.createCell(2, CellType.STRING).setCellValue("客户银行卡");
                            sheetRow.createCell(3, CellType.NUMERIC).setCellValue(list.get(j).get("SHFE"));
                            sheetRow.createCell(4, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("SCOMMISSION")).doubleValue());
                            sheetRow.createCell(5, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("SREBATE")).doubleValue());
                            //客户返还
                            sheetRow.createCell(6, CellType.NUMERIC).setCellValue(rebate.doubleValue());
                            //剔税
                            BigDecimal tax = rebate.divide(new BigDecimal("1.06"), RoundingMode.HALF_UP);
                            sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tax.doubleValue());
                            //营业税风险金
                            BigDecimal risk = tax.multiply(new BigDecimal("0.1172")).setScale(2, RoundingMode.HALF_UP);
                            sheetRow.createCell(8, CellType.NUMERIC).setCellValue(risk.doubleValue());
                            //净得 = 客户返还-营业税风险金
                            BigDecimal net = rebate.subtract(risk);
                            sheetRow.createCell(9, CellType.NUMERIC).setCellValue(net.doubleValue());
                            
                            
                            sjsxf = sjsxf.add(new BigDecimal(list.get(j).get("SCOMMISSION")));
                            sqsfh = sqsfh.add(new BigDecimal(list.get(j).get("SREBATE")));
                            khfh = khfh.add(rebate);
                            tsfh = tsfh.add(tax);
                            yysfxj = yysfxj.add(risk);
                            jd = jd.add(net);
                        }
                    }
                    //加帐户合计
                    if(khfh.compareTo(BigDecimal.ZERO) > 0){
                        Row sheetRow= SHFESheet.createRow(SHFESheet.getLastRowNum()+1);
                        sheetRow.createCell(0, CellType.STRING).setCellValue("客户银行卡");
                        sheetRow.createCell(1, CellType.STRING);
                        sheetRow.createCell(2, CellType.STRING);
                        sheetRow.createCell(3, CellType.STRING);
                        sheetRow.createCell(4, CellType.NUMERIC).setCellValue(sjsxf.doubleValue());
                        sheetRow.createCell(5, CellType.NUMERIC).setCellValue(sqsfh.doubleValue());
                        sheetRow.createCell(6, CellType.NUMERIC).setCellValue(khfh.doubleValue());
                        sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tsfh.doubleValue());
                        sheetRow.createCell(8, CellType.NUMERIC).setCellValue(yysfxj.doubleValue());
                        sheetRow.createCell(9, CellType.NUMERIC).setCellValue(jd.doubleValue());
                    }
                }
                
                //郑商sheet
                {
                    BigDecimal sjsxf = BigDecimal.ZERO;//上交手续费
                    BigDecimal sqsfh = BigDecimal.ZERO;//上期所返还
                    BigDecimal khfh = BigDecimal.ZERO;//客户返还
                    BigDecimal tsfh = BigDecimal.ZERO;//剔税返还
                    BigDecimal yysfxj = BigDecimal.ZERO;//营业税风险金
                    BigDecimal jd = BigDecimal.ZERO;//净得
                    
                    for (int j = 0; j < list.size() ; j++) {
                        BigDecimal rebate = new BigDecimal(list.get(j).get("ZREBATE")).multiply(new BigDecimal(list.get(j).get("CZCE"))).setScale(2, RoundingMode.HALF_UP);
                        if(rebate.compareTo(BigDecimal.ZERO) > 0){
                            
                            Row sheetRow= CZCESheet.createRow(CZCESheet.getLastRowNum()+1);
                            sheetRow.createCell(0, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NO"));
                            sheetRow.createCell(1, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NAME"));
                            sheetRow.createCell(2, CellType.STRING).setCellValue("客户银行卡");
                            sheetRow.createCell(3, CellType.NUMERIC).setCellValue(list.get(j).get("CZCE"));
                            sheetRow.createCell(4, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("ZCOMMISSION")).doubleValue());
                            sheetRow.createCell(5, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("ZREBATE")).doubleValue());
                            //客户返还
                            sheetRow.createCell(6, CellType.NUMERIC).setCellValue(rebate.doubleValue());
                            //剔税
                            BigDecimal tax = rebate.divide(new BigDecimal("1.06"), RoundingMode.HALF_UP);
                            sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tax.doubleValue());
                            //营业税风险金
                            BigDecimal risk = tax.multiply(new BigDecimal("0.1172")).setScale(2, RoundingMode.HALF_UP);
                            sheetRow.createCell(8, CellType.NUMERIC).setCellValue(risk.doubleValue());
                            //净得 = 客户返还-营业税风险金
                            BigDecimal net = rebate.subtract(risk);
                            sheetRow.createCell(9, CellType.NUMERIC).setCellValue(net.doubleValue());
                            
                            sjsxf = sjsxf.add(new BigDecimal(list.get(j).get("ZCOMMISSION")));
                            sqsfh = sqsfh.add(new BigDecimal(list.get(j).get("ZREBATE")));
                            khfh = khfh.add(rebate);
                            tsfh = tsfh.add(tax);
                            yysfxj = yysfxj.add(risk);
                            jd = jd.add(net);
                        }
                    }
                    //加帐户合计
                    if(khfh.compareTo(BigDecimal.ZERO) > 0){
                        Row sheetRow= CZCESheet.createRow(CZCESheet.getLastRowNum()+1);
                        sheetRow.createCell(0, CellType.STRING).setCellValue("客户银行卡");
                        sheetRow.createCell(1, CellType.STRING);
                        sheetRow.createCell(2, CellType.STRING);
                        sheetRow.createCell(3, CellType.STRING);
                        sheetRow.createCell(4, CellType.NUMERIC).setCellValue(sjsxf.doubleValue());
                        sheetRow.createCell(5, CellType.NUMERIC).setCellValue(sqsfh.doubleValue());
                        sheetRow.createCell(6, CellType.NUMERIC).setCellValue(khfh.doubleValue());
                        sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tsfh.doubleValue());
                        sheetRow.createCell(8, CellType.NUMERIC).setCellValue(yysfxj.doubleValue());
                        sheetRow.createCell(9, CellType.NUMERIC).setCellValue(jd.doubleValue());
                    }
                }
                
                //大商sheet
                {
                    BigDecimal sjsxf = BigDecimal.ZERO;//上交手续费
                    BigDecimal sqsfh = BigDecimal.ZERO;//上期所返还
                    BigDecimal khfh = BigDecimal.ZERO;//客户返还
                    BigDecimal tsfh = BigDecimal.ZERO;//剔税返还
                    BigDecimal yysfxj = BigDecimal.ZERO;//营业税风险金
                    BigDecimal jd = BigDecimal.ZERO;//净得
                    
                    for (int j = 0; j < list.size() ; j++) {
                        BigDecimal rebate = new BigDecimal(list.get(j).get("DREBATE")).multiply(new BigDecimal(list.get(j).get("DCE"))).setScale(2, RoundingMode.HALF_UP);
                        if(rebate.compareTo(BigDecimal.ZERO) > 0){
                            
                            Row sheetRow= DCESheet.createRow(DCESheet.getLastRowNum()+1);
                            sheetRow.createCell(0, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NO"));
                            sheetRow.createCell(1, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NAME"));
                            sheetRow.createCell(2, CellType.STRING).setCellValue("客户银行卡");
                            sheetRow.createCell(3, CellType.NUMERIC).setCellValue(list.get(j).get("DCE"));
                            sheetRow.createCell(4, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("DCOMMISSION")).doubleValue());
                            sheetRow.createCell(5, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("DREBATE")).doubleValue());
                            //客户返还
                            sheetRow.createCell(6, CellType.NUMERIC).setCellValue(rebate.doubleValue());
                            //剔税
                            BigDecimal tax = rebate.divide(new BigDecimal("1.06"), RoundingMode.HALF_UP);
                            sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tax.doubleValue());
                            //营业税风险金
                            BigDecimal risk = tax.multiply(new BigDecimal("0.1172")).setScale(2, RoundingMode.HALF_UP);
                            sheetRow.createCell(8, CellType.NUMERIC).setCellValue(risk.doubleValue());
                            //净得 = 客户返还-营业税风险金
                            BigDecimal net = rebate.subtract(risk);
                            sheetRow.createCell(9, CellType.NUMERIC).setCellValue(net.doubleValue());
                            
                            sjsxf = sjsxf.add(new BigDecimal(list.get(j).get("DCOMMISSION")));
                            sqsfh = sqsfh.add(new BigDecimal(list.get(j).get("DREBATE")));
                            khfh = khfh.add(rebate);
                            tsfh = tsfh.add(tax);
                            yysfxj = yysfxj.add(risk);
                            jd = jd.add(net);
                        }
                    }
                    //加帐户合计
                    if(khfh.compareTo(BigDecimal.ZERO) > 0){
                        Row sheetRow= DCESheet.createRow(DCESheet.getLastRowNum()+1);
                        sheetRow.createCell(0, CellType.STRING).setCellValue("客户银行卡");
                        sheetRow.createCell(1, CellType.STRING);
                        sheetRow.createCell(2, CellType.STRING);
                        sheetRow.createCell(3, CellType.STRING);
                        sheetRow.createCell(4, CellType.NUMERIC).setCellValue(sjsxf.doubleValue());
                        sheetRow.createCell(5, CellType.NUMERIC).setCellValue(sqsfh.doubleValue());
                        sheetRow.createCell(6, CellType.NUMERIC).setCellValue(khfh.doubleValue());
                        sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tsfh.doubleValue());
                        sheetRow.createCell(8, CellType.NUMERIC).setCellValue(yysfxj.doubleValue());
                        sheetRow.createCell(9, CellType.NUMERIC).setCellValue(jd.doubleValue());
                    }
                }
                
                //能源sheet
                {
                    BigDecimal sjsxf = BigDecimal.ZERO;//上交手续费
                    BigDecimal sqsfh = BigDecimal.ZERO;//上期所返还
                    BigDecimal khfh = BigDecimal.ZERO;//客户返还
                    BigDecimal tsfh = BigDecimal.ZERO;//剔税返还
                    BigDecimal yysfxj = BigDecimal.ZERO;//营业税风险金
                    BigDecimal jd = BigDecimal.ZERO;//净得
                    
                    for (int j = 0; j < list.size() ; j++) {
                        BigDecimal rebate = new BigDecimal(list.get(j).get("IREBATE")).multiply(new BigDecimal(list.get(j).get("INE"))).setScale(2, RoundingMode.HALF_UP);
                        if(rebate.compareTo(BigDecimal.ZERO) > 0){
                            
                            Row sheetRow= INESheet.createRow(INESheet.getLastRowNum()+1);
                            sheetRow.createCell(0, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NO"));
                            sheetRow.createCell(1, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NAME"));
                            sheetRow.createCell(2, CellType.STRING).setCellValue("客户银行卡");
                            sheetRow.createCell(3, CellType.NUMERIC).setCellValue(list.get(j).get("INE"));
                            sheetRow.createCell(4, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("ICOMMISSION")).doubleValue());
                            sheetRow.createCell(5, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("IREBATE")).doubleValue());
                            //客户返还
                            sheetRow.createCell(6, CellType.NUMERIC).setCellValue(rebate.doubleValue());
                            //剔税
                            BigDecimal tax = rebate.divide(new BigDecimal("1.06"), RoundingMode.HALF_UP);
                            sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tax.doubleValue());
                            //营业税风险金
                            BigDecimal risk = tax.multiply(new BigDecimal("0.1172")).setScale(2, RoundingMode.HALF_UP);
                            sheetRow.createCell(8, CellType.NUMERIC).setCellValue(risk.doubleValue());
                            //净得 = 客户返还-营业税风险金
                            BigDecimal net = rebate.subtract(risk);
                            sheetRow.createCell(9, CellType.NUMERIC).setCellValue(net.doubleValue());
                            
                            sjsxf = sjsxf.add(new BigDecimal(list.get(j).get("ICOMMISSION")));
                            sqsfh = sqsfh.add(new BigDecimal(list.get(j).get("IREBATE")));
                            khfh = khfh.add(rebate);
                            tsfh = tsfh.add(tax);
                            yysfxj = yysfxj.add(risk);
                            jd = jd.add(net);
                        }
                    }
                    //加帐户合计
                    if(khfh.compareTo(BigDecimal.ZERO) > 0){
                        Row sheetRow= INESheet.createRow(INESheet.getLastRowNum()+1);
                        sheetRow.createCell(0, CellType.STRING).setCellValue("客户银行卡");
                        sheetRow.createCell(1, CellType.STRING);
                        sheetRow.createCell(2, CellType.STRING);
                        sheetRow.createCell(3, CellType.STRING);
                        sheetRow.createCell(4, CellType.NUMERIC).setCellValue(sjsxf.doubleValue());
                        sheetRow.createCell(5, CellType.NUMERIC).setCellValue(sqsfh.doubleValue());
                        sheetRow.createCell(6, CellType.NUMERIC).setCellValue(khfh.doubleValue());
                        sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tsfh.doubleValue());
                        sheetRow.createCell(8, CellType.NUMERIC).setCellValue(yysfxj.doubleValue());
                        sheetRow.createCell(9, CellType.NUMERIC).setCellValue(jd.doubleValue());
                    }
                }
            }
        }
        
        //查居间人客户
        {
            List<String> mediators = this.specialListMapper.selectMediatorByDepName(depName);
            if(mediators.size() > 0){
                //查询居间人名下客户交返
                for (String mediatorNo : mediators) {
                    CrmMediator mediator = this.mediatorMapper.selectByPrimaryKey(mediatorNo);
                    if(mediator == null){
                        mediator = new CrmMediator();
                        mediator.setMediatorName(mediatorNo);
                    }
                    List<Map<String, String>> list = this.specialListMapper.selectMediatorCustomer(month, mediatorNo);
                    if(list.size() > 0){
                        
                        //上期sheet
                        {
                            BigDecimal sjsxf = BigDecimal.ZERO;//上交手续费
                            BigDecimal sqsfh = BigDecimal.ZERO;//上期所返还
                            BigDecimal khfh = BigDecimal.ZERO;//客户返还
                            BigDecimal tsfh = BigDecimal.ZERO;//剔税返还
                            BigDecimal yysfxj = BigDecimal.ZERO;//营业税风险金
                            BigDecimal jd = BigDecimal.ZERO;//净得
                            
                            for (int j = 0; j < list.size() ; j++) {
                                BigDecimal rebate = new BigDecimal(list.get(j).get("SREBATE")).multiply(new BigDecimal(list.get(j).get("SHFE"))).setScale(2, RoundingMode.HALF_UP);
                                if(rebate.compareTo(BigDecimal.ZERO) > 0){
                                    
                                    Row sheetRow= SHFESheet.createRow(SHFESheet.getLastRowNum()+1);
                                    sheetRow.createCell(0, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NO"));
                                    sheetRow.createCell(1, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NAME"));
                                    sheetRow.createCell(2, CellType.STRING).setCellValue(mediator.getMediatorName());
                                    sheetRow.createCell(3, CellType.NUMERIC).setCellValue(list.get(j).get("SHFE"));
                                    sheetRow.createCell(4, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("SCOMMISSION")).doubleValue());
                                    sheetRow.createCell(5, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("SREBATE")).doubleValue());
                                    //客户返还
                                    sheetRow.createCell(6, CellType.NUMERIC).setCellValue(rebate.doubleValue());
                                    //剔税
                                    BigDecimal tax = rebate.divide(new BigDecimal("1.06"), RoundingMode.HALF_UP);
                                    sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tax.doubleValue());
                                    //营业税风险金
                                    BigDecimal risk = tax.multiply(new BigDecimal("0.1172")).setScale(2, RoundingMode.HALF_UP);
                                    sheetRow.createCell(8, CellType.NUMERIC).setCellValue(risk.doubleValue());
                                    //净得 = 客户返还-营业税风险金
                                    BigDecimal net = rebate.subtract(risk);
                                    sheetRow.createCell(9, CellType.NUMERIC).setCellValue(net.doubleValue());
                                    
                                    sjsxf = sjsxf.add(new BigDecimal(list.get(j).get("SCOMMISSION")));
                                    sqsfh = sqsfh.add(new BigDecimal(list.get(j).get("SREBATE")));
                                    khfh = khfh.add(rebate);
                                    tsfh = tsfh.add(tax);
                                    yysfxj = yysfxj.add(risk);
                                    jd = jd.add(net);
                                }
                            }
                            //居间合计
                            if(khfh.compareTo(BigDecimal.ZERO) > 0){
                                Row sheetRow= SHFESheet.createRow(SHFESheet.getLastRowNum()+1);
                                sheetRow.createCell(0, CellType.STRING).setCellValue("居间");
                                sheetRow.createCell(1, CellType.STRING).setCellValue(mediator.getMediatorName());
                                sheetRow.createCell(2, CellType.STRING);
                                sheetRow.createCell(3, CellType.STRING);
                                sheetRow.createCell(4, CellType.NUMERIC).setCellValue(sjsxf.doubleValue());
                                sheetRow.createCell(5, CellType.NUMERIC).setCellValue(sqsfh.doubleValue());
                                sheetRow.createCell(6, CellType.NUMERIC).setCellValue(khfh.doubleValue());
                                sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tsfh.doubleValue());
                                sheetRow.createCell(8, CellType.NUMERIC).setCellValue(yysfxj.doubleValue());
                                sheetRow.createCell(9, CellType.NUMERIC).setCellValue(jd.doubleValue());
                            }
                        }
                        
                        //郑商sheet
                        {
                            BigDecimal sjsxf = BigDecimal.ZERO;//上交手续费
                            BigDecimal sqsfh = BigDecimal.ZERO;//上期所返还
                            BigDecimal khfh = BigDecimal.ZERO;//客户返还
                            BigDecimal tsfh = BigDecimal.ZERO;//剔税返还
                            BigDecimal yysfxj = BigDecimal.ZERO;//营业税风险金
                            BigDecimal jd = BigDecimal.ZERO;//净得
                            
                            for (int j = 0; j < list.size() ; j++) {
                                BigDecimal rebate = new BigDecimal(list.get(j).get("ZREBATE")).multiply(new BigDecimal(list.get(j).get("CZCE"))).setScale(2, RoundingMode.HALF_UP);
                                if(rebate.compareTo(BigDecimal.ZERO) > 0){
                                    
                                    Row sheetRow= CZCESheet.createRow(CZCESheet.getLastRowNum()+1);
                                    sheetRow.createCell(0, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NO"));
                                    sheetRow.createCell(1, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NAME"));
                                    sheetRow.createCell(2, CellType.STRING).setCellValue(mediator.getMediatorName());
                                    sheetRow.createCell(3, CellType.STRING).setCellValue(list.get(j).get("CZCE"));
                                    sheetRow.createCell(4, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("ZCOMMISSION")).doubleValue());
                                    sheetRow.createCell(5, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("ZREBATE")).doubleValue());
                                    //客户返还
                                    sheetRow.createCell(6, CellType.NUMERIC).setCellValue(rebate.doubleValue());
                                    //剔税
                                    BigDecimal tax = rebate.divide(new BigDecimal("1.06"), RoundingMode.HALF_UP);
                                    sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tax.doubleValue());
                                    //营业税风险金
                                    BigDecimal risk = tax.multiply(new BigDecimal("0.1172")).setScale(2, RoundingMode.HALF_UP);
                                    sheetRow.createCell(8, CellType.NUMERIC).setCellValue(risk.doubleValue());
                                    //净得 = 客户返还-营业税风险金
                                    BigDecimal net = rebate.subtract(risk);
                                    sheetRow.createCell(9, CellType.NUMERIC).setCellValue(net.doubleValue());
                                    
                                    sjsxf = sjsxf.add(new BigDecimal(list.get(j).get("ZCOMMISSION")));
                                    sqsfh = sqsfh.add(new BigDecimal(list.get(j).get("ZREBATE")));
                                    khfh = khfh.add(rebate);
                                    tsfh = tsfh.add(tax);
                                    yysfxj = yysfxj.add(risk);
                                    jd = jd.add(net);
                                }
                            }
                            //居间合计
                            if(khfh.compareTo(BigDecimal.ZERO) > 0){
                                Row sheetRow= CZCESheet.createRow(CZCESheet.getLastRowNum()+1);
                                sheetRow.createCell(0, CellType.STRING).setCellValue("居间");
                                sheetRow.createCell(1, CellType.STRING).setCellValue(mediator.getMediatorName());
                                sheetRow.createCell(2, CellType.STRING);
                                sheetRow.createCell(3, CellType.STRING);
                                sheetRow.createCell(4, CellType.NUMERIC).setCellValue(sjsxf.doubleValue());
                                sheetRow.createCell(5, CellType.NUMERIC).setCellValue(sqsfh.doubleValue());
                                sheetRow.createCell(6, CellType.NUMERIC).setCellValue(khfh.doubleValue());
                                sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tsfh.doubleValue());
                                sheetRow.createCell(8, CellType.NUMERIC).setCellValue(yysfxj.doubleValue());
                                sheetRow.createCell(9, CellType.NUMERIC).setCellValue(jd.doubleValue());
                            }
                        }
                        
                        //大商sheet
                        {
                            BigDecimal sjsxf = BigDecimal.ZERO;//上交手续费
                            BigDecimal sqsfh = BigDecimal.ZERO;//上期所返还
                            BigDecimal khfh = BigDecimal.ZERO;//客户返还
                            BigDecimal tsfh = BigDecimal.ZERO;//剔税返还
                            BigDecimal yysfxj = BigDecimal.ZERO;//营业税风险金
                            BigDecimal jd = BigDecimal.ZERO;//净得
                            
                            for (int j = 0; j < list.size() ; j++) {
                                BigDecimal rebate = new BigDecimal(list.get(j).get("DREBATE")).multiply(new BigDecimal(list.get(j).get("DCE"))).setScale(2, RoundingMode.HALF_UP);
                                if(rebate.compareTo(BigDecimal.ZERO) > 0){
                                    
                                    Row sheetRow= DCESheet.createRow(DCESheet.getLastRowNum()+1);
                                    sheetRow.createCell(0, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NO"));
                                    sheetRow.createCell(1, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NAME"));
                                    sheetRow.createCell(2, CellType.STRING).setCellValue(mediator.getMediatorName());
                                    sheetRow.createCell(3, CellType.NUMERIC).setCellValue(list.get(j).get("DCE"));
                                    sheetRow.createCell(4, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("DCOMMISSION")).doubleValue());
                                    sheetRow.createCell(5, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("DREBATE")).doubleValue());
                                    //客户返还
                                    sheetRow.createCell(6, CellType.NUMERIC).setCellValue(rebate.doubleValue());
                                    //剔税
                                    BigDecimal tax = rebate.divide(new BigDecimal("1.06"), RoundingMode.HALF_UP);
                                    sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tax.doubleValue());
                                    //营业税风险金
                                    BigDecimal risk = tax.multiply(new BigDecimal("0.1172")).setScale(2, RoundingMode.HALF_UP);
                                    sheetRow.createCell(8, CellType.NUMERIC).setCellValue(risk.doubleValue());
                                    //净得 = 客户返还-营业税风险金
                                    BigDecimal net = rebate.subtract(risk);
                                    sheetRow.createCell(9, CellType.NUMERIC).setCellValue(net.doubleValue());
                                    
                                    sjsxf = sjsxf.add(new BigDecimal(list.get(j).get("DCOMMISSION")));
                                    sqsfh = sqsfh.add(new BigDecimal(list.get(j).get("DREBATE")));
                                    khfh = khfh.add(rebate);
                                    tsfh = tsfh.add(tax);
                                    yysfxj = yysfxj.add(risk);
                                    jd = jd.add(net);
                                }
                            }
                            //居间合计
                            if(khfh.compareTo(BigDecimal.ZERO) > 0){
                                Row sheetRow= DCESheet.createRow(DCESheet.getLastRowNum()+1);
                                sheetRow.createCell(0, CellType.STRING).setCellValue("居间");
                                sheetRow.createCell(1, CellType.STRING).setCellValue(mediator.getMediatorName());
                                sheetRow.createCell(2, CellType.STRING);
                                sheetRow.createCell(3, CellType.STRING);
                                sheetRow.createCell(4, CellType.NUMERIC).setCellValue(sjsxf.doubleValue());
                                sheetRow.createCell(5, CellType.NUMERIC).setCellValue(sqsfh.doubleValue());
                                sheetRow.createCell(6, CellType.NUMERIC).setCellValue(khfh.doubleValue());
                                sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tsfh.doubleValue());
                                sheetRow.createCell(8, CellType.NUMERIC).setCellValue(yysfxj.doubleValue());
                                sheetRow.createCell(9, CellType.NUMERIC).setCellValue(jd.doubleValue());
                            }
                        }
                        
                        //能源sheet
                        {
                            BigDecimal sjsxf = BigDecimal.ZERO;//上交手续费
                            BigDecimal sqsfh = BigDecimal.ZERO;//上期所返还
                            BigDecimal khfh = BigDecimal.ZERO;//客户返还
                            BigDecimal tsfh = BigDecimal.ZERO;//剔税返还
                            BigDecimal yysfxj = BigDecimal.ZERO;//营业税风险金
                            BigDecimal jd = BigDecimal.ZERO;//净得
                            
                            for (int j = 0; j < list.size() ; j++) {
                                BigDecimal rebate = new BigDecimal(list.get(j).get("IREBATE")).multiply(new BigDecimal(list.get(j).get("INE"))).setScale(2, RoundingMode.HALF_UP);
                                if(rebate.compareTo(BigDecimal.ZERO) > 0){
                                    
                                    Row sheetRow= INESheet.createRow(INESheet.getLastRowNum()+1);
                                    sheetRow.createCell(0, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NO"));
                                    sheetRow.createCell(1, CellType.STRING).setCellValue(list.get(j).get("INVESTOR_NAME"));
                                    sheetRow.createCell(2, CellType.STRING).setCellValue(mediator.getMediatorName());
                                    sheetRow.createCell(3, CellType.NUMERIC).setCellValue(list.get(j).get("INE"));
                                    sheetRow.createCell(4, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("ICOMMISSION")).doubleValue());
                                    sheetRow.createCell(5, CellType.NUMERIC).setCellValue(new BigDecimal(list.get(j).get("IREBATE")).doubleValue());
                                    //客户返还
                                    sheetRow.createCell(6, CellType.NUMERIC).setCellValue(rebate.doubleValue());
                                    //剔税
                                    BigDecimal tax = rebate.divide(new BigDecimal("1.06"), RoundingMode.HALF_UP);
                                    sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tax.doubleValue());
                                    //营业税风险金
                                    BigDecimal risk = tax.multiply(new BigDecimal("0.1172")).setScale(2, RoundingMode.HALF_UP);
                                    sheetRow.createCell(8, CellType.NUMERIC).setCellValue(risk.doubleValue());
                                    //净得 = 客户返还-营业税风险金
                                    BigDecimal net = rebate.subtract(risk);
                                    sheetRow.createCell(9, CellType.NUMERIC).setCellValue(net.doubleValue());
                                    
                                    sjsxf = sjsxf.add(new BigDecimal(list.get(j).get("ICOMMISSION")));
                                    sqsfh = sqsfh.add(new BigDecimal(list.get(j).get("IREBATE")));
                                    khfh = khfh.add(rebate);
                                    tsfh = tsfh.add(tax);
                                    yysfxj = yysfxj.add(risk);
                                    jd = jd.add(net);
                                }
                            }
                            //居间合计
                            if(khfh.compareTo(BigDecimal.ZERO) > 0){
                                Row sheetRow= INESheet.createRow(INESheet.getLastRowNum()+1);
                                sheetRow.createCell(0, CellType.STRING).setCellValue("居间");
                                sheetRow.createCell(1, CellType.STRING).setCellValue(mediator.getMediatorName());
                                sheetRow.createCell(2, CellType.STRING);
                                sheetRow.createCell(3, CellType.STRING);
                                sheetRow.createCell(4, CellType.NUMERIC).setCellValue(sjsxf.doubleValue());
                                sheetRow.createCell(5, CellType.NUMERIC).setCellValue(sqsfh.doubleValue());
                                sheetRow.createCell(6, CellType.NUMERIC).setCellValue(khfh.doubleValue());
                                sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tsfh.doubleValue());
                                sheetRow.createCell(8, CellType.NUMERIC).setCellValue(yysfxj.doubleValue());
                                sheetRow.createCell(9, CellType.NUMERIC).setCellValue(jd.doubleValue());
                            }
                        }
                    
                    }
                }
            }
            
        }
        
        
        //上期合计
        {
            BigDecimal sjsxf = BigDecimal.ZERO;//上交手续费
            BigDecimal sqsfh = BigDecimal.ZERO;//上期所返还
            BigDecimal khfh = BigDecimal.ZERO;//客户返还
            BigDecimal tsfh = BigDecimal.ZERO;//剔税返还
            BigDecimal yysfxj = BigDecimal.ZERO;//营业税风险金
            BigDecimal jd = BigDecimal.ZERO;//净得
            for (int i = 2; i <= SHFESheet.getLastRowNum(); i++) {
                sjsxf = sjsxf.add(new BigDecimal(SHFESheet.getRow(i).getCell(4).getNumericCellValue()));
                sqsfh = sqsfh.add(new BigDecimal(SHFESheet.getRow(i).getCell(5).getNumericCellValue()));
                khfh = khfh.add(new BigDecimal(SHFESheet.getRow(i).getCell(6).getNumericCellValue()));
                tsfh = tsfh.add(new BigDecimal(SHFESheet.getRow(i).getCell(7).getNumericCellValue()));
                yysfxj = yysfxj.add(new BigDecimal(SHFESheet.getRow(i).getCell(8).getNumericCellValue()));
                jd = jd.add(new BigDecimal(SHFESheet.getRow(i).getCell(9).getNumericCellValue()));
            }
            Row sheetRow= SHFESheet.createRow(SHFESheet.getLastRowNum()+1);
            sheetRow.createCell(0, CellType.STRING).setCellValue("合计");
            sheetRow.createCell(1, CellType.STRING);
            sheetRow.createCell(2, CellType.STRING);
            sheetRow.createCell(3, CellType.STRING);
            sheetRow.createCell(4, CellType.NUMERIC).setCellValue(sjsxf.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(5, CellType.NUMERIC).setCellValue(sqsfh.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(6, CellType.NUMERIC).setCellValue(khfh.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tsfh.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(8, CellType.NUMERIC).setCellValue(yysfxj.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(9, CellType.NUMERIC).setCellValue(jd.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            
            Row footRow= SHFESheet.createRow(SHFESheet.getLastRowNum()+3);
            if("营销总部".equals(depName)){
                footRow.createCell(0, CellType.STRING).setCellValue("营销总部复核人： ");
                footRow.createCell(4, CellType.STRING).setCellValue("审核人： ");
                footRow.createCell(8, CellType.STRING).setCellValue("制表人： ");
            }else {
                footRow.createCell(0, CellType.STRING).setCellValue("业务部门经理： ");
                footRow.createCell(4, CellType.STRING).setCellValue("审核人： ");
                footRow.createCell(8, CellType.STRING).setCellValue("制表人： ");
            }
        }
        //郑商合计
        {
            BigDecimal sjsxf = BigDecimal.ZERO;//上交手续费
            BigDecimal sqsfh = BigDecimal.ZERO;//上期所返还
            BigDecimal khfh = BigDecimal.ZERO;//客户返还
            BigDecimal tsfh = BigDecimal.ZERO;//剔税返还
            BigDecimal yysfxj = BigDecimal.ZERO;//营业税风险金
            BigDecimal jd = BigDecimal.ZERO;//净得
            for (int i = 2; i <= CZCESheet.getLastRowNum(); i++) {
                sjsxf = sjsxf.add(new BigDecimal(CZCESheet.getRow(i).getCell(4).getNumericCellValue()));
                sqsfh = sqsfh.add(new BigDecimal(CZCESheet.getRow(i).getCell(5).getNumericCellValue()));
                khfh = khfh.add(new BigDecimal(CZCESheet.getRow(i).getCell(6).getNumericCellValue()));
                tsfh = tsfh.add(new BigDecimal(CZCESheet.getRow(i).getCell(7).getNumericCellValue()));
                yysfxj = yysfxj.add(new BigDecimal(CZCESheet.getRow(i).getCell(8).getNumericCellValue()));
                jd = jd.add(new BigDecimal(CZCESheet.getRow(i).getCell(9).getNumericCellValue()));
            }
            Row sheetRow= CZCESheet.createRow(CZCESheet.getLastRowNum()+1);
            sheetRow.createCell(0, CellType.STRING).setCellValue("合计");
            sheetRow.createCell(1, CellType.STRING);
            sheetRow.createCell(2, CellType.STRING);
            sheetRow.createCell(3, CellType.STRING);
            sheetRow.createCell(4, CellType.NUMERIC).setCellValue(sjsxf.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(5, CellType.NUMERIC).setCellValue(sqsfh.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(6, CellType.NUMERIC).setCellValue(khfh.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tsfh.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(8, CellType.NUMERIC).setCellValue(yysfxj.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(9, CellType.NUMERIC).setCellValue(jd.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            
            Row footRow= CZCESheet.createRow(CZCESheet.getLastRowNum()+3);
            if("营销总部".equals(depName)){
                footRow.createCell(0, CellType.STRING).setCellValue("营销总部复核人： ");
                footRow.createCell(4, CellType.STRING).setCellValue("审核人： ");
                footRow.createCell(8, CellType.STRING).setCellValue("制表人： ");
            }else {
                footRow.createCell(0, CellType.STRING).setCellValue("业务部门经理： ");
                footRow.createCell(4, CellType.STRING).setCellValue("审核人： ");
                footRow.createCell(8, CellType.STRING).setCellValue("制表人： ");
            }
        }
        //大商合计
        {
            BigDecimal sjsxf = BigDecimal.ZERO;//上交手续费
            BigDecimal sqsfh = BigDecimal.ZERO;//上期所返还
            BigDecimal khfh = BigDecimal.ZERO;//客户返还
            BigDecimal tsfh = BigDecimal.ZERO;//剔税返还
            BigDecimal yysfxj = BigDecimal.ZERO;//营业税风险金
            BigDecimal jd = BigDecimal.ZERO;//净得
            for (int i = 2; i <= DCESheet.getLastRowNum(); i++) {
                sjsxf = sjsxf.add(new BigDecimal(DCESheet.getRow(i).getCell(4).getNumericCellValue()));
                sqsfh = sqsfh.add(new BigDecimal(DCESheet.getRow(i).getCell(5).getNumericCellValue()));
                khfh = khfh.add(new BigDecimal(DCESheet.getRow(i).getCell(6).getNumericCellValue()));
                tsfh = tsfh.add(new BigDecimal(DCESheet.getRow(i).getCell(7).getNumericCellValue()));
                yysfxj = yysfxj.add(new BigDecimal(DCESheet.getRow(i).getCell(8).getNumericCellValue()));
                jd = jd.add(new BigDecimal(DCESheet.getRow(i).getCell(9).getNumericCellValue()));
            }
            Row sheetRow= DCESheet.createRow(DCESheet.getLastRowNum()+1);
            sheetRow.createCell(0, CellType.STRING).setCellValue("合计");
            sheetRow.createCell(1, CellType.STRING);
            sheetRow.createCell(2, CellType.STRING);
            sheetRow.createCell(3, CellType.STRING);
            sheetRow.createCell(4, CellType.NUMERIC).setCellValue(sjsxf.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(5, CellType.NUMERIC).setCellValue(sqsfh.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(6, CellType.NUMERIC).setCellValue(khfh.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tsfh.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(8, CellType.NUMERIC).setCellValue(yysfxj.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(9, CellType.NUMERIC).setCellValue(jd.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            
            Row footRow= DCESheet.createRow(DCESheet.getLastRowNum()+3);
            if("营销总部".equals(depName)){
                footRow.createCell(0, CellType.STRING).setCellValue("营销总部复核人： ");
                footRow.createCell(4, CellType.STRING).setCellValue("审核人： ");
                footRow.createCell(8, CellType.STRING).setCellValue("制表人： ");
            }else {
                footRow.createCell(0, CellType.STRING).setCellValue("业务部门经理： ");
                footRow.createCell(4, CellType.STRING).setCellValue("审核人： ");
                footRow.createCell(8, CellType.STRING).setCellValue("制表人： ");
            }
        }
        //能源合计
        {
            BigDecimal sjsxf = BigDecimal.ZERO;//上交手续费
            BigDecimal sqsfh = BigDecimal.ZERO;//上期所返还
            BigDecimal khfh = BigDecimal.ZERO;//客户返还
            BigDecimal tsfh = BigDecimal.ZERO;//剔税返还
            BigDecimal yysfxj = BigDecimal.ZERO;//营业税风险金
            BigDecimal jd = BigDecimal.ZERO;//净得
            for (int i = 2; i <= INESheet.getLastRowNum(); i++) {
                sjsxf = sjsxf.add(new BigDecimal(INESheet.getRow(i).getCell(4).getNumericCellValue()));
                sqsfh = sqsfh.add(new BigDecimal(INESheet.getRow(i).getCell(5).getNumericCellValue()));
                khfh = khfh.add(new BigDecimal(INESheet.getRow(i).getCell(6).getNumericCellValue()));
                tsfh = tsfh.add(new BigDecimal(INESheet.getRow(i).getCell(7).getNumericCellValue()));
                yysfxj = yysfxj.add(new BigDecimal(INESheet.getRow(i).getCell(8).getNumericCellValue()));
                jd = jd.add(new BigDecimal(INESheet.getRow(i).getCell(9).getNumericCellValue()));
            }
            Row sheetRow= INESheet.createRow(INESheet.getLastRowNum()+1);
            sheetRow.createCell(0, CellType.STRING).setCellValue("合计");
            sheetRow.createCell(1, CellType.STRING);
            sheetRow.createCell(2, CellType.STRING);
            sheetRow.createCell(3, CellType.STRING);
            sheetRow.createCell(4, CellType.NUMERIC).setCellValue(sjsxf.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(5, CellType.NUMERIC).setCellValue(sqsfh.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(6, CellType.NUMERIC).setCellValue(khfh.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(7, CellType.NUMERIC).setCellValue(tsfh.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(8, CellType.NUMERIC).setCellValue(yysfxj.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            sheetRow.createCell(9, CellType.NUMERIC).setCellValue(jd.divide(new BigDecimal("2"),RoundingMode.HALF_UP).doubleValue());
            
            Row footRow= INESheet.createRow(INESheet.getLastRowNum()+3);
            if("营销总部".equals(depName)){
                footRow.createCell(0, CellType.STRING).setCellValue("营销总部复核人： ");
                footRow.createCell(4, CellType.STRING).setCellValue("审核人： ");
                footRow.createCell(8, CellType.STRING).setCellValue("制表人： ");
            }else {
                footRow.createCell(0, CellType.STRING).setCellValue("业务部门经理： ");
                footRow.createCell(4, CellType.STRING).setCellValue("审核人： ");
                footRow.createCell(8, CellType.STRING).setCellValue("制表人： ");
            }
        }
        
        //表格样式
        XSSFCellStyle bodyStyle = setStyle(wb.createCellStyle());
        for(int i=2;i<=SHFESheet.getLastRowNum()-3;i++){
            String rate = SHFESheet.getRow(i).getCell(3).getStringCellValue();
            for (int j = 0; j < SHFESheet.getRow(i).getLastCellNum(); j++) {
                if(SHFESheet.getRow(i).getCell(j)!=null){
                    if(StringUtils.isBlank(rate)){
                        SHFESheet.getRow(i).getCell(j).setCellStyle(yellowStyle);
                    }else {
                        SHFESheet.getRow(i).getCell(j).setCellStyle(bodyStyle);
                    }
                }
            }
        }
        for(int i=2;i<=CZCESheet.getLastRowNum()-3;i++){
            String rate = CZCESheet.getRow(i).getCell(3).getStringCellValue();
            for (int j = 0; j < CZCESheet.getRow(i).getLastCellNum(); j++) {
                if(CZCESheet.getRow(i).getCell(j)!=null){
                    if(StringUtils.isBlank(rate)){
                        CZCESheet.getRow(i).getCell(j).setCellStyle(yellowStyle);
                    }else {
                        CZCESheet.getRow(i).getCell(j).setCellStyle(bodyStyle);
                    }
                }
            }
        }
        for(int i=2;i<=DCESheet.getLastRowNum()-3;i++){
            String rate = DCESheet.getRow(i).getCell(3).getStringCellValue();
            for (int j = 0; j < DCESheet.getRow(i).getLastCellNum(); j++) {
                if(DCESheet.getRow(i).getCell(j)!=null){
                    if(StringUtils.isBlank(rate)){
                        DCESheet.getRow(i).getCell(j).setCellStyle(yellowStyle);
                    }else {
                        DCESheet.getRow(i).getCell(j).setCellStyle(bodyStyle);
                    }
                }
            }
        }
        for(int i=2;i<=INESheet.getLastRowNum()-3;i++){
            String rate = INESheet.getRow(i).getCell(3).getStringCellValue();
            for (int j = 0; j < INESheet.getRow(i).getLastCellNum(); j++) {
                if(INESheet.getRow(i).getCell(j)!=null){
                    if(StringUtils.isBlank(rate)){
                        INESheet.getRow(i).getCell(j).setCellStyle(yellowStyle);
                    }else {
                        INESheet.getRow(i).getCell(j).setCellStyle(bodyStyle);
                    }
                }
            }
        }
        
        //删除空的能源sheet
        if(INESheet.getLastRowNum() <=5 ){
            wb.removeSheetAt(3);
        }
        
        return wb;
    }
    
    //设置边框
    private XSSFCellStyle setStyle(XSSFCellStyle style) {
         style.setAlignment(HorizontalAlignment.CENTER);
      
         style.setBorderBottom(BorderStyle.THIN);
         style.setBorderTop(BorderStyle.THIN);
         style.setBorderBottom(BorderStyle.THIN);
         style.setBorderLeft(BorderStyle.THIN);
         style.setBorderRight(BorderStyle.THIN);
         
         style.setTopBorderColor(new XSSFColor(Color.black));
         style.setBottomBorderColor(new XSSFColor(Color.black));
         style.setLeftBorderColor(new XSSFColor(Color.black));
         style.setRightBorderColor(new XSSFColor(Color.black));
         
         return style;
    }
    
    /**
     * 交返汇总
     * @param month
     * @return
     */
    public XSSFWorkbook exportSummary(String month,List<String> depNames){
        
        XSSFWorkbook wb = new XSSFWorkbook();
        //标题样式
        XSSFCellStyle labelStyle = wb.createCellStyle();
        XSSFFont font = wb.createFont(); 
        //font.setBold(true);//粗体显示    
        font.setFontHeightInPoints((short) 24);//设置字体大小
        font.setFontName("宋体");
        labelStyle.setFont(font);
        labelStyle.setAlignment(HorizontalAlignment.CENTER);
        labelStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        setStyle(labelStyle);
        //内容样式
        XSSFCellStyle contextStyle = wb.createCellStyle();
        XSSFFont font1 = wb.createFont(); 
        //font.setBold(true);//粗体显示    
        font1.setFontHeightInPoints((short) 18);//设置字体大小
        font1.setFontName("宋体");
        contextStyle.setFont(font1);
        contextStyle.setAlignment(HorizontalAlignment.CENTER);
        contextStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        setStyle(contextStyle);
        
        //数字格式
        DataFormat format = wb.createDataFormat();
        XSSFCellStyle bodyStyle = wb.createCellStyle();
        XSSFFont font5 = wb.createFont(); 
        //font.setBold(true);//粗体显示    
        font5.setFontHeightInPoints((short) 18);//设置字体大小
        font5.setFontName("宋体");
        bodyStyle.setFont(font5);
        bodyStyle.setAlignment(HorizontalAlignment.CENTER);
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        bodyStyle.setDataFormat(format.getFormat("#,##0.00"));
        setStyle(bodyStyle);
        
        //在sheet里增加合并单元格  
        CellRangeAddress cra=new CellRangeAddress(0, 0, 1, 2);   //合并单元格
        
        XSSFSheet sh = wb.createSheet("上期");
        {
            sh.addMergedRegion(cra);
            XSSFRow firstRow = sh.createRow(0);
            firstRow.setHeight((short) (40*20));
            XSSFCell titleCell = firstRow.createCell(1, CellType.STRING);
            titleCell.setCellStyle(labelStyle);
            titleCell.setCellValue("渤海期货"+month+"上期所返还汇总表");
            sh.setColumnWidth(1, 256*58);
            sh.setColumnWidth(2, 256*58);
            
            //表头
            XSSFRow newRow =  sh.createRow(sh.getLastRowNum()+1);
            newRow.setHeight((short) (40*20));
            newRow.createCell(1).setCellStyle(contextStyle);
            newRow.getCell(1).setCellValue("营业部");
            newRow.createCell(2).setCellStyle(contextStyle);
            newRow.getCell(2).setCellValue("上期所");
            
        }
        
        XSSFSheet zz = wb.createSheet("郑商");
        {
            zz.addMergedRegion(cra);
            XSSFRow firstRow = zz.createRow(0);
            firstRow.setHeight((short) (40*20));
            XSSFCell titleCell = firstRow.createCell(1, CellType.STRING);
            titleCell.setCellStyle(labelStyle);
            titleCell.setCellValue("渤海期货"+month+"郑商所返还汇总表");
            zz.setColumnWidth(1, 256*58);
            zz.setColumnWidth(2, 256*58);
          //表头
            XSSFRow newRow =  zz.createRow(zz.getLastRowNum()+1);
            newRow.setHeight((short) (40*20));
            newRow.createCell(1).setCellStyle(contextStyle);
            newRow.getCell(1).setCellValue("营业部");
            newRow.createCell(2).setCellStyle(contextStyle);
            newRow.getCell(2).setCellValue("郑商所");
        }
        
        XSSFSheet dl = wb.createSheet("大商");
        {
            dl.addMergedRegion(cra);
            XSSFRow firstRow = dl.createRow(0);
            firstRow.setHeight((short) (40*20));
            XSSFCell titleCell = firstRow.createCell(1, CellType.STRING);
            titleCell.setCellStyle(labelStyle);
            //String previousMonth = this.specialListMapper.selectPreviousMonth(month);
            titleCell.setCellValue("渤海期货"+month+"大商所返还汇总表");
            dl.setColumnWidth(1, 256*58);
            dl.setColumnWidth(2, 256*58);
          //表头
            XSSFRow newRow =  dl.createRow(dl.getLastRowNum()+1);
            newRow.setHeight((short) (40*20));
            newRow.createCell(1).setCellStyle(contextStyle);
            newRow.getCell(1).setCellValue("营业部");
            newRow.createCell(2).setCellStyle(contextStyle);
            newRow.getCell(2).setCellValue("大商所");
        }
        
        XSSFSheet ny = wb.createSheet("能源中心");
        {
            ny.addMergedRegion(cra);
            XSSFRow firstRow = ny.createRow(0);
            firstRow.setHeight((short) (40*20));
            XSSFCell titleCell = firstRow.createCell(1, CellType.STRING);
            titleCell.setCellStyle(labelStyle);
            //String previousMonth = this.specialListMapper.selectPreviousMonth(month);
            titleCell.setCellValue("渤海期货"+month+"能源中心返还汇总表");
            ny.setColumnWidth(1, 256*58);
            ny.setColumnWidth(2, 256*58);
          //表头
            XSSFRow newRow =  ny.createRow(ny.getLastRowNum()+1);
            newRow.setHeight((short) (40*20));
            newRow.createCell(1).setCellStyle(contextStyle);
            newRow.getCell(1).setCellValue("营业部");
            newRow.createCell(2).setCellStyle(contextStyle);
            newRow.getCell(2).setCellValue("能源中心");
        }
        
        for (String depName : depNames) {
            XSSFWorkbook depWb = null;
            try {
                depWb = new XSSFWorkbook(depName+".xlsx");
                {
                    XSSFSheet sheet = depWb.getSheetAt(0);
                    XSSFRow row = sheet.getRow(sheet.getLastRowNum()-3);
                    XSSFCell cell = row.getCell(row.getLastCellNum()-1);
                    double net = cell.getNumericCellValue();
                    XSSFRow newRow =  sh.createRow(sh.getLastRowNum()+1);
                    newRow.setHeight((short) (40*20));
                    newRow.createCell(1).setCellStyle(contextStyle);
                    newRow.getCell(1).setCellValue(depName);
                    newRow.createCell(2,CellType.NUMERIC).setCellStyle(bodyStyle);
                    newRow.getCell(2).setCellValue(net);
                }
                {
                    XSSFSheet sheet = depWb.getSheetAt(1);
                    XSSFRow row = sheet.getRow(sheet.getLastRowNum()-3);
                    XSSFCell cell = row.getCell(row.getLastCellNum()-1);
                    double net = cell.getNumericCellValue();
                    XSSFRow newRow =  zz.createRow(zz.getLastRowNum()+1);
                    newRow.setHeight((short) (40*20));
                    newRow.createCell(1).setCellStyle(contextStyle);
                    newRow.getCell(1).setCellValue(depName);
                    newRow.createCell(2,CellType.NUMERIC).setCellStyle(bodyStyle);
                    newRow.getCell(2).setCellValue(net);
                }
                {
                    XSSFSheet sheet = depWb.getSheetAt(2);
                    XSSFRow row = sheet.getRow(sheet.getLastRowNum()-3);
                    XSSFCell cell = row.getCell(row.getLastCellNum()-1);
                    double net = cell.getNumericCellValue();
                    XSSFRow newRow =  dl.createRow(dl.getLastRowNum()+1);
                    newRow.setHeight((short) (40*20));
                    newRow.createCell(1).setCellStyle(contextStyle);
                    newRow.getCell(1).setCellValue(depName);
                    newRow.createCell(2,CellType.NUMERIC).setCellStyle(bodyStyle);
                    newRow.getCell(2).setCellValue(net);
                }
                if(depWb.getNumberOfSheets()>3){
                    XSSFSheet sheet = depWb.getSheetAt(3);
                    if(sheet != null){
                        XSSFRow row = sheet.getRow(sheet.getLastRowNum()-3);
                        XSSFCell cell = row.getCell(row.getLastCellNum()-1);
                        double net = cell.getNumericCellValue();
                        XSSFRow newRow =  ny.createRow(ny.getLastRowNum()+1);
                        newRow.setHeight((short) (40*20));
                        newRow.createCell(1).setCellStyle(contextStyle);
                        newRow.getCell(1).setCellValue(depName);
                        newRow.createCell(2,CellType.NUMERIC).setCellStyle(bodyStyle);
                        newRow.getCell(2).setCellValue(net);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(depWb != null){
                        depWb.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        for (int i = 0; i < 4; i++) {
            XSSFSheet sheet = wb.getSheetAt(i);
            XSSFRow newRow =  sheet.createRow(sheet.getLastRowNum()+1);
            newRow.setHeight((short) (40*20));
            newRow.createCell(1).setCellStyle(contextStyle);
            newRow.getCell(1).setCellValue("公司合计");
            newRow.createCell(2,CellType.NUMERIC).setCellStyle(bodyStyle);
            
            BigDecimal sum = BigDecimal.ZERO;
            for(int j = 2; j<=sheet.getLastRowNum(); j++){
                sum = sum.add(new BigDecimal(sheet.getRow(j).getCell(2).getNumericCellValue()));
            }
            newRow.getCell(2).setCellValue(sum.doubleValue());
        }
        
        
        //签名样式
        XSSFCellStyle footStyle = wb.createCellStyle();
        XSSFFont font2 = wb.createFont(); 
        //font.setBold(true);//粗体显示    
        font2.setFontHeightInPoints((short) 16);//设置字体大小
        font2.setFontName("宋体");
        footStyle.setFont(font2);
        footStyle.setAlignment(HorizontalAlignment.CENTER);
        for (int i = 0; i < 4; i++) {
            XSSFSheet sheet = wb.getSheetAt(i);
            CellRangeAddress cra1=new CellRangeAddress(sheet.getLastRowNum()+3, sheet.getLastRowNum()+3, 0, 3);
            sheet.addMergedRegion(cra1);
            XSSFRow newRow =  sheet.createRow(sheet.getLastRowNum()+3);
            newRow.createCell(0).setCellStyle(footStyle);
            newRow.getCell(0).setCellValue("总经理：        分管领导：         财务总监：         财务部经理：          经纪业务部审核人：");
        }
        
        
        return wb;
    }
    
}
