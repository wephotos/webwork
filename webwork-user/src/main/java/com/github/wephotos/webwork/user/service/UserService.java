package com.github.wephotos.webwork.user.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wephotos.webwork.schema.entity.EntityState;
import com.github.wephotos.webwork.schema.entity.Page;
import com.github.wephotos.webwork.schema.entity.Pageable;
import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.exception.StateCode;
import com.github.wephotos.webwork.schema.exception.WebworkRuntimeException;
import com.github.wephotos.webwork.schema.utils.Results;
import com.github.wephotos.webwork.user.client.entity.po.ChangePasswordPO;
import com.github.wephotos.webwork.user.client.entity.po.UpdateUserInfoPO;
import com.github.wephotos.webwork.user.client.entity.po.UserLoginPO;
import com.github.wephotos.webwork.user.entity.Organization;
import com.github.wephotos.webwork.user.entity.User;
import com.github.wephotos.webwork.user.entity.UserOrg;
import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;
import com.github.wephotos.webwork.user.entity.po.UserPO;
import com.github.wephotos.webwork.user.entity.po.UserQueryPO;
import com.github.wephotos.webwork.user.entity.vo.NodeVO;
import com.github.wephotos.webwork.user.entity.vo.UserVO;
import com.github.wephotos.webwork.user.mapper.UserMapper;
import com.github.wephotos.webwork.user.utils.NodeConverter;
import com.github.wephotos.webwork.user.utils.UserStateCode;
import com.github.wephotos.webwork.user.utils.ValidationUtils;
import com.github.wephotos.webwork.utils.BeanUtils;
import com.github.wephotos.webwork.utils.StringUtils;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * 用户业务层
 * 
 * @author TianQi
 *
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class UserService extends ServiceImpl<UserMapper, User> {
	
    @Resource
    private UserMapper userMapper;
    
    @Resource
    private UserOrgService userOrgService;
    
    @Resource
    private OrganizationService organizationService;
    
    /**
     * 创建用户
     * @param po 用户信息
     * @return 用户ID
     */
    public Integer create(UserPO po) {
    	Integer deptId = po.getDeptId();
    	if(deptId == null) {
    		throw new IllegalArgumentException("部门ID不能为空");
    	}
    	// 新增用户时，用户账号，姓名，密码不能为空
    	if(StringUtils.isAnyBlank(po.getAccount(), po.getName(), po.getPassword())) {
    		ValidationUtils.isTrue(false, UserStateCode.USER_PROPS_NOT_NULL);
    	}

        ValidationUtils.isTrue(checkUniqueProperty(User::getAccount, po.getAccount()), UserStateCode.USER_ACCOUNT_EXIST);
        ValidationUtils.isTrue(checkUniqueProperty(User::getPhone, po.getPhone()), UserStateCode.USER_PHONE_EXIST);
        ValidationUtils.isTrue(checkUniqueProperty(User::getEmail, po.getEmail()), UserStateCode.USER_MAIL_EXIST);
        
    	User user = BeanUtils.toObject(po, User.class);
    	if(user.getStatus() == null) {
    		user.setStatus(EntityState.NORMAL.getCode());
    	}
    	user.setUpdateUser(user.getCreateUser());
        Date time = WebworkUtils.nowTime();
        user.setCreateTime(time);
        user.setUpdateTime(time);
        userMapper.insert(user);
        
        // 获取部门单位
        int sort = userMapper.maxSortByDeptId(deptId);
        Organization org = organizationService.findDeptGroup(deptId);
        UserOrg userOrg = new UserOrg();
        userOrg.setUserId(user.getId());
        userOrg.setDeptId(deptId);
        userOrg.setOrgId(org.getId());
        userOrg.setUserSort(sort);
        userOrg.setMainDept(true);
        userOrg.setTopTime(time);
        userOrg.setCreateUser(user.getCreateUser());
        userOrg.setCreateTime(user.getCreateTime());
        userOrgService.save(userOrg);
        return user.getId();
    }

    /**
     * 根据ID删除用户
     * @param id
     * @return
     */
	public boolean deleteById(Integer id) {
		User user = new User();
		user.setId(id);
		user.setStatus(EntityState.DELETED.getCode());
		return userMapper.updateById(user) == 1;
	}
	
    /**
     * 根据用户ID，查询用户详细信息，包含部门单位角色等
     * @param id 用户ID
     * @return {@link UserVO}
     */
    public UserVO findUserDetailsById(Integer id) {
    	return userMapper.findById(id);
    }
    
    /**
     * 查询唯一用户记录
     * @param query 查询条件
     * @param password 是否包含密码
     * @return {@link User}
     */
	public User selectOne(UserQueryPO query) {
    	LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getId, User::getName, User::getAccount, User::getPassword, 
        		User::getEmail, User::getPhone, User::getStatus, User::getPost, User::getAvatar);
        if(query.getId() != null) {
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
     * 检测用户唯一属性是否已经存在
     * @param column 列方法引用
     * @param val 列值
     * @return true: 检测通过
     */
    public boolean checkUniqueProperty(SFunction<User, ?> column, Object val) {
    	return checkUniqueProperty(null, column, val);
    }
    
    /**
     * 检测用户唯一属性是否已经存在
     * @param userId 用户ID
     * @param column 列方法引用
     * @param val 列值
     * @return true: 检测通过
     */
    public boolean checkUniqueProperty(Integer userId, SFunction<User, ?> column, Object val) {
    	if(ObjectUtils.isEmpty(val)) {
    		return true;
    	}
		LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(column, val);
        // 不包含当前用户
    	wrapper.ne(userId != null, User::getId, userId);
        
    	return userMapper.selectCount(wrapper) == 0;
    }

    /**
     * 根据账号查询用户
     * @param account 用户账号
     * @return {@link User}
     */
    public User getByAccount(String account) {
    	UserQueryPO query = UserQueryPO.builder().account(account).build();
        return selectOne(query);
    }

    /**
     * 用户置顶
     * @param userId 用户ID
     * @param deptId 部门ID
     * @return 是否成功
     */
    public boolean top(String userId, String deptId) {
    	UserOrg entity = new UserOrg();
    	entity.setUserSort(0);
    	entity.setTopTime(WebworkUtils.nowTime());
    	LambdaQueryWrapper<UserOrg> wrapper = new LambdaQueryWrapper<>();
    	wrapper.eq(UserOrg::getUserId, userId);
    	wrapper.eq(UserOrg::getDeptId, deptId);
        return userOrgService.update(entity, wrapper);
    }

    /**
     * 更新用户
     * @param po 用户信息
     * @return 成功返回 true
     */
    public boolean update(UserPO po) {
    	if(po.getId() == null) {
    		throw new WebworkRuntimeException(StateCode.PARAMETER_MISSING, "用户ID不能为空");
    	}
    	// 更新用户时，用户 账号、姓名 不能为空
    	if(StringUtils.isAnyBlank(po.getAccount(), po.getName())) {
    		ValidationUtils.isTrue(false, UserStateCode.USER_PROPS_NOT_NULL, "用户账号、姓名不能为空");
    	}
    	// 用户账号、手机、邮箱不能重复
    	ValidationUtils.isTrue(checkUniqueProperty(po.getId(), User::getAccount, po.getAccount()), 
    			UserStateCode.USER_ACCOUNT_EXIST);
		ValidationUtils.isTrue(checkUniqueProperty(po.getId(), User::getPhone, po.getPhone()), 
				UserStateCode.USER_PHONE_EXIST);
		ValidationUtils.isTrue(checkUniqueProperty(po.getId(), User::getEmail, po.getEmail()), 
				UserStateCode.USER_MAIL_EXIST);
        
    	User user = BeanUtils.toObject(po, User.class);
    	user.setUpdateUser(user.getCreateUser());
    	user.setUpdateTime(WebworkUtils.nowTime());
    	
    	// 更新用户机构信息
    	Integer deptId = po.getDeptId();
    	if(deptId != null) {
	    	List<UserOrg> orgs = userOrgService.listOrgByUserId(po.getId());
	    	UserOrg userOrg = orgs.stream().filter(UserOrg::getMainDept)
	    			.findAny().orElseGet(UserOrg::new);
    		if(!deptId.equals(userOrg.getDeptId())) {
    			Organization deptGroup = organizationService.findDeptGroup(deptId);
    			userOrg.setDeptId(deptId);
    			userOrg.setOrgId(deptGroup.getId());
    			userOrgService.updateById(userOrg);
    		}
    	}
    	
    	return userMapper.updateById(user) == 1;
    }

    /**
     * 分页查询
     * @param pageable 分页参数
     * @return 分页数据 {@link Page}
     */
    public Page<UserVO> page(Pageable<UserQueryPO> pageable) {
        Page<UserVO> page = new Page<>();
        page.setData(userMapper.pageList(pageable));
        page.setCount(userMapper.pageCount(pageable));
        return page;
    }
    
    /**
     * 查询部门下用户信息
     * @param deptId 部门ID
     * @return 部门下用户集合
     */
    public List<UserVO> listUserByDeptId(Integer deptId){
    	Objects.requireNonNull(deptId, "部门ID不能为空");
    	return userMapper.listUserByDeptId(deptId);
    }

    /**
     * 获取用户树数据
     * @param parentId 父ID
     * @param user 会话用户
     * @return 节点数据
     */
    public List<NodeVO> listUserNodes(Integer parentId){
    	// 加载子节点
    	List<NodeVO> nodes = organizationService.children(parentId);
    	// 当前非部门节点时，不再继续加载用户
    	Organization orga = organizationService.selectById(parentId);
    	if(orga == null || !NodeTypeEnum.DEPT.is(orga.getType())) {
    		return nodes;
    	}
    	// 加载部门下用户
		List<UserVO> users = userMapper.listUserByDeptId(parentId);
		if(!users.isEmpty()) {
    		List<NodeVO> userNodes = users.stream().map(NodeConverter::from).collect(Collectors.toList());
    		// 检查集合信息
    		if(nodes == null || nodes == Collections.EMPTY_LIST) {
    			nodes = new ArrayList<>(users.size());
    		}
    		nodes.addAll(userNodes);
		}
    	return nodes;
    }
    
    /**
     * 用户登录
     * @param po 用户名密码等登录参数
     * @return 登录反馈信息，登录成功时包含当前用户信息
     */
    public Result<UserVO> login(UserLoginPO po) {
		if(StringUtils.isAnyBlank(po.getUsername(), po.getPassword())) {
			return Results.newResult(StateCode.PARAMETER_MISSING, "用户名或密码不能为空");
		}
		User user = this.getByAccount(po.getUsername());
        if (user == null) {
            return Results.newResult(UserStateCode.USER_NOT_EXISTS);
        }
        Integer status = user.getStatus();
        // 账号被删除，返回不存在
        if (EntityState.DELETED.is(status)) {
            return Results.newResult(UserStateCode.USER_NOT_EXISTS);
        }
        // 账号禁用
        if (EntityState.DISABLED.is(status)) {
            return Results.newResult(UserStateCode.USER_DISABLED);
        }
        // 登录
        boolean isEq = po.getPassword().equals(user.getPassword());
        if (isEq) {
            // 获取部门与组织信息
            UserVO vo = this.findUserDetailsById(user.getId());
            return Results.newSuccessfullyResult(vo);
        } else {
        	return Results.newResult(UserStateCode.USER_PASSWORD_ERROR);
        }
	}
    
    /**
     * 修改用户密码
     * @param po 入参
     * @return 是否修改成功
     */
    public Result<Boolean> changePassword(ChangePasswordPO po) {
    	if (po.getUserId() == null) {
    		return Results.newResult(StateCode.PARAMETER_MISSING, "用户名ID不能为空");
    	}
    	if (StringUtils.isAnyBlank(po.getPassword(), po.getNewPassword())) {
    		return Results.newResult(StateCode.PARAMETER_MISSING, "原密码或新密码都不能为空");
    	}
    	if (StringUtils.equals(po.getPassword(), po.getNewPassword())) {
    		return Results.newResult(StateCode.PARAMETER_ILLEGAL, "新密码和原密码不能相同");
    	}
    	if (po.getNewPassword().length() < 6) {
    		return Results.newResult(StateCode.PARAMETER_ILLEGAL, "新密码不能少于6位字符");
    	}
    	final User user = getById(po.getUserId());
    	if (!StringUtils.equals(user.getPassword(), po.getPassword())) {
    		return Results.newResult(StateCode.PARAMETER_ILLEGAL, "原密码错误，请重新输入");
    	}
    	user.setPassword(po.getNewPassword());
    	user.setUpdateTime(WebworkUtils.nowTime());
    	return Results.newSuccessfullyResult(updateById(user));
    }
    
    /**
     * 更新简单的用户信息，提供给用户更新个人信息使用
     * 
     * @param po 更新参数
     * @return 是否更新成功
     */
    public Result<Boolean> updateSimpleUserInfo(UpdateUserInfoPO po) {
    	if (po.getId() == null) {
    		return Results.newResult(StateCode.PARAMETER_MISSING, "用户名ID不能为空");
    	}
    	if (StringUtils.isAnyBlank(po.getName(), po.getPhone())) {
    		return Results.newResult(UserStateCode.USER_PROPS_NOT_NULL, "用户姓名、手机号不能为空");
    	}
    	final User user = BeanUtils.toObject(po, User.class);
    	user.setUpdateTime(WebworkUtils.nowTime());
    	return Results.newSuccessfullyResult(updateById(user));
    }

}
