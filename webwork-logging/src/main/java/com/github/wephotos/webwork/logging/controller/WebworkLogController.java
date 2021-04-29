package com.github.wephotos.webwork.logging.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.http.Page;
import com.github.wephotos.webwork.http.Pageable;
import com.github.wephotos.webwork.http.RestObject;
import com.github.wephotos.webwork.logging.entity.WebworkLog;
import com.github.wephotos.webwork.logging.service.WebworkLogServiceDb;

/**
 * 日志WEB接口
 * @author TQ
 *
 */
@RestController
@RequestMapping("/webwork-log")
public class WebworkLogController {

	@Resource
	private WebworkLogServiceDb webworkLogServiceDb;
	
	/**
	 * 日志分页查询接口
	 * @param pageable 分页参数
	 * @return {@link RestObject} {@link Page}
	 */
	@PostMapping("/page-query")
	public RestObject pageQuery(@RequestBody Pageable<WebworkLog> pageable) {
		Page<WebworkLog> page = webworkLogServiceDb.pageQuery(pageable);
		return RestObject.builder().data(page).build();
	}
}
