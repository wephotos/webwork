package com.github.wephotos.webwork.core.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xc
 * @date 2021-04-14 21:32
 */

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class DropSort {
    private Integer targetSort;
    private Integer sort;
    private String parent;
}
