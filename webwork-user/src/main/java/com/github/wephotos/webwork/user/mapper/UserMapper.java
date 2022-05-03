package com.github.wephotos.webwork.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.schema.entity.Pageable;
import com.github.wephotos.webwork.user.api.entity.po.UserQueryPo;
import com.github.wephotos.webwork.user.api.entity.ro.UserRo;
import com.github.wephotos.webwork.user.entity.User;

/**
 * @author chengzi
 * @date 2021-01-25 16:43
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 根据用户ID查询用户详细信息
	 * @param id 用户ID
	 * @return {@link UserRo}
	 */
	UserRo findById(Integer id);
	/**
	 * 获取部门下最大人员排序
	 * @param deptId 部门ID
	 * @return 最大排序
	 */
	int maxSortByDeptId(Integer deptId);
    /**
     * 分页总数
     *
     * @param page 参数
     * @return 总记录数
     */
    long pageCount(Pageable<UserQueryPo> page);
    /**
     * 用户分页
     *
     * @param page 分页参数
     * @return Page<User>
     */
    List<UserRo> pageList(Pageable<UserQueryPo> page);
    
    /**
     * 查询部门下的用户
     * @param deptId 部门ID
     * @return 用户集合
     */
	List<User> listUserByDeptId(Integer deptId);

}
