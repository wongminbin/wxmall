package com.wong.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class HttpUtil {
	
	/**
	 * 获取客服端浏览器的md5特征
	 * @param request
	 * @return
	 */
	public static String createMD5(HttpServletRequest request){
		String addr = request.getRemoteAddr();
		String host = request.getRemoteHost();
		String agent = request.getHeader("User-Agent");
		return MD5Util.getInstance().getStr(addr+host+agent);
	}
	
	/**
	 * 获取客服端ip地址
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getRemoteIP(HttpServletRequest request) throws Exception{
		String ip = request.getHeader("x-forwarded-for");  
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		   ip = request.getHeader("Proxy-Client-IP");  
		}  
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		   ip = request.getHeader("WL-Proxy-Client-IP");  
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		    ip = request.getRemoteAddr();  
		}  
	     return ip;   
	}
	
	/**
	 * 把键值对保存到session中
	 * @param session
	 * @param key
	 * @param value
	 */
	public static void saveSession(HttpSession session, String key, Object value){
		session.setAttribute(key, value);
	}

	/**
	 * 把Map集合中的键值对保存到session中
	 * @param session
	 * @param attr
	 */
	public static void saveSession(HttpSession session, Map<String, Object> attr){
	    Set<Entry<String, Object>> entrys = attr.entrySet();
	    for (Entry<String, Object> entry : entrys) {
			saveSession(session, entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * 根据时间（单位：秒）把键值对保存到session中
	 * @param session
	 * @param key
	 * @param value
	 */
	public static void saveSession(HttpSession session, String key, Object value, int time){
		session.setMaxInactiveInterval(time);
		saveSession(session, key, value);
	}
	
	/**
	 * 根据时间（单位：秒）把Map集合中的键值对保存到session中
	 * @param session
	 * @param attr
	 */
	public static void saveSession(HttpSession session, Map<String, Object> attr, int time){
		session.setMaxInactiveInterval(time);
		saveSession(session, attr);
	}
	
	/**
	 * 清除session中指定的key
	 * @param session
	 * @param key
	 */
	public static void cleanSession(HttpSession session, String key){
		session.setAttribute(key, null);
	}
	
	/**
	 * 把集合中的key在session中清空
	 * @param session
	 * @param keys
	 */
	public static void cleanSession(HttpSession session, List<String> keys){
		for (String key : keys) {
			cleanSession(session, key);
		}
	}
	
	/**
	 * 清除session中的所有信息
	 * @param session
	 */
	public static void cleanSession(HttpSession session){
		session.setMaxInactiveInterval(0);
		Enumeration<String> str = session.getAttributeNames();
		if (str == null )
			return;
		while (str.hasMoreElements()) {
			cleanSession(session, str.nextElement());
		}
	}
	
	/**
	 * 根据key从session中获取值
	 * @param session
	 * @param key
	 * @return
	 */
	public static Object getInSession(HttpSession session, String key){
		return session.getAttribute(key);
		
	}
	
	public static String getStrInSession(HttpSession session, String key){
		Object obj = getInSession(session, key);
		return obj == null? null:(String)obj;
	}
	
	public static Integer getIntInSession(HttpSession session, String key){
		Object obj = getInSession(session, key);
		return obj == null? null:(Integer)obj;
	}
	
	/**
	 * 把session中保存的所有键值对获取出来
	 * @param session
	 * @return
	 */
	public static Map<String, Object> getInSession(HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> str = session.getAttributeNames();
		while (str.hasMoreElements()) {
			String key = str.nextElement();
			map.put(key, getInSession(session, key));
		}
		return map;
	}
	
	/**
	 * 根据指定的key集合从session中获取键值对集合
	 * @param session
	 * @param keys
	 * @return
	 */
	public static Map<String, Object> getInSession(HttpSession session, List<String> keys){
		Map<String, Object> map = new HashMap<String, Object>();
		for (String key : keys) {
			map.put(key, getInSession(session, key));
		}
		return map;
	}
	
	/**
	 * 判断请求方式
	 * @return
	 */
	public static boolean isPost(HttpServletRequest request){
		String method = request.getMethod();
		return "POST".equalsIgnoreCase(method);
	}
	
	/**
	 * 根据指定时间（单位为秒）把键值对保存到cookie中
	 * @param response
	 * @param key
	 * @param value
	 * @param time
	 * @throws UnsupportedEncodingException
	 */
	public static void saveCookie(HttpServletResponse response, String key, String value, int time) throws UnsupportedEncodingException{
		Cookie c = new Cookie(URLEncoder.encode(key, "utf-8"), URLEncoder.encode(value, "utf-8"));
		c.setMaxAge(time);
		c.setPath("/");
		response.addCookie(c);
	}
	
	/**
	 * 根据指定时间（单位为秒）包键值对集合保存到cookie中
	 * @param response
	 * @param attr
	 * @param time
	 * @throws UnsupportedEncodingException
	 */
	public static void saveCookie(HttpServletResponse response, Map<String, String> attr, int time) throws UnsupportedEncodingException{
		Set<Entry<String, String>> entrys = attr.entrySet();
		for (Entry<String, String> entry : entrys) {
			saveCookie(response, entry.getKey(), entry.getValue(), time);
		}
	}
	
	/**
	 * 把cookie中该key的键值对清空
	 * @param response
	 * @param key
	 * @throws UnsupportedEncodingException
	 */
	public static void cleanCookie(HttpServletResponse response, String key) throws UnsupportedEncodingException{
		Cookie c = new Cookie(URLEncoder.encode(key, "utf-8"), null);
		c.setMaxAge(0);
		c.setPath("/");
		response.addCookie(c);
	}
	
	/**
	 * 把cookie中该keys集合的键值对清空
	 * @param response
	 * @param keys
	 * @throws UnsupportedEncodingException
	 */
	public static void cleanCookie(HttpServletResponse response, List<String> keys) throws UnsupportedEncodingException{
		for (String key : keys) {
			cleanCookie(response, key);
		}
	}
	
	/**
	 * 把cookie中所有键值对清空
	 * @param response
	 * @param request
	 */
	public static void cleanCookie(HttpServletResponse response, HttpServletRequest request){
		Cookie[] cs = request.getCookies();
		if(cs == null || cs.length == 0)
			return;
		for (Cookie c : cs) {
			c.setValue(null);
			c.setMaxAge(0);
			c.setPath("/");
			response.addCookie(c);
		}
	}
	
	/**
	 * 根据key从cookie中获取value值
	 * @param request
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getInCookie(HttpServletRequest request, String key) throws UnsupportedEncodingException{
		for (Cookie c : getInCookie(request)) {
			if(key.equalsIgnoreCase(URLDecoder.decode(c.getName(), "utf-8")))
				return URLDecoder.decode(c.getValue(), "utf-8");
		}
		return null;
	}
	
	/**
	 * 根据keys集合从cookie中该keys的键值对集合
	 * @param request
	 * @param keys
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> getInCookie(HttpServletRequest request, List<String> keys) throws UnsupportedEncodingException{
		Map<String, String> map = new HashMap<String, String>();
		for (String key : keys) {
			map.put(key, getInCookie(request, key));
		}
		return map;
	}
	
	/**
	 * 获取所有的cookie数组
	 * @param request
	 * @return
	 */
	public static Cookie[] getInCookie(HttpServletRequest request){
		return request.getCookies();
	}
	
}
