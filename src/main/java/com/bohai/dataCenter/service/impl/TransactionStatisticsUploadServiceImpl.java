package com.bohai.dataCenter.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.bohai.dataCenter.entity.TransactionStatistics;
import com.bohai.dataCenter.persistence.TransactionStatisticsMapper;
import com.bohai.dataCenter.service.FileUploadService;

import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 交易统计上传
 * @author caojia
 */
@Service("transactionStatisticsUploadService")
public class TransactionStatisticsUploadServiceImpl implements FileUploadService {

	static Logger logger = Logger.getLogger(TransactionStatisticsUploadServiceImpl.class);
	
	@Autowired
	private TransactionStatisticsMapper transactionStatisticsMapper;
	
	
	@Override
	public void upload(MultipartFile file, Object... objects) {
		
		logger.debug("交易统计表上传");
		try {
			Workbook workbook = Workbook.getWorkbook(file.getInputStream());
			Sheet transStatSheet = workbook.getSheet(0);
			
			//批次号
			String batchNo = transStatSheet.getCell(0, 2).getContents();
			logger.debug("批次号："+batchNo);
			
			transactionStatisticsMapper.deleteAll();
			
			for(int i = 4;i<transStatSheet.getRows();i++){
				
				String investorNo = transStatSheet.getCell(1, i).getContents();
				if(StringUtils.isEmpty(investorNo)){
					continue;
				}
				
				//交易统计信息
				TransactionStatistics transactionStatistics = new TransactionStatistics();
				//批次
				transactionStatistics.setBatchNo(batchNo);
				//交易日
				transactionStatistics.setTradeDate(transStatSheet.getCell(0, i).getContents());
				//投资者代码
				transactionStatistics.setInvestorNo(investorNo);
				//投资者名称
				transactionStatistics.setInvestorName(transStatSheet.getCell(2, i).getContents());
				//投资者类型
				transactionStatistics.setInvestorType(transStatSheet.getCell(3, i).getContents());
				//币种代码
				transactionStatistics.setCurrency(transStatSheet.getCell(4, i).getContents());
				//交易所
				transactionStatistics.setExchange(transStatSheet.getCell(5, i).getContents());
				//合约
				transactionStatistics.setInstrument(transStatSheet.getCell(6, i).getContents());
				//产品
				transactionStatistics.setProduct(transStatSheet.getCell(7, i).getContents());
				//交割期
				transactionStatistics.setDeliveryPeriod(transStatSheet.getCell(8, i).getContents());
				//投保
				transactionStatistics.setTradeType(transStatSheet.getCell(9, i).getContents());
				//手续费
				transactionStatistics.setCharge(((NumberCell)transStatSheet.getCell(10, i)).getValue()+"");
				//上交手续费
				transactionStatistics.setOnhandCharge(((NumberCell)transStatSheet.getCell(11, i)).getValue()+"");
				//留存手续费
				transactionStatistics.setRetentionCharge(((NumberCell)transStatSheet.getCell(12, i)).getValue()+"");
				//盈亏
				transactionStatistics.setProfit(((NumberCell)transStatSheet.getCell(13, i)).getValue()+"");
				//总成交量
				transactionStatistics.setTotalVolume(transStatSheet.getCell(14, i).getContents());
				//本平台成交量
				transactionStatistics.setPlatformVolume(transStatSheet.getCell(15, i).getContents());
				//平今量
				transactionStatistics.setCloseTodayVolume(transStatSheet.getCell(16, i).getContents());
				//本平台收费平今量
				transactionStatistics.setPlatformChargeVolume(transStatSheet.getCell(17, i).getContents());
				//本平台免费平今量
				transactionStatistics.setPlatformFreeVolume(transStatSheet.getCell(18, i).getContents());
				//成交额
				transactionStatistics.setTurnover(((NumberCell)transStatSheet.getCell(19, i)).getValue()+"");
				//平今成交额
				transactionStatistics.setCloseTodayTurnover(((NumberCell)transStatSheet.getCell(20, i)).getValue()+"");
				//平仓盈亏
				transactionStatistics.setCloseProfit(((NumberCell)transStatSheet.getCell(21, i)).getValue()+"");
				//持仓盈亏
				transactionStatistics.setPositionProfit(((NumberCell)transStatSheet.getCell(22, i)).getValue()+"");
				//净利润
				transactionStatistics.setNetProfit(((NumberCell)transStatSheet.getCell(23, i)).getValue()+"");
				//交割手续费
				transactionStatistics.setDeliveryCharge(((NumberCell)transStatSheet.getCell(24, i)).getValue()+"");
				//上交交割手续费
				transactionStatistics.setOnhandDeliveryCharge(((NumberCell)transStatSheet.getCell(25, i)).getValue()+"");
				//留存交割手续费
				transactionStatistics.setRetainedDeliveryCharge(((NumberCell)transStatSheet.getCell(26, i)).getValue()+"");
				//交割数量
				transactionStatistics.setDeliveryQuantity(transStatSheet.getCell(27, i).getContents());
				//买持量
				transactionStatistics.setBuyVolume(transStatSheet.getCell(28, i).getContents());
				//卖持量
				transactionStatistics.setSellVolume(transStatSheet.getCell(29, i).getContents());
				//投资者保证金
				transactionStatistics.setInvestorMargin(((NumberCell)transStatSheet.getCell(30, i)).getValue()+"");
				//交易所保证金
				transactionStatistics.setExchangeMargin(((NumberCell)transStatSheet.getCell(31, i)).getValue()+"");
				//多头市值期权
				transactionStatistics.setLongOptionValue(((NumberCell)transStatSheet.getCell(32, i)).getValue()+"");
				//空头市值期权
				transactionStatistics.setShortOptionValue(((NumberCell)transStatSheet.getCell(33, i)).getValue()+"");
				//权利金收入
				transactionStatistics.setRoyaltyIncome(((NumberCell)transStatSheet.getCell(34, i)).getValue()+"");
				//权利金支出
				transactionStatistics.setRoyaltyPayment(((NumberCell)transStatSheet.getCell(35, i)).getValue()+"");
				//交易所行权手续费
				transactionStatistics.setExchangeExerciseCharge(((NumberCell)transStatSheet.getCell(36, i)).getValue()+"");
				//行权手续费
				transactionStatistics.setExerciseCharge(((NumberCell)transStatSheet.getCell(37, i)).getValue()+"");
				//执行盈亏
				transactionStatistics.setExerciseProfit(((NumberCell)transStatSheet.getCell(38, i)).getValue()+"");
				//创建时间
				transactionStatistics.setCreateTime(new Date());
				
				try {
					this.transactionStatisticsMapper.insertSelective(transactionStatistics);
				} catch (Exception e) {
					logger.error("插入交易统计信息失败："+JSON.toJSONString(transactionStatistics),e);
				}
			}
			
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
