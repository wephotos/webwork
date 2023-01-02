package com.github.wephotos.webwork.docs.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.docs.entity.Document;
import com.github.wephotos.webwork.docs.entity.po.DeleteDocPO;
import com.github.wephotos.webwork.docs.entity.po.DocQueryPO;
import com.github.wephotos.webwork.docs.entity.po.DocumentPO;
import com.github.wephotos.webwork.docs.entity.vo.DocVO;
import com.github.wephotos.webwork.docs.entity.vo.ListDocVO;
import com.github.wephotos.webwork.docs.entity.vo.SaveDocResult;
import com.github.wephotos.webwork.docs.service.DocumentService;
import com.github.wephotos.webwork.schema.entity.Page;
import com.github.wephotos.webwork.schema.entity.Pageable;
import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.utils.Results;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.security.utils.SecurityUtils;

/**
 * 文档接口
 * @author TQ
 *
 */
@RestController
@RequestMapping("/document")
public class DocumentController {

	@Resource
	private DocumentService docService;
	
	/**
	 * 新建文档
	 * @param doc 文档数据
	 * @return {@link Result} 文档ID
	 */
	@PostMapping("/save")
	public Result<SaveDocResult> save(@RequestBody DocumentPO doc, HttpSession session) {
		SecurityUser user = SecurityUtils.getSecurityUser(session);
		doc.setUserId(user.getId());
		doc.setUserName(user.getName());
		SaveDocResult data = docService.save(doc);
		return Results.newSuccessfullyResult(data);
	}
	
	/**
	 * 删除文档
	 * @param docId 文档ID
	 * @param session 会员对象
	 * @return 删除成功返回true
	 */
	@GetMapping("/delete/{id}")
	public Result<Boolean> delete(@PathVariable("id") Integer docId, HttpSession session) {
		SecurityUser user = SecurityUtils.getSecurityUser(session);
		DeleteDocPO po = new DeleteDocPO();
		po.setId(docId);
		po.setUserId(user.getId());
		boolean ret = docService.delete(po);
		return Results.newSuccessfullyResult(ret);
	}
	
	/**
	 * 查询文档
	 * @param docId 文档ID
	 * @param session 会话对象
	 * @return 文档内容
	 */
	@GetMapping("/get/{id}")
	public Result<DocVO> get(@PathVariable("id") Integer docId, HttpSession session) {
		SecurityUser user = SecurityUtils.getSecurityUser(session);
		DocVO document = docService.get(docId, user.getId());
		return Results.newSuccessfullyResult(document);
	}
	
	/**
	 * 分页查询文档
	 * @param pageable 分页必要条件
	 * @param session 会话对象
	 * @return 分页数据 {@link Page} {@link Document}
	 */
	@PostMapping("/page")
	public Result<Page<ListDocVO>> page(@RequestBody Pageable<DocQueryPO> pageable, HttpSession session) {
		SecurityUser user = SecurityUtils.getSecurityUser(session);
		// 设置当前用户
		DocQueryPO cond = pageable.getCondition();
		if(cond == null) {
			cond = new DocQueryPO();
			pageable.setCondition(cond);
		}
		cond.setUserId(user.getId());
		Page<ListDocVO> page = docService.page(pageable);
		return Results.newSuccessfullyResult(page);
	}

}
