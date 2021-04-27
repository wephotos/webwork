package com.github.wephotos.logging.disruptor;

import com.github.wephotos.logging.WebworkLoggingEvent;
import com.lmax.disruptor.EventFactory;

/**
 * 事件工厂
 * @author TQ
 *
 */
public class LoggingEventFactory implements EventFactory<WebworkLoggingEvent> {

	@Override
	public WebworkLoggingEvent newInstance() {
		return new WebworkLoggingEvent();
	}

}
