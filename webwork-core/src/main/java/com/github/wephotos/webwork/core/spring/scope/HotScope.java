package com.github.wephotos.webwork.core.spring.scope;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.github.wephotos.webwork.logging.LoggerFactory;

/**
 * 自定义作用域,用于配置文件热加载
 * 
 * @author TQ
 *
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class HotScope implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent>,
		BeanFactoryPostProcessor, BeanDefinitionRegistryPostProcessor, Scope, DisposableBean {

	// 日志
	private static final Logger log = LoggerFactory.getLogger(HotScope.class);

	private String name = "hot";

	private ApplicationContext context;

	private BeanDefinitionRegistry registry;

	private boolean eager = true;

	private BeanLifecycleWrapperCache cache = new BeanLifecycleWrapperCache(new ScopeCache());

	private ConcurrentMap<String, ReadWriteLock> locks = new ConcurrentHashMap<>();

	public String getName() {
		return name;
	}

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		BeanLifecycleWrapper value = this.cache.put(name, new BeanLifecycleWrapper(name, objectFactory));
		this.locks.putIfAbsent(name, new ReentrantReadWriteLock());
		return value.getBean();
	}

	@Override
	public Object remove(String name) {
		BeanLifecycleWrapper value = this.cache.remove(name);
		if (value == null) {
			return null;
		}
		// Someone might have added another object with the same key, but we
		// keep the method contract by removing the
		// value we found anyway
		return value.getBean();
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		BeanLifecycleWrapper value = this.cache.get(name);
		if (value == null) {
			return;
		}
		value.setDestroyCallback(callback);
	}

	@Override
	public Object resolveContextualObject(String key) {
		return null;
	}

	@Override
	public String getConversationId() {
		return this.name;
	}

	@Override
	public void destroy() {
		Collection<BeanLifecycleWrapper> wrappers = this.cache.clear();
		for (BeanLifecycleWrapper wrapper : wrappers) {
			try {
				Lock lock = this.locks.get(wrapper.getName()).writeLock();
				lock.lock();
				try {
					wrapper.destroy();
				} finally {
					lock.unlock();
				}
			} catch (RuntimeException e) {
				log.error("销毁Bean失败:{}", e.getMessage());
			}
		}
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		beanFactory.registerScope(this.name, this);
	}

	/**
	 * Flag to determine whether all beans in refresh scope should be instantiated
	 * eagerly on startup. Default true.
	 * 
	 * @param eager
	 *            The flag to set.
	 */
	public void setEager(boolean eager) {
		this.eager = eager;
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		this.registry = registry;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		start(event);
	}

	public void start(ContextRefreshedEvent event) {
		if (event.getApplicationContext() == this.context && this.eager && this.registry != null) {
			eagerlyInitialize();
		}
	}

	private void eagerlyInitialize() {
		for (String name : this.context.getBeanDefinitionNames()) {
			BeanDefinition definition = this.registry.getBeanDefinition(name);
			if (this.getName().equals(definition.getScope()) && !definition.isLazyInit()) {
				Object bean = this.context.getBean(name);
				if (bean != null) {
					bean.getClass();
				}
			}
		}
	}

	private static class BeanLifecycleWrapperCache {

		private final ScopeCache cache;

		BeanLifecycleWrapperCache(ScopeCache cache) {
			this.cache = cache;
		}

		public BeanLifecycleWrapper remove(String name) {
			return (BeanLifecycleWrapper) this.cache.remove(name);
		}

		public Collection<BeanLifecycleWrapper> clear() {
			Collection<Object> values = this.cache.clear();
			Collection<BeanLifecycleWrapper> wrappers = new LinkedHashSet<>();
			for (Object object : values) {
				wrappers.add((BeanLifecycleWrapper) object);
			}
			return wrappers;
		}

		public BeanLifecycleWrapper get(String name) {
			return (BeanLifecycleWrapper) this.cache.get(name);
		}

		public BeanLifecycleWrapper put(String name, BeanLifecycleWrapper value) {
			return (BeanLifecycleWrapper) this.cache.put(name, value);
		}

	}

	private static class BeanLifecycleWrapper {

		private final String name;

		private final ObjectFactory<?> objectFactory;

		private volatile Object bean;

		private Runnable callback;

		BeanLifecycleWrapper(String name, ObjectFactory<?> objectFactory) {
			this.name = name;
			this.objectFactory = objectFactory;
		}

		public String getName() {
			return this.name;
		}

		public void setDestroyCallback(Runnable callback) {
			this.callback = callback;
		}

		public Object getBean() {
			if (this.bean == null) {
				synchronized (this.name) {
					if (this.bean == null) {
						this.bean = this.objectFactory.getObject();
					}
				}
			}
			return this.bean;
		}

		public void destroy() {
			if (this.callback == null) {
				return;
			}
			synchronized (this.name) {
				Runnable callback = this.callback;
				if (callback != null) {
					callback.run();
				}
				this.callback = null;
				this.bean = null;
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			BeanLifecycleWrapper other = (BeanLifecycleWrapper) obj;
			if (this.name == null) {
				if (other.name != null) {
					return false;
				}
			} else if (!this.name.equals(other.name)) {
				return false;
			}
			return true;
		}

	}

}
