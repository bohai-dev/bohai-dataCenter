package com.bohai.dataCenter.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CapitalStatement;
import com.bohai.dataCenter.entity.InstrumentAttribute;
import com.bohai.dataCenter.entity.Mediator;
import com.bohai.dataCenter.entity.RebateList;
import com.bohai.dataCenter.entity.ReportExchangeRebate;
import com.bohai.dataCenter.entity.ReportRebate;
import com.bohai.dataCenter.entity.TradingDate;
import com.bohai.dataCenter.entity.VTradeDetail;
import com.bohai.dataCenter.persistence.CapitalStatementMapper;
import com.bohai.dataCenter.persistence.InstrumentAttributeMapper;
import com.bohai.dataCenter.persistence.MarketerMapper;
import com.bohai.dataCenter.persistence.MediatorMapper;
import com.bohai.dataCenter.persistence.RebateListMapper;
import com.bohai.dataCenter.persistence.ReportRebateMapper;
import com.bohai.dataCenter.persistence.TradingDateMapper;
import com.bohai.dataCenter.persistence.TransactionStatisticsMapper;
import com.bohai.dataCenter.persistence.VTradeDetailMapper;
import com.bohai.dataCenter.service.ReportExchangeRebateService;
import com.bohai.dataCenter.service.ReportRebateService;
import com.bohai.dataCenter.service.ReportService;
import com.bohai.dataCenter.util.DateFormatterUtil;
import com.bohai.dataCenter.vo.CountExchangeRebateParamVO;
import com.bohai.dataCenter.vo.CountRebatReportParamVO;

import oracle.net.aso.a;

@Service("reportService")
public class ReportServiceImpl implements ReportService {
	
	static Logger logger = Logger.getLogger(ReportServiceImpl.class);
	
	@Autowired
	private TransactionStatisticsMapper transactionStatisticsMapper;
	
	@Autowired
	private RebateListMapper rebateListMapper;
	
	@Autowired
	private MediatorMapper mediatorMapper;
	
	@Autowired
	private CapitalStatementMapper capitalStatementMapper;
	
	@Autowired
	private ReportRebateService reportRebateService;
	
	@Autowired
	private MarketerMapper marketerMapper;
	
	@Autowired
	private ReportRebateMapper reportRebateMapper;
	
	@Autowired
	private TradingDateMapper tradingDateMapper;
	
	@Autowired
	private VTradeDetailMapper vTradeDetailMapper;
	
	@Autowired
	private ReportExchangeRebateService reportExchangeRebateService;
	
	@Autowired
	private InstrumentAttributeMapper instrumentAttributeMapper;

	@Override
	public List<Map<String, Object>> queryTBSoftReport() {
		
		return this.transactionStatisticsMapper.selectSoftCommission();
		
	}

