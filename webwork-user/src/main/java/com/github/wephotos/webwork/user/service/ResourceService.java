package com.github.wephotos.webwork.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.wephotos.webwork.schema.entity.EntityState;
import com.github.wephotos.webwork.schema.entity.Page;
import com.github.wephotos.webwork.schema.entity.Pageable;
import com.github.wephotos.webwork.schema.exception.StateCode;
import com.github.wephotos.webwork.schema.exception.WebworkRuntimeException;
import com.github.wephotos.webwork.user.client.entity.po.UserResoQueryPO;
import com.github.wephotos.webwork.user.client.entity.po.UserRoleQueryPO;
import com.github.wephotos.webwork.user.entity.Organization;
import com.github.wephotos.webwork.user.entity.Resource;
import com.github.wephotos.webwork.user.entity.Role;
import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;
import com.github.wephotos.webwork.user.entity.po.NodeQueryPO;
import com.github.wephotos.webwork.user.entity.po.OrganizationQueryPO;
import com.github.wephotos.webwork.user.entity.po.ResourceQueryPO;
import com.github.wephotos.webwork.user.entity.vo.NodeVO;
import com.github.wephotos.webwork.user.entity.vo.ResoVO;
import com.github.wephotos.webwork.user.mapper.ResourceMapper;
import com.github.wephotos.webwork.user.utils.NodeConverter;
import com.github.wephotos.webwork.user.utils.UserStateCode;
import com.github.wephotos.webwork.user.utils.ValidationUtils;
import com.github.wephotos.webwork.utils.BeanUtils;
import com.github.wephotos.webwork.utils.StringUtils;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * 资源业务层
 * @author TianQi
 *
 */
@Service
public class ResourceService {
	
    @javax.annotation.Resource
    private ResourceMapper resourceMapper;
    
    @javax.annotation.Resource
    private OrganizationService organizationService;
    
    @javax.annotation.Resource
    private RoleService roleService;

    /**
     * 检测资源类型是否合法
     * @param type 资源类型值
     */
    private void checkResourceType(Integer type) {
    	if(!(NodeTypeEnum.APP.is(type) || 
    			NodeTypeEnum.MODULE.is(type) || 
    			NodeTypeEnum.FUNCTION.is(type))) {
    		throw new WebworkRuntimeException(StateCode.PARAMETER_ILLEGAL, "错误的资源类型");
    	}
    }
    
    /**
     * 获取资源数据
     * @param id 资源ID
     * @return 资源对象
     */
    public Resource selectById(Integer id) {
        return resourceMapper.selectById(id);
    }
    
    /**
     * 根据资源代码(自定义)查询
     * @param appCode 自定义代码
     * @return 资源数据
     */
    public Resource selectByResoCode(String resoCode) {
    	ResourceQueryPO query = new ResourceQueryPO();
    	query.setPermission(resoCode);
    	List<Resource> ress = listQuery(query);
    	if(ress == null || ress.isEmpty()) {
    		return null;
    	}else {
    		return ress.get(0);
    	}
    }
    
    /**
     * 获取父节点下的最大层级代码
     * @param parentId 父ID
     * @param parentType 父类型
     * @return 最大编码
     */
    private String getMaxCode(Integer parentId, Integer parentType) {
    	return resourceMapper.getMaxCode(parentId, parentType);
    }
    
    /**
     * 获取父节点下的最大序号
     * @param parentId
     * @param parentType
     * @return
     */
    private Integer getMaxSort(Integer parentId, Integer parentType) {
    	return resourceMapper.getMaxSort(parentId, parentType);
    }
    
    /**
     * 获取最大的应用编码
     * @return 应用编码
     */
    private String getAppMaxCode() {
    	return resourceMapper.getMaxCodeApp();
    }

