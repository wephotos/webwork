package com.github.wephotos.webwork.docs.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.docs.entity.DocumentUser;

/**
 * 文档用户数据层接口
 * @author TianQi
 *
 */
@Mapper
public interface DocumentUserMapper extends BaseMapper<DocumentUser> {

}
