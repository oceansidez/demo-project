package com.telecom.base;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.RowBoundsMapper;

/**
 * 继承自己的MyMapper
 *
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>, ConditionMapper<T>, RowBoundsMapper<T>  {

	// 查询关联表主键对应的对象（原始方法）
	@SelectProvider(type = BaseProvider.class, method = "dynamicSQL")
	T selectForDefault(@Param("key") Object key);
}