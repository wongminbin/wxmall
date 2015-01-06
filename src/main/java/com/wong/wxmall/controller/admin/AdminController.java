package com.wong.wxmall.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wong.core.commons.RsaKey;
import com.wong.wxmall.cons.AdminConstants;
import com.wong.wxmall.entity.Admin;
import com.wong.wxmall.service.AdminService;
import com.wong.wxmall.util.AdminLoginUtil;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月30日 下午1:10:12
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(method = RequestMethod.POST)
	public String login(@Valid Admin admin, BindingResult result, Model model, HttpServletRequest request){
		
		if(! result.hasErrors()){
			
			admin = adminService.getExist(admin);
			
			if(admin != null){
				AdminLoginUtil.saveLogin(admin, request);
				
				return AdminConstants.REDIRECT_HOME;
			}	
			
		}
		
		//使用rsa加密传输的数据
		initRsa(model);
		return AdminConstants.VIEW_LOGIN;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model){

		//使用rsa加密传输的数据
		initRsa(model);
		return AdminConstants.VIEW_LOGIN;
	}
	
	@RequestMapping(value = "/registe.html", method = RequestMethod.GET)
	public String registe(Model model){
		initRsa(model);
		return AdminConstants.VIEW_REGISTE;
	}

	@RequestMapping(value = "/registe.html", method = RequestMethod.POST)
	public String registe(@Valid Admin admin, BindingResult result, Model model){
		
		if(! result.hasErrors()){
			
			adminService.add(admin);
			
			return "redirect:/admin/home.html";
		}
		
		initRsa(model);
		return AdminConstants.VIEW_REGISTE;
	}
	
	private void initRsa(Model model){
		model.addAttribute("modulus", RsaKey.getModulus());
		model.addAttribute("exponent", RsaKey.getExponent());
	}
}
