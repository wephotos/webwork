package com.github.wephotos.logging;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;

/**
 * 日志实现
 * @author TQ
 *
 */
public class WebworkLogger implements Logger {
	
	//日志发布
	LoggerPublisher publisher;
	/**
	 * 代理指定日志实现
	 * @param logger 被代理对象
	 */
	public WebworkLogger(Logger logger) {
		Objects.requireNonNull(logger);
		this.logger = logger;
		this.publisher = new LoggerPublisher(this);
	}
	
	/**
	 * 日志对象
	 */
	private Logger logger;

	@Override
	public String getName() {
		return logger.getName();
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	@Override
	public void trace(String msg) {
		logger.trace(msg);
		publisher.recordEvent_0Args(Level.TRACE, null, msg, null);
	}

	@Override
	public void trace(String format, Object arg) {
		logger.trace(format, arg);
		publisher.recordEvent_1Args(Level.TRACE, null, format, arg);
	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {
		logger.trace(format, arg1, arg2);
		publisher.recordEvent2Args(Level.TRACE, null, format, arg1, arg2);
	}

	@Override
	public void trace(String format, Object... arguments) {
		logger.trace(format, arguments);
		publisher.recordEventArgArray(Level.TRACE, null, format, arguments);
	}

	@Override
	public void trace(String msg, Throwable t) {
		logger.trace(msg, t);
		publisher.recordEvent_0Args(Level.TRACE, null, msg, t);
	}

	@Override
	public boolean isTraceEnabled(Marker marker) {
		return logger.isTraceEnabled(marker);
	}

	@Override
	public void trace(Marker marker, String msg) {
		logger.trace(marker, msg);
		publisher.recordEvent_0Args(Level.TRACE, marker, msg, null);
	}

	@Override
	public void trace(Marker marker, String format, Object arg) {
		logger.trace(marker, format, arg);
		publisher.recordEvent_1Args(Level.TRACE, marker, format, arg);
	}

	@Override
	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		logger.trace(marker, format, arg1, arg2);
		publisher.recordEvent2Args(Level.TRACE, marker, format, arg1, arg2);
	}

	@Override
	public void trace(Marker marker, String format, Object... argArray) {
		logger.trace(marker, format, argArray);
		publisher.recordEventArgArray(Level.TRACE, marker, format, argArray);
	}

	@Override
	public void trace(Marker marker, String msg, Throwable t) {
		logger.trace(marker, msg, t);
		publisher.recordEvent_0Args(Level.TRACE, marker, msg, t);
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public void debug(String msg) {
		logger.debug(msg);
		publisher.recordEvent_0Args(Level.DEBUG, null, msg, null);
	}

	@Override
	public void debug(String format, Object arg) {
		logger.debug(format, arg);
		publisher.recordEvent_1Args(Level.DEBUG, null, format, arg);
	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
		logger.debug(format, arg1, arg2);
		publisher.recordEvent2Args(Level.DEBUG, null, format, arg1, arg2);
	}

	@Override
	public void debug(String format, Object... arguments) {
		logger.debug(format, arguments);
		publisher.recordEventArgArray(Level.DEBUG, null, format, arguments);
	}

	@Override
	public void debug(String msg, Throwable t) {
		logger.debug(msg, t);
		publisher.recordEvent_0Args(Level.DEBUG, null, msg, t);
	}

	@Override
	public boolean isDebugEnabled(Marker marker) {
		return logger.isDebugEnabled(marker);
	}

	@Override
	public void debug(Marker marker, String msg) {
		logger.debug(marker, msg);
		publisher.recordEvent_0Args(Level.DEBUG, marker, msg, null);
	}

	@Override
	public void debug(Marker marker, String format, Object arg) {
		logger.debug(marker, format, arg);
		publisher.recordEvent_1Args(Level.DEBUG, marker, format, arg);
	}

	@Override
	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		logger.debug(marker, format, arg1, arg2);
		publisher.recordEvent2Args(Level.DEBUG, marker, format, arg1, arg2);
	}

	@Override
	public void debug(Marker marker, String format, Object... arguments) {
		logger.debug(marker, format, arguments);
		publisher.recordEventArgArray(Level.DEBUG, marker, format, arguments);
	}

	@Override
	public void debug(Marker marker, String msg, Throwable t) {
		logger.debug(marker, msg, t);
		publisher.recordEvent_0Args(Level.DEBUG, marker, msg, t);
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public void info(String msg) {
		logger.info(msg);
		publisher.recordEvent_0Args(Level.INFO, null, msg, null);
	}

	@Override
	public void info(String format, Object arg) {
		logger.info(format, arg);
		publisher.recordEvent_1Args(Level.INFO, null, format, arg);
	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
		logger.info(format, arg1, arg2);
		publisher.recordEvent2Args(Level.INFO, null, format, arg1, arg2);
	}

	@Override
	public void info(String format, Object... arguments) {
		logger.info(format, arguments);
		publisher.recordEventArgArray(Level.INFO, null, format,  arguments);
	}

	@Override
	public void info(String msg, Throwable t) {
		logger.info(msg, t);
		publisher.recordEvent_0Args(Level.INFO, null, msg, t);
	}

	@Override
	public boolean isInfoEnabled(Marker marker) {
		return logger.isInfoEnabled(marker);
	}

	@Override
	public void info(Marker marker, String msg) {
		logger.info(marker, msg);
		publisher.recordEvent_0Args(Level.INFO, marker, msg, null);
	}

	@Override
	public void info(Marker marker, String format, Object arg) {
		logger.info(marker, format, arg);
		publisher.recordEvent_1Args(Level.INFO, marker, format, arg);
	}

	@Override
	public void info(Marker marker, String format, Object arg1, Object arg2) {
		logger.info(marker, format, arg1, arg2);
		publisher.recordEvent2Args(Level.INFO, marker, format, arg1, arg2);
	}

	@Override
	public void info(Marker marker, String format, Object... arguments) {
		logger.info(marker, format, arguments);
		publisher.recordEventArgArray(Level.INFO, marker, format, arguments);
	}

	@Override
	public void info(Marker marker, String msg, Throwable t) {
		logger.info(marker, msg, t);
		publisher.recordEvent_0Args(Level.INFO, marker, msg, t);
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	@Override
	public void warn(String msg) {
		logger.warn(msg);
		publisher.recordEvent_0Args(Level.WARN, null, msg, null);
	}

	@Override
	public void warn(String format, Object arg) {
		logger.warn(format, arg);
		publisher.recordEvent_1Args(Level.WARN, null, format, arg);
	}
	
	@Override
	public void warn(String format, Object arg1, Object arg2) {
		logger.warn(format, arg1, arg2);
		publisher.recordEvent2Args(Level.WARN, null, format, arg1, arg2);
	}

	@Override
	public void warn(String format, Object... arguments) {
		logger.warn(format, arguments);
		publisher.recordEventArgArray(Level.WARN, null, format, arguments);
	}

	@Override
	public void warn(String msg, Throwable t) {
		logger.warn(msg, t);
		publisher.recordEvent_0Args(Level.WARN, null, msg, t);
	}

	@Override
	public boolean isWarnEnabled(Marker marker) {
		return logger.isWarnEnabled(marker);
	}

	@Override
	public void warn(Marker marker, String msg) {
		logger.warn(marker, msg);
		publisher.recordEvent_0Args(Level.WARN, marker, msg, null);
	}

	@Override
	public void warn(Marker marker, String format, Object arg) {
		logger.warn(marker, format, arg);
		publisher.recordEvent_1Args(Level.WARN, marker, format, arg);
	}

	@Override
	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		logger.warn(marker, format, arg1, arg2);
		publisher.recordEvent2Args(Level.WARN, marker, format, arg1, arg2);
	}

	@Override
	public void warn(Marker marker, String format, Object... arguments) {
		logger.warn(marker, format, arguments);
		publisher.recordEventArgArray(Level.WARN, marker, format, arguments);
	}

	@Override
	public void warn(Marker marker, String msg, Throwable t) {
		logger.warn(marker, msg, t);
		publisher.recordEvent_0Args(Level.WARN, marker, msg, t);
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	@Override
	public void error(String msg) {
		logger.error(msg);
		publisher.recordEvent_0Args(Level.ERROR, null, msg, null);
	}

	@Override
	public void error(String format, Object arg) {
		logger.error(format, arg);
		publisher.recordEvent_1Args(Level.ERROR, null, format, arg);
	}

	@Override
	public void error(String format, Object arg1, Object arg2) {
		logger.error(format, arg1, arg2);
		publisher.recordEvent2Args(Level.ERROR, null, format, arg1, arg2);
	}

	@Override
	public void error(String format, Object... arguments) {
		logger.error(format, arguments);
		publisher.recordEventArgArray(Level.ERROR, null, format, arguments);
	}

	@Override
	public void error(String msg, Throwable t) {
		logger.error(msg, t);
		publisher.recordEvent_0Args(Level.ERROR, null, msg, t);
	}

	@Override
	public boolean isErrorEnabled(Marker marker) {
		return logger.isErrorEnabled(marker);
	}

	@Override
	public void error(Marker marker, String msg) {
		logger.error(marker, msg);
		publisher.recordEvent_0Args(Level.ERROR, marker, msg, null);
	}

	@Override
	public void error(Marker marker, String format, Object arg) {
		logger.error(marker, format, arg);
		publisher.recordEvent_1Args(Level.ERROR, marker, format, arg);
	}

	@Override
	public void error(Marker marker, String format, Object arg1, Object arg2) {
		logger.error(marker, format, arg1, arg2);
		publisher.recordEvent2Args(Level.ERROR, marker, format, arg1, arg2);
	}

	@Override
	public void error(Marker marker, String format, Object... arguments) {
		logger.error(marker, format, arguments);
		publisher.recordEventArgArray(Level.ERROR, marker, format, arguments);
	}

	@Override
	public void error(Marker marker, String msg, Throwable t) {
		logger.error(marker, msg, t);
		publisher.recordEvent_0Args(Level.ERROR, marker, msg, t);
	}

}