	@Override
	public void countRebatReport(CountRebatReportParamVO paramVO) throws BohaiException {
		
		List<RebateList> list = rebateListMapper.selectAll();
		
		if(list == null || list.size() < 1){
			return;
		}
		
		for(RebateList rebate : list){
			
			if (rebate.getCateDesp().equals("居间人")) {
				//居间人名下所有客户
				String mediatorNo = rebate.getMdeiatorNo();
				if(!StringUtils.isEmpty(mediatorNo)){
					//查询居间人对应的营业部
					Mediator mediator = this.mediatorMapper.selectByMediatorNo(mediatorNo);
					if(mediator == null){
						continue;
					}
					//营业部
					String depName = mediator.getDepName();
					//查询居间人名下所有客户的资金对账信息
					List<CapitalStatement> statements = this.capitalStatementMapper.selectByMediatorNo(mediatorNo,paramVO.getYear(),paramVO.getMonth());
					if(statements == null || statements.size() <1){
						//居间人下没有客户的资金信息
						continue;
					}
					this.generateRebat(statements, rebate, depName);
				}else {
					//居间人没有居间人编号的情况
					String depName = "其他";
					//投资者编号
					String investorNo = rebate.getInvestorNo();
					List<CapitalStatement> statements = this.capitalStatementMapper.selectByInvestorNo(investorNo,paramVO.getYear(),paramVO.getMonth());
					if(statements == null || statements.size() <1){
						//客户没有资金信息
						continue;
					}
					this.generateRebat(statements, rebate, depName);
					
				}
			}else if (rebate.getCateDesp().equals("客户")) {//类型为客户
				
				String depName = "";
				//投资者编号
				String investorNo = rebate.getInvestorNo();
				
				if(StringUtils.isEmpty(investorNo)){
					continue;
				}
				
				if(!StringUtils.isEmpty(rebate.getMdeiatorNo())){
					//查询居间人对应的营业部
					try {
						Mediator mediator = this.mediatorMapper.selectByMediatorNo(rebate.getMdeiatorNo());
						depName = mediator.getDepName();
					} catch (Exception e) {
						logger.error("查询居间人信息失败："+rebate.getMdeiatorNo(),e);
						throw new BohaiException("", "查询居间人信息失败："+rebate.getMdeiatorNo());
					}
				}else if (!StringUtils.isEmpty(rebate.getInvestorNo())) {
					//查询客户所在营业部
					try {
						depName = this.marketerMapper.selectDepNameByInvestorNo(investorNo);
					} catch (Exception e) {
						logger.error("根据投资者编号查询所在营业部失败："+investorNo,e);
						throw new BohaiException("", "根据投资者编号查询所在营业部失败："+investorNo);
					}
				}
				
				List<CapitalStatement> statements = this.capitalStatementMapper.selectByInvestorNo(investorNo,paramVO.getYear(),paramVO.getMonth());
				if(statements == null || statements.size() <1){
					//客户没有资金信息
					continue;
				}
				this.generateRebat(statements, rebate, depName);
			}
		}
		
		//补充节假日利息
		this.fillDate(paramVO.getYear(),paramVO.getMonth());
		
	}
	
	private void generateRebat(List<CapitalStatement> statements,RebateList rebate,String depName) throws BohaiException{
		
		//固定利率
		String fixPro = rebate.getFixProportion();
		//日均权益计算
		String dailyRights = rebate.getDailyRights();
		
		for(CapitalStatement statement:statements){
			//返利统计表
			ReportRebate reportRebate = new ReportRebate();
			//交易日
			reportRebate.setTradeDate(statement.getTradeDate());
			reportRebate.setTradeDateStr(statement.getTradeDateStr());
			//投资者编号
			reportRebate.setInvestorNo(statement.getInvestorNo());
			//投资者名称
			reportRebate.setInvestorName(statement.getInvestorName());
			//居间人编号
			reportRebate.setMediatorName(rebate.getMediatorName());
			//居间人姓名
			reportRebate.setMediatorNo(rebate.getMdeiatorNo());
			//营业部
			reportRebate.setDeptName(depName);
			//可用资金
			String availableFunds = statement.getAvailableFunds();
			//客户权益
			String investorRights = statement.getInvestorRights();
			//固定利率
			reportRebate.setFixProportion(fixPro);
			//可用资金
			reportRebate.setAvailableFunds(statement.getAvailableFunds());
			//客户权益
			reportRebate.setRights(statement.getInvestorRights());
			
			//自定义区间
			String custom = rebate.getCustomInterval();
			BigDecimal interest = new BigDecimal("0");
			if(!StringUtils.isEmpty(fixPro)){
				//按照固定利率计算     利息 = 可用资金*年化固定利率/360
				interest = new BigDecimal(availableFunds).multiply(new BigDecimal(fixPro)).divide(new BigDecimal("360"), 2, RoundingMode.HALF_UP);
			}else if(!StringUtils.isEmpty(custom)) {
				JSONObject json = JSONObject.parseObject(custom);
				for (Map.Entry<String, Object> entry : json.entrySet()) {
					
					logger.debug("获取区间："+entry.getKey()+",对应的利率："+entry.getValue().toString());
					
					String customStr = entry.getKey();
					
					BigDecimal minValue = new BigDecimal(customStr.substring(0, customStr.indexOf("-")));
					BigDecimal maxValue = new BigDecimal(customStr.substring(customStr.indexOf("-")+1));
					//可用资金
					BigDecimal available = new BigDecimal(availableFunds);
					//客户权益
					BigDecimal rights = new BigDecimal(investorRights);
					//  左区间<可用资金<=右区间
					if(rights.compareTo(minValue)>0 && rights.compareTo(maxValue)<1){
						//日均权益利率
						reportRebate.setDailyRights(entry.getValue().toString());
						
						logger.debug("客户权益："+investorRights+"在区间："+entry.getKey()+"范围内");
						//浮动利率
						BigDecimal fint = new BigDecimal(entry.getValue().toString()); 
						interest = available.multiply(fint).divide(new BigDecimal("360"), 2, RoundingMode.HALF_UP);
						break;
					}
				}
			}
			//利息
			reportRebate.setInterestAmount(interest.toString());
			//日期类型 1:交易日 0：节假日
			reportRebate.setDateType("1");
			
			reportRebateService.saveOrUpdate(reportRebate);
		}
	}
	
