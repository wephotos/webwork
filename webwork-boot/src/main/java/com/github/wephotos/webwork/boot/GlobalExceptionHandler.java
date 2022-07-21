package com.github.wephotos.webwork.boot;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.wephotos.webwork.logging.LoggerFactory;
import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.exception.StateCode;
import com.github.wephotos.webwork.schema.exception.WebworkException;

/**
 * @author chengzi
 * @date 2021-01-27 10:33
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
    static final String CLIENT_ABORT_EXCEPTION_NAME = "ClientAbortException";

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(WebworkException.class)
    public Result<Void> handleWebworkException(WebworkException e) {
        log.error("系统异常", e);
        return new Result<>(e.getStateCode());
    }

    /**
     * 异常处理
     *
     * @param request  request
     * @param response response
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler(value = Exception.class)
    public Result<String> uncaughtException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        // 忽略客户端导致的异常
        String simpleName = e.getClass().getSimpleName();
        if (CLIENT_ABORT_EXCEPTION_NAME.equals(simpleName)) {
            return null;
        }
        log.error("系统错误", e);
        return new Result<>(e.getMessage(), StateCode.SERVER_ERROR);
    }
}
