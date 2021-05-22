package com.github.wephotos.webwork.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.core.entity.Role;
import com.github.wephotos.webwork.core.entity.query.RoleQuery;
import com.github.wephotos.webwork.http.Pageable;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author chengzi
 * @date 2021-01-25 16:43
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 获取角色最大排序号
	 * @param parentId 父ID
	 * @return 序号
	 */
	int getMaxSort(String parentId);
	
	/**
	 * 分页数据总条数
	 * @param pageable 分页条件
	 * @return 条数
	 */
	long pageCount(Pageable<RoleQuery> pageable);
	
	/**
	 * 分页查询角色
	 * @param pageable 分页条件
	 * @return 角色集合
	 */
	List<Role> pageList(Pageable<RoleQuery> pageable);


}
