package com.github.wephotos.webwork.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.core.entity.User;
import com.github.wephotos.webwork.core.entity.query.UserQuery;
import com.github.wephotos.webwork.core.entity.vo.UserVo;
import com.github.wephotos.webwork.http.Pageable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author chengzi
 * @date 2021-01-25 16:43
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 根据用户ID查询用户详细信息
	 * @param id 用户ID
	 * @return {@link UserVo}
	 */
	UserVo findById(String id);
	/**
	 * 获取部门下最大人员排序
	 * @param deptId 部门ID
	 * @return 最大排序
	 */
	int maxSortByDeptId(String deptId);
    /**
     * 分页总数
     *
     * @param page 参数
     * @return 总记录数
     */
    long pageCount(Pageable<UserQuery> page);
    /**
     * 用户分页
     *
     * @param page 分页参数
     * @return Page<User>
     */
    List<UserVo> pageList(Pageable<UserQuery> page);
    
    /**
     * 查询部门下的用户
     * @param deptId 部门ID
     * @return 用户集合
     */
	List<User> listUserByDeptId(String deptId);

}