	private void fillDate(String year, String month) throws BohaiException{
		
		logger.debug("开始填补节假日利息");
		
		//查询某一月份所有返息人员名单
		List<ReportRebate> investors = this.reportRebateMapper.selectDistinctInvestorByYearAndMonth(year, month);
		if(investors == null || investors.size() < 1){
			return;
		}
		for(ReportRebate investorMap:investors){
			
			String investorNo = investorMap.getInvestorNo();
			String investorName = investorMap.getInvestorName();
			String depName = investorMap.getDeptName();
			//查询当月所有节假日信息
			List<TradingDate> holidays = tradingDateMapper.selectHolidaysByYearAndMonth(year, month);
			
			for(TradingDate holiday:holidays){
				//查询节假日前一交易日信息
				Date tradingDate = this.tradingDateMapper.selectPreTraDateByDate(holiday.getTdate());
				
				String dateStr = DateFormatterUtil.getDateStryyyyMMdd(tradingDate);
				//查询前一交易日返利信息
				ReportRebate rebate = this.reportRebateMapper.selectByTradeDateAndInvestorNo(dateStr, investorNo);
				if(rebate == null || StringUtils.isEmpty(rebate.getInvestorNo())){
					//补充一条利息为0的数据
					ReportRebate reportRebate = new ReportRebate();
					//节假日
					reportRebate.setTradeDate(holiday.getTdate());
					reportRebate.setTradeDateStr(DateFormatterUtil.getDateStryyyyMMdd(holiday.getTdate()));
					reportRebate.setInvestorNo(investorNo);
					reportRebate.setInvestorName(investorName);
					reportRebate.setDeptName(depName);
					reportRebate.setInterestAmount("0.00");
					//日期类型为节假日
					reportRebate.setDateType("0");
					reportRebateService.saveOrUpdate(reportRebate);
				}else {
					rebate.setId(null);
					rebate.setDateType("0");
					rebate.setTradeDate(holiday.getTdate());
					rebate.setTradeDateStr(DateFormatterUtil.getDateStryyyyMMdd(holiday.getTdate()));
					reportRebateService.saveOrUpdate(rebate);
				}
			}
			
		}
		
	}

