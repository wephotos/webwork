package com.github.wephotos.webwork.utils.validation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;

import com.github.wephotos.webwork.utils.validation.ValidException;
import com.github.wephotos.webwork.utils.validation.annotation.Pattern;
import com.github.wephotos.webwork.utils.validation.annotation.Pattern.Flag;

/**
 * 正则校验处理器
 * 
 * @author TianQi
 *
 */
public enum PatternProcessor implements IProcessor {
	
	PROCESSOR;
	
	private static final Map<String, java.util.regex.Pattern> CACHE = new ConcurrentHashMap<>();

	@Override
	public Class<? extends Annotation> annotationType() {
		return Pattern.class;
	}

	@Override
	public void validate(Object root, Field field) {
		final Pattern annotation = field.getAnnotation(Pattern.class);
		if(!isPredicate(annotation.predicate(), root)) {
			return;
		}
		final Object value = getFieldValue(field, root);
		if(value instanceof CharSequence) {
			final Flag[] flags = annotation.flags();
			final String regexp = annotation.regexp();
			int mask = 0;
			for(Pattern.Flag flag : flags) {
				mask |= flag.getFlag();
			}
			final String key = String.format("%s_%s", regexp, mask);
			java.util.regex.Pattern pattern = CACHE.get(key);
			if(pattern == null) {
				pattern = java.util.regex.Pattern.compile(regexp, mask);
				CACHE.putIfAbsent(key, pattern);
			}
			final Matcher matcher = pattern.matcher((CharSequence)value);
			if(!matcher.matches()) {
				throw new ValidException(annotation.errcode(), annotation.message());
			}
		}
	}

}
