package com.github.wephotos.webwork.logging.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import org.slf4j.event.Level;

/**
 * @author xc
 * @date 2021-04-28 21:46
 */
@Getter
@Setter
@ToString
public class WebworkLog {
    /**
     * 主键
     */
    private String id;
    /**
     * 日志名称
     */
    private String name;
    /**
     * 请求者的ip
     */
    private String ip;
    /**
     * 操作者
     */
    private String operator;
    /**
     * 日志级别
     */
    private Level level;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * 请求路径
     */
    private String url;
    /**
     * 操作时间
     */
    private Date createTime;
    /**
     * 日志详情
     */
    private String content;
    
    /**
     * 堆栈信息
     */
    private String stackTrace;
}
