package com.github.wephotos.webwork.logging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.wephotos.webwork.logging.entity.WebworkLog;

/**
 * 请求参数处理
 * @author TQ
 *
 */
public final class LoggerRequestHandler {

	/**
	 * 获取日志操作者接口
	 */
	private static LoggerOperator operator;
	
	public static void setOperator(LoggerOperator operator) {
		LoggerRequestHandler.operator = operator;
	}
	
	/**
	 * 抽取请求信息设置到日志中
	 * @param log
	 */
	public static void extractRequest2Log(WebworkLog log) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if(!(requestAttributes instanceof ServletRequestAttributes)) {
			return;
		}
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		
		log.setIp(request.getRemoteAddr());
		log.setBrowser(getUserAgent(request));
		log.setUrl(request.getRequestURL().toString());
		
		// 设置用户信息
		HttpSession session = request.getSession();
		if(operator != null) {
			log.setOperator(operator.getName(session));
		}
	}
	
	/**
	 * 获取浏览器相关信息
	 * @param request 请求对象
	 * @return 用户代理信息
	 */
	static String getUserAgent(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}
}
