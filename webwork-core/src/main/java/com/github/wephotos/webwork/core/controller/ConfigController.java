package com.github.wephotos.webwork.core.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.core.entity.Config;
import com.github.wephotos.webwork.core.service.ConfigService;
import com.github.wephotos.webwork.core.service.HotConfigService;
import com.github.wephotos.webwork.http.Page;
import com.github.wephotos.webwork.http.Pageable;
import com.github.wephotos.webwork.http.RestObject;

/**
 * 系统配置服务接口
 * @author TQ
 *
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

	@Resource
	private ConfigService configService;
	
	/**
	 * 新增配置信息
	 * @param config 配置信息
	 * @return {@link RestObject} 包含新增配置主键
	 */
	@PostMapping("/add")
	public RestObject add(@RequestBody Config config) {
		String id = configService.add(config);
		return RestObject.builder().data(id).build();
	}
	
	/**
	 * 更新配置信息
	 * @param config 配置信息
	 * @return {@link RestObject} 更新成功返回数据为true
	 */
	@PostMapping("/update")
	public RestObject update(@RequestBody Config config) {
		boolean ret = configService.update(config);
		return RestObject.builder().data(ret).build();
	}
	
	/**
	 * 分页查询配置信息
	 * @param pageable 分页必要条件
	 * @return 分页数据 {@link Page} {@link Config}
	 */
	@PostMapping("/page")
	public RestObject page(@RequestBody Pageable<Config> pageable) {
		Page<Config> page = configService.page(pageable);
		return RestObject.builder().data(page).build();
	}
	
	/**
	 * 刷新配置
	 * @return
	 */
	@GetMapping("/refresh")
	public RestObject refresh() {
		this.configService.refreshAll();
		return RestObject.builder().build();
	}
	
	// 测试热加载配置
	@Resource
	private HotConfigService hotConfigService;
	
	@GetMapping("/hot-config")
	public RestObject hotConfig() {
		String size = hotConfigService.getUploadLimitSize();
		return RestObject.builder().data(size).build();
	}

}
