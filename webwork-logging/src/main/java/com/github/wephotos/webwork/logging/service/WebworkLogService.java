package com.github.wephotos.webwork.logging.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.helpers.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.wephotos.webwork.logging.disruptor.LoggingEventHandler;
import com.github.wephotos.webwork.logging.entity.WebworkLog;
import com.github.wephotos.webwork.logging.entity.po.LogQueryPO;
import com.github.wephotos.webwork.logging.mapper.WebworkLogMapper;
import com.github.wephotos.webwork.schema.entity.Page;
import com.github.wephotos.webwork.schema.entity.Pageable;

/**
 * 日志业务类，实现了日志持久化接口
 * 
 * @author TQ
 *
 */
@Service
public class WebworkLogService implements IPersistenceService {
	
	private static final BlockingQueue<WebworkLog> QUEUE = new LinkedBlockingQueue<>();
	
	private static final ScheduledExecutorService SCHEDULED_SERVICE = Executors.newSingleThreadScheduledExecutor();

	@Value("${log.save-db.max-size: 200}")
	private Integer maxSize;
	
	@Resource
	private WebworkLogMapper webworkLogMapper;
	
	/**
	 * 实体初始化后执行
	 */
	@PostConstruct
	public void init() {
		LoggingEventHandler.setPersistenceService(this);
		// 首次延迟60秒，之后10秒入库
		SCHEDULED_SERVICE.scheduleAtFixedRate(() -> {
			try {
				drainToDB();
			}catch (Exception ex) {
				Util.report("日志批量入库失败", ex);
			}
		}, 60, 10, TimeUnit.SECONDS);
	}
	
	/**
	 * 将队列中的日志数据入库
	 */
	private void drainToDB() {
		List<WebworkLog> logs = new ArrayList<>(maxSize);
		QUEUE.drainTo(logs, maxSize);
		if (logs.size() > 0) {
			webworkLogMapper.insertBatch(logs);
		}
	}
	
	@Override
	public int save(WebworkLog log) {
		if (QUEUE.offer(log)) {
			// 入队成功且满足入库条件，入库
			if (QUEUE.size() == maxSize) {
				drainToDB();
			}
		} else {
			// 队列满 了，入库
			drainToDB();
			QUEUE.add(log);
		}
		return 1;
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
