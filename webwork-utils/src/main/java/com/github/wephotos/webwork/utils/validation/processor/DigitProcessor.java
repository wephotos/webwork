package com.github.wephotos.webwork.utils.validation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.github.wephotos.webwork.utils.validation.ValidException;
import com.github.wephotos.webwork.utils.validation.annotation.Digit;

/**
 * 数字校验注解处理器
 * 
 * @author TianQi
 *
 */
public enum DigitProcessor implements IProcessor {

	PROCESSOR;
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return Digit.class;
	}
	
	@Override
	public void validate(Object root, Field field) {
		final Digit digit = field.getAnnotation(Digit.class);
		if(!isPredicate(digit.predicate(), root)) {
			return;
		}
		final Object value = getFieldValue(field, root);
		if (value instanceof Number) {
			final long min = digit.min();
			final long max = digit.max();
			final long number = ((Number) value).longValue();
			if (min > number || max < number) {
				throw new ValidException(digit.errcode(), digit.message());
			}
		}
	}
}
