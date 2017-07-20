package com.bohai.dataCenter.shiro.realm;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.bohai.dataCenter.entity.SysUser;
import com.bohai.dataCenter.entity.SysUsersPermissions;
import com.bohai.dataCenter.persistence.SysUserMapper;
import com.bohai.dataCenter.persistence.SysUsersPermissionsMapper;

public class UserRealm extends AuthorizingRealm {
    
    static Logger logger = Logger.getLogger(UserRealm.class);
    
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUsersPermissionsMapper sysUsersPermissionsMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        // TODO Auto-generated method stub
    	String userName = (String) arg0.fromRealm(getName()).iterator().next();
    	System.out.println("用户名："+userName);
    	//用户对应权限集合
    	
    	SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    	
    	ArrayList<SysUsersPermissions> sysUsersPermissionsList =new ArrayList<SysUsersPermissions>();
    	sysUsersPermissionsList=this.sysUsersPermissionsMapper.select(userName);
    	for(SysUsersPermissions sysUsersPermission:sysUsersPermissionsList){
    		authorizationInfo.addStringPermission(sysUsersPermission.getPermission());
    	}
    	return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        
        String username = (String) token.getPrincipal();
        //String password = new String((char[])token.getCredentials());
        
        SysUser sysUser = this.sysUserMapper.queryUserByUsername(username);
        if(sysUser == null){
            logger.warn("无此用户信息,用户名："+username);
            throw new UnknownAccountException("无此用户信息,用户名："+username);
        }
        
        logger.info("token: "+JSON.toJSONString(token));
        
        return new SimpleAuthenticationInfo(username, sysUser.getPassword(), getName());
        
    }

}
