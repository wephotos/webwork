package com.github.wephotos.webwork.logging.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.logging.service.WebworkLogService;

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
	
	
	
}
