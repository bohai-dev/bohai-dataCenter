package com.bohai.dataCenter.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bohai.dataCenter.entity.SysPermission;
import com.bohai.dataCenter.persistence.SysPermissionMapper;
import com.bohai.dataCenter.service.PermissionService;
import com.bohai.dataCenter.vo.TreeView;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	static Logger logger = Logger.getLogger(PermissionServiceImpl.class);
	
	@Autowired
	private SysPermissionMapper sysPermissionMapper;
	
	@Override
	public List<TreeView> queryUserPermissionTree(String userName,Long parentPermissionId) {

		List<TreeView> treeList = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("parentPermissionId", parentPermissionId);
		List<SysPermission> list = this.sysPermissionMapper.queryPermissionsSelective(map);
		
		logger.debug("查询权限返回 " + JSON.toJSONString(list));
		if(list != null && list.size() > 0 ){
			treeList = new ArrayList<TreeView>();
			for (SysPermission permission : list){
				TreeView treeView = new TreeView();
				treeView.setText(permission.getDescription());
				System.out.println(permission.getDescription());
				treeView.setHref(permission.getUrl());
				//递归查询子菜单权限
				treeView.setNodes(this.queryUserPermissionTree(userName,permission.getId()));
				
				treeList.add(treeView);
			}
		}
		
		return treeList;
	}

}
