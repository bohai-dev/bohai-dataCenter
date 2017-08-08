package com.bohai.dataCenter.service.impl;

import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CloseData;
import com.bohai.dataCenter.service.CloseDataService;
import com.bohai.dataCenter.service.FileUploadService;
import com.bohai.dataCenter.util.DateFormatterUtil;

@Service("closeDataTXTUploadService")
public class CloseDataTXTUploadServiceImpl implements FileUploadService {

	static Logger logger = Logger.getLogger(CloseDataTXTUploadServiceImpl.class);
	
	@Autowired
	private CloseDataService closeDataService;
	
	@Override
	public void upload(MultipartFile file, Object... objects) throws BohaiException {
		
		logger.debug("上传期货平仓明细数据");
		
		//文件名
		String fileName = (String) objects[0];
		//获取日期
		String dateStr = fileName.substring(17, 25);
		logger.debug("交易日为："+dateStr);
		try {
			Date tradeDate = DateFormatterUtil.getDateyyyyMMdd(dateStr);
			//删除历史数据
			this.closeDataService.removeByDate(tradeDate);
		} catch (ParseException e1) {
			logger.error("时间格式转换错误");
			throw new BohaiException("", "时间格式转换错误");
		}
		
		Reader in;
		try {
			in = new InputStreamReader(file.getInputStream(),"GBK");
			CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT.withDelimiter('@'));
			for (final CSVRecord record : parser) {
				CloseData closeData = new CloseData();
				//日期
				closeData.setDay(record.get(0));
				//客户账户
				closeData.setInvestorNo(record.get(1));
				//品种合约
				closeData.setInstrument(record.get(2));
				//成交流水号
				closeData.setDealSerial(record.get(3));
				//买卖标志
				closeData.setDirection(record.get(4));
				//成交价
				closeData.setDealPrice(new BigDecimal(record.get(5)));
				//开仓价
				closeData.setOpenPrice(new BigDecimal(record.get(6)));
				//成交量
				closeData.setVolume(Long.parseLong(record.get(7)));
				//昨结算价
				closeData.setYstatement(new BigDecimal(record.get(8)));
				//今结算价
				closeData.setTstatement(new BigDecimal(record.get(9)));
				//平仓盈亏(逐日盯市)
				closeData.setDailyMarketWin(new BigDecimal(record.get(10)));
				//平仓盈亏(逐笔对冲)
				closeData.setHedgeWin(new BigDecimal(record.get(11)));
				//原成交流水号
				closeData.setOrginialSerial(record.get(12));
				//交易编码
				closeData.setTradeCode(record.get(13));
				//币种
				closeData.setCurrency(record.get(14));
				//成交日期
				closeData.setTradeDate(record.get(15));
				
				closeDataService.save(closeData);
			}
			
		} catch (Exception e) {
			logger.error("上传文件失败",e);
			throw new BohaiException("", "上传文件失败");
		}

	}

}
