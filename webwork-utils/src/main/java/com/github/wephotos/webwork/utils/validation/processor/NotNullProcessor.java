package com.github.wephotos.webwork.utils.validation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.github.wephotos.webwork.utils.validation.ValidException;
import com.github.wephotos.webwork.utils.validation.annotation.NotNull;

/**
 * 非空校验处理器
 * 
 * @author TianQi
 *
 */
public enum NotNullProcessor implements IProcessor {
	
	PROCESSOR;
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return NotNull.class;
	}

	@Override
	public void validate(Object root, Field field) {
		final NotNull notNull = field.getAnnotation(NotNull.class);
		if(!isPredicate(notNull.predicate(), root)) {
			return;
		}
		final Object value = getFieldValue(field, root);
		if (value == null) {
			throw new ValidException(notNull.errcode(), notNull.message());
		}
	}

}
