package com.github.wephotos.webwork.logging;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.MDC.MDCCloseable;

/**
 * MDC设置
 * @author TianQi
 *
 */
public class MDCFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try(MDCCloseable mdc = MDCUtils.putCloseable()){
			chain.doFilter(request, response);
		}
	}

}
