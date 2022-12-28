package com.github.wephotos.webwork.file.stor;

import com.github.wephotos.webwork.file.entity.WebworkFile;
import com.github.wephotos.webwork.logging.LoggerFactory;
import com.github.wephotos.webwork.utils.FileUtils;
import com.github.wephotos.webwork.utils.IOUtils;
import com.github.wephotos.webwork.utils.StringUtils;
import lombok.Getter;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;

/**
 * 本地磁盘存储
 *
 * @author TQ
 */
@Getter
public class FileStorDisk implements FileStor {

    // 日志
    private static final Logger log = LoggerFactory.getLogger(FileStorDisk.class);

    /**
     * 无参构造
     */
    public FileStorDisk() {

    }

    /**
     * 使用指定目录构造
     *
     * @param directory 存储目录
     */
    public FileStorDisk(String directory) {
        if (directory != null) {
            this.directory = directory;
        }
    }

    /**
     * 存储目录
     */
    private String directory = "./webwork-file";

    @Override
    public StorDialect getStorDialect() {
        return StorDialect.DISK;
    }

    @Override
    public String getNewObjectName(String filename) {
        LocalDate now = LocalDate.now();
        return String.format("%s/%s/%s/%s-%s", now.getYear(),
                now.getMonthValue(), now.getDayOfMonth(),
                System.currentTimeMillis(), filename);
    }

    @Override
    public void storage(WebworkFile file) throws IOException {
        String objectName = file.getObjectName();
        if (StringUtils.isBlank(objectName)) {
            objectName = getNewObjectName(file.getName());
            file.setObjectName(objectName);
        }
        File dest = new File(directory, objectName);
        log.info("保存文件:{}", dest.getCanonicalPath());
        try (InputStream input = file.getInputStream()) {
            try (OutputStream output = FileUtils.openOutputStream(dest)) {
                IOUtils.copy(input, output);
            }
        }
    }

    @Override
    public void delete(String objectName) throws IOException {
        File file = new File(directory, objectName);
        log.info("删除文件:{}", file.getCanonicalPath());
        FileUtils.deleteQuietly(file);
    }

    @Override
    public InputStream get(String objectName) throws IOException {
        File file = new File(directory, objectName);
        log.info("获取文件:{}", file.getCanonicalPath());
        return FileUtils.openInputStream(file);
    }

}
