package com.github.wephotos.webwork.http;

import com.github.wephotos.webwork.error.Errors;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 接口返回的数据结构
 *
 * @author Dell-Aaron
 */
@Getter
@Setter
@ToString
@SuperBuilder
public class RestObject {
    /**
     * 错误代码
     */
    @Default
    private int code = 0;
    /**
     * 错误消息
     */
    @Default
    private String msg = "";
    /**
     * 返回值
     */
    private Object data;

    public RestObject(Errors errors) {
        this.code = errors.getCode();
        this.msg = errors.getMessage();
    }

}
