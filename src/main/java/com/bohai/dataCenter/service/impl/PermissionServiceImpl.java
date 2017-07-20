package com.bohai.dataCenter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bohai.dataCenter.entity.SysPermission;
import com.bohai.dataCenter.entity.SysUsersPermissions;
import com.bohai.dataCenter.persistence.SysPermissionMapper;
import com.bohai.dataCenter.persistence.SysUsersPermissionsMapper;
import com.bohai.dataCenter.service.PermissionService;
import com.bohai.dataCenter.vo.TreeView;
import com.bohai.dataCenter.vo.TreeView.State;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	static Logger logger = Logger.getLogger(PermissionServiceImpl.class);
	
	@Autowired
	private SysPermissionMapper sysPermissionMapper;
	
	@Autowired
	private SysUsersPermissionsMapper sysUsersPermissionsMapper;
	
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

    @Override
    public List<TreeView<SysPermission>> queryUserPermissions(String userName, Long parentPermissionId) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parentPermissionId", parentPermissionId);
        
        List<TreeView<SysPermission>> treeList = null;
        
        List<SysPermission> list = this.sysPermissionMapper.queryPermissionsSelective(map);
        if(list != null && list.size() > 0 ){
            
            treeList = new ArrayList<TreeView<SysPermission>>();
            
            for (SysPermission permission : list){
                TreeView<SysPermission> treeView = new TreeView<SysPermission>();
                treeView.setText(permission.getDescription());
                treeView.setHref(permission.getUrl());
                treeView.setData(permission);
                State state = new State();
                state.setChecked(true);
                state.setExpanded(true);
                treeView.setState(state);
                
                //递归查询子菜单权限
                treeView.setNodes(this.queryUserPermissions(userName,permission.getId()));
                
                
                treeList.add(treeView);
            }
        }
        
        return treeList;
        
    }

    @Override
    public void updateUserPermissions(String userName, List<TreeView<SysPermission>> list) {
        
        //先删除所有权限
        this.sysUsersPermissionsMapper.deleteByUserName(userName);
        
        //再保存新的权限
        if(list != null && list.size() >0 ){
            for (TreeView<SysPermission> treeView : list) {
                
                SysUsersPermissions usersPermissions = new SysUsersPermissions();
                usersPermissions.setPermission(treeView.getData().getPermission());
                usersPermissions.setUserName(userName);
                usersPermissions.setUpdateTime(new Date());
                this.sysUsersPermissionsMapper.insertSelective(usersPermissions);
                
            }
        }
    }
	
	

}
