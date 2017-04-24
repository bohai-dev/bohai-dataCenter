package com.bohai.dataCenter.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CloseData;
import com.bohai.dataCenter.persistence.CloseDataMapper;
import com.bohai.dataCenter.service.CloseDataService;

@Service("closeDataService")
public class CloseDataServiceImpl implements CloseDataService {

	static Logger logger = Logger.getLogger(CloseDataServiceImpl.class);
	
	@Autowired
	private CloseDataMapper closeDataMapper;
	
	@Override
	public void save(CloseData closeData) throws BohaiException {

		try {
			this.closeDataMapper.insert(closeData);
		} catch (Exception e) {
			logger.error("保存期货平仓明细失败:"+JSON.toJSONString(closeData),e);
			throw new BohaiException("", "保存期货平仓明细失败");
		}
	}

	@Override
	public void removeByDate(Date date) throws BohaiException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		
		int count = this.closeDataMapper.removeByDate(dateStr);
		
		logger.debug("共删除"+count+"条数据");
		
	}

}
