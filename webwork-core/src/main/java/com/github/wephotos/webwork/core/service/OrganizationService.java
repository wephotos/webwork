package com.github.wephotos.webwork.core.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.wephotos.webwork.core.entity.OrgType;
import com.github.wephotos.webwork.core.entity.Organization;
import com.github.wephotos.webwork.core.entity.dto.DropSort;
import com.github.wephotos.webwork.core.entity.query.OrgQuery;
import com.github.wephotos.webwork.core.entity.vo.TreeNode;
import com.github.wephotos.webwork.core.mapper.OrganizationMapper;
import com.github.wephotos.webwork.http.EntityState;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.utils.StringUtils;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class OrganizationService {
	
    @Resource
    private OrganizationMapper organizationMapper;

    /**
     * 获取节点对象
     * @param id 节点ID
     * @return 节点对象
     */
    public Organization selectById(String id) {
        return organizationMapper.selectById(id);
    }
    
    /**
     * 保存节点
     * @param data 节点数据
     * @return 主键
     */
    public synchronized String add(Organization data) {
        data.setId(WebworkUtils.uuid());
        String parentId = data.getParentId();
        String maxCode = organizationMapper.getMaxCode(parentId);
        if(StringUtils.isBlank(parentId)) {
        	data.setCode(maxCode);
        }else {
        	Organization parent = organizationMapper.selectById(parentId);
        	if(parent == null) {
        		throw new IllegalArgumentException(String.format("上级节点不存在:%s", parentId));
        	}
        	data.setCode(parent.getCode().concat(maxCode));
        }
        organizationMapper.insert(data);
        return data.getId();
    }

    /**
     * 更新
     * @param organization
     * @return
     */
    public boolean update(Organization organization) {
        return SqlHelper.retBool(organizationMapper.updateById(organization));
    }

    /**
     * 删除 --逻辑删除
     * @param id 节点ID
     * @return 是否成功
     */
    public boolean delete(String id) {
        Organization org = new Organization();
        org.setId(id);
        org.setStatus(EntityState.DELETED.getValue());
        return SqlHelper.retBool(organizationMapper.updateById(org));
    }
    
    /**
     * 查询组织架构列表
     * @param query 查询条件
     * @return 组织架构列表
     */
    public List<Organization> listQuery(OrgQuery query){
    	LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
    	wrapper.select(Organization::getId, Organization::getName,
                Organization::getCode, Organization::getStatus,
                Organization::getType, Organization::getParentId);
    	wrapper.gt(Organization::getStatus, EntityState.DELETED.getValue());
    	if(query.getType() != null) {
    		wrapper.eq(Organization::getType, query.getType());
    	}
    	if(query.getNeType() != null) {
    		wrapper.ne(Organization::getType, query.getNeType());
    	}
    	if(StringUtils.isNotBlank(query.getName())) {
    		wrapper.like(Organization::getName, query.getName());
    	}
    	if(StringUtils.isNotBlank(query.getCode())) {
    		wrapper.likeRight(Organization::getCode, query.getCode());
    	}
    	if(StringUtils.isNotBlank(query.getParentId())) {
    		wrapper.eq(Organization::getParentId, query.getParentId());
    	}
    	return organizationMapper.selectList(wrapper);
    }
    
    /**
     * 获取部门所在单位
     * @param deptId 部门ID
     * @return 单位对象
     */
    public Organization findDepartGroup(String deptId) {
    	Organization dept = selectById(deptId);
    	Organization node = selectById(dept.getParentId());
    	while(node.getType() != OrgType.GROUP.getType()) {
    		node = selectById(node.getParentId());
    	}
    	return node;
    }

    /**
     * 查询下级组织机构
     *
     * @param parentId 父ID
     * @param user     当前用户
     * @return {@link Organization}
     */
    public List<TreeNode> children(String parentId, User user) {
        // 父节点为空，返回当前单位节点
        if (StringUtils.isBlank(parentId)) {
            Organization root = selectById(user.getGroupId());
            return Arrays.asList(TreeNode.from(root));
        }
        OrgQuery query = OrgQuery.builder().parentId(parentId).build();
        return listQuery(query).stream().map(TreeNode::new).collect(Collectors.toList());
    }
    
    /**
     * 获取当前节点及其所有子节点的树结构
     * @param id 当前节点标识
     * @return {@link TreeNode}
     */
    public List<TreeNode> deepTreeNodes(String id){
    	Objects.requireNonNull(id, "组织机构id不能为空");
    	Organization org = selectById(id);
    	OrgQuery query = OrgQuery.builder()
    			.code(org.getCode())
    			.neType(OrgType.DEPT.getType()).build();
    	List<Organization> orgList = listQuery(query);
    	List<TreeNode> flatNodes = orgList.stream().map(TreeNode::new).collect(Collectors.toList());
    	List<TreeNode> treeNodes = new ArrayList<>();
    	for(TreeNode node : flatNodes) {
    		if(node.getParentId() == null) {
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
     * 检测当前层级下是否存在相同名称的节点
     * @param org 部门/单位节点对象
     * @return 是否存在同名
     */
    public boolean isExistsName(Organization org) {
        LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(org.getId())) {
        	wrapper.ne(Organization::getId, org.getId());
        }
        wrapper.eq(Organization::getName, org.getName()).eq(Organization::getParentId, org.getParentId());
        return organizationMapper.selectCount(wrapper) == 1;
    }

    /**
     * 拖动排序
     * @param dropSort 拖动参数
     * @return 影响行数
     */
    public int dropSort(DropSort dropSort) {
        return organizationMapper.dropSort(dropSort);
    }
}
