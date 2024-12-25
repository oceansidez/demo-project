package com.telecom.mongo.base;

import java.util.List;

public interface BaseDaoForMongo<T> {

	public T save(T entity);

	public void update(T entity);

	public void delete(String id);
	
	public void delete(String... ids);

	public T find(String id);

	public List<T> findAll();

	public List<T> findAll(String order);

	public List<T> findByProp(String propName, Object value);

	public List<T> findByProp(String propName, Object value, String order);

	public List<T> findByProps(String[] propName, Object[] values);

	public List<T> findByProps(String[] propName, Object[] values, String order);

	public T uniqueByProp(String propName, Object value);

	public T uniqueByProps(String[] propName, Object[] values);

	public int countByCondition(String[] params, Object[] values);
	
}
