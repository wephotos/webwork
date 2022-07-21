package com.github.wephotos.webwork.user.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.schema.entity.Page;
import com.github.wephotos.webwork.schema.entity.Pageable;
import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.entity.Results;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.security.utils.SecurityUtils;
import com.github.wephotos.webwork.user.api.entity.po.ResourceQueryPo;
import com.github.wephotos.webwork.user.api.entity.ro.NodeRo;
import com.github.wephotos.webwork.user.entity.Resource;
import com.github.wephotos.webwork.user.service.ResourceService;
import com.github.wephotos.webwork.user.utils.UserStateCode;
import com.github.wephotos.webwork.user.utils.ValidationUtil;

/**
 * 资源菜单
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {
	
    @javax.annotation.Resource
    private ResourceService resourceService;

    /**
     * 根据id获取资源
     *
     * @param id 资源id
     * @return {@link Result}
     */
    @GetMapping("/get/{id}")
    public Result<Resource> get(@PathVariable("id") Integer id) {
        Resource resource = resourceService.selectById(id);
        return Results.newResult(resource);
    }
    
    /**
     * 新增资源
     *
     * @param resource resource
     * @return {@link Result}
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody Resource resource) {
        ValidationUtil.isFalse(UserStateCode.RESOURCE_CODE_EXIST, resourceService.isExistsPermission(resource));
        Integer id = resourceService.add(resource);
        return Results.newResult(id);
    }

    /**
     * 修改资源
     *
     * @param resource resource
     * @return {@link Result}
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody Resource resource) {
        ValidationUtil.isFalse(UserStateCode.RESOURCE_CODE_EXIST, resourceService.isExistsPermission(resource));
        boolean ret = resourceService.update(resource);
        return Results.newResult(ret);
    }
    
    /**
     * 删除权限
     * @param id 唯一标识
     * @return {@link Result}
     */
    @GetMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable("id") Integer id) {
    	boolean ret = resourceService.deleteById(id);
    	return Results.newResult(ret);
    }
    
    /**
     * 资源分页查询
     * @param pageable 分页参数
     * @return 分页数据 {@link Page} {@link Resource}
     */
    @PostMapping("/page")
    public Result<Page<Resource>> page(@RequestBody Pageable<ResourceQueryPo> pageable) {
    	Page<Resource> page = resourceService.page(pageable);
    	return Results.newResult(page);
    }
    
    /**
     * 获取下级资源节点
     * @param parentId 父 节点
     * @param session 会话对象
     * @return {@link NodeRo}
     */
    @GetMapping("/list-nodes")
    public Result<List<NodeRo>> listNodes(Integer parentId, HttpSession session) {
    	SecurityUser user = SecurityUtils.getSecurityUser(session);
    	List<NodeRo> nodes = resourceService.listTreeNodes(parentId, user);
    	return Results.newResult(nodes);
    }
    
    /**
     * 获取资源树结构全部树节点
     * @param parentId 父节点ID
     * @return {@link NodeRo}
     */
    @GetMapping("/deep-tree-nodes")
    public Result<List<NodeRo>> deepTreeNodes(Integer parentId) {
    	List<NodeRo> nodes = resourceService.deepTreeNodes(parentId);
    	return Results.newResult(nodes);
    }
    
    /**
     * 获取角色下配置的资源
     * @param roleId 角色ID
     * @return {@link Result} {@link Resource}
     */
    @GetMapping("/list-by-role")
    public Result<List<Resource>> listByRoleId(Integer roleId) {
    	List<Resource> resources = resourceService.listByRoleId(roleId);
    	return Results.newResult(resources);
    }

}
