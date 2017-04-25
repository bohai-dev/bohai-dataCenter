package com.bohai.dataCenter.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.service.ReportService;
import com.bohai.dataCenter.service.UploadFactory;
import com.bohai.dataCenter.service.factory.FileUploadFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
public class FileUploadController {
	
	static Logger logger = Logger.getLogger(FileUploadController.class);
	
	@Autowired
	FileUploadFactory fileUploadFactory ;
	
	@Autowired
	UploadFactory rebateUploadFactory;
	
	@Autowired
	UploadFactory exchangeRebateUploadFactory;
	
	@Autowired
	private ReportService reportService;
	
	@RequestMapping(value="fileUpload",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> fileUpload(@RequestParam("file_data") MultipartFile file, HttpServletRequest request) throws BohaiException {
		logger.debug("文件上传");
		
		String fileName;
		try {
			fileName = new String(file.getOriginalFilename().getBytes("ISO-8859-1"),"UTF-8");
			logger.debug(fileName);
			fileUploadFactory.createService(fileName).upload(file);
			
		} catch (UnsupportedEncodingException e1) {
			logger.error("格式不支持",e1);
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("status", "success");
		return map;
	}
	
	@RequestMapping(value="uploadCapitalStatement",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> uploadCapitalStatement(@RequestParam("file_data") MultipartFile file, HttpServletRequest request) throws BohaiException{
		logger.debug("上传返利息统计文件");
		
		String fileName;
		try {
			fileName = new String(file.getOriginalFilename().getBytes("ISO-8859-1"),"UTF-8");
			logger.debug(fileName);
			rebateUploadFactory.createService(fileName).upload(file,fileName);
			
		} catch (UnsupportedEncodingException e1) {
			logger.error("格式不支持",e1);
		}
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("status", "success");
		return map;
	}
	
	@RequestMapping(value="uploadExchangeRebate",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> uploadExchangeRebate(@RequestParam("file_data") MultipartFile file, HttpServletRequest request) throws BohaiException{
		logger.debug("上传交易所返还所需文件");
		
		String fileName;
		try {
			fileName = new String(file.getOriginalFilename().getBytes("ISO-8859-1"),"UTF-8");
			logger.debug(fileName);
			exchangeRebateUploadFactory.createService(fileName).upload(file,fileName);
			
		} catch (UnsupportedEncodingException e1) {
			logger.error("格式不支持",e1);
		}
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("status", "success");
		return map;
	}
	
	
	/**
	 * 文件下载
	 */
	@RequestMapping(value="download",method=RequestMethod.GET)
	public void download(HttpServletRequest request, HttpServletResponse response){
		
		response.setContentType("application/x-xls");  
	    response.setCharacterEncoding("UTF-8");  
	     try  
	     {
	         String FileName = new String("软件费用统计".getBytes("UTF-8"),"ISO-8859-1");//java.net.URLEncoder.encode("软件费用统计", "ISO-8859-1");  
	         response.setHeader("Content-Disposition", "attachment;filename=" + FileName + ".xls");  
	         // 产生工作簿对象  
	         WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());  
	         //产生工作表对象
	         WritableSheet sheet = workbook.createSheet("软件费用统计", 0);
	         List<Map<String,Object>> list = reportService.queryTBSoftReport();
	         sheet.addCell(new Label(0, 0, "序号"));
	         sheet.addCell(new Label(1, 0, "投资者代码"));
	         sheet.addCell(new Label(2, 0, "投资者姓名"));
	         sheet.addCell(new Label(3, 0, "所在营业部"));
	         sheet.addCell(new Label(4, 0, "总佣金"));
	         sheet.addCell(new Label(5, 0, "交易所佣金"));
	         sheet.addCell(new Label(6, 0, "软件服务费"));
	         sheet.addCell(new Label(7, 0, "计费时间段"));
	         if(list != null && list.size()>0){
	        	 for(int i=0; i<list.size();i++){
	        		 sheet.addCell(new Label(0, i+1, i+1+""));
	    	         sheet.addCell(new Label(1, i+1, (String) list.get(i).get("INVESTOR_NO")));
	    	         sheet.addCell(new Label(2, i+1, (String) list.get(i).get("INVESTOR_NAME")));
	    	         sheet.addCell(new Label(3, i+1, (String) list.get(i).get("DEPT")));
	    	         sheet.addCell(new Label(4, i+1, (String) list.get(i).get("CHARGE")));
	    	         sheet.addCell(new Label(5, i+1, (String) list.get(i).get("ONHAND_CHARGE")));
	    	         sheet.addCell(new Label(6, i+1, ((BigDecimal) list.get(i).get("SOFT_CHARGE")).toString()));
	    	         /*String batchNo = (String) list.get(i).get("BATCH_NO");
	    	         batchNo = batchNo.substring(batchNo.indexOf("："));*/
	    	         sheet.addCell(new Label(7, i+1, (String) list.get(i).get("BATCH_NO")));
	        	 }
	         }
	         workbook.write();
	         workbook.close();
	     }catch (Exception e){
	    	logger.error("导出失败",e); 
	     }finally{
	    	 
	     }  
	}
	
	

	
}
