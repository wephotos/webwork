package com.github.wephotos.webwork.utils.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 空校验
 * 
 * @author TianQi
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotNull {

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
}
