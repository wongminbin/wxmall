package com.wong.core.util;

import java.io.File;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月19日 下午1:03:22
 *
 */
public class PathUtil {
	
	private static String tomcat;
	private static String webapp;
	private static String classes;
	
	static {
		tomcat = System.getProperty("catalina.home") + File.separator;
		classes = PathUtil.class.getClassLoader().getResource("/").getPath()+File.separator;
		webapp = new File(classes).getParentFile().getParent() + File.separator;
	}

	/**
	 * 获取tomcat服务器的根路径
	 * @return
	 */
	public static String tomcat(){
		
		return tomcat;
	}
	
	/**
	 * 获取项目的根路径
	 * @return
	 */
	public static String webapp(){
		
		return webapp;
	}
	
	/**
	 * 获取.class文件的根路径
	 * @return
	 */
	public static String classes(){
		
		return classes;
	}
	
}
