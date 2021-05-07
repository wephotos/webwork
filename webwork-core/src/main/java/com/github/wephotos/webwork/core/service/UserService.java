package com.github.wephotos.webwork.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wephotos.webwork.core.entity.User;
import com.github.wephotos.webwork.core.entity.UserVo;
import com.github.wephotos.webwork.core.entity.dto.UserDto;
import com.github.wephotos.webwork.core.entity.query.UserQuery;
import com.github.wephotos.webwork.core.mapper.OrganizationMapper;
import com.github.wephotos.webwork.core.mapper.UserMapper;
import com.github.wephotos.webwork.core.mapper.UserOrgMapper;
import com.github.wephotos.webwork.core.mapper.UserRoleMapper;
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
    private UserRoleMapper userRoleMapper;
    @Resource
    private OrganizationMapper organizationMapper;
    @Resource
    private UserOrgMapper userOrgMapper;
    
    /**
     * 根据用户ID，查询用户详细信息，包含部门单位角色等
     * @param id 用户ID
     * @return {@link UserVo}
     */
    public UserVo findById(String id) {
    	return userMapper.findById(id);
    }
    /**
     * 查询唯一用户记录
     * @param query 包含可唯一标识用户的字段属性
     * @return {@link User}
     */
    public User selectOne(UserQuery query) {
    	return selectOne(query, false);
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
	@SuppressWarnings("unchecked")
	public User selectOne(UserQuery query, boolean password) {
    	LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
    	List<SFunction<User, ?>> columns = new ArrayList<>();
    	columns.add(User::getId);
    	columns.add(User::getName);
    	columns.add(User::getAccount);
    	columns.add(User::getEmail);
    	columns.add(User::getPhone);
    	columns.add(User::getStatus);
    	//包含密码
    	if(password) {
    		columns.add(User::getPassword);
    	}
        wrapper.select(columns.toArray(new SFunction[0]));
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
    public int selectCount(UserQuery query) {
    	LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
    	if(query != null) {
	    	if(StringUtils.isNotBlank(query.getId())) {
	        	wrapper.eq(User::getId, query.getId());
	        }else if(StringUtils.isNotBlank(query.getAccount())) {
	        	wrapper.eq(User::getAccount, query.getAccount());
	        }else if(StringUtils.isNotBlank(query.getPhone())) {
				wrapper.eq(User::getPhone, query.getPhone());
			}else if(StringUtils.isNotBlank(query.getEmail())) {
	        	wrapper.eq(User::getEmail, query.getEmail());
	        }
    	}
    	return userMapper.selectCount(wrapper);
    }
    
    /**
     * 检测唯一属性是否唯一
     * @param query 查询条件 账号，邮箱，手机
     * @return 不存在返回true
     */
    public boolean checkUniqueProperty(UserQuery query) {
        return selectCount(query) == 0;
    }

    /**
     * 根据账号查询用户
     * @param account 用户账号
     * @return {@link User}
     */
    public User getByAccount(String account) {
    	UserQuery query = UserQuery.builder().account(account).build();
        return selectOne(query, true);
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
        user.setId(WebworkUtils.uuid());
        user.setStatus(EntityState.ENABLED.getValue());
        Date time = WebworkUtils.timestamp();
        user.setCreateTime(time);
        user.setUpdateTime(time);
        userMapper.insert(user);
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
