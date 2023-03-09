package com.github.wephotos.webwork.utils.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 正则校验
 * 
 * @author TianQi
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Pattern {


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
	 * @return 正则表达式
	 */
	String regexp();
	
	/**
	 * @return Match flags, a bit mask that may include
	 */
	Flag[] flags() default {};
	
	
	public static enum Flag {
		
		UNIX_LINES(java.util.regex.Pattern.UNIX_LINES),
		CASE_INSENSITIVE(java.util.regex.Pattern.CASE_INSENSITIVE),
		COMMENTS(java.util.regex.Pattern.COMMENTS),
		MULTILINE(java.util.regex.Pattern.MULTILINE),
		LITERAL(java.util.regex.Pattern.LITERAL),
		DOTALL(java.util.regex.Pattern.DOTALL),
		UNICODE_CASE(java.util.regex.Pattern.UNICODE_CASE),
		CANON_EQ(java.util.regex.Pattern.CANON_EQ),
		UNICODE_CHARACTER_CLASS(java.util.regex.Pattern.UNICODE_CHARACTER_CLASS);

		Flag(int flag) {
			this.flag = flag;
		}
		
		private int flag;
		
		public int getFlag() {
			return flag;
		}
	}
}
