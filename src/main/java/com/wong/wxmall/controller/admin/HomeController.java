package com.wong.wxmall.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月30日 下午2:38:09
 *
 */
@Controller
@RequestMapping("/admin")
public class HomeController {
	
	@RequestMapping("/home.html")
	public String home(){
		
		return "admin/index";
	}

}
