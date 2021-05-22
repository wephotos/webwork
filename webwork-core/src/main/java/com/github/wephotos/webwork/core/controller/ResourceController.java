package com.github.wephotos.webwork.core.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.core.entity.Resource;
import com.github.wephotos.webwork.core.entity.query.ResQuery;
import com.github.wephotos.webwork.core.entity.vo.TreeNode;
import com.github.wephotos.webwork.core.service.ResourceService;
import com.github.wephotos.webwork.core.utils.ValidationUtil;
import com.github.wephotos.webwork.error.Errors;
import com.github.wephotos.webwork.http.Page;
import com.github.wephotos.webwork.http.Pageable;
import com.github.wephotos.webwork.http.RestObject;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.security.storage.SessionUserStorage;

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
     * @return {@link RestObject}
     */
    @GetMapping("/get/{id}")
    public RestObject get(@PathVariable("id") String id) {
        Resource resource = resourceService.get(id);
        return RestObject.builder().data(resource).build();
    }
    
    /**
     * 新增资源
     *
     * @param resource resource
     * @return {@link RestObject}
     */
    @PostMapping("/add")
    public RestObject add(@RequestBody Resource resource) {
        ValidationUtil.isFalse(Errors.RESOURCE_CODE_EXIST, resourceService.isExistsPermission(resource));
        String id = resourceService.add(resource);
        return RestObject.builder().data(id).build();
    }

    /**
     * 修改资源
     *
     * @param resource resource
     * @return {@link RestObject}
     */
    @PostMapping("/update")
    public RestObject update(@RequestBody Resource resource) {
        ValidationUtil.isFalse(Errors.RESOURCE_CODE_EXIST, resourceService.isExistsPermission(resource));
        boolean result = resourceService.update(resource);
        return RestObject.builder().data(result).build();
    }
    
    /**
     * 删除权限
     * @param id 唯一标识
     * @return {@link RestObject}
     */
    @GetMapping("/delete/{id}")
    public RestObject delete(@PathVariable("id") String id) {
    	boolean ret = resourceService.deleteById(id);
    	return RestObject.builder().data(ret).build();
    }
    
    /**
     * 资源分页查询
     * @param pageable 分页参数
     * @return 分页数据 {@link Page} {@link Resource}
     */
    @PostMapping("/page")
    public RestObject page(@RequestBody Pageable<ResQuery> pageable) {
    	Page<Resource> page = resourceService.page(pageable);
    	return RestObject.builder().data(page).build();
    }
    
    /**
     * 获取下级资源节点
     * @param parentId 父 节点
     * @param session 会话对象
     * @return {@link TreeNode}
     */
    @GetMapping("/list-nodes")
    public RestObject listNodes(String parentId, HttpSession session) {
    	User user = SessionUserStorage.get(session);
    	List<TreeNode> nodes = resourceService.listNodes(parentId, user);
    	return RestObject.builder().data(nodes).build();
    }

}
