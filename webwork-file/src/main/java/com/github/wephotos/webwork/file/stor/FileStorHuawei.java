package com.github.wephotos.webwork.file.stor;

import com.github.wephotos.webwork.file.config.FileProperties;
import com.github.wephotos.webwork.file.entity.WebworkFile;
import com.github.wephotos.webwork.logging.LoggerFactory;
import lombok.Getter;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * 华为对象存储
 *
 * @author TQ
 */
@Getter
public class FileStorHuawei implements FileStor {

    // 日志
    private static final Logger log = LoggerFactory.getLogger(FileStorHuawei.class);

    public FileStorHuawei(FileProperties props) {
        this.props = props;
    }

    private FileProperties props;

    @PostConstruct
    public void initConfig() {
        log.info("初始配置");
    }

    @Override
    public StorDialect getStorDialect() {
        return StorDialect.HUAWEI_CLOUD;
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
