package com.github.wephotos.webwork.core.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wephotos.webwork.core.entity.Organization;
import com.github.wephotos.webwork.core.entity.User;
import com.github.wephotos.webwork.core.entity.UserOrg;
import com.github.wephotos.webwork.core.entity.dto.UserDto;
import com.github.wephotos.webwork.core.entity.query.UserQuery;
import com.github.wephotos.webwork.core.entity.vo.UserVo;
import com.github.wephotos.webwork.core.mapper.UserMapper;
import com.github.wephotos.webwork.core.mapper.UserOrgMapper;
import com.github.wephotos.webwork.http.EntityState;
import com.github.wephotos.webwork.http.Page;
import com.github.wephotos.webwork.http.Pageable;
import com.github.wephotos.webwork.utils.StringUtils;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService extends ServiceImpl<UserMapper, User> {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserOrgMapper userOrgMapper;
    @Resource
    private OrganizationService organizationService;
    
    /**
     * 根据用户ID，查询用户详细信息，包含部门单位角色等
     * @param id 用户ID
     * @return {@link UserVo}
     */
    public UserVo findById(String id) {
    	return userMapper.findById(id);
    }

    /**
     * 根据ID删除用户
     * @param id
     * @return
     */
	public boolean deleteById(String id) {
		User user = new User();
		user.setId(id);
		user.setStatus(EntityState.DELETED.getValue());
		return userMapper.updateById(user) == 1;
	}
    /**
     * 查询唯一用户记录
     * @param query 查询条件
     * @param password 是否包含密码
     * @return {@link User}
     */
	public User selectOne(UserQuery query) {
    	LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getId, User::getName, User::getAccount, User::getPassword, 
        		User::getEmail, User::getPhone, User::getStatus);
        if(StringUtils.isNotBlank(query.getId())) {
        	wrapper.eq(User::getId, query.getId());
        }else if(StringUtils.isNotBlank(query.getAccount())) {
        	wrapper.eq(User::getAccount, query.getAccount());
        }else if(StringUtils.isNotBlank(query.getPhone())) {
			wrapper.eq(User::getPhone, query.getPhone());
		}else if(StringUtils.isNotBlank(query.getEmail())) {
        	wrapper.eq(User::getEmail, query.getEmail());
        }else {
			throw new IllegalArgumentException("参数错误，缺少用户唯一标识");
		}
        return userMapper.selectOne(wrapper);
    }
    
    /**
     * 根据条件查询用户数量
     * @param query 查询条件
     * @return 用户数量
     */
    public boolean checkUniqueProperty(UserQuery query) {
    	LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
    	if(query != null) {
    		// 不包含当前用户
	    	if(StringUtils.isNotBlank(query.getId())) {
	        	wrapper.ne(User::getId, query.getId());
	        }
	    	if(StringUtils.isNotBlank(query.getAccount())) {
	        	wrapper.eq(User::getAccount, query.getAccount());
	        }else if(StringUtils.isNotBlank(query.getPhone())) {
				wrapper.eq(User::getPhone, query.getPhone());
			}else if(StringUtils.isNotBlank(query.getEmail())) {
	        	wrapper.eq(User::getEmail, query.getEmail());
	        }
    	}
    	return userMapper.selectCount(wrapper) == 0;
    }

    /**
     * 根据账号查询用户
     * @param account 用户账号
     * @return {@link User}
     */
    public User getByAccount(String account) {
    	UserQuery query = UserQuery.builder().account(account).build();
        return selectOne(query);
    }

    /**
     * 用户置顶
     * @param id 用户ID
     * @return 是否成功
     */
    public boolean top(String id) {
        User user = new User();
        user.setId(id);
        user.setTopTime(WebworkUtils.timestamp());
        user.setSort(1);
        return userMapper.updateById(user) == 1;
    }
    
    /**
     * 创建用户
     * @param user
     * @return
     */
    public String create(UserDto user) {
    	String deptId = user.getDeptId();
    	if(StringUtils.isBlank(deptId)) {
    		throw new IllegalArgumentException("部门ID不能为空");
    	}
    	if(StringUtils.isBlank(user.getId())) {
    		user.setId(WebworkUtils.uuid());
    	}
    	if(user.getStatus() == null) {
    		user.setStatus(EntityState.ENABLED.getValue());
    	}
    	int sort = userMapper.maxSortByDeptId(deptId);
    	user.setSort(sort);
        Date time = WebworkUtils.timestamp();
        user.setCreateTime(time);
        user.setUpdateTime(time);
        user.setTopTime(time);
        userMapper.insert(user);
        // 获取部门单位
        Organization org = organizationService.findDepartGroup(deptId);
        UserOrg userOrg = new UserOrg();
        userOrg.setId(WebworkUtils.uuid());
        userOrg.setUserId(user.getId());
        userOrg.setDeptId(deptId);
        userOrg.setOrgId(org.getId());
        userOrgMapper.insert(userOrg);
        return user.getId();
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    public boolean update(UserDto user) {
        
    	userMapper.updateById(user);
        return true;
    }

    /**
     * 分页查询
     * @param pageable 分页参数
     * @return 分页数据 {@link Page}
     */
    public Page<UserVo> page(Pageable<UserQuery> pageable) {
        Page<UserVo> page = new Page<>();
        page.setData(userMapper.pageList(pageable));
        page.setCount(userMapper.pageCount(pageable));
        return page;
    }


}
