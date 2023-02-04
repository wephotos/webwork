package com.github.wephotos.webwork.photos.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.photos.entity.UserProfile;

/**
 * 用户信息持久层接口
 * @author TQ
 *
 */
@Mapper
public interface UserProfileMapper extends BaseMapper<UserProfile> {


}
