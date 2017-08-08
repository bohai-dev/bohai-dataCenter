package com.bohai.dataCenter.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bohai.dataCenter.entity.Mediator;
import com.bohai.dataCenter.persistence.MediatorMapper;
import com.bohai.dataCenter.service.FileUploadService;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 居间人信息上传
 * @author caojia
 */
@Service("mediatorUploadService")
public class MediatorUploadServiceImpl implements FileUploadService{
	
	static Logger logger = Logger.getLogger(MediatorUploadServiceImpl.class);
	
	@Autowired
	private MediatorMapper mediatorMapper;

	@Override
	public void upload(MultipartFile file, Object... objects) {
		
		try {
			Workbook workbook = Workbook.getWorkbook(file.getInputStream());
			
			Sheet mediatorsheet = workbook.getSheet(0);
			//List<Mediator> mediators = new ArrayList<Mediator>();
			
			
			mediatorMapper.deleteAll();
			
			for(int i = 2;i<mediatorsheet.getRows();i++){
				Mediator mediator = new Mediator();
				//行号
				mediator.setNo(mediatorsheet.getCell(0, i).getContents());
				//居间人编号
				mediator.setMediatorNo(mediatorsheet.getCell(1, i).getContents());
				//居间人全称
				mediator.setMediatorName(mediatorsheet.getCell(2, i).getContents());
				//居间人简称
				mediator.setMediatorShortName(mediatorsheet.getCell(3, i).getContents());
				//在职状态
				mediator.setStatus(mediatorsheet.getCell(4, i).getContents());
				//所属营业部代码
				mediator.setDepCode(mediatorsheet.getCell(5, i).getContents());
				//所属营业部
				mediator.setDepName(mediatorsheet.getCell(6, i).getContents());
				//默认分配比例
				mediator.setAllocationProportion(mediatorsheet.getCell(7, i).getContents());
				//佣金支付类型
				mediator.setPayType(mediatorsheet.getCell(8, i).getContents());
				//联系电话
				mediator.setTelephone(mediatorsheet.getCell(9, i).getContents());
				//联系传真
				mediator.setFax(mediatorsheet.getCell(10, i).getContents());
				//联系地址
				mediator.setAddress(mediatorsheet.getCell(11, i).getContents());
				//电子邮箱
				mediator.setEmail(mediatorsheet.getCell(12, i).getContents());
				//身份证号
				mediator.setCertNo(mediatorsheet.getCell(13, i).getContents());
				//备注
				mediator.setRemark(mediatorsheet.getCell(14, i).getContents());
				mediator.setCreateTime(new Date());
				
				try {
					mediatorMapper.insertSelective(mediator);
				} catch (Exception e) {
					logger.error("导入居间人信息失败",e);
				}
				
				//mediators.add(mediator);
			}
			
			
			
			/*try {
				int count = mediatorMapper.batchInsert(mediators);
				logger.debug("成功导入"+count+"条居间人信息");
			} catch (Exception e) {
				logger.error("批量导入居间人信息失败",e);
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
