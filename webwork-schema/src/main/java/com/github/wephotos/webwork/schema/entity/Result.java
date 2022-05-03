package com.github.wephotos.webwork.schema.entity;

import lombok.Builder.Default;

import java.util.Objects;

import com.github.wephotos.webwork.schema.exception.StateCode;

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
public class Result<T> {
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
    private T data;
    
    public Result(T data) {
    	this(data, StateCode.SUCCESS);
    }

    public Result(StateCode code) {
        this(null, code);
    }
    
    public Result(T data, StateCode code) {
    	Objects.requireNonNull(code, "错误码不能为空");
    	this.data = data;
    	this.code = code.getCode();
        this.msg = code.getMessage();
    }

}
