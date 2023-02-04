package com.github.wephotos.webwork.file.facade.ro;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 上传文件出参
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class UploadResultRO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
     * 文件ID
     */
    private Integer id;
    /**
     * 附件名称
     */
    private String name;
    /**
     * 存储对象名
     */
    private String objectName;
}
