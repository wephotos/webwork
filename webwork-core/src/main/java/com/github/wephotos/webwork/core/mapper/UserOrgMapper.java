package com.github.wephotos.webwork.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.core.entity.UserOrg;
import com.github.wephotos.webwork.core.entity.dto.UserOrgDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author chengzi
 * @date 2021-01-25 16:43
 */
@Mapper
public interface UserOrgMapper extends BaseMapper<UserOrg> {

    UserOrgDto getByUserId(@Param("userId") String userId);
}
