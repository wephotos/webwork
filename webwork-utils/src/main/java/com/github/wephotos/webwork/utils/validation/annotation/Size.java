package com.github.wephotos.webwork.utils.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 元素大小校验
 * 可校验如下类型：Array、Collection、Map、String
 * 
 * @author TianQi
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Size {

	/**
	 * @return 错误码
	 */
	String errcode();
	
	/**
	 * @return 错误消息
	 */
	String message() default "";
	
	/**
	 * 注解开启条件
	 * @return OGNL Boolean expression
	 */
	String predicate() default "";
	
	/**
	 * @return 最小长度
	 */
	int min() default 0;
	
	/**
	 * @return 最大长度
	 */
	int max() default Integer.MAX_VALUE;
}
