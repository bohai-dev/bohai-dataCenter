package com.bohai.dataCenter.service.impl;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CapitalStatement;
import com.bohai.dataCenter.service.CapitalStatementService;
import com.bohai.dataCenter.service.FileUploadService;
import com.bohai.dataCenter.util.DateFormatterUtil;

/**
 * 资金对账文件上传
 * @author caojia
 */
@Service("capitalStatement2007UploadService")
public class CapitalStatement2007UploadServiceImpl implements FileUploadService {

	static Logger logger = Logger.getLogger(CapitalStatement2007UploadServiceImpl.class);
	
	@Autowired
	private CapitalStatementService capitalStatementService;
	
	@Override
	public void upload(MultipartFile file, Object... objects) throws BohaiException {

		logger.debug("资金对账表上传");
		
		XSSFWorkbook wb = null;
		
		try {
			wb = new XSSFWorkbook(file.getInputStream());
			
		} catch (Exception e1) {
			logger.error("资金对账上传失败",e1);
			throw new BohaiException("", "读取资金对账文件失败");
		}
		
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row = sheet.getRow(3);
		
		//第一列
		int mincol = row.getFirstCellNum();
		//最后一列
		int maxcol = row.getLastCellNum();
		
		//交易日所在的列
		int tradeDateIndex = 0;
		//投资者代码所在列
		int investorNoIndex = 0;
		//投资者名称所在列
		int investorNameIndex = 0;
		//组织架构代码所在列
		int deptNoIndex = 0;
		//组织架构名称所在列
		int deptNameIndex = 0;
		//可用资金所在列
		int availableIndex = 0;
		//投资者权益所在列
		int investorRightsIndex = 0;
		
		for(int i = mincol; i < maxcol ; i++){
			String colName = row.getCell(i).getStringCellValue();
			if(StringUtils.isEmpty(colName)){
				continue;
			}
			
			if(colName.indexOf("交易日")>-1){
				tradeDateIndex = i;
			}else if (colName.indexOf("投资者代码")>-1) {
				investorNoIndex = i;
			}else if (colName.indexOf("投资者名称")>-1){
				investorNameIndex = i;
			}else if (colName.indexOf("组织架构代码")>-1) {
				deptNoIndex = i;
			}else if (colName.indexOf("组织架构名称")>-1) {
				deptNameIndex = i;
			}else if (colName.indexOf("可用资金")>-1) {
				availableIndex = i;
			}else if (colName.indexOf("投资者权益")>-1) {
				investorRightsIndex = i;
			}
		}
		
		for(int i = 4;i<sheet.getLastRowNum();i++){
			//资金对账信息
			CapitalStatement capitalStatement = new CapitalStatement();
			//交易日
			String tradeDateStr = sheet.getRow(i).getCell(tradeDateIndex).getStringCellValue();
			
			capitalStatement.setTradeDateStr(tradeDateStr);
			
			//投资者代码
			String investorNo = sheet.getRow(i).getCell(investorNoIndex).getStringCellValue();
			capitalStatement.setInvestorNo(investorNo);
			//投资者名称
			String investorName = sheet.getRow(i).getCell(investorNameIndex).getStringCellValue();
			capitalStatement.setInvestorName(investorName);
			
			if(StringUtils.isEmpty(tradeDateStr)||StringUtils.isEmpty(investorNo)||StringUtils.isEmpty(investorName)){
				continue;
			}
			
			try {
				capitalStatement.setTradeDate(DateFormatterUtil.getDateyyyyMMdd(tradeDateStr));
			} catch (Exception e) {
				logger.error("时间转换失败",e);
			}
			//组织架构代码
			capitalStatement.setDeptCode(sheet.getRow(i).getCell(deptNoIndex).getStringCellValue());
			//组织架构名称
			capitalStatement.setDeptName(sheet.getRow(i).getCell(deptNameIndex).getStringCellValue());
			//可用资金
			capitalStatement.setAvailableFunds(sheet.getRow(i).getCell(availableIndex).getNumericCellValue()+"");
			//客户权益
			capitalStatement.setInvestorRights(sheet.getRow(i).getCell(investorRightsIndex).getNumericCellValue()+"");
			
			capitalStatementService.saveOrUpdate(capitalStatement);
		}
		
	}

}
