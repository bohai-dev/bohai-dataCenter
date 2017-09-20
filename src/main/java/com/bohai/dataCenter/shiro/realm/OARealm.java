package com.bohai.dataCenter.shiro.realm;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.bohai.dataCenter.entity.SysUser;
import com.bohai.dataCenter.persistence.SysUserMapper;

/**
 * OA单点登录验证
 * @author caojia
 *
 */
public class OARealm extends AuthenticatingRealm {
    
    static Logger logger = Logger.getLogger(OARealm.class);
    
    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        
        String username = (String) token.getPrincipal();
        String password = new String((char[])token.getCredentials());
        
        SysUser sysUser = this.sysUserMapper.queryUserByOA(username);
        if(sysUser == null){
            logger.warn("无此OA用户信息,用户名："+username);
            throw new UnknownAccountException("无此OA用户信息,用户名："+username);
        }
        
        logger.info("token: "+JSON.toJSONString(token));
        
        AuthenticationInfo authenticationInfo =new SimpleAuthenticationInfo(username, sysUser.getOaPass(), getName());
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.getSession().setAttribute("user", sysUser);
        return authenticationInfo; 
        
    }

}
