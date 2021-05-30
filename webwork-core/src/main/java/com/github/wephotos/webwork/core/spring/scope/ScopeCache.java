package com.github.wephotos.webwork.core.spring.scope;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 缓存作用域对象
 * @author TQ
 *
 */
public class ScopeCache {
	
	private final ConcurrentMap<String, Object> cache = new ConcurrentHashMap<String, Object>();

	public Object remove(String name) {
		return this.cache.remove(name);
	}

	public Collection<Object> clear() {
		Collection<Object> values = new ArrayList<Object>(this.cache.values());
		this.cache.clear();
		return values;
	}

	public Object get(String name) {
		return this.cache.get(name);
	}

	public Object put(String name, Object value) {
		Object result = this.cache.putIfAbsent(name, value);
		if (result != null) {
			return result;
		}
		return value;
	}
}
