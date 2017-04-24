package com.bohai.dataCenter.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CapitalStatement;
import com.bohai.dataCenter.service.CapitalStatementService;
import com.bohai.dataCenter.service.FileUploadService;
import com.bohai.dataCenter.util.DateFormatterUtil;

import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * 资金对账文件上传
 * @author caojia
 */
@Service("capitalStatementUploadService")
public class CapitalStatementUploadServiceImpl implements FileUploadService {

	static Logger logger = Logger.getLogger(CapitalStatementUploadServiceImpl.class);
	
	@Autowired
	private CapitalStatementService capitalStatementService;
	
	@Override
	public void upload(MultipartFile file, Object... objects) throws BohaiException {

		logger.debug("资金对账表上传");
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(file.getInputStream());
		}catch(Exception e){
			logger.error("资金对账上传失败",e);
			throw new BohaiException("", "读取资金对账文件失败");
		}
		
		Sheet capitalSheet = workbook.getSheet(0);
		
		//总列数
		int cols = capitalSheet.getColumns();
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
		
		for(int i = 0; i < cols ; i++){
			String colName = capitalSheet.getCell(i, 3).getContents();
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
		
		
		for(int i = 4;i<capitalSheet.getRows();i++){
			//资金对账信息
			CapitalStatement capitalStatement = new CapitalStatement();
			//交易日
			String tradeDateStr = capitalSheet.getCell(tradeDateIndex, i).getContents();
			
			capitalStatement.setTradeDateStr(tradeDateStr);
			
			//投资者代码
			String investorNo = capitalSheet.getCell(investorNoIndex, i).getContents();
			capitalStatement.setInvestorNo(investorNo);
			//投资者名称
			String investorName = capitalSheet.getCell(investorNameIndex, i).getContents();
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
			capitalStatement.setDeptCode(capitalSheet.getCell(deptNoIndex, i).getContents());
			//组织架构名称
			capitalStatement.setDeptName(capitalSheet.getCell(deptNameIndex, i).getContents());
			//可用资金
			capitalStatement.setAvailableFunds(((NumberCell)capitalSheet.getCell(availableIndex, i)).getValue()+"");
			//客户权益
			capitalStatement.setInvestorRights(((NumberCell)capitalSheet.getCell(investorRightsIndex, i)).getValue()+"");
			
			capitalStatementService.saveOrUpdate(capitalStatement);
		}
		
	}

}
