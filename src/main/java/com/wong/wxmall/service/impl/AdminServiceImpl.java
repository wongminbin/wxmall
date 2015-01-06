package com.wong.wxmall.service.impl;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wong.core.util.MD5Util;
import com.wong.wxmall.dao.AdminDAO;
import com.wong.wxmall.entity.Admin;
import com.wong.wxmall.service.AdminService;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月30日 下午3:23:21
 *
 */
@Service(value = "adminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDAO adminDAO;
	
	@Override
	public Admin query(String name, String password) {
		// TODO Auto-generated method stub
		return adminDAO.find("from Admin a where a.name = ? and a.password = ? ", name, MD5Util.getInstance().getStr(password));
	}

	@Override
	public Admin getExist(Admin admin) {
		// TODO Auto-generated method stub
		try {
			//rsa解密
			admin.decrypt();
			return query(admin.getName(), admin.getPassword());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void add(Admin admin) {
		// TODO Auto-generated method stub
		try {
			//rsa解密
			admin.decrypt();
			admin.setPassword(MD5Util.getInstance().getStr(admin.getPassword()));
			adminDAO.add(admin);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
