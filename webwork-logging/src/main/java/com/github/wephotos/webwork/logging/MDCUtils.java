package com.github.wephotos.webwork.logging;

import java.util.UUID;

import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;

/**
 * MDC 工具类
 * @author TianQi
 *
 */
public final class MDCUtils {
	
	public static final String TRACE_ID_KEY = "traceId";

	public static void put() {
		put(UUID.randomUUID().toString().replace("-", ""));
	}
	
	public static void put(String val) {
		MDC.put(TRACE_ID_KEY, val);
	}
	
	public static MDCCloseable putCloseable() {
		return putCloseable(UUID.randomUUID().toString().replace("-", ""));
	}
	
	public static MDCCloseable putCloseable(String val) {
		return MDC.putCloseable(TRACE_ID_KEY, val);
	}
	
	public static String get() {
		return MDC.get(TRACE_ID_KEY);
	}
	
	public static void remove() {
		MDC.remove(TRACE_ID_KEY);
	}
}
