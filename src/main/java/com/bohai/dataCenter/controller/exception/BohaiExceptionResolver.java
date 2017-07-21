package com.bohai.dataCenter.controller.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 渤海期货数据中心异常处理类
 * @author caojia
 *
 */
public class BohaiExceptionResolver extends SimpleMappingExceptionResolver{
    
    @Override
    public ModelAndView doResolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {
        
        ModelAndView mv = new ModelAndView();
        
        if(ex instanceof UnauthorizedException){  
            mv.setViewName("unauthorized");
        }else {
            mv.setViewName("error");
        }
        mv.addObject(DEFAULT_EXCEPTION_ATTRIBUTE, ex.getMessage());
        
        
        return mv;
        
        
    }
    
    

}
