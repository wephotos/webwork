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
import com.github.wephotos.webwork.schema.entity.Results;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.user.api.entity.po.RoleQueryPo;
import com.github.wephotos.webwork.user.api.entity.ro.NodeRo;
import com.github.wephotos.webwork.user.entity.Role;
import com.github.wephotos.webwork.user.service.RoleService;
import com.github.wephotos.webwork.user.utils.SessionUserUtils;
import com.github.wephotos.webwork.user.utils.UserStateCode;
import com.github.wephotos.webwork.user.utils.ValidationUtil;

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
        return Results.newResult(role);
    }
    
    /**
     * 根据ID删除角色
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable("id") Integer id) {
    	boolean ret = roleService.deleteById(id);
    	return Results.newResult(ret);
    }
    
    /**
     * 新增角色
     *
     * @param role 角色对象
     * @return {@link Result}
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody Role role) {
        ValidationUtil.isFalse(UserStateCode.ROLE_NAME_EXIST, roleService.isExistsName(role));
        Integer id = roleService.add(role);
        return Results.newResult(id);
    }

    /**
     * 修改角色
     *
     * @param role 角色对象
     * @return {@link Result}
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody Role role) {
        ValidationUtil.isFalse(UserStateCode.ROLE_NAME_EXIST, roleService.isExistsName(role));
        boolean ret = roleService.update(role);
        return Results.newResult(ret);
    }
    
    /**
     * 角色分页查询接口
     * @param pageable 分页参数
     * @return 分页数据 {@link Page} {@link Role}
     */
    @PostMapping("/page")
    public Result<Page<Role>> page(@RequestBody Pageable<RoleQueryPo> pageable) {
    	Page<Role> page = roleService.page(pageable);
    	return Results.newResult(page);
    }

    /**
     * 获取角色树节点数据
     * @param parentId 父 节点
     * @param session 会话对象
     * @return {@link NodeRo}
     */
    @GetMapping("/list-nodes")
    public Result<List<NodeRo>> listNodes(Integer parentId, HttpSession session) {
    	SecurityUser user = SessionUserUtils.getUser(session);
    	List<NodeRo> nodes = roleService.listNodes(parentId, user);
    	return Results.newResult(nodes);
    }
}
