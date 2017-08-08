package com.bohai.dataCenter.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.dataCenter.entity.SysPermission;
import com.bohai.dataCenter.service.PermissionService;
import com.bohai.dataCenter.vo.QueryUserPermissionsParamVO;
import com.bohai.dataCenter.vo.TreeView;
import com.bohai.dataCenter.vo.UpdateUserPermissionsParamVO;

@Controller
public class PermissionController {
    
    static Logger logger = Logger.getLogger(PermissionController.class);
    
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value="toPermission")
    @RequiresPermissions("permission:view")
    public String toPermission(){
        return "permission/permission";
    }
    
    /**
     * 查询所有权限及用户是否拥有该权限
     * @param userName
     * @param parentPermissionId
     * @return
     */
    @RequestMapping(value="queryUserPermissions")
    @ResponseBody
    public List<TreeView<SysPermission>> queryUserPermissions(@RequestBody QueryUserPermissionsParamVO paramVO){
        
        logger.debug("查询用户权限入参："+paramVO.getUserName());
        
        return this.permissionService.queryUserPermissions(paramVO.getUserName(), null);
    }
    
    /**
     * 更新用户的权限
     * @param list
     */
    @RequestMapping(value="updateUserPermissions")
    @ResponseBody
    public void updateUserPermissions(@RequestBody(required=false) UpdateUserPermissionsParamVO paramVO){
        
        String userName = paramVO.getUserName();
        List<TreeView<SysPermission>> list = paramVO.getPermissionList();
        
        this.permissionService.updateUserPermissions(userName, list);
        
    }
    
}
