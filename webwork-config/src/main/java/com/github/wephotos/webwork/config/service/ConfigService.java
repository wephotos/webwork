package com.github.wephotos.webwork.config.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wephotos.webwork.config.entity.Config;
import com.github.wephotos.webwork.config.mapper.ConfigMapper;
import com.github.wephotos.webwork.config.spring.HotConfigEnvironmentPostProcessor;
import com.github.wephotos.webwork.config.spring.scope.HotScope;
import com.github.wephotos.webwork.logging.LoggerFactory;
import com.github.wephotos.webwork.schema.entity.EntityState;
import com.github.wephotos.webwork.schema.entity.Page;
import com.github.wephotos.webwork.schema.entity.Pageable;

/**
 * 系统配置服务
 * @author TQ
 *
 */
@Service
public class ConfigService implements EnvironmentAware {
	
	//日志
	private static final Logger log = LoggerFactory.getLogger(ConfigService.class);
	/**
	 * 配置文件缓存
	 */
	private static final Map<String, String> CACHE = new ConcurrentHashMap<>();
	/**
	 * 环境变量
	 */
	@Resource
	private Environment environment;
	
	@Resource
	private ConfigMapper configMapper;
	
	@Resource
	private HotScope hotScope;
	
	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
		log.info("{}", this.environment);
	}
	
	/**
	 * 初始化配置信息
	 */
	@PostConstruct
	private void init() {
		listAll().forEach(item -> CACHE.put(item.getName(), item.getValue()));
	}
	
	/**
	 * 获取缓存配置信息
	 * @return 缓存配置集合
	 */
	public Map<String, String> getCache() {
		return Collections.unmodifiableMap(CACHE);
	}
	
	/**
	 * 获取配置信息
	 * @param name 配置名
	 * @return 配置数据
	 */
	public String getValue(String name) {
		return CACHE.get(name);
	}
	
	/**
	 * 获取所有配置信息
	 * @return 配置信息集合
	 */
	public List<Config> listAll(){
		return configMapper.selectList(new QueryWrapper<>());
	}
	
	/**
	 * 新增配置
	 * @param record 配置信息
	 * @return 新增记录主键
	 */
	public int add(Config record) {
		record.setSort(configMapper.getMaxSort());
		record.setStatus(EntityState.NORMAL.getCode());
		configMapper.insert(record);
		CACHE.put(record.getName(), record.getValue());
		return record.getId();
	}
	
	/**
	 * 更新配置
	 * @param record 配置信息
	 * @return 是否更新成功
	 */
	public boolean update(Config record) {
		CACHE.put(record.getName(), record.getValue());
		return configMapper.updateById(record) == 1;
	}
	
	/**
     * 分页查询
     * @param pageable 分页参数
     * @return 分页数据 {@link Page}
     */
    public Page<Config> page(Pageable<Config> pageable) {
        Page<Config> page = new Page<>();
        page.setData(configMapper.pageList(pageable));
        page.setCount(configMapper.pageCount(pageable));
        return page;
    }
    
    /**
     * 刷新配置
     */
    public void refreshAll() {
    	this.updateEnvironment();
    	this.hotScope.destroy();
    }

	/**
	 * 更新配置
	 */
	private void updateEnvironment() {
		log.info("updateEnvironment:{}", this.environment);
		if(!(this.environment instanceof ConfigurableEnvironment)) {
			return;
		}
		MutablePropertySources propertySources = ((ConfigurableEnvironment)this.environment).getPropertySources();
		PropertySource<?> propertySource = propertySources.get(HotConfigEnvironmentPostProcessor.HOT_CONFIG_NAME);
		Map<String, Object> source = ((MapPropertySource)propertySource).getSource();
		CACHE.forEach((key, value) -> {
			source.put(key, value);
		});
	}

}
