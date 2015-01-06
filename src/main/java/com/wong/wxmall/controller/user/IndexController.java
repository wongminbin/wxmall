package com.wong.wxmall.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月30日 上午10:54:28
 *
 */
@Controller
public class IndexController {

	@RequestMapping("/index.html")
	public String index(){
		return "user/index";
	}
}
