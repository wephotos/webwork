package com.github.wephotos.webwork.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.core.entity.User;
import com.github.wephotos.webwork.core.entity.UserVo;
import com.github.wephotos.webwork.core.entity.query.UserQuery;
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

}
