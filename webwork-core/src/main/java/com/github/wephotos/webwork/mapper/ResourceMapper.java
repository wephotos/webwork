package com.github.wephotos.webwork.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.entity.Resource;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author chengzi
 * @date 2021-01-25 16:43
 */
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {
    /**
     * get maxCode
     *
     * @param parentId parentId
     * @return code
     */
    String findMaxCode(String parentId);
}
