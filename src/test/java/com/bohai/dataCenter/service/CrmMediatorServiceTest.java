package com.bohai.dataCenter.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.bohai.dataCenter.controller.exception.BohaiException;

@ContextConfiguration(locations = "classpath:applicationContextTest.xml")
public class CrmMediatorServiceTest extends AbstractJUnit4SpringContextTests{

    
    
    @Autowired
    private CrmMediatorService mediatorService;
    
    @Test
    public void publicity(){
        
        try {
            this.mediatorService.publicityMediator("28030024");
        } catch (BohaiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
