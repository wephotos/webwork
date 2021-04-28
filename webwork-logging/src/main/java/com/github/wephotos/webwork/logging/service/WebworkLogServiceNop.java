package com.github.wephotos.webwork.logging.service;

import java.util.List;

import com.github.wephotos.webwork.logging.entity.WebworkLog;

/**
 * 日志服务接口的空实现
 * @author TQ
 *
 */
public class WebworkLogServiceNop implements WebworkLogService {

	@Override
	public int save(WebworkLog log) {
		return 0;
	}

	@Override
	public int saveBatch(List<WebworkLog> logs) {
		return 0;
	}

}
