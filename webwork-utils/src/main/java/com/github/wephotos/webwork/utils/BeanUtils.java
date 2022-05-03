package com.github.wephotos.webwork.utils;

/**
 * JavaBean工具类
 * @author TQ
 *
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

	/**
	 * 拷贝对象
	 * @param source 源对象
	 * @param targetClass 目标对象类型信息
	 * @return 目标对象
	 */
	public static <T> T toBean(Object source, Class<T> targetClass){
		try {
			T target = targetClass.newInstance();
			BeanUtils.copyProperties(source, target);
			return target;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalStateException("拷贝对象时创建目标对象失败", e);
		}
	}
}
