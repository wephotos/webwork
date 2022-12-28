package com.github.wephotos.webwork.logging.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.event.Level;

/**
 * @author xc
 * @date 2021-04-28 21:46
 */
@Getter
@Setter
@ToString
public class WebworkLog implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 主键
     */
    private Integer id;
    /**
     * 日志级别
     */
    private Level level;
    /**
     * 日志名称
     */
    private String name;
    /**
     * Trace ID
     */
    private String traceId;
    /**
     * 操作者
     */
    private String username;
    /**
     * 请求路径(HTTP接口)
     */
    private String url;
    /**
     * 客户端信息
     */
    private String clientInfo;
    /**
     * 客户端主机
     */
    private String clientHost;
    /**
     * 日志详情
     */
    private String content;
    
    /**
     * 堆栈信息
     */
    private String stackTrace;
    
    /**
     * 操作时间
     */
    private Date createTime;
}
