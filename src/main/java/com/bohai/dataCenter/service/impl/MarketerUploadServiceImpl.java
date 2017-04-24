package com.bohai.dataCenter.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bohai.dataCenter.entity.Marketer;
import com.bohai.dataCenter.entity.MarketerCust;
import com.bohai.dataCenter.entity.MarketerMediator;
import com.bohai.dataCenter.persistence.MarketerCustMapper;
import com.bohai.dataCenter.persistence.MarketerMapper;
import com.bohai.dataCenter.persistence.MarketerMediatorMapper;
import com.bohai.dataCenter.service.FileUploadService;
import com.bohai.dataCenter.util.DateFormatterUtil;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 上传营销人员信息表
 * @author caojia
 */
@Service("marketerUploadService")
public class MarketerUploadServiceImpl implements FileUploadService{
	
	static Logger logger = Logger.getLogger(MarketerUploadServiceImpl.class);
	
	@Autowired
	private MarketerMapper marketerMapper;
	
	@Autowired
	private MarketerCustMapper marketerCustMapper;
	
	@Autowired
	private MarketerMediatorMapper marketerMediatorMapper;

	@Override
	public void upload(MultipartFile file, Object... objects) {
		
		try {
			Workbook workbook = Workbook.getWorkbook(file.getInputStream());
			//营销人员sheet
			Sheet marketersheet = workbook.getSheet(0);
			
			List<Marketer> marketers = new ArrayList<Marketer>();
			for(int i = 2;i<marketersheet.getRows();i++){
				//营销人员信息
				Marketer marketer = new Marketer();
				//行号
				marketer.setNo(marketersheet.getCell(0, i).getContents());
				//营销人员编号
				marketer.setMarketerNo(marketersheet.getCell(1, i).getContents());
				//营销人员姓名
				marketer.setMarketerName(marketersheet.getCell(2, i).getContents());
				//所属营业部代码
				marketer.setDepCode(marketersheet.getCell(3, i).getContents());
				//所属营业部
				marketer.setDepName(marketersheet.getCell(4, i).getContents());
				//人员分区
				marketer.setPersonnelDivision(marketersheet.getCell(5, i).getContents());
				//在职状态
				marketer.setStatus(marketersheet.getCell(6, i).getContents());
				//入职日期
				String entryDate = marketersheet.getCell(7, i).getContents();
				try {
					marketer.setEntryDate(DateFormatterUtil.getDate(entryDate));
				} catch (ParseException e) {
					logger.error("入职日期格式转换错误",e);
				}
				//离职日期
				String leaveDate = marketersheet.getCell(8, i).getContents();
				try {
					marketer.setLeaveDate(DateFormatterUtil.getDate(leaveDate));
				} catch (ParseException e) {
					logger.error("离职时间格式转换错误",e);
				}
				//默认分配比例
				marketer.setAllocationProportion(marketersheet.getCell(9, i).getContents());
				//所属级别
				marketer.setMarketerLevel(marketersheet.getCell(10, i).getContents());
				//身份证号码
				marketer.setCertNo(marketersheet.getCell(11, i).getContents());
				//联系电话
				marketer.setTelephone(marketersheet.getCell(12, i).getContents());
				//联系地址
				marketer.setAddress(marketersheet.getCell(13, i).getContents());
				//电子邮箱
				marketer.setEmail(marketersheet.getCell(14, i).getContents());
				//备注
				marketer.setRemark(marketersheet.getCell(15, i).getContents());
				marketers.add(marketer);
			}
			
			marketerMapper.deleteAll();
			try {
				int count = marketerMapper.batchInsert(marketers);
				logger.info("成功导入"+count+"条营销人员信息");
			} catch (Exception e) {
				logger.error("批量插入营销人员信息失败",e);
			}
			
			//营销人员与客户关系
			Sheet marketerCustsheet = workbook.getSheet(1);
			List<MarketerCust> marketerCusts = new ArrayList<MarketerCust>();
			for(int i = 2;i<marketerCustsheet.getRows();i++){
				//营销人员与客户关系
				MarketerCust marketerCust = new MarketerCust();
				//行号
				marketerCust.setNo(marketerCustsheet.getCell(0, i).getContents());
				//营销人员标号
				marketerCust.setMarketerNo(marketerCustsheet.getCell(1, i).getContents());
				//营销人员姓名
				marketerCust.setMarketerName(marketerCustsheet.getCell(2, i).getContents());
				//客户编号
				marketerCust.setCustNo(marketerCustsheet.getCell(3, i).getContents());
				//客户姓名
				marketerCust.setCustName(marketerCustsheet.getCell(4, i).getContents());
				//客户类型
				marketerCust.setCustType(marketerCustsheet.getCell(5, i).getContents());
				//生效日期
				String effectiveDate = marketerCustsheet.getCell(6, i).getContents();
				try {
					marketerCust.setEffectiveDate(DateFormatterUtil.getDate(effectiveDate));
				} catch (ParseException e) {
					logger.error("生效日期格式错误:"+effectiveDate,e);
				}
				//失效日期
				String expiryDate = marketerCustsheet.getCell(7, i).getContents();
				try {
					marketerCust.setExpiryDate(DateFormatterUtil.getDate(expiryDate));
				} catch (ParseException e) {
					logger.error("失效日期格式错误:"+expiryDate,e);
				}
				//备注
				marketerCust.setRemark(marketerCustsheet.getCell(8, i).getContents());
				
				marketerCusts.add(marketerCust);
			}
			
			marketerCustMapper.deleteAll();
			try {
				int count = marketerCustMapper.batchInsert(marketerCusts);
				logger.info("成功导入"+count+"条营销人员与客户关系信息");
			} catch (Exception e1) {
				logger.error("批量插入营销人员与客户关系失败",e1);
			}
			
			
			//营销人员与居间人关系
			Sheet marketerMediatorsheet = workbook.getSheet(2);
			List<MarketerMediator> marketerMediators = new ArrayList<MarketerMediator>();
			for(int i = 2;i<marketerMediatorsheet.getRows();i++){
				MarketerMediator mediator = new MarketerMediator();
				//行号
				mediator.setNo(marketerMediatorsheet.getCell(0, i).getContents());
				//营销人员编号
				mediator.setMarketerNo(marketerMediatorsheet.getCell(1, i).getContents());
				//营销人员姓名
				mediator.setMarketerName(marketerMediatorsheet.getCell(2, i).getContents());
				//居间人编号
				mediator.setMediatorNo(marketerMediatorsheet.getCell(3, i).getContents());
				//居间人姓名
				mediator.setMediatorName(marketerMediatorsheet.getCell(4, i).getContents());
				//生效日期
				String effectiveDate = marketerMediatorsheet.getCell(5, i).getContents();
				try {
					mediator.setEffectiveDate(DateFormatterUtil.getDate(effectiveDate));
				} catch (ParseException e) {
					logger.error("营销人员与居间人关系生效日期格式错误："+effectiveDate,e);
				}
				//失效日期
				String expiryDate = marketerMediatorsheet.getCell(6, i).getContents();
				try {
					mediator.setExpiryDate(DateFormatterUtil.getDate(expiryDate));
				} catch (ParseException e) {
					logger.error("营销人员与居间人关系失效日期格式错误："+expiryDate,e);
				}
				mediator.setRemark(marketerMediatorsheet.getCell(7, i).getContents());
				
				marketerMediators.add(mediator);
			}
			
			marketerMediatorMapper.deleteAll();
			try {
				int count = marketerMediatorMapper.batchInsert(marketerMediators);
				logger.info("成功导入"+count+"条营销人员与居间人关系信息");
			} catch (Exception e) {
				logger.error("批量插入营销人员与居间人关系失败",e);
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
