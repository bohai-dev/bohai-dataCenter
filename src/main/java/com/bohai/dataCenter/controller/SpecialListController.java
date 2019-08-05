package com.bohai.dataCenter.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.service.impl.SpecialListServiceImpl;
import com.bohai.dataCenter.vo.ResponseBody;
import com.github.pagehelper.PageInfo;

/**
 * 交返接口
 * @author caojia
 * @since 2019-07-19
 */
@RestController
public class SpecialListController {
    
    @Autowired
    private SpecialListServiceImpl specialListService;

    @RequestMapping(value = "saveSpecial", method = RequestMethod.POST)
    public ResponseBody saveSpecial(@RequestBody Map<String, String> param) {
        
        ResponseBody responseBody = new ResponseBody();
        try {
            specialListService.saveSpecial(param);
        } catch (BohaiException e) {
            responseBody.setCode(e.getErrorCode());
            responseBody.setMsg(e.getMessage());
        }
        return responseBody;
    }
    
    @RequestMapping(value = "querySpecial", method = RequestMethod.GET)
    public PageInfo<Map<String, String>> querySpecial(@RequestParam Map<String, String> param, 
            @RequestParam("pageNum")int pageNum, 
            @RequestParam("pageSize")int pageSize){
        return this.specialListService.querySpecial(param, pageNum, pageSize);
    }
    
    @RequestMapping(value = "removeSpecial", method = RequestMethod.GET)
    public ResponseBody remove(@RequestParam("ID")String id, 
            @RequestParam(value = "REMOVE_ALL", required=false)String removeAll, 
            @RequestParam(value = "MEDIATOR_NO", required=false)String mediatorNo){
        this.specialListService.remove(id, removeAll, mediatorNo);
        return new ResponseBody();
    }
    
    
    @RequestMapping("exportSpecial")
    public void exportSpecial(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        XSSFWorkbook wb = this.specialListService.exportSpecial();
        OutputStream output=response.getOutputStream();
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");  
        String FileName = "交返特例名单";
        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(FileName, "UTF-8")+".xlsx");
        wb.write(output);  
        output.close(); 
        
    }
}
