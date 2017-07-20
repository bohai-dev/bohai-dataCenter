package com.bohai.dataCenter.service;

import java.util.List;

import com.bohai.dataCenter.entity.SysPermission;
import com.bohai.dataCenter.vo.TreeView;

public interface PermissionService {
	

    /**
     * 仅查询用户拥有的权限
     * @param userId
     * @param permissionLevel
     * @return
     */
    public List<TreeView> queryUserPermissionTree(String userName,Long permissionLevel);
	
	/**
	 * 查询所有权限及用户是否拥有该权限
	 * @param userName
	 * @param parentPermissionId
	 * @return
	 */
	public List<TreeView<SysPermission>> queryUserPermissions(String userName, Long parentPermissionId);
	
	/**
	 * 更新用户权限
	 * @param userName
	 * @param list
	 */
	public void updateUserPermissions(String userName, List<TreeView<SysPermission>> list);

	

}
