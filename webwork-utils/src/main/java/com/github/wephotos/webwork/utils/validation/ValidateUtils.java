package com.github.wephotos.webwork.utils.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.wephotos.webwork.utils.validation.annotation.Digit;
import com.github.wephotos.webwork.utils.validation.annotation.NotBlank;
import com.github.wephotos.webwork.utils.validation.annotation.NotNull;
import com.github.wephotos.webwork.utils.validation.annotation.Pattern;
import com.github.wephotos.webwork.utils.validation.annotation.Size;
import com.github.wephotos.webwork.utils.validation.processor.DigitProcessor;
import com.github.wephotos.webwork.utils.validation.processor.IProcessor;
import com.github.wephotos.webwork.utils.validation.processor.NOProcessor;
import com.github.wephotos.webwork.utils.validation.processor.NotBlankProcessor;
import com.github.wephotos.webwork.utils.validation.processor.NotNullProcessor;
import com.github.wephotos.webwork.utils.validation.processor.PatternProcessor;
import com.github.wephotos.webwork.utils.validation.processor.SizeProcessor;

/**
 * 参数校验工具类
 * 
 * @author TianQi
 *
 */
public final class ValidateUtils {

	private static final Map<String, IProcessor> PROCESSORS = new ConcurrentHashMap<>();
	
	static {
		PROCESSORS.put(Size.class.getName(), SizeProcessor.PROCESSOR);
		PROCESSORS.put(Digit.class.getName(), DigitProcessor.PROCESSOR);
		PROCESSORS.put(Pattern.class.getName(), PatternProcessor.PROCESSOR);
		PROCESSORS.put(NotNull.class.getName(), NotNullProcessor.PROCESSOR);
		PROCESSORS.put(NotBlank.class.getName(), NotBlankProcessor.PROCESSOR);
	}
	
	/**
	 * 校验参数对象
	 * 
	 * @param root 参数对象
	 */
	private static void validate(Object root) {
		final Field[] fields = root.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			final Field field = fields[i];
			final Annotation[] annotations = field.getAnnotations();
			for (int j = 0; j < annotations.length; j++) {
				final String name = annotations[j].annotationType().getName();
				PROCESSORS.getOrDefault(name, NOProcessor.NOP).validate(root, field);
			}
		}
	}
	
	/**
	 * 校验方法中被 {@link Valid} 注解标记的参数
	 * @param method 被校验方法
	 * @param args 方法参数
	 */
	public static void validate(Method method, Object[] args) {
		Parameter[] parameters = method.getParameters();
		for (int i = 0; i < parameters.length; i++) {
			Parameter parameter = parameters[i];
			Valid valid = parameter.getAnnotation(Valid.class);
			if (valid != null && args[i] != null) {
				validate(args[i]);
			}
		}
	}
}
