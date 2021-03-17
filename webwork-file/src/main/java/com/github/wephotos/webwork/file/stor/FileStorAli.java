package com.github.wephotos.webwork.file.stor;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;

import com.github.wephotos.logging.LoggerFactory;
import com.github.wephotos.webwork.file.config.FileProperties;
import com.github.wephotos.webwork.file.entity.WebworkFile;

import lombok.Getter;

/**
 * 阿里云对象存储
 * @author TQ
 *
 */
@Getter
public class FileStorAli implements FileStor {

	// 日志
	private static final Logger log = LoggerFactory.getLogger(FileStorAli.class);

	public FileStorAli(FileProperties props) {
		this.props = props;
	}
	
	private FileProperties props;
	
	@PostConstruct
	public void initConfig() {
		log.info("初始配置");
	}
	
	@Override
	public StorDialect getStorDialect() {
		return StorDialect.ALI_CLOUD;
	}

	@Override
	public String getNewObjectName(String filename) {
		return null;
	}

	@Override
	public void storage(WebworkFile file) throws IOException {

	}

	@Override
	public void delete(String objectName) throws IOException {

	}

	@Override
	public InputStream get(String objectName) throws IOException {
		return null;
	}

}
