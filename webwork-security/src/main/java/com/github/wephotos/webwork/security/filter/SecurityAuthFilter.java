package com.github.wephotos.webwork.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;

import com.github.wephotos.webwork.logging.LoggerFactory;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.security.utils.SecurityUtils;
import com.github.wephotos.webwork.utils.StringUtils;

/**
 * 权限认证过滤器
 *
 * @author TQ
 */
public class SecurityAuthFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(SecurityAuthFilter.class);

    /**
     * 重定向页面
     */
    private String redirect = "/login";
    /**
     * 不进行安全验证的访问路径
     */
    private static final Set<String> ignorePaths = new HashSet<>(8);
    
    static {
    	ignorePaths.add("/resources");
    	ignorePaths.add("/platform/login");
    }

    /**
     * 无参构造
     */
    public SecurityAuthFilter() {
    	this(null, null);
    }

    /**
     * 使用重定向页面构造
     *
     * @param redirect 重定向页面地址
     */
    public SecurityAuthFilter(String redirect, String ignorePatterns) {
        if (redirect != null) {
            this.redirect = redirect;
        }
        if (StringUtils.isNotBlank(ignorePatterns)) {
        	ignorePaths.addAll(Arrays.asList(ignorePatterns.split(",")));
        }
        log.debug("未授权转发地址:{}", this.redirect);
        log.debug("无需授权访问地址:{}", ignorePaths);
    }

    /**
     * 权限过滤
     */
    @Override
    public void doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) sRequest;
        HttpServletResponse response = (HttpServletResponse) sResponse;
        String URI = request.getRequestURI();
        // 认证页面
        if (redirect.equals(URI)) {
            chain.doFilter(request, response);
            return;
        }
        // 忽略路径
        for (String path : ignorePaths) {
            if (URI.contains(path)) {
                chain.doFilter(request, response);
                return;
            }
        }
        HttpSession session = request.getSession();
        SecurityUser user = SecurityUtils.getSecurityUser(session);
        if (user == null) {
        	if(!isXMLHttpRequest(request)) {
        		response.sendRedirect(redirect);
        	}else {
        		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "未登录");
        	}
            return;
        }
        chain.doFilter(request, response);
    }
    
    /**
     * 是否AJAX请求
     * @param request HTTP请求
     * @return AJAX返回true
     */
    private static boolean isXMLHttpRequest(HttpServletRequest request) {
        String xRequestedWith = request.getHeader("X-Requested-With");
        return xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest");
    }
}
