package com.github.wephotos.webwork.file;

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
     * 文件目录
     */
    private String directory;
}
