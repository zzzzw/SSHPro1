package com.zq.www.common;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

//使用泛型
@Transactional 
public class BaseDAO<T> {

	//spring里的注解，实现自动注入(自动new)
	@Resource
	private SessionFactory sessionFactory;
	
	
	/*
	 * 得到session，所有增删改查都从session开始
	 */
    
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session gs() {
		return this.sessionFactory.getCurrentSession();
	}
	
	
	
	/*
	 * 根据T得到真正的class类型，使用反射
	 */
	protected Class<T> entityClass;

	protected Class getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return entityClass;
	}

	


	/*
	 * 添加
	 */
	@Transactional
	public void add(T t) {
		 gs().save(t);
	}
	
	/*
	 * 更新
	 */
	@Transactional
	public void update(T t) {
		gs().update(t);
	}

	/*
	 * 删除
	 */
	@Transactional
	public void delete(Integer id) {
		gs().delete(this.get(id));
	}
	

	/*
	 * 查询
	 */
	@Transactional
	public T get(Integer id) {
		T instance = (T) gs().get(getEntityClass(), id);
		return instance;
       
	}
	


	/*
	 * 自动判断添加或修改，保存
	 */
	@Transactional
	public void save(T t) {
		gs().saveOrUpdate(t);
	}
	
	
	/*
	 * 查询全部
	 */
	@Transactional
	public List<T> listall() {
		List<T> result=gs().createCriteria(getEntityClass()).list();
		return result;
	}
	
	
	/*
	 * 通用查询
	 */	
	@Transactional
	public List<T> getListByHQL(final String hqlString, final List values) {
		Query query = gs().createQuery(hqlString);
		if (values != null) {
			Object[] vvs = values.toArray(new Object[values.size()]);
			for (int i = 0; i < vvs.length; i++) {
				query.setParameter(i, vvs[i]);
			}
		}
		return query.list();
	}

	/* 执行复杂的sql查询
	 * 
	 *  得到一列：List<String>
	 * 
	 * 得到多列：List<String[]>
	 */
	@Transactional(readOnly = true)
	public List getListBySQL(final String sqlString, final List values) {
		Query query = gs().createSQLQuery(sqlString);
		if (values != null) {
			Object[] vvs = values.toArray(new Object[values.size()]);
			for (int i = 0; i < vvs.length; i++) {
				query.setParameter(i, vvs[i]);
			}
		}
		return query.list();
	}
	
	
	
	/* 执行复杂的sql更新、删除、添加
	 * 
	 *  得到受影响的行数
	 * 
	 */
	@Transactional
	public Integer executeSQL(final String sqlString, final List values) {
		Query query = gs().createSQLQuery(sqlString);
		if (values != null) {
			Object[] vvs = values.toArray(new Object[values.size()]);
			for (int i = 0; i < vvs.length; i++) {
				query.setParameter(i, vvs[i]);
			}
		}
		return query.executeUpdate();
	}

	
}
