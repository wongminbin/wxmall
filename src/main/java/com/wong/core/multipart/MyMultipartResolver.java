package com.wong.core.multipart;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月25日 上午9:41:26
 *
 */
public class MyMultipartResolver extends CommonsMultipartResolver{
	
	@Override
	public boolean isMultipart(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String dir = request.getParameter("dir");
		// kindeditor 上传图片的时候 不进行request 的转换
        if(dir!=null){  
            return false;
        }
		return super.isMultipart(request);
	}

}
