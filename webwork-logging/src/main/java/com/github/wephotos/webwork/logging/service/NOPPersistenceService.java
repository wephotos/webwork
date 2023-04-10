package com.github.wephotos.webwork.logging.service;

import com.github.wephotos.webwork.logging.entity.WebworkLog;

/**
 * 日志服务接口的空实现
 * @author TQ
 *
 */
public class NOPPersistenceService implements IPersistenceService {

	@Override
	public int save(WebworkLog log) {
		return 0;
	}
}
