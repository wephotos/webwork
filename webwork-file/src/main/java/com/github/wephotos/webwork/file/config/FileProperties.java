package com.github.wephotos.webwork.file.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chengzi
 * @date 2021-03-08 20:18
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "webwork.file")
public class FileProperties {
	
	/**
	 * 存储 类型
	 */
	private String dialect = "disk";
    /**
     * 文件目录
     */
    private String directory;
    
    /*======================以下配置对象存储使用===============*/
    /**
     * 存储区域
     */
    private String cloudZone;
    /***
     * 存储空间
     */
    private String cloudBucket;
    /**
     * KEY
     */
    private String cloudKey;
    /**
     * 密钥
     */
    private String cloudSecret;
}
