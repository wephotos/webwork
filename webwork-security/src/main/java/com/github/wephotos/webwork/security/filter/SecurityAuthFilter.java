package com.github.wephotos.webwork.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;

import com.github.wephotos.logging.LoggerFactory;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.security.storage.SessionUserStorage;
import com.github.wephotos.webwork.utils.StringUtils;

/**
 * 权限认证过滤器
 * @author TQ
 *
 */
public class SecurityAuthFilter implements Filter {
	
	private static final Logger log = LoggerFactory.getLogger(SecurityAuthFilter.class);
	/**
	 * 无参构造
	 */
	public SecurityAuthFilter() {
		
	}
	
	/**
	 * 使用重定向页面构造
	 * @param redirect 重定向页面地址
	 */
	public SecurityAuthFilter(String redirect, String ignorePatterns) {
		if(redirect != null) {
			this.redirect = redirect;
		}
		if(StringUtils.isNotBlank(ignorePatterns)) {
			patternList.addAll(Arrays.asList(ignorePatterns.split(",")));
		}
	}
	
	/**
	 * 重定向页面
	 */
	private String redirect = "/login";
	/**
	 * 忽略地址
	 */
	private final List<String> patternList = new ArrayList<>(Arrays.asList("/security/auth"));

	/**
	 * 权限过滤
	 */
	@Override
	public void doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) sRequest;
		HttpServletResponse response = (HttpServletResponse) sResponse;
		String URI = request.getRequestURI();
		log.debug("当前请求地址:{}, 登录地址为:{}", URI, redirect);
		// 认证页面
		if(redirect.equals(URI)) {
			chain.doFilter(request, response);
			return;
		}
		log.debug("忽略地址为:{}", patternList);
		// 忽略路径
		for(String path : patternList) {
			if(URI.contains(path)) {
				chain.doFilter(request, response);
				return;
			}
		}
		HttpSession session = request.getSession();
		User user = SessionUserStorage.get(session);
		log.debug("当前用户为:{}", user);
		if(user == null) {
			response.sendRedirect(redirect);
			return;
		}
		chain.doFilter(request, response);
	}
}
