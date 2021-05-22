package com.github.wephotos.webwork.core.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.core.entity.Organization;
import com.github.wephotos.webwork.core.entity.dto.DropSort;
import com.github.wephotos.webwork.core.entity.vo.TreeNode;
import com.github.wephotos.webwork.core.service.OrganizationService;
import com.github.wephotos.webwork.http.RestObject;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.security.storage.SessionUserStorage;

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
    public RestObject add(@RequestBody Organization data) {
        String id = organizationService.add(data);
        return RestObject.builder().data(id).build();
    }

    @PostMapping("/update")
    public RestObject update(@RequestBody Organization organization) {
        boolean result = organizationService.update(organization);
        return RestObject.builder().data(result).build();
    }

    @GetMapping("/delete/{id}")
    public RestObject disable(@PathVariable String id) {
        boolean result = organizationService.delete(id);
        return RestObject.builder().data(result).build();
    }

    /**
     * 根据id获取组织
     *
     * @param id id
     * @return RestObject
     */
    @GetMapping("/get/{id}")
    public RestObject get(@PathVariable String id) {
        Organization org = organizationService.selectById(id);
        return RestObject.builder().data(org).build();
    }

    /**
     * 查询组织下组织或部门
     *
     * @param parentId 父节点ID
     * @return {@link RestObject}
     */
    @GetMapping("/children")
    public RestObject children(String parentId, HttpSession session) {
        User user = SessionUserStorage.get(session);
        List<Organization> organizationList = organizationService.children(parentId, user);
        return RestObject.builder().data(organizationList).build();
    }
    
    /**
     * 获取当前用户所在组织节点树 - 包含除部门外的所有节点
     * @param session 会话
     * @return {@link TreeNode}
     */
    @GetMapping("/deep-tree-nodes")
    public RestObject deepTreeNodes(HttpSession session) {
    	User user = SessionUserStorage.get(session);
    	List<TreeNode> nodes = organizationService.deepTreeNodes(user.getGroupId());
    	return RestObject.builder().data(nodes).build();
    }

    @PostMapping("/drop-sort")
    public RestObject dropSort(DropSort dropSort) {
        organizationService.dropSort(dropSort);
        return RestObject.builder().build();
    }

    /**
     * 检测名称是否存在，需要传入父节点ID和当前节点名称
     * @param org 父ID和节点名称参数封装
     * @return 是否存在
     */
    @GetMapping("/check-exists-name")
    public RestObject checkExistsName(Organization org) {
        boolean bool = organizationService.isExistsName(org);
        return RestObject.builder().data(bool).build();
    }
}
