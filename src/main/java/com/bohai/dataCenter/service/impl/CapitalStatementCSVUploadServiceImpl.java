package com.bohai.dataCenter.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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

/**
 * 资金对账文件上传
 * @author caojia
 */
@Service("capitalStatementCSVUploadService")
public class CapitalStatementCSVUploadServiceImpl implements FileUploadService {

	static Logger logger = Logger.getLogger(CapitalStatementCSVUploadServiceImpl.class);
	
	@Autowired
	private CapitalStatementService capitalStatementService;
	
	@Override
	public void upload(MultipartFile file, Object... objects) throws BohaiException {

		logger.debug("资金对账表上传");
				
		try {
			Reader in = new InputStreamReader(file.getInputStream(),"GBK");
			CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT);
			
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
			//入金所在列
			int depositIndex = 0;
			//出金所在列
			int turnoutIndex = 0;
			for (final CSVRecord record : parser) {
		    	
				int s = record.size();
				if(s > 0 ){
					for(int i = 0; i<s; i++){
						String colName = record.get(i);
						if(StringUtils.isEmpty(colName)){
							continue;
						}
						
						if(colName.replace(" ", "").equals("交易日")){
							tradeDateIndex = i;
						}else if (colName.replace(" ", "").equals("投资者代码")) {
							investorNoIndex = i;
						}else if (colName.replace(" ", "").equals("投资者名称")){
							investorNameIndex = i;
						}else if (colName.replace(" ", "").equals("组织架构代码")) {
							deptNoIndex = i;
						}else if (colName.replace(" ", "").equals("组织架构名称")) {
							deptNameIndex = i;
						}else if (colName.replace(" ", "").equals("可用资金")) {
							availableIndex = i;
						}else if (colName.replace(" ", "").equals("投资者权益")) {
							investorRightsIndex = i;
						}else if (colName.replace(" ", "").equals("入金")) {
                            depositIndex = i;
                        }else if (colName.replace(" ", "").equals("出金")) {
                            turnoutIndex = i;
                        }
					}
				}
				
				if(record.getRecordNumber()==4){
					break;
				}
		    }
			
			try {
			    for (final CSVRecord record : parser) {
			    	
			    	//资金对账信息
					CapitalStatement capitalStatement = new CapitalStatement();
					//交易日
					String tradeDateStr = record.get(tradeDateIndex);
					
					capitalStatement.setTradeDateStr(tradeDateStr);
					
					//投资者代码
					String investorNo = record.get(investorNoIndex);
					capitalStatement.setInvestorNo(investorNo);
					//投资者名称
					String investorName = record.get(investorNameIndex);
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
					capitalStatement.setDeptCode(record.get(deptNoIndex));
					//组织架构名称
					capitalStatement.setDeptName(record.get(deptNameIndex));
					//可用资金
					capitalStatement.setAvailableFunds(record.get(availableIndex).replaceAll(",", ""));
					//客户权益
					capitalStatement.setInvestorRights(record.get(investorRightsIndex).replaceAll(",", ""));
					//入金
					capitalStatement.setDeposit(record.get(depositIndex).replace(",", ""));
					//出金
					capitalStatement.setTurnout(record.get(turnoutIndex).replace(",", ""));
					capitalStatementService.saveOrUpdate(capitalStatement);
			    	
			    }
			} finally {
			    parser.close();
			    in.close();
			}
			
		} catch (IOException e2) {
			logger.error("资金对账上传失败",e2);
			throw new BohaiException("", "读取资金对账文件失败");
		}
		
		
	}

}
