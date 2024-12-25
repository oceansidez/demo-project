package com.telecom.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.telecom.bean.Pager;
import com.telecom.bean.Pager.Order;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 实现类也采用泛型，传入哪个类型就是对哪个类型进行操作
 * @param <T>
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	private Class<T> entityClass;
	private final static String DEFAULT_ORDER_BY_CREATE_DATE = "create_date";
	public abstract BaseMapper<T> getBaseMapper();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseServiceImpl() {
        Class c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
	}
	//通过方法进行查询然后进行返回
	public T get(String id){
		return getBaseMapper().selectByPrimaryKey(id);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public T save(T item){
		Integer result = getBaseMapper().insert(item);
		if(result == 1){
			return item;
		}
		else return null;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Integer update(T item){
		return getBaseMapper().updateByPrimaryKey(item);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Integer delete(String id){
		return getBaseMapper().deleteByPrimaryKey(id);
	}
	
	public List<T> getAllList() {
		return getBaseMapper().selectAll();
	}
	
	public Integer getCount() {
		return getBaseMapper().selectCount(null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Pager findPager(Pager pager) {
		
		Condition condition = new Condition(entityClass);
    	Criteria criteria = condition.createCriteria();
		
		// 模糊查询
    	if(pager.getSearchMap() != null && pager.getSearchMap().size() > 0){
			for (String key : pager.getSearchMap().keySet()) {
				if (StringUtils.isNotEmpty(pager.getSearchMap().get(key))) {
					String value = pager.getSearchMap().get(key);
					criteria.andLike(key, "%" + value + "%");
				}
			}
    	}
				
		// 时间条件
		if(StringUtils.isNotEmpty(pager.getTimeBy())){
			
			if(StringUtils.isNotEmpty(pager.getBeginDate())){
				criteria.andGreaterThanOrEqualTo(pager.getTimeBy(), pager.getBeginDate());
			}
			if(StringUtils.isNotEmpty(pager.getEndDate())){
				criteria.andLessThan(pager.getTimeBy(), pager.getEndDate());
			}
		}
		
		// 排序
		if(StringUtils.isNotEmpty(pager.getOrderBy()) && pager.getOrder() != null){
			String orderSQL = pager.getOrderBy() + " " + pager.getOrder().toString();
			condition.setOrderByClause(orderSQL);
		}
		else{
			if(pager.getOrderMap() != null && pager.getOrderMap().size() > 0){
				String orderSQL = "";
				for (String key : pager.getOrderMap().keySet()) {
					Order order = pager.getOrderMap().get(key);
					orderSQL += (key + " " + order.toString() + ",");
				}
				orderSQL = orderSQL.substring(0, orderSQL.length() - 1);
				condition.setOrderByClause(orderSQL);
			}
			else{
				// 默认按照创建日期倒序
				condition.setOrderByClause(DEFAULT_ORDER_BY_CREATE_DATE + " desc");
			}
		}
		
		PageHelper.startPage(pager.getPageNumber(), pager.getPageSize());
    	List<T> list = getBaseMapper().selectByCondition(condition);
    	PageInfo<T> pageInfo = new PageInfo(list);
    	pager.setResult(pageInfo.getList());
		pager.setTotalCount((int) pageInfo.getTotal());
    	return pager;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Pager findPager(Pager pager, Criteria criteria,Condition condition) {
		
		// 模糊查询
    	if(pager.getSearchMap() != null && pager.getSearchMap().size() > 0){
			for (String key : pager.getSearchMap().keySet()) {
				if (StringUtils.isNotEmpty(pager.getSearchMap().get(key))) {
					String value = pager.getSearchMap().get(key);
					criteria.andLike(key, "%" + value + "%");
				}
			}
    	}
				
		// 时间条件
		if(StringUtils.isNotEmpty(pager.getTimeBy())){
			
			if(StringUtils.isNotEmpty(pager.getBeginDate())){
				criteria.andGreaterThanOrEqualTo(pager.getTimeBy(), pager.getBeginDate());
			}
			if(StringUtils.isNotEmpty(pager.getEndDate())){
				criteria.andLessThan(pager.getTimeBy(), pager.getEndDate());
			}
		}
		
		// 排序
		if(StringUtils.isNotEmpty(pager.getOrderBy()) && pager.getOrder() != null){
			String orderSQL = pager.getOrderBy() + " " + pager.getOrder().toString();
			condition.setOrderByClause(orderSQL);
		}
		else{
			if(pager.getOrderMap() != null && pager.getOrderMap().size() > 0){
				String orderSQL = "";
				for (String key : pager.getOrderMap().keySet()) {
					Order order = pager.getOrderMap().get(key);
					orderSQL += (key + " " + order.toString() + ",");
				}
				orderSQL = orderSQL.substring(0, orderSQL.length() - 1);
				condition.setOrderByClause(orderSQL);
			}
			else{
				// 默认按照创建日期倒序
				condition.setOrderByClause(DEFAULT_ORDER_BY_CREATE_DATE + " desc");
			}
		}
		
		PageHelper.startPage(pager.getPageNumber(), pager.getPageSize());
    	List<T> list = getBaseMapper().selectByCondition(condition);
    	PageInfo<T> pageInfo = new PageInfo(list);
    	pager.setResult(pageInfo.getList());
		pager.setTotalCount((int) pageInfo.getTotal());
    	return pager;
	}
}
