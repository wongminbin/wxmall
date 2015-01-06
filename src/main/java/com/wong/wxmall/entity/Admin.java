package com.wong.wxmall.entity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.wong.core.util.RSAUtil;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月30日 下午3:18:49
 *
 */
@Entity
@Table(name = "admin")
public class Admin {

	private Long id;
	
	@NotEmpty(message = "用户名不能为空")
	private String name;
	@NotEmpty(message = "密码不能为空")
	private String password;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(unique = true, nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void decrypt() throws UnsupportedEncodingException{
		name = URLDecoder.decode(RSAUtil.decryptStringByJs(name), "utf-8");
		password = URLDecoder.decode(RSAUtil.decryptStringByJs(password), "utf-8");
	}
	
}
