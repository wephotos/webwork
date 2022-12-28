package com.github.wephotos.webwork.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.schema.entity.Pageable;
import com.github.wephotos.webwork.user.entity.User;
import com.github.wephotos.webwork.user.entity.po.UserQueryPO;
import com.github.wephotos.webwork.user.entity.vo.UserVO;

/**
 * @author chengzi
 * @date 2021-01-25 16:43
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 根据用户ID查询用户详细信息
	 * @param id 用户ID
	 * @return {@link UserVO}
	 */
	UserVO findById(Integer id);
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
    long pageCount(Pageable<UserQueryPO> page);
    /**
     * 用户分页
     *
     * @param page 分页参数
     * @return Page<User>
     */
    List<UserVO> pageList(Pageable<UserQueryPO> page);
    
    /**
     * 查询部门下的用户
     * @param deptId 部门ID
     * @return 用户集合
     */
	List<UserVO> listUserByDeptId(Integer deptId);

}
