package com.github.wephotos.webwork.logging.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.helpers.Util;
import org.springframework.stereotype.Service;

import com.github.wephotos.webwork.logging.disruptor.LoggingEventHandler;
import com.github.wephotos.webwork.logging.entity.WebworkLog;
import com.github.wephotos.webwork.logging.entity.po.LogQueryPO;
import com.github.wephotos.webwork.logging.mapper.WebworkLogMapper;
import com.github.wephotos.webwork.schema.entity.Page;
import com.github.wephotos.webwork.schema.entity.Pageable;

/**
 * 日志服务的入库实现
 * @author TQ
 *
 */
@Service
public class WebworkLogServiceDb implements WebworkLogService {

	@Resource
	private WebworkLogMapper webworkLogMapper;
	
	/**
	 * 实体初始化后执行
	 */
	@PostConstruct
	public void init() {
		LoggingEventHandler.setLogService(this);
	}
	
	@Override
	public int save(WebworkLog log) {
		try {
			return webworkLogMapper.insert(log);
		}catch (Exception e) {
			Util.report(e.getMessage());
			Util.report(log.toString());
			return 0;
		}
	}

	@Override
	public int saveBatch(List<WebworkLog> logs) {
		return 0;
	}
	
	/**
	 * 分页查询
	 * @param pageable 分页条件
	 * @return 分页数据
	 */
	public Page<WebworkLog> pageQuery(Pageable<LogQueryPO> pageable){
		int count = this.webworkLogMapper.pageCount(pageable);
		List<WebworkLog> data = this.webworkLogMapper.pageList(pageable);
		return new Page<>(count, data);
	}

}
