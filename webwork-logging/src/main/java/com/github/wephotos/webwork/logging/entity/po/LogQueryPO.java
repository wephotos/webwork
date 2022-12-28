package com.github.wephotos.webwork.logging.entity.po;

import java.util.Date;

import org.slf4j.event.Level;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 日志查询参数
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class LogQueryPO {

	/**
	 * 日志级别
	 * {@link Level}
	 */
	private String level;
	
	/**
	 * 追踪ID
	 */
	private String traceId;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 日志内容，用于针对内容的模糊查询
	 */
	private String content;
	
	/**
	 * 起始时间
	 */
	private Date btime;
	
	/**
	 * 结束时间
	 */
	private Date etime;
}
