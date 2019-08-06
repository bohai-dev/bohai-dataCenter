package com.bohai.dataCenter.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CrmCustomer;
import com.bohai.dataCenter.persistence.CrmCustomerMapper;
import com.bohai.dataCenter.persistence.SpecialListMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class SpecialListServiceImpl {

    static Logger logger = Logger.getLogger(SpecialListServiceImpl.class);
    
    @Autowired
    private SpecialListMapper specialListMapper;
    
    @Autowired
    private CrmCustomerMapper customerMapper;
    
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
        
        String[] customerHead = {"返还类型","是否名下所有客户","居间人编号","居间人名称","投资者编号","投资者名称","上期返还比例","大商返还比例","郑商返还比例","能源返还比例","中金返还比例","返还方式","生效日期","失效日期","备注"};
        
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
                //返还类型
                String returnType = list.get(i).get("RETURN_TYPE");
                if("1".equals(returnType)){
                    returnType = "客户";
                }else {
                    returnType = "居间人";
                }
                row2.createCell(0).setCellValue(returnType);
                //是否名下所有客户
                String isAll = list.get(i).get("IS_ALL");
                if("0".equals(isAll)){
                    isAll = "否";
                }else if("1".equals(isAll)){
                    isAll = "是";
                }
                row2.createCell(1).setCellValue(isAll);
                //居间人编号
                row2.createCell(2).setCellValue(list.get(i).get("MEDIATOR_NO"));
                //居间人名称
                row2.createCell(3).setCellValue(list.get(i).get("MEDIATOR_NAME"));
                //投资者编号
                row2.createCell(4).setCellValue(list.get(i).get("INVESTOR_NO"));
                //投资者名称
                row2.createCell(5).setCellValue(list.get(i).get("INVESTOR_NAME"));
                //上期返还比例
                row2.createCell(6).setCellValue(list.get(i).get("SHFE"));
                //大商返还比例
                row2.createCell(7).setCellValue(list.get(i).get("DCE"));
                //郑商返还比例
                row2.createCell(8).setCellValue(list.get(i).get("CZCE"));
                //能源返还比例
                row2.createCell(9).setCellValue(list.get(i).get("INE"));
                //中金返还比例
                row2.createCell(10).setCellValue(list.get(i).get("CFFEX"));
                //返还方式
                String returnWay = list.get(i).get("RETURN_WAY");
                if("1".equals(returnWay)){
                    returnWay = "加帐户";
                }else if("2".equals(returnWay)){
                    returnWay = "客户银行卡";
                }
                row2.createCell(11).setCellValue(returnWay);
                //生效日期
                row2.createCell(12).setCellValue(list.get(i).get("EFFECT_DATE"));
                //失效日期
                row2.createCell(13).setCellValue(list.get(i).get("EXPIRE_DATE"));
                //备注
                row2.createCell(14).setCellValue(list.get(i).get("REMARK"));
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
    
    public void exportZip(){
        
    }
    
}
