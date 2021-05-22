package com.github.wephotos.webwork.core.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wephotos.webwork.core.entity.Config;
import com.github.wephotos.webwork.core.mapper.ConfigMapper;
import com.github.wephotos.webwork.http.EntityState;
import com.github.wephotos.webwork.http.Page;
import com.github.wephotos.webwork.http.Pageable;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * 系统配置服务
 * @author TQ
 *
 */
@Service
public class ConfigService {
	/**
	 * 配置文件缓存
	 */
	private static final Map<String, String> CACHE = new ConcurrentHashMap<>();

	@Resource
	private ConfigMapper configMapper;
	
	/**
	 * 初始化配置信息
	 */
	@PostConstruct
	private void init() {
		Wrapper<Config> wrapper = new QueryWrapper<>();
		List<Config> list = configMapper.selectList(wrapper);
		list.forEach(item -> CACHE.put(item.getName(), item.getValue()));
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
	 * 新增配置
	 * @param record 配置信息
	 * @return 新增记录主键
	 */
	public String add(Config record) {
		record.setId(WebworkUtils.uuid());
		record.setSort(configMapper.getMaxSort());
		record.setStatus(EntityState.ENABLED.getValue());
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
	
}
