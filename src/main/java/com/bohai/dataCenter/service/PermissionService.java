package com.bohai.dataCenter.service;

import java.util.List;

import com.bohai.dataCenter.vo.TreeView;

public interface PermissionService {
	
	public List<TreeView> queryUserPermissionTree(Long userId,Long permissionLevel);

}
