package com.telecom.mongo.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.persistence.Id;
import java.lang.reflect.*;
import java.util.*;

public class BaseDaoImplForMongo<T> implements BaseDaoForMongo<T> {

	@Autowired
	MongoTemplate mongoTemplate;

	private Class<T> entityClass;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseDaoImplForMongo() {
        Class c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
	}
	
	@Override
	public T save(T entity) {
		return mongoTemplate.insert(entity);
	}

	@Override
	public void update(T entity) {
		Map<String, Object> map = null;
		try {
			map = parseEntity(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String id = null;
		Object value = null;
		Update update = new Update();
		if (map != null && map.size() > 0) {
			for (String key : map.keySet()) {
				if (key.startsWith("{")) {
					id = key.substring(key.indexOf("{") + 1, key.indexOf("}"));
					value = map.get(key);
				} else {
					update.set(key, map.get(key));
				}
			}
		}
		mongoTemplate.updateFirst(new Query().addCriteria(Criteria.where(id).is(value)), update, entityClass);
	}

	@Override
	public void delete(String id) {
		mongoTemplate.remove(mongoTemplate.findById(id, entityClass));
	}
	
	@Override
	public void delete(String... ids) {
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				mongoTemplate.remove(mongoTemplate.findById(id, entityClass));
			}
		}

	}

	@Override
	public T find(String id) {
		return mongoTemplate.findById(id, entityClass);
	}

	@Override
	public List<T> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public List<T> findAll(String order) {
		List<Order> orderlist = parseOrder(order);
		if (orderlist == null || orderlist.size() == 0) {
			return findAll();
		}
		return mongoTemplate.find(new Query().with(Sort.by(orderlist)), entityClass);
	}

	@Override
	public List<T> findByProp(String propName, Object value) {
		return findByProp(propName, value, null);
	}

	@Override
	public List<T> findByProp(String propName, Object value, String order) {
		Query query = new Query();
		query.addCriteria(Criteria.where(propName).is(value));
		List<Order> orderlist = parseOrder(order);
		if (orderlist != null && orderlist.size() > 0) {
			query.with(Sort.by(orderlist));
		}
		return mongoTemplate.find(query, entityClass);
	}

	@Override
	public List<T> findByProps(String[] propName, Object[] values) {
		return findByProps(propName, values, null);
	}

	@Override
	public List<T> findByProps(String[] propName, Object[] values, String order) {
		Query query = createQuery(propName, values, order);
		return mongoTemplate.find(query, entityClass);
	}

	@Override
	public T uniqueByProp(String propName, Object value) {
		return mongoTemplate.findOne(new Query().addCriteria(Criteria.where(propName).is(value)), entityClass);
	}

	@Override
	public T uniqueByProps(String[] propName, Object[] values) {
		Query query = createQuery(propName, values, null);
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public int countByCondition(String[] params, Object[] values) {
		Query query = createQuery(params, values, null);
		Long count = mongoTemplate.count(query, entityClass);
		return count.intValue();
	}

	public Query createQuery(String[] propName, Object[] values, String order) {
		Query query = new Query();
		// where
		if (propName != null && values != null) {
			for (int i = 0; i < propName.length; i++) {
				query.addCriteria(Criteria.where(propName[i]).is(values[i]));
			}
		}

		List<Order> orderlist = parseOrder(order);
		if (orderlist != null && orderlist.size() > 0) {
			query.with(Sort.by(orderlist));
		}
		return query;
	}

	public List<Order> parseOrder(String order) {
		List<Order> list = null;
		if (order != null && !"".equals(order)) {
			list = new ArrayList<>();
			String[] fields = order.split(",");
			Order o = null;
			String[] items = null;
			for (int i = 0; i < fields.length; i++) {
				if (fields[i] == null) {
					continue;
				}
				items = fields[i].split(" ");
				if (items.length == 1) {
					o = new Order(Direction.ASC, items[0]);
				} else if (items.length == 2) {
					o = new Order("desc".equalsIgnoreCase(items[1]) ? Direction.DESC : Direction.ASC, items[0]);
				} else {
					throw new RuntimeException("order field parse error");
				}
				list.add(o);
			}
		}
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	private Map<String, Object> parseEntity(T t) throws Exception {
		Map<String, Object> map = new HashMap<>();
		String id = "";
		List<Field> declaredFields = new ArrayList<>();
		Class clazz = entityClass;
		while (clazz != null) {
			declaredFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		}
		
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(Id.class)) {
				field.setAccessible(true);
				map.put("{" + field.getName() + "}", field.get(t));
				id = field.getName();
				break;
			}
		}

		Method[] declaredMethods = entityClass.getDeclaredMethods();
		if (declaredFields != null && declaredFields.size() > 0) {
			for (Method method : declaredMethods) {
				if (method.getName().startsWith("get") && method.getModifiers() == Modifier.PUBLIC) {
					String fieldName = parseToFieldName(method.getName());
					if (!fieldName.equals(id)) {
						map.put(fieldName, method.invoke(t));
					}
				}
			}
		}
		return map;
	}

	private String parseToFieldName(String method) {
		String name = method.replace("get", "");
		name = name.substring(0, 1).toLowerCase() + name.substring(1);
		return name;
	}

}
