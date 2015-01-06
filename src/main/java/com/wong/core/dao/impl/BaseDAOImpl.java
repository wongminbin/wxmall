package com.wong.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.wong.core.dao.BaseDAO;
import com.wong.core.vo.Page;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月16日 上午10:11:01
 *
 */
@SuppressWarnings("unchecked")
public class BaseDAOImpl extends HibernateDaoSupport implements BaseDAO {

	public Query getQuery(String sql) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		return session.createQuery(sql).setCacheable(true);
	}

	public Query getNativeQuery(String sql) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		return session.createSQLQuery(sql).setCacheable(true);
	}

	@Override
	@Autowired
	public void initSessionFactory(SessionFactory sessionFactory) {
		// TODO Auto-generated method stub
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public void add(Object obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(obj);
	}

	@Override
	public <T> T find(Class<T> clazz, long id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().load(clazz, id);
	}
	
	@Override
	public <T> T find(String sql, Object... args) {
		// TODO Auto-generated method stub
		Query query = getQuery(sql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		Object obj = query.uniqueResult();
		if(obj != null)
			return (T)obj;
		return null;
	}
	
	@Override
	public <T> T findByNativeSQL(String sql, Object... args) {
		// TODO Auto-generated method stub
		Query query = getNativeQuery(sql);
		
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		
		
		
		return null;
	}
	
	@Override
	public <T> List<T> findAll(Class<T> clazz) {
		// TODO Auto-generated method stub
		String sql = String.format("from %s e ", clazz.getSimpleName());
		Query query = getQuery(sql);
		return query.list();
	}
	
	@Override
	public <T> List<T> findAll(Class<T> clazz, String sql, Object... args) {
		// TODO Auto-generated method stub
		Query query = getQuery(sql);
		
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		
		return query.list();
	}

	@Override
	public List<Object[]> findAllByNativeSQL(String nativeSQL, Object... args) {
		// TODO Auto-generated method stub
		Query query = getNativeQuery(nativeSQL);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		return query.list();
	}
	
	@Override
	public <T> void findByPage(Page<T> page) {
		// TODO Auto-generated method stub
		page.setTotalSize(findCount(page.getClazz()));
		if(page.getPageCount() > 0) {
			String sql = String.format("from %s e ", page.getClazz().getSimpleName());
			Query query = getQuery(sql);
			
			query.setFirstResult(page.getFirstResult());
			query.setMaxResults(page.getMaxResult());
			page.setList(query.list());
		} else {
			page.setList(new ArrayList<T>());
		}
	}

	@Override
	public <T> int findCount(Class<T> clazz) {
		// TODO Auto-generated method stub
		String sql = String.format("select count(e.id) from %s e ", clazz.getSimpleName());
		
		Query query = getQuery(sql);
		
		Object obj = query.uniqueResult();
		if (obj != null)
			return ((Long) obj).intValue();
		
		return 0;
	}

	@Override
	public int findCount(String sql, Object... args) {
		// TODO Auto-generated method stub
		Query query = getQuery(sql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		Object obj = query.uniqueResult();
		if (obj != null)
			return ((Long) obj).intValue();
		
		return 0;
	}
	
	@Override
	public int findCountByNativeSQL(String nativeSQL, Object... args) {
		// TODO Auto-generated method stub
		Query query = getNativeQuery(nativeSQL);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		Object obj = query.uniqueResult();
		if (obj != null)
			return ((Long) obj).intValue();
		
		return 0;
	}

}
