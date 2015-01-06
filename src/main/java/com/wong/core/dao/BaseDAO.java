package com.wong.core.dao;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.SessionFactory;

import com.wong.core.vo.Page;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月16日 上午10:11:16
 *
 */
@SuppressWarnings("hiding")
public interface BaseDAO {
	public void initSessionFactory(SessionFactory sessionFactory);
	
	public void add(Object obj);
	
	public <T> T find(Class<T> clazz, long id);
	
	public <T> T find(String sql, Object... args);
	
	public <T> T findByNativeSQL(String sql, Object... args);
	
	public <T> List<T> findAll(Class<T> clazz);
	
	public <T> List<T> findAll(Class<T> clazz, String sql, Object... args);
	
	public List<Object[]> findAllByNativeSQL(String nativeSQL, Object... args);
	
	public <T> void findByPage(Page<T> page);
	
	public <T> int findCount(Class<T> clazz);
	
	public int findCount(String sql, Object... args);
	
	public int findCountByNativeSQL(String nativeSQL, Object... args);

}
