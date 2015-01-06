package com.wong.wxmall.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wong.wxmall.cons.AdminConstants;
import com.wong.wxmall.util.AdminLoginUtil;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月30日 下午1:12:32
 *
 */
public class AdminInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		String uri = request.getRequestURI();
		System.out.println("uri ----------- "+uri);
		
		if (AdminLoginUtil.isLogin(request)) {
			return true;
		}else {
			response.sendRedirect(AdminConstants.REDIRECT_LOGIN);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

}
