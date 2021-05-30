package com.github.wephotos.webwork.core.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import com.github.wephotos.webwork.core.entity.Config;
import com.github.wephotos.webwork.core.utils.DBUtils;

/**
 * 加载自定义配置文件
 * @author TQ
 *
 */
@Order(1)
public class HotConfigEnvironmentPostProcessor implements EnvironmentPostProcessor {
	
	/**
	 * 配置名称
	 */
	public static final String HOT_CONFIG_NAME = "hot-config";
	/**
	 * 数据源配置前缀
	 */
	private static final String SPRING_DATASOURCE_PREFIX = "spring.datasource";
	
	/**
	 * 环境变量
	 */
	private ConfigurableEnvironment environment;
	
	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		this.environment = environment;
		environment.getPropertySources().addLast(hotConfigPropertySource());
	}

	/**
	 * 加载配置文件
	 * @return 配置信息
	 */
	private PropertySource<?> hotConfigPropertySource(){
		String sql = "select name, value from sys_config";
		List<Config> configs = DBUtils.getInstance(getDataSourceProperties()).selectSql(sql, Config.class);
		Map<String, Object> source = new HashMap<>();
		for(Config config : configs) {
			source.put(config.getName(), config.getValue());
		}
		return new MapPropertySource(HOT_CONFIG_NAME, source);
	}
	
	/**
	 * 获取数据源配置信息
	 * @return 数据源配置信息
	 */
	private DataSourceProperties getDataSourceProperties() {
		String url = this.environment.getProperty(SPRING_DATASOURCE_PREFIX.concat(".url"));
		String username = this.environment.getProperty(SPRING_DATASOURCE_PREFIX.concat(".username"));
		String password = this.environment.getProperty(SPRING_DATASOURCE_PREFIX.concat(".password"));
		String driverClassName = this.environment.getProperty(SPRING_DATASOURCE_PREFIX.concat(".driver-class-name"));
		DataSourceProperties dataSourceProperties = new DataSourceProperties();
		dataSourceProperties.setUrl(url);
		dataSourceProperties.setUsername(username);
		dataSourceProperties.setPassword(password);
		dataSourceProperties.setDriverClassName(driverClassName);
		return dataSourceProperties;
	}
}
