package com.github.wephotos.webwork.core.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.wephotos.webwork.core.entity.OrgType;
import com.github.wephotos.webwork.core.entity.Organization;
import com.github.wephotos.webwork.core.entity.Resource;
import com.github.wephotos.webwork.core.entity.ResourceType;
import com.github.wephotos.webwork.core.entity.query.OrgQuery;
import com.github.wephotos.webwork.core.entity.query.ResQuery;
import com.github.wephotos.webwork.core.entity.vo.TreeNode;
import com.github.wephotos.webwork.core.mapper.ResourceMapper;
import com.github.wephotos.webwork.http.EntityState;
import com.github.wephotos.webwork.http.Page;
import com.github.wephotos.webwork.http.Pageable;
import com.github.wephotos.webwork.security.entity.User;
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
    public Resource selectById(String id) {
        return resourceMapper.selectById(id);
    }

    /**
     * 新增资源
     * @param record 资源对象
     * @return 添加是否成功
     */
    public synchronized String add(Resource record) {
    	record.setId(WebworkUtils.uuid());
    	record.setStatus(EntityState.ENABLED.getValue());
        Date time = WebworkUtils.timestamp();
        record.setCreateTime(time);
        record.setUpdateTime(time);
        String parentId = record.getParentId();
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
	public boolean deleteById(String id) {
		Resource entity = new Resource();
		entity.setId(id);
		entity.setStatus(EntityState.DELETED.getValue());
		return resourceMapper.updateById(entity) == 1;
	}
    
    /**
     * 检测权限编码是否存在
     * @param resource 包含 ID和权限代码的资源对象
     * @return 存在返回 true
     */
    public boolean isExistsPermission(Resource resource) {
        LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(resource.getId())) {
        	wrapper.ne(Resource::getId, resource.getId());
        }
        wrapper.eq(Resource::getPermission, resource.getPermission());
        return resourceMapper.selectCount(wrapper) > 0;
    }
    
    /**
     * 列表搜索
     * @param query {@link ResQuery}
     * @return 符合条件的资源集合
     */
    public List<Resource> listQuery(ResQuery query){
    	LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<>();
    	wrapper.gt(Resource::getStatus, EntityState.DELETED.getValue());
    	if(StringUtils.isNotBlank(query.getParentId())) {
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
    public Page<Resource> page(Pageable<ResQuery> pageable) {
        Page<Resource> page = new Page<>();
        page.setData(resourceMapper.pageList(pageable));
        page.setCount(resourceMapper.pageCount(pageable));
        return page;
    }
    
    /**
     * 获取当前节点及其所有子节点的树结构
     * @param id 当前节点标识
     * @return {@link TreeNode}
     */
    public List<TreeNode> deepTreeNodes(String id){
    	Objects.requireNonNull(id, "资源id不能为空");
    	Resource res = selectById(id);
    	ResQuery query = ResQuery.builder().code(res.getCode()).build();
    	List<Resource> resList = listQuery(query);
    	List<TreeNode> flatNodes = resList.stream().map(TreeNode::new).collect(Collectors.toList());
    	List<TreeNode> treeNodes = new ArrayList<>();
    	for(TreeNode node : flatNodes) {
    		if(id.equals(node.getId())) {
    			treeNodes.add(node);
    		}
    		for(TreeNode child : flatNodes) {
    			if(node.getId().equals(child.getParentId())) {
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
	public List<TreeNode> listTreeNodes(String parentId, User user) {
		Organization org;
		if(StringUtils.isBlank(parentId)) {
			org = organizationService.selectById(user.getGroupId());
			TreeNode node = TreeNode.from(org);
			node.setType(ResourceType.ORG.getType());
			return Arrays.asList(node);
		}
		ResQuery query = ResQuery.builder().parentId(parentId).build();
		List<TreeNode> nodes = listQuery(query).stream()
				.map(TreeNode::new).collect(Collectors.toList());
		org = organizationService.selectById(parentId);
		if(org != null) {
			// 获取下级单位
			OrgQuery orgQuery = OrgQuery.builder()
					.parentId(parentId)
					.neType(OrgType.DEPT.getType()).build();
			List<Organization> orgs = organizationService.listQuery(orgQuery);
			List<TreeNode> orgNodes = orgs.stream().map(TreeNode::new).map(node -> {
				node.setType(ResourceType.ORG.getType());
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
	public List<Resource> listByRoleId(String roleId) {
		return resourceMapper.listByRoleId(roleId);
	}

}
