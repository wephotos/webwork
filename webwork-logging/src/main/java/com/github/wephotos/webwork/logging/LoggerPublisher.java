package com.github.wephotos.webwork.logging;

import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.MessageFormatter;

import com.github.wephotos.webwork.logging.disruptor.LoggingEventProducerWithTranslator;

/**
 * 日志发布
 *
 * @author TQ
 */
public class LoggerPublisher {

    String name;
    WebworkLogger logger;

    LoggerPublisher(WebworkLogger logger) {
        this.logger = logger;
        this.name = logger.getName();
    }

    public void recordEvent_0Args(Level level, Marker marker, String msg, Throwable t) {
        recordEvent(level, marker, msg, null, t);
    }

    public void recordEvent_1Args(Level level, Marker marker, String msg, Object arg1) {
        recordEvent(level, marker, msg, new Object[]{arg1}, null);
    }

    public void recordEvent2Args(Level level, Marker marker, String msg, Object arg1, Object arg2) {
        if (arg2 instanceof Throwable) {
            recordEvent(level, marker, msg, new Object[]{arg1}, (Throwable) arg2);
        } else {
            recordEvent(level, marker, msg, new Object[]{arg1, arg2}, null);
        }
    }

    public void recordEventArgArray(Level level, Marker marker, String msg, Object[] args) {
        Throwable throwableCandidate = MessageFormatter.getThrowableCandidate(args);
        if (throwableCandidate != null) {
            Object[] trimmedCopy = MessageFormatter.trimmedCopy(args);
            recordEvent(level, marker, msg, trimmedCopy, throwableCandidate);
        } else {
            recordEvent(level, marker, msg, args, null);
        }
    }


    // WARNING: this method assumes that any throwable is properly extracted
    private void recordEvent(Level level, Marker marker, String msg, Object[] args, Throwable throwable) {
    	if(!isEnabled(level, marker)) {
    		return;
    	}
        WebworkLoggingEvent loggingEvent = new WebworkLoggingEvent();
        loggingEvent.setTimeStamp(System.currentTimeMillis());
        loggingEvent.setLevel(level);
        loggingEvent.setLogger(logger);
        loggingEvent.setLoggerName(logger.getName());
        loggingEvent.setMarker(marker);
        // 格式化消息
        String message = MessageFormatter.arrayFormat(msg, args).getMessage();
        loggingEvent.setMessage(message);
        loggingEvent.setThreadName(Thread.currentThread().getName());

        loggingEvent.setArgumentArray(args);
        loggingEvent.setThrowable(throwable);
        // 获取当前请求信息
    	if(WEBUtils.isWebEnv()) {
    		loggingEvent.setRequest(LoggerRequestHandler.getHttpLogRequest());
    	}
    	// MDC traceId
    	loggingEvent.setTraceId(MDCUtils.get());
        //发布
        LoggingEventProducerWithTranslator.getProducer().onData(loggingEvent);
    }
    
    /**
     * 判断日志级别是否启用
     * @param level {@link Level}
     * @param marker {@link Marker}
     * @return true/false
     */
    private boolean isEnabled(Level level, Marker marker) {
    	switch (level) {
		case TRACE:
			return logger.isTraceEnabled(marker);
		case DEBUG:
			return logger.isDebugEnabled(marker);
		case INFO:
			return logger.isInfoEnabled(marker);
		case WARN:
			return logger.isWarnEnabled(marker);
		case ERROR:
			return logger.isErrorEnabled(marker);
		default:
			return false;
		}
    }
}
