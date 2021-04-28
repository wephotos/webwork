package com.github.wephotos.webwork.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.core.entity.Organization;
import com.github.wephotos.webwork.core.entity.dto.DropSort;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chengzi
 * @date 2021-01-25 16:43
 */
@Mapper
public interface OrganizationMapper extends BaseMapper<Organization> {
    /**
     * get maxCode
     *
     * @param parentId parentId
     * @return code
     */
    String findMaxCode(String parentId);

    int dropSort(DropSort dropSort);
}
