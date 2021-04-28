package com.github.wephotos.webwork.logging.mapper;

import com.github.wephotos.webwork.logging.entity.WebworkLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xc
 * @date 2021-04-28 21:49
 */
@Mapper
public interface WebworkLogMapper {
    /**
     * 新增
     *
     * @param log log
     * @return int
     */
    int insert(WebworkLog log);
}
