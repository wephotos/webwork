package com.github.wephotos.webwork.logging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.wephotos.webwork.logging.WebworkLoggingEvent.HttpLogRequest;

/**
 * 请求参数处理
 * @author TQ
 *
 */
public final class LoggerRequestHandler {

	/**
	 * 获取日志操作者接口
	 */
	private static ILogUserContext context;
	
	public static void setLogUserContext(ILogUserContext context) {
		LoggerRequestHandler.context = context;
	}
	
	/**
	 * 抽取请求信息设置到日志中
	 * @param loggerRequest 日志请求对象
	 */
	@Nullable
	public static HttpLogRequest getHttpLogRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if(!(requestAttributes instanceof ServletRequestAttributes)) {
			return null;
		}
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		HttpLogRequest request2 = new HttpLogRequest();
		request2.setClientHost(request.getRemoteAddr());
		request2.setUserAgent(getUserAgent(request));
		request2.setRequestURL(request.getRequestURL().toString());
		
		// 设置用户信息
		HttpSession session = request.getSession();
		if(context != null) {
			request2.setUsername(context.getUsername(session));
		}
		return request2;
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
