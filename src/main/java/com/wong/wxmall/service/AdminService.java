package com.wong.wxmall.service;

import com.wong.wxmall.entity.Admin;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月30日 下午3:22:44
 *
 */
public interface AdminService {

	Admin query(String name, String password);

	Admin getExist(Admin admin);

	void add(Admin admin);

}
