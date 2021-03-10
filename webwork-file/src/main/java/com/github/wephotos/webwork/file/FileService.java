package com.github.wephotos.webwork.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author chengzi
 * @date 2021-03-08 19:59
 */
@Slf4j
public class FileService {
    public String directory = "./temp_file";

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void storage(WebWorkFile workFile) throws IOException {
        String objectName = workFile.getObjectName();
        File dest = new File(directory, objectName);
        log.info("destFile:{}", dest.getCanonicalPath());
        try (InputStream input = workFile.getInputStream()) {
            try (OutputStream output = FileUtils.openOutputStream(dest)) {
                IOUtils.copy(input, output);
            }
        }
    }

}
