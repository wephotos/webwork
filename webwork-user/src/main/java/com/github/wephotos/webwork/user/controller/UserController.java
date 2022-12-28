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
import com.github.wephotos.webwork.user.entity.po.UserPO;
import com.github.wephotos.webwork.user.entity.po.UserQueryPO;
import com.github.wephotos.webwork.user.entity.vo.NodeVO;
import com.github.wephotos.webwork.user.entity.vo.UserVO;
import com.github.wephotos.webwork.user.service.UserService;

/**
 * 用户管理
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Resource
    private UserService userService;

    /**
     * 新增用户
     * @param user 用户对象
     * @return {@link Result}
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody UserPO user, HttpSession session) {
    	SecurityUser sessionUser = SecurityUtils.getSecurityUser(session);
    	user.setCreateUser(sessionUser.getName());
        Integer id = userService.create(user);
        return Results.newSuccessfullyResult(id);
    }

    /**
     * 更新用户
     * @param user 用户数据
     * @return
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody UserPO user, HttpSession session) {
    	SecurityUser sessionUser = SecurityUtils.getSecurityUser(session);
    	user.setCreateUser(sessionUser.getName());
        boolean ret = userService.update(user);
        return Results.newSuccessfullyResult(ret);
    }
    
    /**
     * 删除用户
     * @param id 用户ID
     * @return {@link Result}
     */
    @GetMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable("id") Integer id) {
    	boolean ret = userService.deleteById(id);
    	return Results.newSuccessfullyResult(ret);
    }

    /**
     * 根据用户编号获取用户
     *
     * @param id 用户编号
     * @return RestObject
     */
    @GetMapping("/get/{id}")
    public Result<UserVO> get(@PathVariable("id") Integer id) {
        UserVO user = userService.findUserDetailsById(id);
        return Results.newSuccessfullyResult(user);
    }

	/**
     * 置顶用户
     *
     * @param userId 用户id
     * @param deptId 部门ID
     * @return {@link Result}
     */
    @GetMapping("/top")
    public Result<Boolean> top(String userId, String deptId) {
        boolean ret = userService.top(userId, deptId);
        return Results.newSuccessfullyResult(ret);
    }

    /**
     * 检测用户不重复属性是否存在
     * @param query 手机号，邮箱，账号等不重复属性
     * @return {@link Result}
     */
    @GetMapping("/check-unique-property")
    public Result<Boolean> checkUniqueProperty(UserQueryPO query) {
        boolean ret = userService.checkUniqueProperty(query);
        return Results.newSuccessfullyResult(ret);
    }
    

    /**
     * 人员分页查询
     * @param pageable 分页参数
     * @return 分页数据 {@link Page} {@link UserVO}
     */
    @PostMapping("/page")
    public Result<Page<UserVO>> page(@RequestBody Pageable<UserQueryPO> pageable) {
    	Page<UserVO> page = userService.page(pageable);
    	return Results.newSuccessfullyResult(page);
    }
    
    /**
     * 获取人员树节点接口
     * @param parentId 父节点ID
     * @param session 当前会话
     * @return {@link Result} {@link NodeVO}
     */
    @GetMapping("/list-tree-nodes")
    public Result<List<NodeVO>> listTreeNodes(Integer parentId, HttpSession session) {
    	// 默认使用当前用户单位
    	if(parentId == null) {
    		parentId = SecurityUtils.getSecurityUser(session).getGroupId();
    	}
    	List<NodeVO> nodes = userService.listUserNodes(parentId);
    	return Results.newSuccessfullyResult(nodes);
    }
}
