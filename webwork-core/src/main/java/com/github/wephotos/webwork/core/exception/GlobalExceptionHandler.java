package com.github.wephotos.webwork.core.exception;


import com.github.wephotos.webwork.error.WebworkException;
import com.github.wephotos.webwork.http.RestObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chengzi
 * @date 2021-01-27 10:33
 */
@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {
    static final String CLIENT_ABORT_EXCEPTION_NAME = "ClientAbortException";

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(WebworkException.class)
    public RestObject handleWebworkException(WebworkException e) {
        log.error(e.getMessage());
        return RestObject.builder().code(e.getCode()).msg(e.getMessage()).build();
    }

    /**
     * 异常处理
     *
     * @param request  request
     * @param response response
     * @param ex       ex
     * @return RestObject
     */
    @ExceptionHandler(value = Exception.class)
    public RestObject uncaughtException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        // 忽略客户端导致的异常
        String simpleName = ex.getClass().getSimpleName();
        if (CLIENT_ABORT_EXCEPTION_NAME.equals(simpleName)) {
            return null;
        }
        log.error("系统错误", ex);
        String message = ex.getMessage();
        if(message == null) {
        	message = "内部错误请联系管理员";
        }
        return RestObject.builder().code(500).msg(message).build();
    }
}
