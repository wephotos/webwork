package com.github.wephotos.webwork.photos.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.photos.entity.Album;

/**
 * 相册持久层接口
 * @author TianQi
 *
 */
@Mapper
public interface AlbumMapper extends BaseMapper<Album> {

}
