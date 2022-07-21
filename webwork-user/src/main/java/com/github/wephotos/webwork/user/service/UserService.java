package com.github.wephotos.webwork.user.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wephotos.webwork.schema.entity.EntityState;
import com.github.wephotos.webwork.schema.entity.Page;
import com.github.wephotos.webwork.schema.entity.Pageable;
import com.github.wephotos.webwork.schema.exception.StateCode;
import com.github.wephotos.webwork.schema.exception.WebworkRuntimeException;
import com.github.wephotos.webwork.user.api.entity.po.UserPo;
import com.github.wephotos.webwork.user.api.entity.po.UserQueryPo;
import com.github.wephotos.webwork.user.api.entity.ro.NodeRo;
import com.github.wephotos.webwork.user.api.entity.ro.UserRo;
import com.github.wephotos.webwork.user.entity.Organization;
import com.github.wephotos.webwork.user.entity.User;
import com.github.wephotos.webwork.user.entity.UserOrg;
import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;
import com.github.wephotos.webwork.user.mapper.UserMapper;
import com.github.wephotos.webwork.user.mapper.UserOrgMapper;
import com.github.wephotos.webwork.user.utils.TreeNodeConverter;
import com.github.wephotos.webwork.utils.BeanUtils;
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
     * @return {@link UserRo}
     */
    public UserRo findUserDetailsById(Integer id) {
    	return userMapper.findById(id);
    }

    /**
     * 根据ID删除用户
     * @param id
     * @return
     */
	public boolean deleteById(Integer id) {
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
	public User selectOne(UserQueryPo query) {
    	LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getId, User::getName, User::getAccount, User::getPassword, 
        		User::getEmail, User::getPhone, User::getStatus);
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
     * 根据条件查询用户数量
     * @param query 查询条件
     * @return 用户数量
     */
    public boolean checkUniqueProperty(UserQueryPo query) {
    	LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
    	if(query != null) {
    		// 不包含当前用户
	    	if(query.getId() != null) {
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
    	UserQueryPo query = UserQueryPo.builder().account(account).build();
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
    	entity.setTopTime(WebworkUtils.timestamp());
    	LambdaQueryWrapper<UserOrg> wrapper = new LambdaQueryWrapper<>();
    	wrapper.eq(UserOrg::getUserId, userId);
    	wrapper.eq(UserOrg::getDeptId, deptId);
        return userOrgMapper.update(entity, wrapper) == 1;
    }
    
    /**
     * 创建用户
     * @param source
     * @return
     */
    public Integer create(UserPo source) {
    	Integer deptId = source.getDeptId();
    	if(deptId == null) {
    		throw new IllegalArgumentException("部门ID不能为空");
    	}
    	User user = BeanUtils.toBean(source, User.class);
    	if(user.getStatus() == null) {
    		user.setStatus(EntityState.ENABLED.getValue());
    	}
        Date time = WebworkUtils.timestamp();
        user.setCreateTime(time);
        user.setUpdateTime(time);
        userMapper.insert(user);
        // 获取部门单位
        int sort = userMapper.maxSortByDeptId(deptId);
        Organization org = organizationService.findDepartGroup(deptId);
        UserOrg userOrg = new UserOrg();
        userOrg.setUserId(user.getId());
        userOrg.setDeptId(deptId);
        userOrg.setOrgId(org.getId());
        userOrg.setUserSort(sort);
        userOrg.setMainDept(true);
        userOrg.setTopTime(time);
        userOrgMapper.insert(userOrg);
        return user.getId();
    }

    /**
     * 更新用户
     * @param source
     * @return
     */
    public boolean update(UserPo source) {
    	User user = BeanUtils.toBean(source, User.class);
    	if(user.getId() == null) {
    		throw new WebworkRuntimeException(StateCode.PARAMETER_MISSING, "用户ID不能为空");
    	}
    	return userMapper.updateById(user) == 1;
    }

    /**
     * 分页查询
     * @param pageable 分页参数
     * @return 分页数据 {@link Page}
     */
    public Page<UserRo> page(Pageable<UserQueryPo> pageable) {
        Page<UserRo> page = new Page<>();
        page.setData(userMapper.pageList(pageable));
        page.setCount(userMapper.pageCount(pageable));
        return page;
    }

    /**
     * 获取用户树数据
     * @param parentId 父ID
     * @param user 会话用户
     * @return 节点数据
     */
    public List<NodeRo> listTreeNodes(Integer parentId, com.github.wephotos.webwork.security.entity.SecurityUser user){
    	List<NodeRo> nodes = organizationService.children(parentId, user);
    	if(parentId != null) {
	    	Organization org = organizationService.selectById(parentId);
	    	if(org != null && NodeTypeEnum.DEPT.is(org.getType())) {
	    		List<User> users = userMapper.listUserByDeptId(parentId);
	    		List<NodeRo> userNodes = users.stream().map(TreeNodeConverter::from).collect(Collectors.toList());
	    		nodes.addAll(userNodes);
	    	}
    	}
    	return nodes;
    }

}
