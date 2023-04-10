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
    private HttpLogRequest request;
    
    /**
     * MDC 全局追踪ID
     */
    private String traceId;

    /**
     * HTTP相关信息
     * 
     * @author TQ
     *
     */
    @Getter
    @Setter
    @ToString
    public static class HttpLogRequest {
        /**
         * 当前用户
         */
        private String username;
        /**
         * 客户端主机
         */
        private String clientHost;
        /**
         * 客户端(浏览器)信息
         */
        private String userAgent;
        /**
         * 请求路径
         */
        private String requestURL;
    }
}
