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
import com.github.wephotos.webwork.schema.utils.Results;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.security.utils.SecurityUtils;
import com.github.wephotos.webwork.user.entity.Resource;
import com.github.wephotos.webwork.user.entity.po.NodeQueryPO;
import com.github.wephotos.webwork.user.entity.po.ResourceQueryPO;
import com.github.wephotos.webwork.user.entity.vo.NodeVO;
import com.github.wephotos.webwork.user.entity.vo.ResoVO;
import com.github.wephotos.webwork.user.service.ResourceService;

/**
 * 资源管理接口
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
        return Results.newSuccessfullyResult(resource);
    }
    
    /**
     * 新增资源
     *
     * @param resource resource
     * @return {@link Result}
     */
    @PostMapping("/add")
    public Result<ResoVO> add(@RequestBody Resource resource, HttpSession session) {
    	SecurityUser user = SecurityUtils.getSecurityUser(session);
    	resource.setCreateUser(user.getName());
    	resource.setUpdateUser(user.getName());
        ResoVO data = resourceService.add(resource);
        return Results.newSuccessfullyResult(data);
    }

    /**
     * 修改资源
     *
     * @param resource resource
     * @return {@link Result}
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody Resource resource, HttpSession session) {
    	SecurityUser user = SecurityUtils.getSecurityUser(session);
    	resource.setUpdateUser(user.getName());
        boolean ret = resourceService.update(resource);
        return Results.newSuccessfullyResult(ret);
    }
    
    /**
     * 删除权限
     * @param id 唯一标识
     * @return {@link Result}
     */
    @GetMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable("id") Integer id) {
    	boolean ret = resourceService.deleteById(id);
    	return Results.newSuccessfullyResult(ret);
    }
    
    /**
     * 资源分页查询
     * @param pageable 分页参数
     * @return 分页数据 {@link Page} {@link Resource}
     */
    @PostMapping("/page")
    public Result<Page<Resource>> page(@RequestBody Pageable<ResourceQueryPO> pageable) {
    	Page<Resource> page = resourceService.page(pageable);
    	return Results.newSuccessfullyResult(page);
    }
    
    /**
     * 获取下级资源节点
     * @param query 父 节点
     * @param session 会话对象
     * @return {@link NodeVO}
     */
    @PostMapping("/list-nodes")
    public Result<List<NodeVO>> listNodes(@RequestBody NodeQueryPO query, HttpSession session) {
    	SecurityUser user = SecurityUtils.getSecurityUser(session);
    	query.setUserGroupId(user.getGroupId());
    	List<NodeVO> nodes = resourceService.listNodes(query);
    	return Results.newSuccessfullyResult(nodes);
    }
    
    /**
     * 获取资源树结构全部树节点
     * @param query 父节点ID
     * @return {@link NodeVO}
     */
    @PostMapping("/load-all-nodes")
    public Result<List<NodeVO>> loadAllNodes(@RequestBody NodeQueryPO query) {
    	List<NodeVO> nodes = resourceService.loadAllNodes(query);
    	return Results.newSuccessfullyResult(nodes);
    }
    
    /**
     * 获取角色下配置的资源
     * @param roleId 角色ID
     * @return {@link Result} {@link Resource}
     */
    @GetMapping("/list-by-role")
    public Result<List<Resource>> listByRoleId(Integer roleId) {
    	List<Resource> resources = resourceService.listByRoleId(roleId);
    	return Results.newSuccessfullyResult(resources);
    }

}
