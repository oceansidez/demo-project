package com.telecom.mongo.base;

import java.util.List;

public abstract class BaseServiceImplForMongo<T> implements BaseServiceForMongo<T> {

	public abstract BaseDaoForMongo<T> getBaseDao();

	public T save(T entity) {
		return getBaseDao().save(entity);
	}

	public void update(T entity) {
		getBaseDao().update(entity);
	}

	public void delete(String id) {
		getBaseDao().delete(id);
	}
	
	public void delete(String... ids) {
		getBaseDao().delete(ids);
	}

	public T find(String id) {
		return getBaseDao().find(id);
	}

	public List<T> findAll() {
		return getBaseDao().findAll();
	}

	public List<T> findAll(String order) {
		return getBaseDao().findAll(order);
	}

	public List<T> findByProp(String propName, Object value) {
		return getBaseDao().findByProp(propName, value);
	}

	public List<T> findByProp(String propName, Object value, String order) {
		return getBaseDao().findByProp(propName, value, order);
	}

	public List<T> findByProps(String[] propName, Object[] values) {
		return getBaseDao().findByProps(propName, values);
	}

	public List<T> findByProps(String[] propName, Object[] values, String order) {
		return getBaseDao().findByProps(propName, values, order);
	}

	public T uniqueByProp(String propName, Object value) {
		return getBaseDao().uniqueByProp(propName, value);
	}

	public T uniqueByProps(String[] propName, Object[] values) {
		return getBaseDao().uniqueByProps(propName, values);
	}

	public int countByCondition(String[] params, Object[] values) {
		return getBaseDao().countByCondition(params, values);
	}
}
