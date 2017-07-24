package com.bohai.dataCenter.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.dataCenter.entity.SysUser;
import com.bohai.dataCenter.persistence.CrmCustomerMapper;
import com.bohai.dataCenter.persistence.SysUserMapper;
import com.bohai.dataCenter.service.UserService;
import com.bohai.dataCenter.vo.TableJsonResponse;
import com.bohai.dataCenter.vo.UserVO;

@Controller
public class UserController {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="toUser")
	@RequiresPermissions(value="system:user:view")
	public String toUser(){
		return "system/user";
	}
	
	@RequestMapping(value="user/queryUsers")
	@ResponseBody
	public TableJsonResponse<UserVO> queryUsersPagination(){
		TableJsonResponse<UserVO> response = new TableJsonResponse<UserVO>();
		
		List<UserVO> list = this.userService.queryUsersPagination(null);
		response.setRows(list);
		response.setTotal(1L);
		
		return response;
	}
	
	@RequestMapping(value="saveSysUser")
	@ResponseBody
	public void saveSysUser(@RequestBody SysUser record){
		record.setCreateTime(new Date());
		this.sysUserMapper.insertSelective(record);
	}
}
