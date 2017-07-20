package com.bohai.dataCenter.vo;

import java.util.List;

import com.bohai.dataCenter.entity.SysPermission;

/**
 * 修改用户权限
 * @author BHQH-CXYWB
 */
public class UpdateUserPermissionsParamVO {
    
    private String userName;
    
    private List<TreeView<SysPermission>> permissionList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<TreeView<SysPermission>> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<TreeView<SysPermission>> permissionList) {
        this.permissionList = permissionList;
    }
    
    
}
