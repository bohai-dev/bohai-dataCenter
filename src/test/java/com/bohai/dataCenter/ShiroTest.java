package com.bohai.dataCenter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;


public class ShiroTest {
    
    @Test  
    public void testHelloworld() {  
        
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager  
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro-realm.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils  
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证） 
        Subject subject = SecurityUtils.getSubject();
        
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        Assert.assertEquals(true, subject.isAuthenticated());
        
        subject.logout();
    }  
    

}
