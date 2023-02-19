package com.github.wephotos.webwork.user.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.wephotos.webwork.logging.LoggerFactory;
import com.github.wephotos.webwork.schema.entity.EntityState;
import com.github.wephotos.webwork.schema.entity.Page;
import com.github.wephotos.webwork.schema.entity.Pageable;
import com.github.wephotos.webwork.schema.exception.StateCode;
import com.github.wephotos.webwork.schema.exception.WebworkRuntimeException;
import com.github.wephotos.webwork.user.client.entity.po.UserRoleQueryPO;
import com.github.wephotos.webwork.user.client.entity.po.UserRoleQueryPO.RoleUserIdTypeQueryPO;
import com.github.wephotos.webwork.user.entity.Organization;
import com.github.wephotos.webwork.user.entity.Role;
import com.github.wephotos.webwork.user.entity.RoleResource;
import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;
import com.github.wephotos.webwork.user.entity.po.NodeQueryPO;
import com.github.wephotos.webwork.user.entity.po.ResourceQueryPO;
import com.github.wephotos.webwork.user.entity.po.RoleQueryPO;
import com.github.wephotos.webwork.user.entity.vo.NodeVO;
import com.github.wephotos.webwork.user.entity.vo.RoleVO;
import com.github.wephotos.webwork.user.entity.vo.SimpleNodeVO;
import com.github.wephotos.webwork.user.mapper.RoleMapper;
import com.github.wephotos.webwork.user.utils.NodeConverter;
import com.github.wephotos.webwork.user.utils.UserStateCode;
import com.github.wephotos.webwork.user.utils.ValidationUtils;
import com.github.wephotos.webwork.utils.StringUtils;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class RoleService {
	
	private static final Logger log = LoggerFactory.getLogger(RoleService.class);
	
    @Resource
    private RoleMapper roleMapper;
    
    @Resource
    private ResourceService resourceService;
    
    @Resource
    private RoleResourceService roleResourceService;
    
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
		log.info("删除角色, id = {}", id);
		Role role = new Role();
		role.setId(id);
		role.setStatus(EntityState.DELETED.getCode());
		return roleMapper.updateById(role) == 1;
	}
	
    /**
     * 新增角色
     * @param role 角色对象
     * @return 角色ID
     */
    public Integer add(Role role) {
    	// 判断角色名称是否存在
    	ValidationUtils.isFalse(isExistsName(role), UserStateCode.ROLE_NAME_EXIST);
    	
    	log.info("新增角色: {}", role);
        role.setStatus(EntityState.NORMAL.getCode());
        role.setCreateTime(WebworkUtils.nowTime());
        role.setUpdateTime(role.getCreateTime());
        int sort = roleMapper.getMaxSort(role.getParentId(), role.getParentType());
        role.setSort(sort);
        roleMapper.insert(role);
        
        // 新建应用角色时自动关联应用
        if(NodeTypeEnum.APP.is(role.getParentType())) {
	        RoleResource roleResource = new RoleResource();
	        roleResource.setRoleId(role.getId());
	        roleResource.setResourceId(role.getParentId());
	        roleResource.setCreateUser(role.getCreateUser());
	        roleResource.setCreateTime(role.getCreateTime());
	        roleResourceService.add(roleResource);
        }
        return role.getId();
    }

    /**
     * 更新角色
     * @param role 角色对象
     * @return 更新成功返回true
     */
    public boolean update(Role role) {
    	// 判断角色名称是否存在
    	ValidationUtils.isFalse(isExistsName(role), UserStateCode.ROLE_NAME_EXIST);
    	
    	log.info("更新角色: {}", role);
    	role.setUpdateTime(WebworkUtils.nowTime());
        return roleMapper.updateById(role) == 1;
    }
    
    /**
     * 判断角色名称是否存在
     * @param role 角色对象
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
	public List<Role> listQuery(RoleQueryPO query) {
		LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
    	wrapper.gt(Role::getStatus, EntityState.DELETED.getCode());
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
    public Page<RoleVO> page(Pageable<RoleQueryPO> pageable) {
        Page<RoleVO> page = new Page<>();
        page.setData(roleMapper.pageList(pageable));
        page.setCount(roleMapper.pageCount(pageable));
        return page;
    }
    
    /**
     * 判断节点是否为角色树节点
     * @param node 节点对象
     * @return 角色树节点返回 true
     */
    private boolean isRoleNode(NodeVO node) {
    	return NodeTypeEnum.GROUP.is(node.getType()) || NodeTypeEnum.APP.is(node.getType());
    }
    
    /**
     * 获取应用树节点数据
     * @param po 查询参数
     * @return 子节点
     */
	public List<NodeVO> listNodes(NodeQueryPO po) {
		Organization org;
		if(po.getParentId() == null) {
			org = organizationService.selectById(po.getUserGroupId());
			return Arrays.asList(NodeConverter.from(org));
		}
		List<NodeVO> nodes;
		Integer parentId = Integer.parseInt(po.getParentId());
		org = organizationService.selectById(parentId);
		// 获取不到单位则节点为应用节点
		if(org == null) {
			// 加载应用下的角色
			RoleQueryPO query = RoleQueryPO.builder()
					.parentId(parentId)
					.parentType(po.getParentType()).build();
			// 转换角色节点
			nodes = listQuery(query).stream().map(NodeConverter::from).collect(Collectors.toList());
		}else {
			// 加载子单位和应用
			nodes = resourceService.listNodes(po);
			// 过滤出单位和应用节点
			nodes = nodes.stream().filter(this::isRoleNode).collect(Collectors.toList());
		}
		return nodes;
	}

	/**
	 * 查询用户角色，包含用户所在部门和单位关联的角色
	 * @param po {@link UserRoleQueryPO} 查询参数
	 * @return 用户角色信息
	 */
	public List<Role> listRoleByUserId(UserRoleQueryPO po){
		Objects.requireNonNull(po.getUserId(), "参数【userId】不能为空");
		
		// 应用代码转换为应用ID
		String appCode = po.getAppCode();
		if(po.getAppId() == null && StringUtils.isNotBlank(appCode)) {
			ResourceQueryPO query = new ResourceQueryPO();
			query.setPermission(appCode);
			List<com.github.wephotos.webwork.user.entity.Resource> ress = resourceService.listQuery(query);
			if(ress.size() != 1 || NodeTypeEnum.APP.is(ress.get(0).getType()) ==  false) {
				throw new WebworkRuntimeException(StateCode.PARAMETER_ILLEGAL, "错误的应用代码");
			}
			po.setAppId(ress.get(0).getId());
		}
		
		// 查询用户所有的部门和单位（包含父单位）
		Integer userId = po.getUserId();
		List<SimpleNodeVO> userNodes = organizationService.listAllNodesByUserId(userId);
		// 根据过滤条件和用户、部门、单位关联查询出角色
		List<RoleUserIdTypeQueryPO> roleUserIds = userNodes.stream().map(node -> {
			RoleUserIdTypeQueryPO record = new RoleUserIdTypeQueryPO();
			record.setId(node.getId());
			record.setType(node.getType());
			return record;
		}).collect(Collectors.toList());
		
		// 添加当前用户
		RoleUserIdTypeQueryPO myself = new RoleUserIdTypeQueryPO();
		myself.setId(userId);
		myself.setType(NodeTypeEnum.USER.getCode());
		roleUserIds.add(myself);
		po.setUserIds(roleUserIds);
		
		return roleMapper.listRoleByUserId(po);
	}
}