	@Override
	public void countExchangeRebate(CountExchangeRebateParamVO paramVO) throws BohaiException {
		
		logger.debug("交易所返还统计年月："+paramVO.getMonth());

		//上海交易所手续费
		List<Map<String,Object>> slist = this.vTradeDetailMapper.selectSumCharge(paramVO.getMonth().replace("-", ""), "上期所");
		
		if(slist != null){
			for(Map<String, Object> map : slist){
				ReportExchangeRebate exchangeRebate = new ReportExchangeRebate();
				//月份
				exchangeRebate.setMonth(paramVO.getMonth());
				//机构名称
				exchangeRebate.setDepName((String) map.get("DEP_NAME"));
				//手续费 上期所返还上交手续费的40%
				BigDecimal scharge = (BigDecimal) map.get("CHARGE");
				scharge = scharge.multiply(new BigDecimal("0.4")).setScale(2, RoundingMode.HALF_UP);
				exchangeRebate.setSrebate(scharge.toString());
				this.reportExchangeRebateService.saveOrUpdate(exchangeRebate);
			}
		}
		
		//郑商所手续费
		List<Map<String,Object>> zlist = this.vTradeDetailMapper.selectSumCharge(paramVO.getMonth().replace("-", ""), "郑商所");
		if(slist != null){
			for(Map<String, Object> map : zlist){
				ReportExchangeRebate exchangeRebate = new ReportExchangeRebate();
				//月份
				exchangeRebate.setMonth(paramVO.getMonth());
				//机构名称
				exchangeRebate.setDepName((String) map.get("DEP_NAME"));
				//手续费  郑州上交手续费20%
				BigDecimal scharge = (BigDecimal) map.get("CHARGE");
				scharge = scharge.multiply(new BigDecimal("0.2")).setScale(2, RoundingMode.HALF_UP);
				exchangeRebate.setZrebate(scharge.toString());
				this.reportExchangeRebateService.saveOrUpdate(exchangeRebate);
			}
		}
		
		//大商所
		VTradeDetail vTradeDetail = new VTradeDetail();
		//vTradeDetail.setExchangeId("D");
		vTradeDetail.setTradeDate(paramVO.getMonth().replace("-", ""));
		//查询大商所交易记录
		List<Map<String,Object>> dlist = this.vTradeDetailMapper.selectTradeInfo(vTradeDetail);
		if(dlist != null){
			Map<String,BigDecimal> rebateMap = new HashMap<String,BigDecimal>();
			
			for(Map<String, Object> map : dlist){
				//合约品种
				String instrument = (String) map.get("INSTRUMENT");
				//套保标志  H:套保 O:非套保
				String hadgeFlag = (String) map.get("HEDGE_FLAG");
				//机构名称
				String depName = (String) map.get("DEP_NAME");
				//总成交量
				BigDecimal volume = (BigDecimal) map.get("VOLUME");
				//总成交额
				BigDecimal turnover = (BigDecimal) map.get("TRUNOVER");
				//平今量
				BigDecimal closeTodayVolume = (BigDecimal) map.get("CLOSE_TODAY_VOLUME");
				if(closeTodayVolume == null){
					closeTodayVolume = new BigDecimal("0");
				}
				
				//平今额
				BigDecimal closeToday = (BigDecimal) map.get("CLOSE_TODAY_TRUNOVER");
				if(closeToday == null){
					closeToday = new BigDecimal("0");
				}
				
				//根据合约名称查询合约属性
				InstrumentAttribute attribute = this.instrumentAttributeMapper.selectByPrimaryKey(instrument);
				if(attribute == null){
					continue;
				}
				
				//开始计算大连交易所返还
				
				//开仓手续费
				String openCharge = attribute.getExOpenCharge();
				//开仓手续费比例
				String openChargeRate = attribute.getExOpenChargeRate();
				//平今手续费
				String closetCharge = attribute.getExClosetCharge();
				//平今手续费比例
				String closetChargeRate = attribute.getExClosetChargeRate();
				//平昨手续费
				String closeyCharge = attribute.getExCloseyCharge();
				//平昨手续费比例
				String closeyChargeRate = attribute.getExCloseyChargeRate();
				
				//日内返还金额
				BigDecimal inday = new BigDecimal("0");
				//非日内返还金额
				BigDecimal outday = new BigDecimal("0");
				
				//交易所返还
				BigDecimal rebate = new BigDecimal("0");
				
				if(depName.equals("深圳营业部")){
					logger.debug(JSON.toJSONString(map));
				}
				
				//鸡蛋和两版
				if(instrument.equals("jd")||instrument.equals("bb")||instrument.equals("fb")){
					if(hadgeFlag.equals("保")){
						//套保日内 90%  非日内  98%
						if(!StringUtils.isEmpty(openCharge) && !StringUtils.isEmpty(closetCharge)){
							inday = new BigDecimal(closetCharge).multiply(closeTodayVolume).multiply(new BigDecimal("2")).multiply(new BigDecimal("0.9"));
							outday = (volume.subtract(closeTodayVolume.multiply(new BigDecimal("2")))).multiply(new BigDecimal(openCharge)).multiply(new BigDecimal("0.98"));
						}else if (!StringUtils.isEmpty(openChargeRate) && !StringUtils.isEmpty(closetChargeRate)) {
							//inday = 
							inday = new BigDecimal(closetChargeRate).multiply(closeToday).multiply(new BigDecimal("2")).multiply(new BigDecimal("0.9"));
							//outday = 
							outday = (turnover.subtract(closeToday.multiply(new BigDecimal("2")))).multiply(new BigDecimal(openChargeRate)).multiply(new BigDecimal("0.98"));
						}
					}else if (hadgeFlag.equals("投")) {
						//非套保日内 0     非日内 80%
						if(!StringUtils.isEmpty(openCharge)){
							outday = (volume.subtract(closeTodayVolume.multiply(new BigDecimal("2")))).multiply(new BigDecimal(openCharge)).multiply(new BigDecimal("0.8"));
						}else if (!StringUtils.isEmpty(openChargeRate)) {
							outday = (turnover.subtract(closeToday.multiply(new BigDecimal("2")))).multiply(new BigDecimal(openChargeRate)).multiply(new BigDecimal("0.8"));
						}
					}
				}else {//非鸡蛋和两板
					if(hadgeFlag.equals("保")){
						//套保日内 92.5%  非日内  98.5%
						if(!StringUtils.isEmpty(openCharge) && !StringUtils.isEmpty(closetCharge)){
							inday = new BigDecimal(closetCharge).multiply(closeTodayVolume).multiply(new BigDecimal("2")).multiply(new BigDecimal("0.925"));
							outday = (volume.subtract(closeTodayVolume.multiply(new BigDecimal("2")))).multiply(new BigDecimal(openCharge)).multiply(new BigDecimal("0.985"));
						}else if (!StringUtils.isEmpty(openChargeRate) && !StringUtils.isEmpty(closetChargeRate)) {
							//inday = 
							inday = new BigDecimal(closetChargeRate).multiply(closeToday).multiply(new BigDecimal("2")).multiply(new BigDecimal("0.925"));
							//outday = 
							outday = (turnover.subtract(closeToday.multiply(new BigDecimal("2")))).multiply(new BigDecimal(openChargeRate)).multiply(new BigDecimal("0.985"));
						}
					}else if (hadgeFlag.equals("投")) {
						//非套保日内 25%     非日内 85%
						if(!StringUtils.isEmpty(openCharge) && !StringUtils.isEmpty(closetCharge)){
							inday = new BigDecimal(closetCharge).multiply(closeTodayVolume).multiply(new BigDecimal("2")).multiply(new BigDecimal("0.25"));
							outday = (volume.subtract(closeTodayVolume.multiply(new BigDecimal("2")))).multiply(new BigDecimal(openCharge)).multiply(new BigDecimal("0.85"));
						}else if (!StringUtils.isEmpty(openChargeRate) && !StringUtils.isEmpty(closetChargeRate)) {
							
							inday = new BigDecimal(closetChargeRate).multiply(closeToday).multiply(new BigDecimal("2")).multiply(new BigDecimal("0.25"));
							outday = (turnover.subtract(closeToday.multiply(new BigDecimal("2")))).multiply(new BigDecimal(openChargeRate)).multiply(new BigDecimal("0.85"));
						}
					}
				}
				
				rebate = inday.add(outday);
				if(depName.equals("深圳营业部")){
					logger.debug("返还："+rebate);
				}
				ReportExchangeRebate exchangeRebate = new ReportExchangeRebate();
				exchangeRebate.setDepName(depName);
				exchangeRebate.setMonth(paramVO.getMonth());
				exchangeRebate.setDrebate(rebate.toString());
				this.reportExchangeRebateService.updateRebate(exchangeRebate);
			}
				
		}
			
	}
		

}
