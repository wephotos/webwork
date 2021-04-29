package com.github.wephotos.webwork.logging;

import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 日志事件
 *
 * @author TQ
 */
@Getter
@Setter
@ToString
public class WebworkLoggingEvent implements LoggingEvent {

    private Level level;
    private Marker marker;
    private String loggerName;
    private WebworkLogger logger;
    private String threadName;
    private String message;
    private Object[] argumentArray;
    private long timeStamp;
    private Throwable throwable;
    private LoggerRequest request;

    /**
     * 当前请求相关信息
     * @author TQ
     *
     */
    @Getter
    @Setter
    @ToString
    public static class LoggerRequest {
        /**
         * 请求者的ip
         */
        private String ip;
        /**
         * 操作者
         */
        private String operator;
        /**
         * 浏览器
         */
        private String userAgent;
        /**
         * 请求路径
         */
        private String requestURL;
    }
}
