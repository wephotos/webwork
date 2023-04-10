package com.github.wephotos.webwork.docs.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.wephotos.webwork.docs.entity.ContentType;
import com.github.wephotos.webwork.docs.entity.Document;
import com.github.wephotos.webwork.docs.entity.DocumentUser;
import com.github.wephotos.webwork.docs.entity.UserAccessType;
import com.github.wephotos.webwork.docs.entity.po.DeleteDocPO;
import com.github.wephotos.webwork.docs.entity.po.DocQueryPO;
import com.github.wephotos.webwork.docs.entity.po.DocumentPO;
import com.github.wephotos.webwork.docs.entity.vo.DocVO;
import com.github.wephotos.webwork.docs.entity.vo.ListDocVO;
import com.github.wephotos.webwork.docs.entity.vo.SaveDocResult;
import com.github.wephotos.webwork.docs.mapper.DocumentMapper;
import com.github.wephotos.webwork.docs.utils.DocStateCode;
import com.github.wephotos.webwork.docs.utils.DocUtils;
import com.github.wephotos.webwork.schema.entity.EntityState;
import com.github.wephotos.webwork.schema.entity.Page;
import com.github.wephotos.webwork.schema.entity.Pageable;
import com.github.wephotos.webwork.schema.exception.StateCode;
import com.github.wephotos.webwork.schema.exception.WebworkRuntimeException;
import com.github.wephotos.webwork.utils.BeanUtils;
import com.github.wephotos.webwork.utils.StringUtils;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * 系统配置服务
 * @author TQ
 *
 */
@Service
public class DocumentService {
	
	/**
	 * 子标题最大长度
	 */
	public static final int SUBTITLE_MAX_LENGTH = 150;
	
	@Resource
	private DocumentMapper documentMapper;
	
	@Resource
	private DocumentUserService documentUserService;
	
	/**
	 * 保存文档
	 * @param doc 文档数据
	 * @return 主键
	 */
	public SaveDocResult save(DocumentPO doc) {
		Integer docId = doc.getId();
		if(docId != null) {
			update(doc);
		}else {
			docId = add(doc);
		}
		Integer versionNo = Optional.ofNullable(doc.getVersionNo()).orElse(0);
		return SaveDocResult.builder()
				.id(docId)
				.versionNo(versionNo + 1).build();
	}
	
	/**
	 * 删除文档
	 * @param po 删除参数
	 * @return 成功返回 true
	 */
	public boolean delete(DeleteDocPO po) {
		Objects.requireNonNull(po.getId(), "文档ID不能为空");
		Objects.requireNonNull(po.getUserId(), "删除用户不能为空");
		
		// 判断用户是否有权删除
		if(canDelete(po.getId(), po.getUserId()) == false) {
			throw new WebworkRuntimeException(DocStateCode.NO_PERMISSION_DELETE, "你对本文档没有删除权限");
		}
		
		Document doc = new Document();
		doc.setId(po.getId());
		doc.setState(EntityState.DELETED.getCode());
		
		return documentMapper.updateById(doc) == 1;
	}
	
	/**
	 * 新建文档
	 * @param po 文档信息
	 * @return 文档ID
	 */
	public int add(DocumentPO po) {
		if(StringUtils.isBlank(po.getTitle()) 
				|| po.getUserId() == null || po.getContentType() == null) {
			throw new WebworkRuntimeException(StateCode.PARAMETER_MISSING, "参数[title, userId, contentType]不能为空");
		}
		ContentType contentType = ContentType.valueOf(po.getContentType());
		if(contentType == null) {
			throw new WebworkRuntimeException(StateCode.PARAMETER_ILLEGAL, "错误的文档内容类型: " + po.getContentType());
		}
		
		Document doc = BeanUtils.toObject(po, Document.class);
		// 抽取文本
		doc.setSubtitle(getSubtitle(doc.getContent(), contentType));
		// 默认非公开
		if(doc.getOpen() == null) {
			doc.setOpen(false);
		}
		doc.setVersionNo(1);
		doc.setState(EntityState.NORMAL.getCode());
		doc.setCreateUser(po.getUserName());
		doc.setUpdateUser(po.getUserName());
		doc.setCreateTime(WebworkUtils.nowTime());
		doc.setUpdateTime(doc.getCreateTime());
		documentMapper.insert(doc);
		
		// 关联文档和作者
		DocumentUser owner = new DocumentUser();
		owner.setDocId(doc.getId());
		owner.setUserId(po.getUserId());
		owner.setUserName(po.getUserName());
		owner.setAccessType(UserAccessType.CREATOR.getCode());
		documentUserService.save(owner);
		
		return doc.getId();
	}
	
