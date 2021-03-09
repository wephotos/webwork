package com.github.wephotos.webwork.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * 工具类
 *
 * @author Dell-Aaron
 */
@Component
public final class WebWorkUtil {

    private static ObjectMapper objectMapper;

    @Resource
    public void setObjectMapper(ObjectMapper objectMapper) {
        WebWorkUtil.objectMapper = objectMapper;
    }

    private WebWorkUtil() {

    }

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static Timestamp timestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static boolean isXMLHttpRequest(HttpServletRequest request) {
        String xRequestedWith = request.getHeader("X-Requested-With");
        return xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest");
    }

    /**
     * 写出JSON数据到响应流中
     *
     * @param value
     * @param response
     * @throws IOException
     */
    public static void responseJson(Object value, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(response.getOutputStream(), value);
    }

}
