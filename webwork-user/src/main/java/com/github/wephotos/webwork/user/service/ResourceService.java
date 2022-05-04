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
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.user.api.entity.po.OrganizationQueryPo;
import com.github.wephotos.webwork.user.api.entity.po.ResourceQueryPo;
import com.github.wephotos.webwork.user.api.entity.ro.NodeRo;
import com.github.wephotos.webwork.user.entity.Organization;
import com.github.wephotos.webwork.user.entity.Resource;
import com.github.wephotos.webwork.user.entity.enums.ResNodeType;
import com.github.wephotos.webwork.user.entity.enums.UserNodeType;
import com.github.wephotos.webwork.user.mapper.ResourceMapper;
import com.github.wephotos.webwork.utils.StringUtils;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class ResourceService {
	
    @javax.annotation.Resource
    private ResourceMapper resourceMapper;
    
    @javax.annotation.Resource
    private OrganizationService organizationService;

    /**
     * 获取资源数据
     * @param id 资源ID
     * @return 资源对象
     */
    public Resource selectById(Integer id) {
        return resourceMapper.selectById(id);
    }

    /**
     * 新增资源
     * @param record 资源对象
     * @return 添加是否成功
     */
    public synchronized Integer add(Resource record) {
    	record.setStatus(EntityState.ENABLED.getValue());
        Date time = WebworkUtils.timestamp();
        record.setCreateTime(time);
        record.setUpdateTime(time);
        Integer parentId = record.getParentId();
    	Resource parent = resourceMapper.selectById(parentId);
    	// 添加应用
    	if(parent == null) {
    		record.setCode(resourceMapper.getMaxCodeApp());
    	}else {
    		String maxCode = resourceMapper.getMaxCode(parentId);
    		record.setCode(parent.getCode().concat(maxCode));
    	}
    	int sort = resourceMapper.getMaxSort(parentId);
    	record.setSort(sort);
        resourceMapper.insert(record);
        return record.getId();
    }

    /**
     * 更新资源
     * @param resource 资源对象
     * @return 更新成功返回 true
     */
    public boolean update(Resource resource) {
        resource.setUpdateTime(WebworkUtils.timestamp());
        return resourceMapper.updateById(resource) == 1;
    }
    
    /**
     * 删除权限
     * @param id 唯一标识
     * @return 删除成功返回true
     */
	public boolean deleteById(Integer id) {
		Resource entity = new Resource()
				.setId(id)
				.setStatus(EntityState.DELETED.getValue());
		return resourceMapper.updateById(entity) == 1;
	}
    
    /**
     * 检测权限编码是否存在
     * @param resource 包含 ID和权限代码的资源对象
     * @return 存在返回 true
     */
    public boolean isExistsPermission(Resource resource) {
        LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<>();
        if(resource.getId() != null) {
        	wrapper.ne(Resource::getId, resource.getId());
        }
        wrapper.eq(Resource::getPermission, resource.getPermission());
        return resourceMapper.selectCount(wrapper) > 0;
    }
    
    /**
     * 列表搜索
     * @param query {@link ResourceQueryPo}
     * @return 符合条件的资源集合
     */
    public List<Resource> listQuery(ResourceQueryPo query){
    	LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<>();
    	wrapper.gt(Resource::getStatus, EntityState.DELETED.getValue());
    	if(query.getParentId() != null) {
    		wrapper.eq(Resource::getParentId, query.getParentId());
    	}
    	if(StringUtils.isNotBlank(query.getCode())) {
    		wrapper.likeRight(Resource::getCode, query.getCode());
    	}
    	return resourceMapper.selectList(wrapper);
    }
    
    /**
     * 分页查询
     * @param pageable 分页参数
     * @return 分页数据 {@link Page}
     */
    public Page<Resource> page(Pageable<ResourceQueryPo> pageable) {
        Page<Resource> page = new Page<>();
        page.setData(resourceMapper.pageList(pageable));
        page.setCount(resourceMapper.pageCount(pageable));
        return page;
    }
    
    /**
     * 获取当前节点及其所有子节点的树结构
     * @param parentId 父节点ID
     * @return {@link NodeRo}
     */
    public List<NodeRo> deepTreeNodes(Integer parentId){
    	Objects.requireNonNull(parentId, "资源id不能为空");
    	Resource res = selectById(parentId);
    	ResourceQueryPo query = ResourceQueryPo.builder().code(res.getCode()).build();
    	List<Resource> resList = listQuery(query);
    	List<NodeRo> flatNodes = resList.stream().map(NodeRo::new).collect(Collectors.toList());
    	List<NodeRo> treeNodes = new ArrayList<>();
    	for(NodeRo node : flatNodes) {
    		if(parentId.equals(node.getId())) {
    			treeNodes.add(node);
    		}
    		for(NodeRo child : flatNodes) {
    			// 应用不作为子级，和单位ID存在冲突
    			if(!ResNodeType.APP.is(child.getType()) 
    					&& node.getId().equals(child.getParentId())) {
    				node.addChild(child);
    			}
    		}
    	}
    	return treeNodes;
    }

    /**
     * 获取子级资源
     * @param parentId 父ID
     * @param user 会话用户
     * @return 子节点集合
     */
	public List<NodeRo> listTreeNodes(Integer parentId, SecurityUser user) {
		Organization org;
		if(parentId == null) {
			org = organizationService.selectById(user.getGroupId());
			NodeRo node = NodeRo.from(org);
			node.setType(ResNodeType.GROUP.getType());
			return Arrays.asList(node);
		}
		ResourceQueryPo query = ResourceQueryPo.builder().parentId(parentId).build();
		List<NodeRo> nodes = listQuery(query).stream()
				.map(NodeRo::new).collect(Collectors.toList());
		org = organizationService.selectById(parentId);
		if(org != null) {
			// 获取下级单位
			OrganizationQueryPo orgQuery = OrganizationQueryPo.builder()
					.parentId(parentId)
					.neType(UserNodeType.DEPT.getType()).build();
			List<Organization> orgs = organizationService.listQuery(orgQuery);
			List<NodeRo> orgNodes = orgs.stream().map(NodeRo::new).map(node -> {
				node.setType(ResNodeType.GROUP.getType());
				return node;
			}).collect(Collectors.toList());
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

}
