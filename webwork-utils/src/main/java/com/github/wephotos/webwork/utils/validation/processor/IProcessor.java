package com.github.wephotos.webwork.utils.validation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.github.wephotos.webwork.utils.StringUtils;
import com.github.wephotos.webwork.utils.validation.ValidException;

/**
 * 校验注解处理器
 * 
 * @author TianQi
 *
 */
public interface IProcessor {

	/**
	 * 获取校验注解类型信息
	 * 
	 * @return 注解信息
	 */
	Class<? extends Annotation> annotationType();
	
	/**
	 * 校验对象属性
	 * 
	 * @param root 对象
	 * @param field 对象属性
	 */
	void validate(Object root, Field field);
	
	/**
	 * 获取字段值
	 * 
	 * @param field 字段
	 * @param root 当前对象
	 * @return 字段值
	 */
	default Object getFieldValue(Field field, Object root) {
		try {
			field.setAccessible(true);
			return field.get(root);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new ValidException("获取字段值时出错", e);
		}
	}
	
	/**
	 * 判断条件是否成立
	 * 
	 * @param ognl boolean expression
	 * @param root 当前对象
	 * @return 条件成立返回 true
	 */
	default boolean isPredicate(String ognl, Object root) {
		if (StringUtils.isBlank(ognl)) {
			return true;
		} else {
			// TODO OGNL
			return true;			
		}
	}
}
