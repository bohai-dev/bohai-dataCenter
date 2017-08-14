package com.bohai.dataCenter.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboApplication {
    
    public static void main(String[] args) {
        
        System.out.println("===================service  start ...==================");
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContextTest.xml");
        classPathXmlApplicationContext.start();
        System.out.println("===================service  start  complete!==================");
        
        
        synchronized (DubboApplication.class) {
            while (true) {
                try {
                    DubboApplication.class.wait();
                } catch (Throwable e) {
                }
            }
        }
    }
    
    

}
