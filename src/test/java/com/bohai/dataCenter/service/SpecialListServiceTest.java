package com.bohai.dataCenter.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.service.impl.SpecialListServiceImpl;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpecialListServiceTest extends AbstractJUnit4SpringContextTests{

	
	@Autowired
	private SpecialListServiceImpl specialListService;
	
	@Test
	public void export() throws BohaiException, FileNotFoundException, IOException {
	    this.specialListService.exportDepartment("2019-06", "上海营业部");
	}
	
	@Test
	public void zip() throws FileNotFoundException, IOException{
	    this.specialListService.exportZip("2019-06",null);
	}
	
}
