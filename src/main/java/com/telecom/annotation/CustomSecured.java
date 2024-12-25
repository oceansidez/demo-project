package com.telecom.annotation;

import java.lang.annotation.*;

/**
 * 自定义验证注解
 * 作用：可配置aop后，对注解的方法进行aop切面拦截
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomSecured {

	String[] value() default {};

	String[] authorities() default {};

	String[] roles() default {};
}
