package com.github.wephotos.webwork.utils.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数字大小校验
 * 
 * @author TianQi
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Digit {

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
	 * 默认为 0
	 * @return 参数最小值
	 */
	long min() default 0;
	
	/**
	 * 默认为 {@link Long#MAX_VALUE}
	 * @return 最大值
	 */
	long max() default Long.MAX_VALUE;
}
