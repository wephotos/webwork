package com.github.wephotos.webwork.file.entity;

import lombok.*;

/**
 * @author xc
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UploadResult extends FileGroupKey {

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
