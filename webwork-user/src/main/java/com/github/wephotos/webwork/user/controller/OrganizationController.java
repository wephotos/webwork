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
import com.github.wephotos.webwork.schema.entity.Results;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.user.api.entity.po.DropSortPo;
import com.github.wephotos.webwork.user.api.entity.ro.TreeNodeRo;
import com.github.wephotos.webwork.user.entity.Organization;
import com.github.wephotos.webwork.user.service.OrganizationService;
import com.github.wephotos.webwork.user.utils.SessionUserUtils;

/**
 * 组织
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController {
	
    @Resource
    private OrganizationService organizationService;

    /**
     * 保存节点
     * @param data 节点数据
     * @return 主键
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody Organization data) {
    	Integer id = organizationService.add(data);
        return Results.newResult(id);
    }

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody Organization organization) {
        boolean ret = organizationService.update(organization);
        return Results.newResult(ret);
    }

    @GetMapping("/delete/{id}")
    public Result<Boolean> disable(@PathVariable Integer id) {
        boolean ret = organizationService.delete(id);
        return Results.newResult(ret);
    }

    /**
     * 根据id获取组织
     *
     * @param id id
     * @return RestObject
     */
    @GetMapping("/get/{id}")
    public Result<Organization> get(@PathVariable Integer id) {
        Organization org = organizationService.selectById(id);
        return Results.newResult(org);
    }

    /**
     * 查询组织下组织或部门
     *
     * @param parentId 父节点ID
     * @return {@link Result}
     */
    @GetMapping("/children")
    public Result<List<TreeNodeRo>> children(Integer parentId, HttpSession session) {
        SecurityUser user = SessionUserUtils.getUser(session);
        List<TreeNodeRo> data = organizationService.children(parentId, user);
        return Results.newResult(data);
    }
    
    /**
     * 获取当前用户所在组织节点树 - 包含除部门外的所有节点
     * @param session 会话
     * @return {@link TreeNodeRo}
     */
    @GetMapping("/deep-tree-nodes")
    public Result<List<TreeNodeRo>> deepTreeNodes(HttpSession session) {
    	SecurityUser user = SessionUserUtils.getUser(session);
    	List<TreeNodeRo> nodes = organizationService.deepTreeNodes(user.getGroupId());
    	return Results.newResult(nodes);
    }

    @PostMapping("/drop-sort")
    public Result<Void> dropSort(DropSortPo dropSort) {
        organizationService.dropSort(dropSort);
        return Results.newSuccessfullyResult();
    }

    /**
     * 检测名称是否存在，需要传入父节点ID和当前节点名称
     * @param org 父ID和节点名称参数封装
     * @return 是否存在
     */
    @GetMapping("/check-exists-name")
    public Result<Boolean> checkExistsName(Organization org) {
        boolean ret = organizationService.isExistsName(org);
        return Results.newResult(ret);
    }
}
