package com.github.wephotos.webwork.utils.validation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import com.github.wephotos.webwork.utils.validation.ValidException;
import com.github.wephotos.webwork.utils.validation.annotation.Size;

/**
 * 数据大小校验处理器
 * 
 * @author TianQi
 *
 */
public enum SizeProcessor implements IProcessor {
	
	PROCESSOR;
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return Size.class;
	}

	@Override
	public void validate(Object root, Field field) {
		final Size size = field.getAnnotation(Size.class);
		if(!isPredicate(size.predicate(), root)) {
			return;
		}
		final Object value = getFieldValue(field, root);
		if(value == null) {
			return;
		}
		final int min = size.min();
		final int max = size.max();
		int length;
		if(value instanceof CharSequence) {
			length = ((CharSequence) value).length();
		}else if(value instanceof Collection<?>) {
			length = ((Collection<?>) value).size();
		}else if(value instanceof Map<?, ?>) {
			length = ((Map<?, ?>) value).size();
		}else if(value instanceof Object[]) {
			length = ((Object[]) value).length;
		}else {
			return;
		}
		if (min > length || max < length) {
			throw new ValidException(size.errcode(), size.message());
		}
	}

}
