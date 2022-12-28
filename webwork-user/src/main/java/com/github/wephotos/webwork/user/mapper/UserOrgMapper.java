package com.github.wephotos.webwork.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.user.entity.UserOrg;

/**
 * 基于mybatisplus实现基本的CRUD功能
 * @author TianQi
 *
 */
@Mapper
public interface UserOrgMapper extends BaseMapper<UserOrg> {
	
}
