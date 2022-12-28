package com.github.wephotos.webwork.user.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.utils.Results;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.security.utils.SecurityUtils;
import com.github.wephotos.webwork.user.entity.Organization;
import com.github.wephotos.webwork.user.entity.po.DropSortPO;
import com.github.wephotos.webwork.user.entity.vo.NodeVO;
import com.github.wephotos.webwork.user.service.OrganizationService;

/**
 * 组织机构对外接口
 * @author TianQi
 *
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController {
	
    @Resource
    private OrganizationService organizationService;

    /**
     * 新增组织机构
     * @param data 机构数据
     * @param session 会话对象
     * @return 机构ID
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody Organization data, HttpSession session) {
    	SecurityUser user = SecurityUtils.getSecurityUser(session);
    	data.setCreateUser(user.getName());
    	data.setUpdateUser(user.getName());
    	Integer id = organizationService.add(data);
        return Results.newSuccessfullyResult(id);
    }

    /**
     * 更新组织机构
     * @param organ 机构信息
     * @param session 会话对象
     * @return 成功返回 true
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody Organization organ, HttpSession session) {
    	SecurityUser user = SecurityUtils.getSecurityUser(session);
    	organ.setUpdateUser(user.getName());
        boolean ret = organizationService.update(organ);
        return Results.newSuccessfullyResult(ret);
    }

    /**
     * 删除组织机构
     * @param id 机构ID
     * @return 删除成功返回 true
     */
    @GetMapping("/delete/{id}")
    public Result<Boolean> disable(@PathVariable Integer id) {
        boolean ret = organizationService.delete(id);
        return Results.newSuccessfullyResult(ret);
    }

    /**
     * 根据id获取组织
     *
     * @param id 机构ID
     * @return {@link Organization} 组织机构信息
     */
    @GetMapping("/get/{id}")
    public Result<Organization> get(@PathVariable Integer id) {
        Organization org = organizationService.selectById(id);
        return Results.newSuccessfullyResult(org);
    }

    /**
     * 查询组织下组织或部门
     *
     * @param parentId 父节点ID
     * @param session 会话对象
     * @return 子节点集合
     */
    @GetMapping("/children")
    public Result<List<NodeVO>> children(Integer parentId, HttpSession session) {
        SecurityUser user = SecurityUtils.getSecurityUser(session);
        List<NodeVO> data = organizationService.children(parentId, user.getGroupId());
        return Results.newSuccessfullyResult(data);
    }
    
    /**
     * 从当前用户单位开始获取所有子单位树结构
     * @param session 会话
     * @return {@link NodeVO} 单位树节点集合
     */
    @GetMapping("/load-nodes")
    public Result<List<NodeVO>> loadGroupNodes(HttpSession session) {
    	SecurityUser user = SecurityUtils.getSecurityUser(session);
    	List<NodeVO> nodes = organizationService.loadGroupNodes(user.getGroupId());
    	return Results.newSuccessfullyResult(nodes);
    }

    /**
     * 拖动排序接口(同级节点)
     * @param dropSortPO 拖动排序参数
     * @return 成功返回 true
     */
    @PostMapping("/drop-sort")
    public Result<Boolean> dropSort(@RequestBody DropSortPO dropSortPO) {
        boolean bool = organizationService.dropSort(dropSortPO);
        return Results.newSuccessfullyResult(bool);
    }

    /**
     * 检测名称是否存在，需要传入父节点ID和当前节点名称
     * @param org 父ID和节点名称参数封装
     * @return 是否存在
     */
    @GetMapping("/check-exists-name")
    public Result<Boolean> checkExistsName(Organization org) {
        boolean ret = organizationService.isExistsName(org);
        return Results.newSuccessfullyResult(ret);
    }
}
