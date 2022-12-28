package com.github.wephotos.webwork.file.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件组唯一标识
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class FileGroupKey implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 文件分组
     */
    private String fileGroup;
    /**
     * 文件分组内的唯一标识
     */
    private String fileGroupKey;
}
