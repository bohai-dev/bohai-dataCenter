package com.bohai.dataCenter.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bohai.dataCenter.entity.MediatorCust;
import com.bohai.dataCenter.persistence.MediatorCustMapper;
import com.bohai.dataCenter.service.FileUploadService;
import com.bohai.dataCenter.util.DateFormatterUtil;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 居间人与客户关系导入
 * @author caojia
 */
@Service("mediatorCustUploadService")
public class MediatorCustUploadServiceImpl implements FileUploadService{
	
	static Logger logger = Logger.getLogger(MediatorCustUploadServiceImpl.class);

	@Autowired
	private MediatorCustMapper mediatorCustMapper;
	
	
	@Override
	public void upload(MultipartFile file, Object... objects) {
		
		Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(file.getInputStream());
			Sheet mediatorCustsheet = workbook.getSheet(0);
			List<MediatorCust> mediatorCusts = new ArrayList<MediatorCust>();
			
			
			mediatorCustMapper.deleteAll();
			
			for(int i = 2;i<mediatorCustsheet.getRows();i++){
				MediatorCust mediatorCust = new MediatorCust();
				//行号
				mediatorCust.setNo(mediatorCustsheet.getCell(0, i).getContents());
				//居间人编号
				mediatorCust.setMediatorNo(mediatorCustsheet.getCell(1, i).getContents());
				//居间人姓名
				mediatorCust.setMediatorName(mediatorCustsheet.getCell(2, i).getContents());
				//客户编号
				mediatorCust.setCustNo(mediatorCustsheet.getCell(3, i).getContents());
				//客户姓名
				mediatorCust.setCustName(mediatorCustsheet.getCell(4, i).getContents());
				//默认分配比例
				mediatorCust.setAllocationProportion(mediatorCustsheet.getCell(5, i).getContents());
				//生效日期
				String effectiveDate = mediatorCustsheet.getCell(6, i).getContents();
				try {
					mediatorCust.setEffectiveDate(DateFormatterUtil.getDate(effectiveDate));
				} catch (ParseException e) {
					logger.error("居间人与客户信息生效日期格式错误："+effectiveDate,e);
				}
				//失效日期
				String expiryDate = mediatorCustsheet.getCell(7, i).getContents();
				try {
					mediatorCust.setExpireDate(DateFormatterUtil.getDate(expiryDate));
				} catch (ParseException e) {
					logger.error("居间人与客户信息失效日期格式错误："+expiryDate,e);
				}
				mediatorCust.setCreateTime(new Date());
				
				mediatorCustMapper.insertSelective(mediatorCust);
				
				mediatorCusts.add(mediatorCust);
			}
			
			logger.debug("一共需要导入"+mediatorCusts.size()+"条数据");
			/*try {
				int count = mediatorCustMapper.batchInsert(mediatorCusts);
				logger.debug("成功导入"+count+"条居间人与客户关系");
			} catch (Exception e) {
				logger.error("批量导入居间人与客户信息失败",e);
			}*/
			
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
