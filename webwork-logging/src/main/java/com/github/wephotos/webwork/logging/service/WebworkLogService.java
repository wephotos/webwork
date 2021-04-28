package com.github.wephotos.webwork.logging.service;

import java.util.List;

import com.github.wephotos.webwork.logging.entity.WebworkLog;

/**
 * 日志服务接口
 * @author TQ
 *
 */
public interface WebworkLogService {

	/**
	 * 保存日志
	 * @param log 日志对象
	 * @return 影响行数
	 */
	int save(WebworkLog log);
	
	/**
	 * 批量保存日志
	 * @param logs 日志集合
	 * @return 影响行数
	 */
	int saveBatch(List<WebworkLog> logs);
}
