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
import com.bohai.dataCenter.entity.CtpTradeData;
import com.bohai.dataCenter.persistence.CtpTradeDataMapper;
import com.bohai.dataCenter.service.FileUploadService;

/**
 * CTP交易明细文件上传
 * @author caojia
 */
@Service("ctpTradeDataCSVUpload")
public class CtpTradeDataCSVUploadImpl implements FileUploadService {
	
	static Logger logger = Logger.getLogger(CtpTradeDataCSVUploadImpl.class);
	
	@Autowired
	private CtpTradeDataMapper ctpTradeDataMapper;

	@Override
	public void upload(MultipartFile file, Object... objects) throws BohaiException {
		
		logger.debug("CTP交易明细信息上传");
		try {
			Reader in = new InputStreamReader(file.getInputStream(),"GBK");
			CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT);
			
			//交易日
			int tradeDateIndex = 0;
			//投资者代码
			int investorNoIndex = 0;
			//投资者名称
			int investorNameIndex = 0;
			//交易所
			int exchangeNameIndex = 0;
			//合约
			int instrumentIndex = 0;
			//买卖
			int directionIndex = 0;
			//开平
			int offsetFlagIndex = 0;
			//投保
			int hedgeFlagIndex = 0;
			//成交手数
			int volumeIndex = 0;
			//成交价格
			int dealPriceIndex = 0;
			//成交额
			int trunoverIndex = 0;
			//手续费
			int chargeIndex = 0;
			//交易所手续费
			int exchangeChargeIndex = 0;
			//成交编号
			int tradeNoIndex = 0;
			
			
			for (final CSVRecord record : parser) {
				//获取表头
				if(record.getRecordNumber() == 3){
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
							}else if (colName.replace(" ", "").equals("交易所")) {
								exchangeNameIndex = i;
							}else if (colName.replace(" ", "").equals("合约")) {
								instrumentIndex = i;
							}else if (colName.replace(" ", "").equals("买卖")) {
								directionIndex = i;
							}else if (colName.replace(" ", "").equals("开平")) {
								offsetFlagIndex = i;
							}else if (colName.replace(" ", "").equals("投保")) {
								hedgeFlagIndex = i;
							}else if (colName.replace(" ", "").equals("成交手数")) {
								volumeIndex = i;
							}else if (colName.replace(" ", "").equals("成交价格")) {
								dealPriceIndex = i;
							}else if (colName.replace(" ", "").equals("成交金额")) {
								trunoverIndex = i;
							}else if (colName.replace(" ", "").equals("手续费")) {
								chargeIndex = i;
							}else if (colName.replace(" ", "").equals("交易所手续费")) {
								exchangeChargeIndex = i;
							}else if (colName.replace(" ", "").equals("成交编号")) {
                                tradeNoIndex = i;
                            }
						}
					}
				}
				
				
				if(record.getRecordNumber() > 3){
					//交易日期
					String tradeDate = record.get(tradeDateIndex);
					//投资者标号
					String investorNo = record.get(investorNoIndex);
					//投资者名称
					String investorName = record.get(investorNameIndex);
					//交易所
					String exchangeName = record.get(exchangeNameIndex);
					//合约
					String instrument = record.get(instrumentIndex);
					//大商所期权也返
					//20200205 只有证商所期权不返
					if("郑商所".equals(exchangeName)){
					    if(StringUtils.isEmpty(instrument) || instrument.indexOf("-") > -1){
					        //期权不返
					        continue;
					    }
					    if(instrument.length() > 8){
					        //期权不返
					        continue;
					    }
					}
					//买卖方向
					String direction = record.get(directionIndex);
					//开平
					String offsetFlag = record.get(offsetFlagIndex);
					//投保
					String hedgeFlag = record.get(hedgeFlagIndex);
					//成交手数
					String volume = record.get(volumeIndex);
					//成交价格
					String dealPrice = record.get(dealPriceIndex).replace(",", "");
					//成交金额
					String trunover = record.get(trunoverIndex).replace(",", "");
					//手续费
					String charge = record.get(chargeIndex).replace(",", "");
					//交易所手续费
					String exchangeCharge = record.get(exchangeChargeIndex).replace(",", "");
					//成交编号
					String tradeNo = record.get(tradeNoIndex);
					
					if(StringUtils.isEmpty(investorNo)||StringUtils.isEmpty(investorName)){
						continue;
					}
					
					//CTP交易明细					
					CtpTradeData tradeData = new CtpTradeData();
					tradeData.setTradeDate(tradeDate);
					tradeData.setInvestorNo(investorNo);
					tradeData.setInvestorName(investorName);
					tradeData.setExchangeName(exchangeName);
					tradeData.setInstrument(instrument);
					tradeData.setDirection(direction);
					tradeData.setOffsetFlag(offsetFlag);
					tradeData.setHedgeFlag(hedgeFlag);
					tradeData.setVolume(volume);
					tradeData.setDealPrice(dealPrice);
					tradeData.setTrunover(trunover);
					tradeData.setCharge(charge);
					tradeData.setExchangeCharge(exchangeCharge);
					tradeData.setTradeNo(tradeNo);
					try {
						ctpTradeDataMapper.insertSelective(tradeData);
					} catch (Exception e) {
						logger.error("保存CTP交易明细失败",e);
						throw new BohaiException("", "保存CTP交易明细失败");
					}
				}
		    }
			
			
		} catch (IOException e1) {
			logger.error("读取资CTP交易明细失败",e1);
			throw new BohaiException("", "读取资CTP交易明细失败");
		}

	}

}
