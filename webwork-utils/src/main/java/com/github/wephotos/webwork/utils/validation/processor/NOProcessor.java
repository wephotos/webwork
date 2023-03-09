package com.github.wephotos.webwork.utils.validation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * NOP
 * 
 * @author TianQi
 *
 */
public enum NOProcessor implements IProcessor {
	
	NOP;

	@Override
	public Class<? extends Annotation> annotationType() {
		return null;
	}

	@Override
	public void validate(Object root, Field field) {
		// NOP
	}

}
