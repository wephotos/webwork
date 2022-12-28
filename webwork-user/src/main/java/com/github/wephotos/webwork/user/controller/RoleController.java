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

import com.github.wephotos.webwork.schema.entity.Page;
import com.github.wephotos.webwork.schema.entity.Pageable;
import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.utils.Results;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.security.utils.SecurityUtils;
import com.github.wephotos.webwork.user.entity.Role;
import com.github.wephotos.webwork.user.entity.po.NodeQueryPO;
import com.github.wephotos.webwork.user.entity.po.RoleQueryPO;
import com.github.wephotos.webwork.user.entity.vo.NodeVO;
import com.github.wephotos.webwork.user.entity.vo.RoleVO;
import com.github.wephotos.webwork.user.service.RoleService;

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
     * @return {@link Result}
     */
    @GetMapping("/get/{id}")
    public Result<Role> get(@PathVariable("id") String id) {
        Role role = roleService.selectById(id);
        return Results.newSuccessfullyResult(role);
    }
    
    /**
     * 根据ID删除角色
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable("id") Integer id) {
    	boolean ret = roleService.deleteById(id);
    	return Results.newSuccessfullyResult(ret);
    }
    
    /**
     * 新增角色
     *
     * @param role 角色对象
     * @return {@link Result}
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody Role role, HttpSession session) {
    	SecurityUser user = SecurityUtils.getSecurityUser(session);
    	role.setCreateUser(user.getName());
    	role.setUpdateUser(user.getName());
        Integer id = roleService.add(role);
        return Results.newSuccessfullyResult(id);
    }

    /**
     * 修改角色
     *
     * @param role 角色对象
     * @return {@link Result}
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody Role role, HttpSession session) {
    	SecurityUser user = SecurityUtils.getSecurityUser(session);
    	role.setUpdateUser(user.getName());
        boolean ret = roleService.update(role);
        return Results.newSuccessfullyResult(ret);
    }
    
    /**
     * 角色分页查询接口
     * @param pageable 分页参数
     * @return 分页数据 {@link Page} {@link Role}
     */
    @PostMapping("/page")
    public Result<Page<RoleVO>> page(@RequestBody Pageable<RoleQueryPO> pageable) {
    	Page<RoleVO> page = roleService.page(pageable);
    	return Results.newSuccessfullyResult(page);
    }

    /**
     * 获取角色树节点数据
     * @param query 父 节点
     * @param session 会话对象
     * @return {@link NodeVO}
     */
    @PostMapping("/list-nodes")
    public Result<List<NodeVO>> listNodes(@RequestBody NodeQueryPO query, HttpSession session) {
    	SecurityUser user = SecurityUtils.getSecurityUser(session);
    	query.setUserGroupId(user.getGroupId());
    	List<NodeVO> nodes = roleService.listNodes(query);
    	return Results.newSuccessfullyResult(nodes);
    }
}
