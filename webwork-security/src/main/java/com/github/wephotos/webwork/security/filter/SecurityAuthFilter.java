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

import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.security.storage.SessionUserStorage;

/**
 * 权限认证过滤器
 * @author TQ
 *
 */
public class SecurityAuthFilter implements Filter {
	
	/**
	 * 无参构造
	 */
	public SecurityAuthFilter() {
		
	}
	
	/**
	 * 使用重定向页面构造
	 * @param redirect 重定向页面地址
	 */
	public SecurityAuthFilter(String redirect, String ignorePaths) {
		if(redirect != null) {
			this.redirect = redirect;
		}
		ignoreList.add(this.redirect);
		if(ignorePaths != null) {
			ignoreList.addAll(Arrays.asList(ignorePaths.split(",")));
		}
	}
	
	/**
	 * 重定向页面
	 */
	private String redirect = "/";
	/**
	 * 忽略地址
	 */
	private final List<String> ignoreList = new ArrayList<>(Arrays.asList("/security/auth"));

	/**
	 * 权限过滤
	 */
	@Override
	public void doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) sRequest;
		HttpServletResponse response = (HttpServletResponse) sResponse;
		String URI = request.getRequestURI();
		for(String path : ignoreList) {
			if(URI.contains(path)) {
				chain.doFilter(request, response);
				return;
			}
		}
		HttpSession session = request.getSession();
		User user = SessionUserStorage.get(session);
		if(user == null) {
			response.sendRedirect(redirect);
			return;
		}
		chain.doFilter(request, response);
	}
}
