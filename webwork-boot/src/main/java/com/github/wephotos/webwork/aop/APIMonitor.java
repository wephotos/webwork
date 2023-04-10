package com.github.wephotos.webwork.aop;

import java.util.Arrays;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wephotos.webwork.logging.LoggerFactory;

/**
 * 应用程序接口监控
 * 
 * @author TianQi
 *
 */
@Aspect
@Component
public class APIMonitor {
	
	private static final Logger log = LoggerFactory.getLogger(APIMonitor.class);

	@Resource
	private ObjectMapper objectMapper;
	
	@Pointcut("execution(public * com.github.wephotos.webwork..controller.*.*(..))")
	public void monitor() {

	}
	
	@Around("monitor()")
	public Object doAround(ProceedingJoinPoint point) throws Throwable {
		long startTime = System.currentTimeMillis();
		try {
			return point.proceed();
		} finally {
			long millis = System.currentTimeMillis() - startTime;
			log.info("\n\t接口: {}\t耗时: {} ms \n\t入参: {}", getCurrentRequestURI(), millis, 
					Arrays.toString(getCustomArgs(point.getArgs())));
		}
	}
	
	/**
	 * 获取客户化的参数
	 * 
	 * @param args 接口参数数组
	 * @return 自定义参数数组
	 */
	private Object[] getCustomArgs(Object[] args) {
		return Stream.of(args).filter(arg -> {
			return arg != null && arg.getClass().getName().startsWith("com.github.wephotos");
		}).toArray();
	}
	
	/**
	 * 获取当前请求接口路径
	 * 
	 * @return 接口路径
	 */
	private String getCurrentRequestURI() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			return request.getRequestURI();
		}
		return null;
	}
}
