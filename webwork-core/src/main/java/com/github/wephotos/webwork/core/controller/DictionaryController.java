package com.github.wephotos.webwork.core.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.core.entity.Config;
import com.github.wephotos.webwork.core.entity.Dictionary;
import com.github.wephotos.webwork.core.entity.vo.TreeNode;
import com.github.wephotos.webwork.core.service.DictionaryService;
import com.github.wephotos.webwork.http.Page;
import com.github.wephotos.webwork.http.Pageable;
import com.github.wephotos.webwork.http.RestObject;

/**
 * 数据字典服务接口
 * @author TQ
 *
 */
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

	@Resource
	private DictionaryService dictionaryService;
	
	/**
	 * 获取字典数据
	 * @param id 主键
	 * @return {@link RestObject} 返回数据包含字典数据对象
	 */
	@GetMapping("/get/{id}")
	public RestObject get(@PathVariable("id") String id) {
		Dictionary data = dictionaryService.get(id);
		return RestObject.builder().data(data).build();
	}
	
	/**
	 * 删除数据字典
	 * @param id 主键
	 * @return {@link RestObject} 成功返回包含数据为true
	 */
	@GetMapping("/delete/{id}")
	public RestObject delete(@PathVariable("id") String id) {
		boolean ret = dictionaryService.delete(id);
		return RestObject.builder().data(ret).build();
	}
	
	/**
	 * 新增配置信息
	 * @param config 配置信息
	 * @return {@link RestObject} 包含新增配置主键
	 */
	@PostMapping("/add")
	public RestObject add(@RequestBody Dictionary dictionary) {
		String id = dictionaryService.add(dictionary);
		return RestObject.builder().data(id).build();
	}
	
	/**
	 * 更新配置信息
	 * @param config 配置信息
	 * @return {@link RestObject} 更新成功返回数据为true
	 */
	@PostMapping("/update")
	public RestObject update(@RequestBody Dictionary dictionary) {
		boolean ret = dictionaryService.update(dictionary);
		return RestObject.builder().data(ret).build();
	}
	
	/**
	 * 分页查询配置信息
	 * @param pageable 分页必要条件
	 * @return 分页数据 {@link Page} {@link Config}
	 */
	@PostMapping("/page")
	public RestObject page(@RequestBody Pageable<Dictionary> pageable) {
		Page<Dictionary> page = dictionaryService.page(pageable);
		return RestObject.builder().data(page).build();
	}
	
	/**
     * 获取下级资源节点
     * @param parentId 父 节点
     * @param session 会话对象
     * @return {@link TreeNode}
     */
    @GetMapping("/list-nodes")
    public RestObject listNodes(String parentId) {
    	List<TreeNode> nodes = dictionaryService.listNodes(parentId);
    	return RestObject.builder().data(nodes).build();
    }
}