	/**
	 * 更新文档
	 * @param po 文档信息
	 * @return 是否更新成功
	 */
	public boolean update(DocumentPO po) {
		if(po == null || po.getId() == null || po.getVersionNo() == null 
				|| StringUtils.isBlank(po.getContent())) {
			throw new WebworkRuntimeException(StateCode.PARAMETER_MISSING, 
					"参数[id, versionNo, content]不能为空");
		}
		// 比对文档版本号
		Document origin = documentMapper.selectById(po.getId());
		if(origin == null) {
			throw new WebworkRuntimeException(StateCode.PARAMETER_MISSING, "文档ID不存在: " + po.getId());
		}
		// 判断是否有权限修改文档
		if(canEdit(po.getId(), po.getUserId()) == false) {
			throw new WebworkRuntimeException(DocStateCode.NO_PERMISSION_EDIT, "你对本文档没有编辑权限");
		}
		if(origin.getVersionNo() > po.getVersionNo()) {
			throw new WebworkRuntimeException(DocStateCode.VERSION_EXPIRED, "文档已经被修改过，请重新打开");
		}
		Document doc = BeanUtils.toObject(po, Document.class);
		// 抽取文本
		ContentType contentType = ContentType.valueOf(origin.getContentType());
		doc.setSubtitle(getSubtitle(doc.getContent(), contentType));
		doc.setVersionNo(po.getVersionNo() + 1);
		doc.setUpdateUser(po.getUserName());
		doc.setUpdateTime(WebworkUtils.nowTime());
		LambdaUpdateWrapper<Document> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(Document::getId, po.getId()).eq(Document::getVersionNo, po.getVersionNo());
		return documentMapper.update(doc, updateWrapper) == 1;
	}
	
	/**
	 * 从内容中抽取头部内容作为子标题
	 * @param content 内容
	 * @param contentType 内容类型
	 * @return 子标题
	 */
	private String getSubtitle(String content, ContentType contentType) {
		String plainText = DocUtils.getPlainText(content, contentType);
		if(plainText != null && plainText.length() > SUBTITLE_MAX_LENGTH) {
			return plainText.substring(0, SUBTITLE_MAX_LENGTH);
		}
		return plainText;
	}
	
	/**
	 * 查询文档
	 * @param docId 文档ID
	 * @param userId 当前用户ID
	 * @return 文档对象
	 */
	public DocVO get(Integer docId, Integer userId) {
		Document doc = documentMapper.selectById(docId);
		DocVO vo = BeanUtils.toObject(doc, DocVO.class);
		vo.setCanEdit(canEdit(docId, userId));
		vo.setCanDelete(canDelete(docId, userId));
		return vo;
	}
	
	/**
	 * 用户是否可编辑文档
	 * @param docId 文档ID
	 * @param userId 用户ID
	 * @return 可编辑返回 true
	 */
	private boolean canEdit(Integer docId, Integer userId) {
		List<DocumentUser> users = documentUserService.listUser(docId);
		return users.stream().anyMatch(user -> 
			user.getUserId().equals(userId) &&
			(UserAccessType.CREATOR.equalsCode(user.getAccessType()) || 
					UserAccessType.EDIT.equalsCode(user.getAccessType())));
	}
	
	/**
	 * 用户是否可删除文档
	 * @param docId 文档ID
	 * @param userId 用户ID
	 * @return 可删除返回 true
	 */
	private boolean canDelete(Integer docId, Integer userId) {
		List<DocumentUser> users = documentUserService.listUser(docId);
		return users.stream().anyMatch(user -> user.getUserId().equals(userId) 
				&& UserAccessType.CREATOR.equalsCode(user.getAccessType()));
	}
	
	/**
     * 分页查询文档
     * @param pageable 分页参数
     * @return 分页数据 {@link Page}
     */
    public Page<ListDocVO> page(Pageable<DocQueryPO> pageable) {
        Page<ListDocVO> page = new Page<>();
        List<Document> docs = documentMapper.pageList(pageable);
        List<ListDocVO> data = BeanUtils.toList(docs, ListDocVO.class);
        page.setData(data);
        page.setCount(documentMapper.pageCount(pageable));
        return page;
    }
    
}
