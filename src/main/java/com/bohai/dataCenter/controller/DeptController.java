package com.bohai.dataCenter.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeptController {

	@RequestMapping(value="toDept")
	@RequiresPermissions(value="system:dept:view")
	public String toDept(){
		return "system/dept";
	}
}
