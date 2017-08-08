package com.bohai.dataCenter.service.impl;

import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.TradeData;
import com.bohai.dataCenter.service.FileUploadService;
import com.bohai.dataCenter.service.TradeDataService;
import com.bohai.dataCenter.util.DateFormatterUtil;

@Service("tradeDataTXTUploadService")
public class TradeDataTXTUploadServiceImpl implements FileUploadService {

	static Logger logger = Logger.getLogger(TradeDataTXTUploadServiceImpl.class);
	
	@Autowired
	private TradeDataService tradeDataService;
	
	@Override
	public void upload(MultipartFile file,Object... objects) throws BohaiException {
		
		logger.debug("上传期货交易明细数据");
		
		//文件名
		String fileName = (String) objects[0];
		//获取日期
		String dateStr = fileName.substring(11,19);
		logger.debug("交易日为："+dateStr);
		try {
			Date tradeDate = DateFormatterUtil.getDateyyyyMMdd(dateStr);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateStr = sdf.format(tradeDate);
			//删除历史数据
			this.tradeDataService.removeByDate(dateStr);
		} catch (ParseException e1) {
			logger.error("时间格式转换错误");
			throw new BohaiException("", "时间格式转换错误");
		}
		
		
		try {
			Reader in = new InputStreamReader(file.getInputStream(),"GBK");
			CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT.withDelimiter('@'));
			for (final CSVRecord record : parser) {
				//期货交易明细
				TradeData tradeData = new TradeData();
				//日期
				tradeData.setDay(record.get(0));
				//客户内部资金账户
				tradeData.setInvestorNo(record.get(1));
				//成交流水号
				tradeData.setSerialNo(record.get(2));
				//品种合约
				tradeData.setInstrument(record.get(3));
				//买卖标志
				tradeData.setDirection(record.get(4));
				//成交量
				tradeData.setVolume(Long.parseLong(record.get(5)));
				//成交价
				tradeData.setDealPrice(new BigDecimal(record.get(6)));
				//成交额
				tradeData.setTurnover(new BigDecimal(record.get(7)));
				//成交时间
				tradeData.setTradeTime(record.get(8));
				//开平仓标志
				tradeData.setOffsetFlag(record.get(9));
				//投机套保标志
				tradeData.setHadgeFlag(record.get(10));
				//平仓盈亏(逐日盯市)
				tradeData.setDailyMarketWin(new BigDecimal(record.get(11)));
				//平仓盈亏(逐笔对冲)
				tradeData.setHedgeWin(new BigDecimal(record.get(12)));
				//手续费
				tradeData.setCharge(new BigDecimal(record.get(13)));
				//交易编码
				tradeData.setTradeCode(record.get(14));
				//交易所统一标识
				tradeData.setExchangeId(record.get(15));
				//是否为交易会员
				tradeData.setIsMember(record.get(16));
				//报单号
				tradeData.setOrderNo(record.get(17));
				//席位号
				tradeData.setSeatNo(record.get(18));
				//币种
				tradeData.setCurrency(record.get(19));
				//成交日期
				tradeData.setTradeDate(record.get(20));
				
				this.tradeDataService.saveOrUpdate(tradeData);
			}
			
		} catch (Exception e) {
			logger.error("上传文件失败",e);
			throw new BohaiException("", "上传文件失败");
		}
	}

}
