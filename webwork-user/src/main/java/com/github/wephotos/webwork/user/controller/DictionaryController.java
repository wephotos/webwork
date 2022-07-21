package com.github.wephotos.webwork.user.controller;

import java.util.List;

import javax.annotation.Resource;

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
import com.github.wephotos.webwork.user.api.entity.ro.TreeNodeRo;
import com.github.wephotos.webwork.user.entity.Dictionary;
import com.github.wephotos.webwork.user.service.DictionaryService;

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
	 * @return {@link Result} 返回数据包含字典数据对象
	 */
	@GetMapping("/get/{id}")
	public Result<Dictionary> get(@PathVariable("id") Integer id) {
		Dictionary data = dictionaryService.selectById(id);
		return Results.newResult(data);
	}
	
	/**
	 * 删除数据字典
	 * @param id 主键
	 * @return {@link Result} 成功返回包含数据为true
	 */
	@GetMapping("/delete/{id}")
	public Result<Boolean> delete(@PathVariable("id") Integer id) {
		boolean ret = dictionaryService.delete(id);
		return Results.newResult(ret);
	}
	
	/**
	 * 新增配置信息
	 * @param config 配置信息
	 * @return {@link Result} 包含新增配置主键
	 */
	@PostMapping("/add")
	public Result<Integer> add(@RequestBody Dictionary dictionary) {
		Integer id = dictionaryService.add(dictionary);
		return Results.newResult(id);
	}
	
	/**
	 * 更新配置信息
	 * @param config 配置信息
	 * @return {@link Result} 更新成功返回数据为true
	 */
	@PostMapping("/update")
	public Result<Boolean> update(@RequestBody Dictionary dictionary) {
		boolean ret = dictionaryService.update(dictionary);
		return Results.newResult(ret);
	}
	
	/**
	 * 分页查询配置信息
	 * @param pageable 分页必要条件
	 * @return 分页数据 {@link Page}
	 */
	@PostMapping("/page")
	public Result<Page<Dictionary>> page(@RequestBody Pageable<Dictionary> pageable) {
		Page<Dictionary> page = dictionaryService.page(pageable);
		return Results.newResult(page);
	}
	
	/**
     * 获取下级资源节点
     * @param parentId 父 节点
     * @param session 会话对象
     * @return {@link TreeNodeRo}
     */
    @GetMapping("/list-nodes")
    public Result<List<TreeNodeRo>> listNodes(String parentId) {
    	List<TreeNodeRo> nodes = dictionaryService.listNodes(parentId);
    	return Results.newResult(nodes);
    }
}
