package com.bohai.dataCenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoleController {

	@RequestMapping(value="toRole")
	public String toRole(){
		return "permission/role";
	}
}
