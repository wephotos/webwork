package com.github.wephotos.webwork.file.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.wephotos.webwork.file.service.FileService;
import com.github.wephotos.webwork.file.stor.FileStor;
import com.github.wephotos.webwork.file.stor.FileStorAli;
import com.github.wephotos.webwork.file.stor.FileStorDisk;
import com.github.wephotos.webwork.file.stor.FileStorHuawei;
import com.github.wephotos.webwork.file.stor.FileStorQing;
import com.github.wephotos.webwork.file.stor.StorDialect;

/**
 * @author chengzi
 * @date 2021-03-08 20:30
 */
@Configuration
@EnableConfigurationProperties(FileProperties.class)
public class FileAutoConfiguration {

	/**
	 * 配置文件
	 */
    private final FileProperties props;

    public FileAutoConfiguration(FileProperties props) {
        this.props = props;
    }
    
    /**
     * 初始化文件存储实例
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public FileStor fileStor() {
    	String dialect = props.getDialect();
    	if(StorDialect.ALI_CLOUD.getDialect().equals(dialect)) {
    		return new FileStorAli(props);
    	}else if(StorDialect.HUAWEI_CLOUD.getDialect().equals(dialect)) {
    		return new FileStorHuawei(props);
    	}else if(StorDialect.QING_CLOUD.getDialect().equals(dialect)) {
    		return new FileStorQing(props);
    	}else {
    		return new FileStorDisk(props.getDirectory()); 
    	}
    }
    
    /**
     * 初始化文件服务
     * @return 文件服务实例
     */
    @Bean
    @ConditionalOnMissingBean
    public FileService fileService(FileStor fileStor) {
        return new FileService(fileStor);
    }

}
