package com.github.wephotos.webwork.file;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chengzi
 * @date 2021-03-08 20:30
 */
@Configuration
@EnableConfigurationProperties(FileProperties.class)
public class FileAutoConfiguration {

    private final FileProperties fileProperties;

    public FileAutoConfiguration(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public FileService fileService() {
        FileService fileService = new FileService();
        fileService.setDirectory(fileProperties.getDirectory());
        return fileService;
    }
}
