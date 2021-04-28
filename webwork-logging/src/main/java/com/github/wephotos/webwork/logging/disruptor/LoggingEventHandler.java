package com.github.wephotos.webwork.logging.disruptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Timestamp;

import com.github.wephotos.webwork.logging.LoggerRequestHandler;
import com.github.wephotos.webwork.logging.LoggerUtils;
import com.github.wephotos.webwork.logging.WebworkLoggingEvent;
import com.github.wephotos.webwork.logging.entity.WebworkLog;
import com.github.wephotos.webwork.logging.service.WebworkLogService;
import com.github.wephotos.webwork.logging.service.WebworkLogServiceNop;
import com.github.wephotos.webwork.utils.WebworkUtils;
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
    	log.setId(WebworkUtils.uuid());
    	log.setName(event.getLoggerName());
    	log.setCreateTime(new Timestamp(event.getTimeStamp()));
    	log.setContent(event.getMessage());
    	log.setLevel(event.getLevel());
    	// 堆栈信息
    	Throwable throwable = event.getThrowable();
    	if(throwable != null) {
    		Writer out = new StringWriter(512);
    		PrintWriter writer = new PrintWriter(out);
    		throwable.printStackTrace(writer);
    		log.setStackTrace(out.toString());
    	}
    	// 是否web环境
    	if(LoggerUtils.isWebEnv()) {
    		LoggerRequestHandler.extractRequest2Log(log);
    	}
    	return log;
    }

}
