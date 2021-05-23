package com.github.wephotos.webwork.core.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.wephotos.webwork.core.entity.Organization;
import com.github.wephotos.webwork.core.entity.ResourceType;
import com.github.wephotos.webwork.core.entity.Role;
import com.github.wephotos.webwork.core.entity.RoleNodeType;
import com.github.wephotos.webwork.core.entity.query.RoleQuery;
import com.github.wephotos.webwork.core.entity.vo.TreeNode;
import com.github.wephotos.webwork.core.mapper.RoleMapper;
import com.github.wephotos.webwork.core.mapper.RoleResourceMapper;
import com.github.wephotos.webwork.http.EntityState;
import com.github.wephotos.webwork.http.Page;
import com.github.wephotos.webwork.http.Pageable;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class RoleService {
	
    @Resource
    private RoleMapper roleMapper;
    
    @Resource
    private RoleResourceMapper roleResourceMapper;
    
    @Resource
    private ResourceService resourceService;
    
    @Resource
    private OrganizationService organizationService;
    /**
     * 根据ID获取角色
     * @param id 角色ID
     * @return 角色对象
     */
    public Role selectById(String id) {
        return roleMapper.selectById(id);
    }
    
    /**
     * 根据ID删除角色
     * @param id 角色ID
     * @return 删除是否成功
     */
	public boolean deleteById(String id) {
		Role role = new Role();
		role.setId(id);
		role.setStatus(EntityState.DELETED.getValue());
		return roleMapper.updateById(role) == 1;
	}
	
    /**
     * 新增角色
     * @param role 角色对象
     * @return 角色ID
     */
    public String add(Role role) {
        role.setId(WebworkUtils.uuid());
        role.setStatus(EntityState.ENABLED.getValue());
        role.setCreateTime(WebworkUtils.timestamp());
        int sort = roleMapper.getMaxSort(role.getParentId());
        role.setSort(sort);
        roleMapper.insert(role);
        return role.getId();
    }

    /**
     * 更新角色
     * @param role 角色对象
     * @return 更新成功返回true
     */
    public boolean update(Role role) {
        return roleMapper.updateById(role) == 1;
    }
    
    /**
     * 检测权限编码是否存在
     * @param resource 包含 ID和权限代码的资源对象
     * @return 存在返回 true
     */
    public boolean isExistsName(Role role) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(role.getId())) {
        	wrapper.ne(Role::getId, role.getId());
        }
        wrapper.eq(Role::getName, role.getName());
        return roleMapper.selectCount(wrapper) > 0;
    }

    /**
     * 角色查询
     * @param query 查询条件
     * @return 角色集合 {@link Role}
     */
	public List<Role> listQuery(RoleQuery query) {
		LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
    	wrapper.gt(Role::getStatus, EntityState.DELETED.getValue());
    	if(StringUtils.isNotBlank(query.getParentId())) {
    		wrapper.eq(Role::getParentId, query.getParentId());
    	}
    	return roleMapper.selectList(wrapper);
	}
	
	/**
     * 分页查询
     * @param pageable 分页参数
     * @return 分页数据 {@link Page} {@link Role}
     */
    public Page<Role> page(Pageable<RoleQuery> pageable) {
        Page<Role> page = new Page<>();
        page.setData(roleMapper.pageList(pageable));
        page.setCount(roleMapper.pageCount(pageable));
        return page;
    }
    
    /**
     * 获取应用树节点数据
     * @param parentId 父ID
     * @param user 会话用户
     * @return 子节点
     */
	public List<TreeNode> listNodes(String parentId, User user) {
		Organization org;
		if(StringUtils.isBlank(parentId)) {
			org = organizationService.selectById(user.getGroupId());
			TreeNode node = TreeNode.from(org);
			node.setType(ResourceType.ORG.getType());
			return Arrays.asList(node);
		}
		List<TreeNode> nodes;
		org = organizationService.selectById(parentId);
		// 加载应用下角色
		if(org == null) {
			RoleQuery query = RoleQuery.builder().parentId(parentId).build();
			nodes = listQuery(query).stream().map(TreeNode::new).collect(Collectors.toList());
		}else {
			// 加载子单位和应用
			nodes = resourceService.listTreeNodes(parentId, user);
			nodes.forEach(node -> {
				if(node.getType() == ResourceType.ORG.getType()) {
					node.setType(RoleNodeType.ORG.getType());
				}else {
					node.setType(RoleNodeType.APP.getType());
				}
			});
		}
		return nodes;
	}

}
