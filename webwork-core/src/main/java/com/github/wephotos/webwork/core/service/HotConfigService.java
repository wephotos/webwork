package com.github.wephotos.webwork.core.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.wephotos.webwork.core.spring.scope.annotation.HotScope;
import com.github.wephotos.webwork.logging.LoggerFactory;

@Service
@HotScope
public class HotConfigService {
	
	private static final Logger log = LoggerFactory.getLogger(HotConfigService.class);

	@Value("${upload.limit.size}")
	private String uploadLimitSize;
	
	@PostConstruct
	private void init() {
		log.info("uploadLimitSize:{}", this.uploadLimitSize);
	}
	
	public String getUploadLimitSize() {
		return uploadLimitSize;
	}
}
