package com.github.wephotos.webwork.docs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wephotos.webwork.docs.entity.DocumentUser;
import com.github.wephotos.webwork.docs.mapper.DocumentUserMapper;

/**
 * 文档用户业务
 * @author TianQi
 *
 */
@Service
public class DocumentUserService extends ServiceImpl<DocumentUserMapper, DocumentUser> {

	/**
	 * 查询文档用户
	 * @param docId 文档ID
	 * @return 文档用户
	 */
	public List<DocumentUser> listUser(Integer docId) {
		return lambdaQuery().eq(DocumentUser::getDocId, docId).list();
	}
}
