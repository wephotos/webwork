package com.github.wephotos.webwork.user.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.logging.LoggerFactory;
import com.github.wephotos.webwork.schema.entity.Page;
import com.github.wephotos.webwork.schema.entity.Pageable;
import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.entity.Results;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.user.api.entity.po.UserPo;
import com.github.wephotos.webwork.user.api.entity.po.UserQueryPo;
import com.github.wephotos.webwork.user.api.entity.ro.NodeRo;
import com.github.wephotos.webwork.user.api.entity.ro.UserRo;
import com.github.wephotos.webwork.user.entity.enums.UserNodeType;
import com.github.wephotos.webwork.user.service.UserService;
import com.github.wephotos.webwork.user.utils.SessionUserUtils;
import com.github.wephotos.webwork.user.utils.UserStateCode;
import com.github.wephotos.webwork.user.utils.ValidationUtil;

/**
 * 用户管理
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    
    @Resource
    private UserService userService;

    /**
     * 新增用户
     * @param user 用户对象
     * @return {@link Result}
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody UserPo user) {
    	UserQueryPo query = UserQueryPo.builder()
    			.account(user.getAccount())
    			.email(user.getEmail())
    			.phone(user.getPhone()).build();
        ValidationUtil.isTrue(UserStateCode.USER_NAME_EXIST, userService.checkUniqueProperty(query));
        ValidationUtil.isTrue(UserStateCode.USER_PHONE_EXIST, userService.checkUniqueProperty(query));
        ValidationUtil.isTrue(UserStateCode.USER_MAIL_EXIST, userService.checkUniqueProperty(query));
        Integer id = userService.create(user);
        log.info("添加用户");
        return Results.newResult(id);
    }

    /**
     * 更新用户
     * @param user 用户数据
     * @return
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody UserPo user) {
    	UserQueryPo query = UserQueryPo.builder()
    			.id(user.getId())
    			.phone(user.getPhone())
    			.email(user.getEmail())
    			.account(user.getAccount()).build();
        ValidationUtil.isTrue(UserStateCode.USER_PHONE_EXIST, userService.checkUniqueProperty(query));
        ValidationUtil.isTrue(UserStateCode.USER_MAIL_EXIST, userService.checkUniqueProperty(query));
        boolean ret = userService.update(user);
        log.info("更新用户");
        return Results.newResult(ret);
    }
    
    /**
     * 删除用户
     * @param id 用户ID
     * @return {@link Result}
     */
    @GetMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable("id") Integer id) {
    	boolean ret = userService.deleteById(id);
    	return Results.newResult(ret);
    }

    /**
     * 根据用户编号获取用户
     *
     * @param id 用户编号
     * @return RestObject
     */
    @GetMapping("/get/{id}")
    public Result<UserRo> get(@PathVariable("id") Integer id) {
        UserRo user = userService.findUserDetailsById(id);
        return Results.newResult(user);
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
        return Results.newResult(ret);
    }

    /**
     * 检测用户不重复属性是否存在
     * @param query 手机号，邮箱，账号等不重复属性
     * @return {@link Result}
     */
    @GetMapping("/check-unique-property")
    public Result<Boolean> checkUniqueProperty(UserQueryPo query) {
        boolean ret = userService.checkUniqueProperty(query);
        return Results.newResult(ret);
    }
    

    /**
     * 人员分页查询
     * @param pageable 分页参数
     * @return 分页数据 {@link Page} {@link UserRo}
     */
    @PostMapping("/page")
    public Result<Page<UserRo>> page(@RequestBody Pageable<UserQueryPo> pageable) {
    	Page<UserRo> page = userService.page(pageable);
    	return Results.newResult(page);
    }
    
    /**
     * 获取人员树节点接口
     * @param parentId 父ID
     * @param session 会话
     * @return {@link Result} {@link NodeRo} {@link UserNodeType}
     */
    @GetMapping("/list-tree-nodes")
    public Result<List<NodeRo>> listTreeNodes(Integer parentId, HttpSession session) {
    	SecurityUser user = SessionUserUtils.getUser(session);
    	List<NodeRo> nodes = userService.listTreeNodes(parentId, user);
    	return Results.newResult(nodes);
    }
}
