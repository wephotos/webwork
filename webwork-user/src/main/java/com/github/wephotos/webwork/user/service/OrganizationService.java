package com.github.wephotos.webwork.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.wephotos.webwork.schema.entity.EntityState;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.user.api.entity.po.DropSortPo;
import com.github.wephotos.webwork.user.api.entity.po.OrganizationQueryPo;
import com.github.wephotos.webwork.user.api.entity.ro.NodeRo;
import com.github.wephotos.webwork.user.entity.Organization;
import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;
import com.github.wephotos.webwork.user.mapper.OrganizationMapper;
import com.github.wephotos.webwork.user.utils.TreeNodeConverter;
import com.github.wephotos.webwork.utils.StringUtils;

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
    public Organization selectById(Integer id) {
        return organizationMapper.selectById(id);
    }
    
    /**
     * 保存节点
     * @param data 节点数据
     * @return 主键
     */
    public synchronized int add(Organization data) {
        Integer parentId = data.getParentId();
        String maxCode = organizationMapper.getMaxCode(parentId);
        if(parentId == null) {
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
    public boolean delete(int id) {
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
    public List<Organization> listQuery(OrganizationQueryPo query){
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
    	if(query.getParentId() != null) {
    		wrapper.eq(Organization::getParentId, query.getParentId());
    	}
    	return organizationMapper.selectList(wrapper);
    }
    
    /**
     * 获取部门所在单位
     * @param deptId 部门ID
     * @return 单位对象
     */
    public Organization findDepartGroup(Integer deptId) {
    	Organization dept = selectById(deptId);
    	Organization node = selectById(dept.getParentId());
    	while(node.getType() != NodeTypeEnum.GROUP.getCode()) {
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
    public List<NodeRo> children(Integer parentId, SecurityUser user) {
        // 父节点为空，返回当前单位节点
        if (parentId == null) {
            Organization root = selectById(user.getGroupId());
            return Arrays.asList(TreeNodeConverter.from(root));
        }
        OrganizationQueryPo query = OrganizationQueryPo.builder().parentId(parentId).build();
        return listQuery(query).stream().map(TreeNodeConverter::from).collect(Collectors.toList());
    }
    
    /**
     * 获取当前节点及其所有子节点的树结构
     * @param id 当前节点标识
     * @return {@link NodeRo}
     */
    public List<NodeRo> deepTreeNodes(Integer id){
    	Objects.requireNonNull(id, "组织机构id不能为空");
    	Organization org = selectById(id);
    	OrganizationQueryPo query = OrganizationQueryPo.builder()
    			.code(org.getCode())
    			.neType(NodeTypeEnum.DEPT.getCode()).build();
    	List<Organization> orgList = listQuery(query);
    	List<NodeRo> flatNodes = orgList.stream().map(TreeNodeConverter::from).collect(Collectors.toList());
    	List<NodeRo> treeNodes = new ArrayList<>();
    	for(NodeRo node : flatNodes) {
    		if(node.getParentId() == null) {
    			treeNodes.add(node);
    		}
    		for(NodeRo child : flatNodes) {
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
        if(org.getId() != null) {
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
    public int dropSort(DropSortPo dropSort) {
        return organizationMapper.dropSort(dropSort);
    }
}
