package com.bohai.dataCenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeptController {

	@RequestMapping(value="toDept")
	public String toDept(){
		return "system/dept";
	}
}
