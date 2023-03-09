package com.github.wephotos.webwork.utils.validation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.github.wephotos.webwork.utils.StringUtils;
import com.github.wephotos.webwork.utils.validation.ValidException;
import com.github.wephotos.webwork.utils.validation.annotation.NotBlank;

/**
 * 空白字符校验处理器
 * 
 * @author TianQi
 *
 */
public enum NotBlankProcessor implements IProcessor {

	PROCESSOR;
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return NotBlank.class;
	}

	@Override
	public void validate(Object root, Field field) {
		final NotBlank notBlank = field.getAnnotation(NotBlank.class);
		if(!isPredicate(notBlank.predicate(), root)) {
			return;
		}
		final Object value = getFieldValue(field, root);
		if (value == null || isBlank(value)) {
			throw new ValidException(notBlank.errcode(), notBlank.message());
		}
	}
	
	private boolean isBlank(Object val) {
		if(val instanceof CharSequence) {
			return StringUtils.isBlank((CharSequence)val);
		} else {
			return false;
		}
	}

}
