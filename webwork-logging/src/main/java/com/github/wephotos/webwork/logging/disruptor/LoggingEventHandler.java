package com.github.wephotos.webwork.logging.disruptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Timestamp;

import com.github.wephotos.webwork.logging.WebworkLoggingEvent;
import com.github.wephotos.webwork.logging.WebworkLoggingEvent.LoggerRequest;
import com.github.wephotos.webwork.logging.entity.WebworkLog;
import com.github.wephotos.webwork.logging.service.WebworkLogService;
import com.github.wephotos.webwork.logging.service.WebworkLogServiceNop;
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
	private static WebworkLogService logService = new WebworkLogServiceNop();
	
	/**
	 * 设置日志服务
	 * @param logService 日志服务
	 */
	public static void setLogService(WebworkLogService logService) {
		if(logService != null) {
			LoggingEventHandler.logService = logService;
		}
	}

	//TODO: 后期可优化为批量入库
    @Override
    public void onEvent(WebworkLoggingEvent event, long sequence, boolean endOfBatch) throws Exception {
        logService.save(toWebworkLog(event));
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
    	// 堆栈信息
    	Throwable throwable = event.getThrowable();
    	if(throwable != null) {
    		Writer out = new StringWriter(512);
    		PrintWriter writer = new PrintWriter(out);
    		throwable.printStackTrace(writer);
    		log.setStackTrace(out.toString());
    	}
    	LoggerRequest request = event.getRequest();
    	if(request != null) {
    		log.setIp(request.getIp());
    		log.setUrl(request.getRequestURL());
    		log.setUsername(request.getOperator());
    		log.setClient(request.getUserAgent());
    	}
    	return log;
    }

}