    /**
     * 新增资源
     * @param record 资源对象
     * @return 添加是否成功
     */
    public synchronized ResoVO add(Resource record) {
    	checkResourceType(record.getType());
    	
        Integer parentId = record.getParentId();
        Integer parentType = record.getParentType();
        if(parentId == null || parentType == null ||
        		StringUtils.isBlank(record.getName()) || 
        		StringUtils.isBlank(record.getPermission())) {
        	throw new WebworkRuntimeException(StateCode.PARAMETER_MISSING, "属性[name,permission,parentId,parentType]不能为空");
        }
        // 校验资源自定义编码
        ValidationUtils.isFalse(containsResource(record), UserStateCode.RESOURCE_CODE_EXIST);
    	// 添加应用
    	if(NodeTypeEnum.GROUP.is(parentType)) {
    		record.setCode(getAppMaxCode());
    	}else {
    		Resource parent = selectById(parentId);
    		String maxCode = getMaxCode(parentId, parentType);
    		record.setCode(parent.getCode().concat(maxCode));
    	}
    	
    	int sort = getMaxSort(parentId, parentType);
    	record.setSort(sort);
    	record.setStatus(EntityState.NORMAL.getCode());
    	Date nowTime = WebworkUtils.nowTime();
    	record.setCreateTime(nowTime);
    	record.setUpdateTime(nowTime);
        resourceMapper.insert(record);
        return BeanUtils.toObject(record, ResoVO.class);
    }

    /**
     * 更新资源
     * @param resource 资源对象
     * @return 更新成功返回 true
     */
    public boolean update(Resource resource) {
    	// 校验资源自定义编码
        ValidationUtils.isFalse(containsResource(resource), UserStateCode.RESOURCE_CODE_EXIST);
        resource.setUpdateTime(WebworkUtils.nowTime());
        return resourceMapper.updateById(resource) == 1;
    }
    
    /**
     * 删除资源
     * @param id 资源ID
     * @return 删除成功返回true
     */
	public boolean deleteById(Integer id) {
		Resource entity = new Resource()
				.setId(id)
				.setStatus(EntityState.DELETED.getCode());
		return resourceMapper.updateById(entity) == 1;
	}
    
    /**
     * 判断资源是否已经存在（根据自定义编码判断）
     * @param resource 要检测的资源代码信息
     * @return 存在返回 true
     */
    public boolean containsResource(Resource resource) {
        LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<>();
        if(resource.getId() != null) {
        	wrapper.ne(Resource::getId, resource.getId());
        }
        wrapper.eq(Resource::getPermission, resource.getPermission());
        return resourceMapper.selectCount(wrapper) > 0;
    }
    
    /**
     * 列表搜索
     * @param query {@link ResourceQueryPO}
     * @return 符合条件的资源集合
     */
    public List<Resource> listQuery(ResourceQueryPO query){
    	LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<>();
    	wrapper.gt(Resource::getStatus, EntityState.DELETED.getCode());
    	if(query.getParentId() != null) {
    		wrapper.eq(Resource::getParentId, query.getParentId());
    	}
    	if(query.getParentType() != null) {
    		wrapper.eq(Resource::getParentType, query.getParentType());
    	}
    	if(StringUtils.isNotBlank(query.getCode())) {
    		wrapper.likeRight(Resource::getCode, query.getCode());
    	}
    	if(StringUtils.isNotBlank(query.getPermission())) {
    		wrapper.eq(Resource::getPermission, query.getPermission());
    	}
    	// 按排序号正序排列
    	wrapper.orderByAsc(Resource::getSort);
    	
    	return resourceMapper.selectList(wrapper);
    }
    
    /**
     * 分页查询
     * @param pageable 分页参数
     * @return 分页数据 {@link Page}
     */
    public Page<Resource> page(Pageable<ResourceQueryPO> pageable) {
        Page<Resource> page = new Page<>();
        page.setData(resourceMapper.pageList(pageable));
        page.setCount(resourceMapper.pageCount(pageable));
        return page;
    }
    
