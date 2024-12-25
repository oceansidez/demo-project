package com.telecom.annotation;

import java.lang.annotation.*;

/**
 * 自定义表字段注解
 * 作用：导出
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyTableColumn {

	String name() default "";
	String mark() default "";
}
