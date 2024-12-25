package com.telecom.base;

import com.telecom.bean.Pager;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.List;

/**
 * 采用泛型的方式传入
 * @param <T>
 */
public interface BaseService<T> {

	public T get(String id);
	
	public T save(T item);
	
	public Integer update(T item);
	
	public Integer delete(String id);
	
	public List<T> getAllList();
	
	public Integer getCount();
	
	public Pager findPager(Pager pager);
	
	public Pager findPager(Pager pager,Criteria criteria,Condition condition);
	
}
