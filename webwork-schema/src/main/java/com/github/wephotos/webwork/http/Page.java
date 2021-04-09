package com.github.wephotos.webwork.http;

import lombok.*;

import java.util.List;

/**
 * 分页数据
 *
 * @param <E>
 * @author TQ
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Page<E> {

    /**
     * 总数
     */
    private long count;
    /**
     * 当前页数据
     */
    private List<E> data;
}
