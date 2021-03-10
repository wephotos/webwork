package com.github.wephotos.webwork.file;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.InputStream;

/**
 * @author chengzi
 * @date 2021-03-08 20:10
 */
@Getter
@Setter
@ToString
public class WebWorkFile {
    private String objectName;
    private InputStream inputStream;
}