    /**
     * 获取当前节点及其所有子节点的树结构
     * @param po 父节点ID
     * @return {@link NodeVO}
     */
    public List<NodeVO> loadAllNodes(NodeQueryPO po){
    	Objects.requireNonNull(po.getParentId(), "父节点不能为空");
    	Integer parentId = Integer.parseInt(po.getParentId());
    	Resource res = selectById(parentId);
    	ResourceQueryPO query = ResourceQueryPO.builder().code(res.getCode()).build();
    	List<NodeVO> flatNodes = listQuery(query).stream()
    			.map(NodeConverter::from).collect(Collectors.toList());
    	
    	List<NodeVO> treeNodes = new ArrayList<>();
    	for(NodeVO node : flatNodes) {
    		if(parentId.equals(node.getRawId())) {
    			treeNodes.add(node);
    		}
    		for(NodeVO child : flatNodes) {
    			if(node.getId().equals(child.getParentId())) {
    				node.addChild(child);
    			}
    		}
    	}
    	return treeNodes;
    }

    /**
     * 获取子级资源
     * @param po 查询参数
     * @return 子节点集合
     */
	public List<NodeVO> listNodes(NodeQueryPO po) {
		if(po.getParentId() == null) {
			Organization userOrg = organizationService.selectById(po.getUserGroupId());
			return Arrays.asList(NodeConverter.from(userOrg));
		}
		Integer parentType = po.getParentType();
		Integer parentId = Integer.parseInt(po.getParentId());
		ResourceQueryPO query = ResourceQueryPO.builder()
				.parentId(parentId)
				.parentType(parentType).build();
		List<NodeVO> nodes = listQuery(query).stream()
				.map(NodeConverter::from).collect(Collectors.toList());
		// 父节点为单位或虚拟单位时，加载下级单位
		if(NodeTypeEnum.GROUP.is(parentType) 
				|| NodeTypeEnum.VIRTUAL.is(parentType)) {
			OrganizationQueryPO orgQuery = OrganizationQueryPO.builder()
					.parentId(parentId)
					.neType(NodeTypeEnum.DEPT.getCode()).build();
			List<Organization> orgs = organizationService.listQuery(orgQuery);
			List<NodeVO> orgNodes = orgs.stream().map(NodeConverter::from).collect(Collectors.toList());
			nodes.addAll(orgNodes);
		}
		return nodes;
	}

	/**
	 * 获取角色下的资源
	 * @param roleId 角色ID
	 * @return 资源集合 {@link Resource}
	 */
	public List<Resource> listByRoleId(Integer roleId) {
		return resourceMapper.listByRoleId(roleId);
	}
	
	/*============ 对外接口 =============*/
	/**
	 * 查询用户的资源信息
	 * @param po 查询参数
	 * @return 资源数据
	 */
	public List<ResoVO> listResourceByUserId(UserResoQueryPO po){
		Objects.requireNonNull(po.getUserId(), "用户ID不能为空");
		
		// 获取内置的应用层级代码
		Resource app = null;
		String appCode = po.getAppCode();
		if(po.getAppId() != null) {
			app = selectById(po.getAppId());
		}else if(StringUtils.isNotBlank(po.getAppCode())) {
			app = selectByResoCode(po.getAppCode());
		}
		if(app != null) {
			po.setAppCode(app.getCode());
		}
		
		// 查询用户关联角色
		UserRoleQueryPO roleQuery = new UserRoleQueryPO();
		roleQuery.setUserId(po.getUserId());
		roleQuery.setAppId(po.getAppId());
		roleQuery.setAppCode(appCode);
		List<Role> roles = roleService.listRoleByUserId(roleQuery);
		
		// 查询角色关联资源
		List<Integer> roleIds = roles.stream().map(Role::getId)
				.collect(Collectors.toList());
		po.setRoleIds(roleIds);
		return resourceMapper.listQueryResource(po);
	}
}
