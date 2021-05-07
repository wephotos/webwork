package com.github.wephotos.webwork.core.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * 工具类
 *
 * @author Dell-Aaron
 */
@Component
public final class CoreUtils {

    private CoreUtils() {
    	throw new IllegalStateException("access denied");
    }

    public static boolean isXmlHttpRequest(HttpServletRequest request) {
        String xRequestedWith = request.getHeader("X-Requested-With");
        return xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest");
    }


}
