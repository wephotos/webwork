package com.github.wephotos.webwork.logging.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.logging.IgnoreOutputArgsLog;
import com.github.wephotos.webwork.logging.entity.WebworkLog;
import com.github.wephotos.webwork.logging.entity.po.LogQueryPO;
import com.github.wephotos.webwork.logging.service.WebworkLogService;
import com.github.wephotos.webwork.schema.entity.Page;
import com.github.wephotos.webwork.schema.entity.Pageable;
import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.utils.Results;

/**
 * 日志WEB接口
 * @author TQ
 *
 */
@RestController
@RequestMapping("/webwork-log")
public class WebworkLogController {

	@Resource
	private WebworkLogService webworkLogService;
	
	/**
	 * 日志分页查询接口
	 * @param pageable 分页参数
	 * @return {@link Result} {@link Page}
	 */
	@IgnoreOutputArgsLog
	@PostMapping("/page-query")
	public Result<Page<WebworkLog>> pageQuery(@RequestBody Pageable<LogQueryPO> pageable) {
		Page<WebworkLog> page = webworkLogService.pageQuery(pageable);
		return Results.newSuccessfullyResult(page);
	}
}
