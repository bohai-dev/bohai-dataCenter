package com.bohai.dataCenter.service.factory;

import org.springframework.stereotype.Service;

import com.bohai.dataCenter.service.FileUploadService;
import com.bohai.dataCenter.service.UploadFactory;
import com.bohai.dataCenter.util.SpringContextUtil;

/**
 * 文件上传工厂方法，负责分发上传文件的处理类
 * @author caojia
 */
@Service("fileUploadFactory")
public class FileUploadFactory implements UploadFactory{

	
	/**
	 * 根据文件名分配实现类
	 * @param fileName
	 * @return
	 */
	@Override
	public FileUploadService createService(String fileName){
		
		FileUploadService fileUploadService = null;
		if(fileName.equals("营销人员信息.xls")){
			fileUploadService = (FileUploadService) SpringContextUtil.getBean("marketerUploadService");
		}else if (fileName.equals("居间人信息.xls")) {
			fileUploadService = (FileUploadService) SpringContextUtil.getBean("mediatorUploadService");
		}else if (fileName.equals("居间人与客户关系.xls")) {
			fileUploadService = (FileUploadService) SpringContextUtil.getBean("mediatorCustUploadService");
		}else if (fileName.equals("TB开通记录.xls")) {
			fileUploadService = (FileUploadService) SpringContextUtil.getBean("openRecordUploadService");
		}else if (fileName.equals("交易统计表.xls")) {
			fileUploadService = (FileUploadService) SpringContextUtil.getBean("transactionStatisticsUploadService");
		}
		
		return fileUploadService;
	}
	
}
