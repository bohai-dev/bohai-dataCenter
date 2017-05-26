package com.bohai.dataCenter.persistence;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CapitalStatementMapperTest extends AbstractJUnit4SpringContextTests {
    
    
    public static SqlSessionFactory sqlSessionFactory;

    @Before
    public void init(){
        String resource = "mybatis/mybatis-config.xml";
        if(sqlSessionFactory ==null){
            
            try {
                InputStream inputStream = Resources.getResourceAsStream(resource);
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    @Test
    public void callInterestReportTest(){
        SqlSession session = sqlSessionFactory.openSession();
        
        CapitalStatementMapper mapper = session.getMapper(CapitalStatementMapper.class);
        
        mapper.callInterestReport("201704");
        
        session.close();
    }

}
