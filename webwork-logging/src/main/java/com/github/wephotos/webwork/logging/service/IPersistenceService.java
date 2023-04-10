package com.github.wephotos.webwork.logging.service;

import com.github.wephotos.webwork.logging.entity.WebworkLog;

/**
 * 日志持久化接口
 * 
 * @author TQ
 *
 */
public interface IPersistenceService {

	/**
	 * 保存日志
	 * 
	 * @param log 日志对象
	 * @return 影响行数
	 */
	int save(WebworkLog log);
}
