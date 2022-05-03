package com.github.wephotos.webwork.user.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.wephotos.webwork.schema.entity.EntityState;
import com.github.wephotos.webwork.schema.entity.Page;
import com.github.wephotos.webwork.schema.entity.Pageable;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.user.api.entity.po.RoleQueryPo;
import com.github.wephotos.webwork.user.api.entity.ro.NodeRo;
import com.github.wephotos.webwork.user.api.entity.ro.enums.ResNodeType;
import com.github.wephotos.webwork.user.api.entity.ro.enums.RoleNodeType;
import com.github.wephotos.webwork.user.entity.Organization;
import com.github.wephotos.webwork.user.entity.Role;
import com.github.wephotos.webwork.user.mapper.RoleMapper;
import com.github.wephotos.webwork.user.mapper.RoleResourceMapper;
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
	public boolean deleteById(Integer id) {
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
    public Integer add(Role role) {
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
        if(role.getId() != null) {
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
	public List<Role> listQuery(RoleQueryPo query) {
		LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
    	wrapper.gt(Role::getStatus, EntityState.DELETED.getValue());
    	if(query.getParentId() != null) {
    		wrapper.eq(Role::getParentId, query.getParentId());
    	}
    	return roleMapper.selectList(wrapper);
	}
	
	/**
     * 分页查询
     * @param pageable 分页参数
     * @return 分页数据 {@link Page} {@link Role}
     */
    public Page<Role> page(Pageable<RoleQueryPo> pageable) {
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
	public List<NodeRo> listNodes(Integer parentId, SecurityUser user) {
		Organization org;
		if(parentId == null) {
			org = organizationService.selectById(user.getGroupId());
			NodeRo node = NodeRo.from(org);
			node.setType(ResNodeType.GROUP.getType());
			return Arrays.asList(node);
		}
		List<NodeRo> nodes;
		org = organizationService.selectById(parentId);
		// 加载应用下角色
		if(org == null) {
			RoleQueryPo query = RoleQueryPo.builder().parentId(parentId).build();
			nodes = listQuery(query).stream().map(NodeRo::new).collect(Collectors.toList());
		}else {
			// 加载子单位和应用
			nodes = resourceService.listTreeNodes(parentId, user);
			nodes.forEach(node -> {
				if(node.getType() == ResNodeType.GROUP.getType()) {
					node.setType(RoleNodeType.GROUP.getType());
				}else {
					node.setType(RoleNodeType.APP.getType());
				}
			});
		}
		return nodes;
	}

}
