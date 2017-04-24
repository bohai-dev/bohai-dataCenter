package com.bohai.dataCenter.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PermissionController {

	@RequestMapping(value="toPermission")
	@RequiresPermissions("permission:view")
	public String toPermission(){
		return "permission/permission";
	}
	
}
