package com.wong.core.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wong.core.util.WeiXinSignUtil;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月30日 上午9:37:31
 *	用于验证URL有效性，成功后即接入生效，成为开发者
 */
@Controller
@RequestMapping("/weixin")
public class WeiXinController {

	@RequestMapping("/check")
	public void check(HttpServletResponse response, 
			String signature, String timestamp, 
			String nonce, String echostr){
		
		String token = null;
		if(WeiXinSignUtil.checkSignature(token, signature, timestamp, nonce)){
			try {
				response.getWriter().print(echostr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
