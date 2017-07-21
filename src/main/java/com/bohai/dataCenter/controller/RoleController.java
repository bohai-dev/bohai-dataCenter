package com.bohai.dataCenter.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoleController {

	@RequestMapping(value="toRole")
	@RequiresPermissions(value="role:view")
	public String toRole(){
		return "permission/role";
	}
}
