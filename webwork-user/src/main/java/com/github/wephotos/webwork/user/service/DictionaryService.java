package com.github.wephotos.webwork.user.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.wephotos.webwork.schema.entity.EntityState;
import com.github.wephotos.webwork.schema.entity.Page;
import com.github.wephotos.webwork.schema.entity.Pageable;
import com.github.wephotos.webwork.user.api.entity.ro.NodeRo;
import com.github.wephotos.webwork.user.entity.Dictionary;
import com.github.wephotos.webwork.user.mapper.DictionaryMapper;
import com.github.wephotos.webwork.utils.StringUtils;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * 数据字典服务
 * @author TQ
 *
 */
@Service
public class DictionaryService {

	@Resource
	private DictionaryMapper dictionaryMapper;
	
	/**
	 * 根据主键获取字典数据
	 * @param id 主键
	 * @return 字典数据
	 */
	public Dictionary selectById(int id) {
		return dictionaryMapper.selectById(id);
	}

	/**
	 * 删除字典数据 -逻辑删除
	 * @param id 数据主键
	 * @return 成功返回true
	 */
	public boolean delete(int id) {
		Dictionary entity = new Dictionary();
		entity.setId(id);
		entity.setStatus(EntityState.DELETED.getValue());
		return dictionaryMapper.updateById(entity) == 1;
	}
	
	/**
	 * 添加数据
	 * @param dictionary 字典数据
	 * @return 数据主键
	 */
	public int add(Dictionary dictionary) {
		dictionary.setCreateTime(WebworkUtils.timestamp());
		dictionary.setStatus(EntityState.ENABLED.getValue());
		dictionary.setSort(dictionaryMapper.getMaxSort(dictionary.getParentId()));
		dictionaryMapper.insert(dictionary);
		return dictionary.getId();
	}

	/**
	 * 更新字典数据
	 * @param dictionary 字典数据
	 * @return 更新成功返回true
	 */
	public boolean update(Dictionary dictionary) {
		return dictionaryMapper.updateById(dictionary) == 1;
	}

	/**
	 * 分页查询字典数据
	 * @param pageable 分页必要条件
	 * @return 分页数据
	 */
	public Page<Dictionary> page(Pageable<Dictionary> pageable) {
		Page<Dictionary> page = new Page<>();
        page.setData(dictionaryMapper.pageList(pageable));
        page.setCount(dictionaryMapper.pageCount(pageable));
        return page;
	}
	
	/**
	 * 查询数据字典树子节点
	 * @param parentId 父ID
	 * @return 子节点集合
	 */
	public List<NodeRo> listNodes(String parentId){
		LambdaQueryWrapper<Dictionary> wrapper = new LambdaQueryWrapper<>();
		if(StringUtils.isBlank(parentId)) {
			wrapper.isNull(Dictionary::getParentId);
		}else {
			wrapper.eq(Dictionary::getParentId, parentId);
		}
		wrapper.gt(Dictionary::getStatus, EntityState.DELETED.getValue());
		List<Dictionary> dataList = dictionaryMapper.selectList(wrapper);
		return dataList.stream().map(NodeRo::new).collect(Collectors.toList());
	}
	
}
