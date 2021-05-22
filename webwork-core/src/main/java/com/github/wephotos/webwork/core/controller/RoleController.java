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

import com.github.wephotos.webwork.core.entity.Role;
import com.github.wephotos.webwork.core.entity.query.RoleQuery;
import com.github.wephotos.webwork.core.entity.vo.TreeNode;
import com.github.wephotos.webwork.core.service.RoleService;
import com.github.wephotos.webwork.core.utils.ValidationUtil;
import com.github.wephotos.webwork.error.Errors;
import com.github.wephotos.webwork.http.Page;
import com.github.wephotos.webwork.http.Pageable;
import com.github.wephotos.webwork.http.RestObject;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.security.storage.SessionUserStorage;

/**
 * 角色
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/role")
public class RoleController {
	
    @Resource
    private RoleService roleService;

    /**
     * 根据id获取角色信息
     *
     * @param id 角色id
     * @return {@link RestObject}
     */
    @GetMapping("/get/{id}")
    public RestObject get(@PathVariable("id") String id) {
        Role role = roleService.get(id);
        return RestObject.builder().data(role).build();
    }
    
    /**
     * 根据ID删除角色
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public RestObject delete(@PathVariable("id") String id) {
    	boolean ret = roleService.deleteById(id);
    	return RestObject.builder().data(ret).build();
    }
    
    /**
     * 新增角色
     *
     * @param role 角色对象
     * @return {@link RestObject}
     */
    @PostMapping("/add")
    public RestObject add(@RequestBody Role role) {
        ValidationUtil.isFalse(Errors.ROLE_NAME_EXIST, roleService.isExistsName(role));
        String result = roleService.add(role);
        return RestObject.builder().data(result).build();
    }

    /**
     * 修改角色
     *
     * @param role 角色对象
     * @return {@link RestObject}
     */
    @PostMapping("/update")
    public RestObject update(@RequestBody Role role) {
        ValidationUtil.isFalse(Errors.ROLE_NAME_EXIST, roleService.isExistsName(role));
        boolean result = roleService.update(role);
        return RestObject.builder().data(result).build();
    }
    
    /**
     * 角色分页查询接口
     * @param pageable 分页参数
     * @return 分页数据 {@link Page} {@link Role}
     */
    @PostMapping("/page")
    public RestObject page(@RequestBody Pageable<RoleQuery> pageable) {
    	Page<Role> page = roleService.page(pageable);
    	return RestObject.builder().data(page).build();
    }

    /**
     * 获取角色树节点数据
     * @param parentId 父 节点
     * @param session 会话对象
     * @return {@link TreeNode}
     */
    @GetMapping("/list-nodes")
    public RestObject listNodes(String parentId, HttpSession session) {
    	User user = SessionUserStorage.get(session);
    	List<TreeNode> nodes = roleService.listNodes(parentId, user);
    	return RestObject.builder().data(nodes).build();
    }
}
