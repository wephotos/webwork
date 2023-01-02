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
import com.github.wephotos.webwork.schema.exception.WebworkRuntimeException;
import com.github.wephotos.webwork.schema.utils.Results;

/**
 * @author chengzi
 * @date 2021-01-27 10:33
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
    static final String CLIENT_ABORT_EXCEPTION_NAME = "ClientAbortException";

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
        StateCode stateCode;
        if(e instanceof WebworkException) {
        	stateCode = ((WebworkException) e).getStateCode();
        }else if(e instanceof WebworkRuntimeException) {
        	stateCode = ((WebworkRuntimeException) e).getStateCode();
        }else {
        	stateCode = StateCode.UNKNOW_ERROR;
        }
        return Results.newResult(stateCode, e.getMessage());
    }
}
