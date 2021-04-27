package com.github.wephotos.logging.disruptor;

import com.github.wephotos.logging.WebworkLoggingEvent;
import com.lmax.disruptor.EventHandler;

/**
 * 日志事件处理,可进行入库等操作
 * @author TQ
 *
 */
public class LoggingEventHandler implements EventHandler<WebworkLoggingEvent> {
	
	@Override
	public void onEvent(WebworkLoggingEvent event, long sequence, boolean endOfBatch) throws Exception {
		System.err.println("消费日志：" + event.getMessage());
	}

}
