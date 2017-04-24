package com.bohai.dataCenter.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.bohai.dataCenter.entity.OpenRecord;
import com.bohai.dataCenter.persistence.OpenRecordMapper;
import com.bohai.dataCenter.service.FileUploadService;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

@Service("openRecordUploadService")
public class OpenRecordUploadServiceImpl implements FileUploadService{
	
	static Logger logger = Logger.getLogger(OpenRecordUploadServiceImpl.class);
	
	@Autowired
	private OpenRecordMapper openRecordMapper;

	@Override
	public void upload(MultipartFile file, Object... objects) {
		
		Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(file.getInputStream());
			Sheet openRecordSheet = workbook.getSheet(0);
			
			//先清空所有
			this.openRecordMapper.deleteAll();
			
			for(int i = 2;i<openRecordSheet.getRows();i++){
				String no = openRecordSheet.getCell(0, i).getContents();
				if(StringUtils.isEmpty(no)){
					continue;
				}
				OpenRecord openRecord = new OpenRecord();
				//编号
				openRecord.setNo(no);
				//日期
				openRecord.setOpenDate(openRecordSheet.getCell(1, i).getContents());
				//营业部
				openRecord.setDept(openRecordSheet.getCell(2, i).getContents());
				//客户名称
				openRecord.setCustName(openRecordSheet.getCell(3, i).getContents());
				//资金账号
				openRecord.setCapitalAccount(openRecordSheet.getCell(4, i).getContents());
				//使用状态
				openRecord.setStatus(openRecordSheet.getCell(5, i).getContents());
				//资料传输方式
				openRecord.setTransferMode(openRecordSheet.getCell(6, i).getContents());
				//资料属性
				openRecord.setDataProperty(openRecordSheet.getCell(7, i).getContents());
				//费用标准说明
				openRecord.setExpenseStandardExplain(openRecordSheet.getCell(8, i).getContents());
				//费用标准
				openRecord.setExpenseStandard(openRecordSheet.getCell(12, i).getContents());
				//关闭时间
				openRecord.setCloseDate(openRecordSheet.getCell(9, i).getContents());
				//软件
				openRecord.setSoft(openRecordSheet.getCell(10, i).getContents());
				//备注
				openRecord.setRemark(openRecordSheet.getCell(11, i).getContents());
				//创建时间
				openRecord.setCreateTime(new Date());
				
				try {
					this.openRecordMapper.insertSelective(openRecord);
				} catch (Exception e) {
					logger.error("导入TB开户记录失败："+JSON.toJSONString(openRecord),e);
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
