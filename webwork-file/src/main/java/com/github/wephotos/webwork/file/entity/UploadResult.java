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
public class UploadResult {

    /**
     * PK
     */
    private String id;
    /**
     * 关联外键
     */
    private String owner;
    /**
     * 附件名称
     */
    private String name;
    /**
     * 存储对象名
     */
    private String objectName;
}
