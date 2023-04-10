package com.github.wephotos.webwork.logging.disruptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Timestamp;

import com.github.wephotos.webwork.logging.WebworkLoggingEvent;
import com.github.wephotos.webwork.logging.WebworkLoggingEvent.HttpLogRequest;
import com.github.wephotos.webwork.logging.entity.WebworkLog;
import com.github.wephotos.webwork.logging.service.IPersistenceService;
import com.github.wephotos.webwork.logging.service.NOPPersistenceService;
import com.lmax.disruptor.EventHandler;

/**
 * 日志事件处理,可进行入库等操作
 *
 * @author TQ
 */
public class LoggingEventHandler implements EventHandler<WebworkLoggingEvent> {
	
	/***
	 * 日志服务
	 */
	private static IPersistenceService persistence = new NOPPersistenceService();
	
	/**
	 * 设置日志服务
	 * @param logService 日志服务
	 */
	public static void setPersistenceService(IPersistenceService persistence) {
		if(persistence != null) {
			LoggingEventHandler.persistence = persistence;
		}
	}

    @Override
    public void onEvent(WebworkLoggingEvent event, long sequence, boolean endOfBatch) throws Exception {
    	persistence.save(toWebworkLog(event));
    }
    
    /**
     * 转换日志对象
     * @param event 日志事件
     * @return 日志实体
     */
    WebworkLog toWebworkLog(WebworkLoggingEvent event) {
    	WebworkLog log = new WebworkLog();
    	log.setLevel(event.getLevel());
    	log.setName(event.getLoggerName());
    	log.setContent(event.getMessage());
    	log.setCreateTime(new Timestamp(event.getTimeStamp()));
    	// MDC TraceId
    	log.setTraceId(event.getTraceId());
    	
    	// 堆栈信息
    	Throwable throwable = event.getThrowable();
    	if(throwable != null) {
    		Writer out = new StringWriter(512);
    		PrintWriter writer = new PrintWriter(out);
    		throwable.printStackTrace(writer);
    		// 将堆栈信息拼接到日志内容后面
    		String content = new StringBuilder(log.getContent())
    				.append("\n\r").append(out.toString()).toString();
    		log.setContent(content);
    	}
    	HttpLogRequest request = event.getRequest();
    	if(request != null) {
    		log.setUrl(request.getRequestURL());
    		log.setUsername(request.getUsername());
    		log.setClientInfo(request.getUserAgent());
    		log.setClientHost(request.getClientHost());
    	}
    	return log;
    }

}
