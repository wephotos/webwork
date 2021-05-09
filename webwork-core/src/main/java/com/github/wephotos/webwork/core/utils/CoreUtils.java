package com.github.wephotos.webwork.core.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 工具类
 *
 * @author Dell-Aaron
 */
public final class CoreUtils {

	// 禁止实例化
    private CoreUtils() {
    	throw new IllegalStateException("access denied");
    }

    /**
     * 判断是否是AJAX请求
     * @param request 请求对象
     * @return AJAX请求返回true
     */
    public static boolean isXmlHttpRequest(HttpServletRequest request) {
        String xRequestedWith = request.getHeader("X-Requested-With");
        return xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest");
    }


}
