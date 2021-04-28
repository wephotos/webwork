package com.github.wephotos.webwork.logging.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author xc
 * @date 2021-04-28 21:46
 */
@Getter
@Setter
@ToString
public class WebworkLog {
    /**
     * pk
     */
    private String id;
    /**
     * 请求者的ip
     */
    private String ip;
    /**
     * 操作者
     */
    private String operator;
    /**
     * 0:异常日志,1:操作日志,2:登录日志
     */
    private String type;
    /**
     * INFO,ERROR,DEBUG
     */
    private String level;
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
}
