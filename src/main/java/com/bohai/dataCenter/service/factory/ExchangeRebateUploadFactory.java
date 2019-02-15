package com.bohai.dataCenter.service.factory;

import org.springframework.stereotype.Service;

import com.bohai.dataCenter.service.FileUploadService;
import com.bohai.dataCenter.service.UploadFactory;
import com.bohai.dataCenter.util.SpringContextUtil;

@Service("exchangeRebateUploadFactory")
public class ExchangeRebateUploadFactory implements UploadFactory {

	@Override
	public FileUploadService createService(String fileName) {

		FileUploadService service = null;
		if(fileName.indexOf("trddata")>-1){
			service = (FileUploadService) SpringContextUtil.getBean("tradeDataTXTUploadService");
		}else if (fileName.indexOf("liquiddetails")>-1) {
			//平仓明细数据上传
			service = (FileUploadService) SpringContextUtil.getBean("closeDataTXTUploadService");
		}else if (fileName.indexOf("大商所")>-1 || fileName.indexOf("上期所")>-1 || fileName.indexOf("郑商所")>-1 
		        || fileName.indexOf("中金所")>-1 || fileName.indexOf("能源中心")>-1) {
			//ctp交易明细数据上传
			service = (FileUploadService) SpringContextUtil.getBean("ctpTradeDataCSVUpload");
		}
		
		return service;
	}

}
