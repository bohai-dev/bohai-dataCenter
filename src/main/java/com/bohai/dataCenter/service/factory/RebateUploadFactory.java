package com.bohai.dataCenter.service.factory;

import org.springframework.stereotype.Service;

import com.bohai.dataCenter.service.FileUploadService;
import com.bohai.dataCenter.service.UploadFactory;
import com.bohai.dataCenter.util.SpringContextUtil;

/**
 * 返佣统计相关文件上传
 * @author caojia
 */
@Service("rebateUploadFactory")
public class RebateUploadFactory implements UploadFactory {

	@Override
	public FileUploadService createService(String fileName) {

		FileUploadService fileUploadService = null;
		if (fileName.indexOf("返利息名单") > -1) {
			fileUploadService = (FileUploadService) SpringContextUtil.getBean("rebateListUploadService");
		}else if (fileName.indexOf("资金对账表") > -1 && fileName.indexOf("xlsx") >-1) {
			fileUploadService = (FileUploadService) SpringContextUtil.getBean("capitalStatement2007UploadService");
		}else if (fileName.indexOf("资金对账表") > -1 && fileName.indexOf("csv") >-1) {
			fileUploadService = (FileUploadService) SpringContextUtil.getBean("capitalStatementCSVUploadService");
		}else if(fileName.indexOf("资金对账表") > -1){
			fileUploadService = (FileUploadService) SpringContextUtil.getBean("capitalStatementUploadService");
		} 
		
		return fileUploadService;
	}

}
