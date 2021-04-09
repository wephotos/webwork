package com.github.wephotos.webwork.core.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.wephotos.webwork.core.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author chengzi
 * @date 2021-01-25 16:43
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User getByAccount(String account);

    IPage<User> page(IPage<User> page, @Param(Constants.WRAPPER) QueryWrapper<User> wrapper);
}
