package com.github.wephotos.webwork.core.controller;


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

import com.github.wephotos.webwork.core.entity.UserNodeType;
import com.github.wephotos.webwork.core.entity.dto.UserDto;
import com.github.wephotos.webwork.core.entity.query.UserQuery;
import com.github.wephotos.webwork.core.entity.vo.TreeNode;
import com.github.wephotos.webwork.core.entity.vo.UserVo;
import com.github.wephotos.webwork.core.service.UserService;
import com.github.wephotos.webwork.core.utils.ValidationUtil;
import com.github.wephotos.webwork.error.Errors;
import com.github.wephotos.webwork.http.Page;
import com.github.wephotos.webwork.http.Pageable;
import com.github.wephotos.webwork.http.RestObject;
import com.github.wephotos.webwork.logging.LoggerFactory;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.security.storage.SessionUserStorage;

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
     * @return {@link RestObject}
     */
    @PostMapping("/add")
    public RestObject add(@RequestBody UserDto user) {
    	UserQuery query = UserQuery.builder()
    			.account(user.getAccount())
    			.email(user.getEmail())
    			.phone(user.getPhone()).build();
        ValidationUtil.isTrue(Errors.USER_NAME_EXIST, userService.checkUniqueProperty(query));
        ValidationUtil.isTrue(Errors.USER_PHONE_EXIST, userService.checkUniqueProperty(query));
        ValidationUtil.isTrue(Errors.USER_MAIL_EXIST, userService.checkUniqueProperty(query));
        String id = userService.create(user);
        log.info("添加用户");
        return RestObject.builder().data(id).build();
    }

    /**
     * 更新用户
     * @param user 用户数据
     * @return
     */
    @PostMapping("/update")
    public RestObject update(@RequestBody UserDto user) {
    	UserQuery query = UserQuery.builder()
    			.id(user.getId())
    			.phone(user.getPhone())
    			.email(user.getEmail())
    			.account(user.getAccount()).build();
        ValidationUtil.isTrue(Errors.USER_PHONE_EXIST, userService.checkUniqueProperty(query));
        ValidationUtil.isTrue(Errors.USER_MAIL_EXIST, userService.checkUniqueProperty(query));
        boolean ret = userService.update(user);
        log.info("更新用户");
        return RestObject.builder().data(ret).build();
    }
    
    /**
     * 删除用户
     * @param id 用户ID
     * @return {@link RestObject}
     */
    @GetMapping("/delete/{id}")
    public RestObject delete(@PathVariable("id") String id) {
    	boolean ret = userService.deleteById(id);
    	return RestObject.builder().data(ret).build();
    }

    /**
     * 根据用户编号获取用户
     *
     * @param id 用户编号
     * @return RestObject
     */
    @GetMapping("/get/{id}")
    public RestObject get(@PathVariable("id") String id) {
        UserVo user = userService.findById(id);
        return RestObject.builder().data(user).build();
    }

	/**
     * 置顶用户
     *
     * @param userId 用户id
     * @param deptId 部门ID
     * @return {@link RestObject}
     */
    @GetMapping("/top")
    public RestObject top(String userId, String deptId) {
        boolean ret = userService.top(userId, deptId);
        return RestObject.builder().data(ret).build();
    }

    /**
     * 检测用户不重复属性是否存在
     * @param query 手机号，邮箱，账号等不重复属性
     * @return {@link RestObject}
     */
    @GetMapping("/check-unique-property")
    public RestObject checkUniqueProperty(UserQuery query) {
        boolean bool = userService.checkUniqueProperty(query);
        return RestObject.builder().data(bool).build();
    }
    

    /**
     * 人员分页查询
     * @param pageable 分页参数
     * @return 分页数据 {@link Page} {@link UserVo}
     */
    @PostMapping("/page")
    public RestObject page(@RequestBody Pageable<UserQuery> pageable) {
    	Page<UserVo> page = userService.page(pageable);
    	return RestObject.builder().data(page).build();
    }
    
    /**
     * 获取人员树节点接口
     * @param parentId 父ID
     * @param session 会话
     * @return {@link RestObject} {@link TreeNode} {@link UserNodeType}
     */
    @GetMapping("/list-tree-nodes")
    public RestObject listTreeNodes(String parentId, HttpSession session) {
    	User user = SessionUserStorage.get(session);
    	List<TreeNode> nodes = userService.listTreeNodes(parentId, user);
    	return RestObject.builder().data(nodes).build();
    }
}
