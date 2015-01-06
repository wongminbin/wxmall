package com.wong.wxmall.util;

import javax.servlet.http.HttpServletRequest;

import com.wong.wxmall.entity.Admin;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月30日 下午1:22:03
 *
 */
public class AdminLoginUtil {

	public static boolean isLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		/*HttpSession session = request.getSession();
		String isLogin = HttpUtil.getStrInSession(session, "admin");
		if(isLogin != null)
			return true;
		return false;*/
		return true;
	}
	
	public static void saveLogin(Admin admin, HttpServletRequest request){
		
	}

}
