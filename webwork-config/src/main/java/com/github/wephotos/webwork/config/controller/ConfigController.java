package com.github.wephotos.webwork.config.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.config.entity.Config;
import com.github.wephotos.webwork.config.service.ConfigService;
import com.github.wephotos.webwork.schema.entity.Page;
import com.github.wephotos.webwork.schema.entity.Pageable;
import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.entity.Results;

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
	 * @return {@link Result} 包含新增配置主键
	 */
	@PostMapping("/add")
	public Result<Integer> add(@RequestBody Config config) {
		int id = configService.add(config);
		return Results.newResult(id);
	}
	
	/**
	 * 更新配置信息
	 * @param config 配置信息
	 * @return {@link Result} 更新成功返回数据为true
	 */
	@PostMapping("/update")
	public Result<Boolean> update(@RequestBody Config config) {
		boolean ret = configService.update(config);
		return Results.newResult(ret);
	}
	
	/**
	 * 分页查询配置信息
	 * @param pageable 分页必要条件
	 * @return 分页数据 {@link Page} {@link Config}
	 */
	@PostMapping("/page")
	public Result<Page<Config>> page(@RequestBody Pageable<Config> pageable) {
		Page<Config> page = configService.page(pageable);
		return Results.newResult(page);
	}
	
	/**
	 * 刷新配置
	 * @return
	 */
	@GetMapping("/refresh")
	public Result<Void> refresh() {
		this.configService.refreshAll();
		return Results.newSuccessfullyResult();
	}

}
