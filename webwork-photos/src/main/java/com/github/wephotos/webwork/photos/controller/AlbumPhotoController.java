package com.github.wephotos.webwork.photos.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.wephotos.webwork.photos.entity.po.UploadPhotoPO;
import com.github.wephotos.webwork.photos.entity.po.DeletePhotoPO;
import com.github.wephotos.webwork.photos.entity.po.ListQueryPhotoPO;
import com.github.wephotos.webwork.photos.entity.po.UpdatePhotoPO;
import com.github.wephotos.webwork.photos.entity.vo.AlbumPhotoVO;
import com.github.wephotos.webwork.photos.service.AlbumPhotoService;
import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.utils.Results;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.security.utils.SecurityUtils;

/**
 * 照片接口
 * @author TianQi
 *
 */
@RestController
@RequestMapping("/album-photo")
public class AlbumPhotoController {

	@Resource
	private AlbumPhotoService albumPhotoService;
	
	/**
	 * 上传照片
	 * @param file 图片数据
	 * @param uploadPhotoPO 上传图片附加参数
	 * @param session HTTP会话
	 * @return 上传图片生成的信息 {@link AlbumPhotoVO}
	 * @throws IOException I/O异常
	 */
	@PostMapping("/upload")
	public Result<AlbumPhotoVO> upload(@RequestParam("file") MultipartFile file, UploadPhotoPO uploadPhotoPO, HttpSession session) throws IOException {
		SecurityUser user = SecurityUtils.getSecurityUser(session);
		uploadPhotoPO.setUserId(user.getId());
		uploadPhotoPO.setUserName(user.getName());
		try (InputStream input = file.getInputStream()) {
			uploadPhotoPO.setInputStream(input);
			uploadPhotoPO.setPhotoSize((int)file.getSize());
			uploadPhotoPO.setName(file.getOriginalFilename());
			uploadPhotoPO.setContentType(file.getContentType());
			AlbumPhotoVO uploadResult = albumPhotoService.upload(uploadPhotoPO);
			return Results.newSuccessfullyResult(uploadResult);
		}
	}
	
	/**
	 * 更新照片信息
	 * @param updatePhotoPO 更新照片参数
	 * @param session HTTP会话对象
	 * @return 更新成功返回 true
	 */
	@PostMapping("/update")
	public Result<Boolean> update(@RequestBody UpdatePhotoPO updatePhotoPO, HttpSession session) {
		SecurityUser user = SecurityUtils.getSecurityUser(session);
		updatePhotoPO.setUserId(user.getId());
		updatePhotoPO.setUserName(user.getName());
		boolean ret = albumPhotoService.update(updatePhotoPO);
		return Results.newSuccessfullyResult(ret);
	}
	
	/**
	 * 删除照片
	 * @param photoId 照片ID
	 * @param session HTTP会话
	 * @return 删除成功返回 true
	 */
	@GetMapping("/delete/{id}")
	public Result<Boolean> delete(@PathVariable("id") Integer photoId, HttpSession session) {
		SecurityUser user = SecurityUtils.getSecurityUser(session);
		DeletePhotoPO deletePhotoPO = new DeletePhotoPO();
		deletePhotoPO.setId(photoId);
		deletePhotoPO.setUserId(user.getId());
		deletePhotoPO.setUserName(user.getName());
		boolean ret = albumPhotoService.delete(deletePhotoPO);
		return Results.newSuccessfullyResult(ret);
	}
	
	/**
	 * 获取相册照片
	 * @param queryPO 查询参数
	 * @return 照片集合
	 */
	@PostMapping("/list-query")
	public Result<List<AlbumPhotoVO>> listQuery(@RequestBody ListQueryPhotoPO queryPO) {
		List<AlbumPhotoVO> photoVOs = albumPhotoService.listQuery(queryPO);
		return Results.newSuccessfullyResult(photoVOs);
	}
}
