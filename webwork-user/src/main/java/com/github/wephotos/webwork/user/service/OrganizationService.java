package com.github.wephotos.webwork.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.wephotos.webwork.schema.entity.EntityState;
import com.github.wephotos.webwork.user.entity.Organization;
import com.github.wephotos.webwork.user.entity.UserOrg;
import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;
import com.github.wephotos.webwork.user.entity.po.DropSortPO;
import com.github.wephotos.webwork.user.entity.po.OrganizationQueryPO;
import com.github.wephotos.webwork.user.entity.vo.NodeVO;
import com.github.wephotos.webwork.user.entity.vo.SimpleNodeVO;
import com.github.wephotos.webwork.user.mapper.OrganizationMapper;
import com.github.wephotos.webwork.user.utils.NodeConverter;
import com.github.wephotos.webwork.utils.StringUtils;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class OrganizationService {
	
	@Resource
	private UserOrgService userOrgService;
	
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
        // 生成层级代码
        String maxCode = organizationMapper.getMaxCode(parentId);
        Organization parent = organizationMapper.selectById(parentId);
        data.setCode(parent.getCode().concat(maxCode));
        // 设置排序号
        int maxSort = organizationMapper.getMaxSort(parentId);
        data.setSort(maxSort + 1);
        Date createTime = WebworkUtils.nowTime();
        data.setCreateTime(createTime);
        data.setUpdateTime(createTime);
        organizationMapper.insert(data);
        return data.getId();
    }

    /**
     * 更新组织机构信息
     * @param organ
     * @return
     */
    public boolean update(Organization organ) {
    	Objects.requireNonNull(organ.getId(), "机构ID不能为空");
    	organ.setUpdateTime(WebworkUtils.nowTime());
        return SqlHelper.retBool(organizationMapper.updateById(organ));
    }

    /**
     * 删除 --逻辑删除
     * @param id 节点ID
     * @return 是否成功
     */
    public boolean delete(int id) {
        Organization org = new Organization();
        org.setId(id);
        org.setStatus(EntityState.DELETED.getCode());
        return SqlHelper.retBool(organizationMapper.updateById(org));
    }
    
    /**
     * 查询组织架构列表
     * @param query 查询条件
     * @return 组织架构列表
     */
    public List<Organization> listQuery(OrganizationQueryPO query){
    	LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
    	wrapper.select(Organization::getId, Organization::getName,
                Organization::getCode, Organization::getStatus, Organization::getSort,
                Organization::getType, Organization::getParentId, Organization::getParentType);
    	wrapper.gt(Organization::getStatus, EntityState.DELETED.getCode());
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
    	// 正序排列
    	wrapper.orderByAsc(Organization::getSort);
    	return organizationMapper.selectList(wrapper);
    }
    
    /**
     * 获取部门所在单位
     * @param deptId 部门ID
     * @return 单位信息
     */
    public Organization findDeptGroup(Integer deptId) {
    	Organization dept = selectById(deptId);
    	Organization node = selectById(dept.getParentId());
    	while(node.getType() != NodeTypeEnum.GROUP.getCode()) {
    		node = selectById(node.getParentId());
    	}
    	return node;
    }
    
    /**
     * 加载子节点，如果父节点ID为空，返回当前用户单位节点
     * @param parentId 单位ID
     * @param userGroupId 用户单位ID
     * @return
     */
    public List<NodeVO> children(Integer parentId, Integer userGroupId) {
    	if(parentId == null) {
    		Organization root = selectById(userGroupId);
            return Arrays.asList(NodeConverter.from(root));
    	}else {
    		return children(parentId);
    	}
    }

    /**
     * 查询下级组织机构
     *
     * @param parentId 父ID
     * @return {@link Organization}
     */
    public List<NodeVO> children(Integer parentId) {
    	// 父节点为空，需要返回根节点
        OrganizationQueryPO query = OrganizationQueryPO.builder().parentId(parentId).build();
        return listQuery(query).stream().map(NodeConverter::from).collect(Collectors.toList());
    }
    
    /**
     * 获取当前单位的所有子单位树结构
     * @param parentId 单位ID
     * @return {@link NodeVO}
     */
    public List<NodeVO> loadGroupNodes(Integer parentId) {
    	Objects.requireNonNull(parentId, "组织机构ID不能为空");
    	Organization org = selectById(parentId);
    	OrganizationQueryPO query = OrganizationQueryPO.builder()
    			.code(org.getCode())
    			.neType(NodeTypeEnum.DEPT.getCode()).build();
    	List<Organization> orgList = listQuery(query);
    	List<NodeVO> flatNodes = orgList.stream().map(NodeConverter::from).collect(Collectors.toList());
    	List<NodeVO> treeNodes = new ArrayList<>();
    	for(NodeVO node : flatNodes) {
    		if(node.getParentId() == null) {
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
    public boolean dropSort(DropSortPO dropSort) {
        return organizationMapper.dropSort(dropSort) > 0;
    }
    
    /**
     * 获取用户所有的部门/单位节点（包含上级单位）
     * @param userId 用户ID
     * @return 组织节点信息
     */
    public List<SimpleNodeVO> listAllNodesByUserId(Integer userId){
    	List<SimpleNodeVO> simpleNodeVOs = new ArrayList<>();
    	List<UserOrg> orgs = userOrgService.listOrgByUserId(userId);
    	orgs.forEach(record -> {
    		// 部门
    		SimpleNodeVO dept = new SimpleNodeVO();
    		dept.setId(record.getDeptId());
    		dept.setType(NodeTypeEnum.DEPT.getCode());
    		simpleNodeVOs.add(dept);
    		// 单位
    		SimpleNodeVO group = new SimpleNodeVO();
    		group.setId(record.getOrgId());
    		group.setType(NodeTypeEnum.GROUP.getCode());
    		simpleNodeVOs.add(group);
    		// 上级单位
    		Organization orga = selectById(record.getOrgId());
    		while(orga.getParentId() != null) {
    			orga = selectById(orga.getParentId());
    			group = new SimpleNodeVO();
        		group.setId(orga.getId());
        		group.setType(NodeTypeEnum.GROUP.getCode());
        		simpleNodeVOs.add(group);
    		}
    	});
    	return simpleNodeVOs;
    }
}
