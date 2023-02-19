package com.github.wephotos.webwork.photos.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wephotos.webwork.file.entity.FileGroup;
import com.github.wephotos.webwork.file.facade.FileFacade;
import com.github.wephotos.webwork.file.facade.po.UploadFilePO;
import com.github.wephotos.webwork.file.facade.ro.UploadResultRO;
import com.github.wephotos.webwork.photos.entity.AlbumPhoto;
import com.github.wephotos.webwork.photos.entity.po.DeletePhotoPO;
import com.github.wephotos.webwork.photos.entity.po.ListQueryPhotoPO;
import com.github.wephotos.webwork.photos.entity.po.PlusPhotoCountPO;
import com.github.wephotos.webwork.photos.entity.po.PlusUsageSpacePO;
import com.github.wephotos.webwork.photos.entity.po.UpdatePhotoPO;
import com.github.wephotos.webwork.photos.entity.po.UploadPhotoPO;
import com.github.wephotos.webwork.photos.entity.vo.AlbumPhotoVO;
import com.github.wephotos.webwork.photos.mapper.AlbumPhotoMapper;
import com.github.wephotos.webwork.schema.entity.EntityState;
import com.github.wephotos.webwork.utils.BeanUtils;
import com.github.wephotos.webwork.utils.FileUtils;
import com.github.wephotos.webwork.utils.WebworkUtils;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 照片业务类
 * @author TianQi
 *
 */
@Service
public class AlbumPhotoService extends ServiceImpl<AlbumPhotoMapper, AlbumPhoto> {
	
	/**
	 * 注册我的照片照片文件组
	 */
	public static final FileGroup PHOTOS_FILE_GROUP = new FileGroup("WEBWORK-PHOTOS", "我的照片");
	
	/**
	 * 缩略图最大为 512KB
	 */
	private static final int THUMBNAIL_MAX_SIZE = 512 * 1024;

	@Resource
	private AlbumService albumService;
	
	@Resource
	private FileFacade fileFacade;
	
	@Resource
	private TransactionManager transactionManager;
	
	/**
	 * 保存照片为本地临时文件
	 * @param uploadPhotoPO 上传照片参数
	 * @return 临时照片文件
	 * @throws IOException I/O异常
	 */
	private File saveTempFile(UploadPhotoPO uploadPhotoPO) throws IOException {
		File tempDirectory = FileUtils.getTempDirectory();
		String tempFilename = String.format("%s-%s", System.currentTimeMillis(), uploadPhotoPO.getName());
		File file = FileUtils.getFile(tempDirectory, tempFilename);
		try (InputStream input = uploadPhotoPO.getInputStream()) {
			FileUtils.copyToFile(input, file);
		}
		return file;
	}
	
	/**
	 * 上传文件至文件服务
	 * @param po 上传文件相关参数
	 * @return 文件服务返回的文件对象名
	 * @throws IOException I/O异常
	 */
	private String upload2FileServer(UploadPhotoPO po) throws IOException {
		UploadFilePO uploadFilePO = new UploadFilePO();
		uploadFilePO.setName(po.getName());
		uploadFilePO.setSize(po.getPhotoSize());
		uploadFilePO.setContentType(po.getContentType());
		uploadFilePO.setInputStream(po.getInputStream());
		uploadFilePO.setUserId(po.getUserId());
		uploadFilePO.setUserName(po.getUserName());
		uploadFilePO.setFileGroup(PHOTOS_FILE_GROUP.getName());
		uploadFilePO.setFileGroupKey(po.getAlbumId() + "");
		UploadResultRO uploadResultRO = fileFacade.upload(uploadFilePO);
		return uploadResultRO.getObjectName();
	}
	
	/**
	 * 生成缩略图
	 * @param image
	 * @return
	 * @throws IOException 
	 */
	private File thumbnails(File image) throws IOException {
		if (image.length() <= THUMBNAIL_MAX_SIZE) {
			return image;
		}
		Thumbnails.of(image).scale(0.95).outputQuality(0.8).allowOverwrite(true).toFile(image);
		return thumbnails(image);
	}
	
	/**
	 * 上传照片
	 * TransactionSynchronizationManager
	 * @param uploadPO 上传参数
	 * @return 上传照片相关信息
	 * @throws IOException 
	 */
	@Transactional
	public AlbumPhotoVO upload(UploadPhotoPO uploadPO) throws IOException {
		Validate.notNull(uploadPO, "上传参数不能为空");

		// 上传照片后，更新相册信息
		PlusPhotoCountPO plusPhotoCountPO = PlusPhotoCountPO.builder()
				.id(uploadPO.getAlbumId()).count(1).build();
		albumService.plusPhotoCount(plusPhotoCountPO);
		PlusUsageSpacePO plusUsageSpacePO = PlusUsageSpacePO.builder()
				.id(uploadPO.getAlbumId()).size(uploadPO.getPhotoSize())
				.userId(uploadPO.getUserId()).userName(uploadPO.getUserName()).build();
		albumService.plusUsageSpace(plusUsageSpacePO);
		
		// 原始照片信息
		AlbumPhoto albumPhoto = BeanUtils.toObject(uploadPO, AlbumPhoto.class);
		
		// 保存临时文件
		File tempFile = saveTempFile(uploadPO);
		try {
			// 上传文件服务
			uploadPO.setInputStream(new FileInputStream(tempFile));
			String objectName = upload2FileServer(uploadPO);
			
			// 压缩原图片到指定大小
			File thumbnail = thumbnails(tempFile);
			uploadPO.setPhotoSize((int)thumbnail.length());
			uploadPO.setName(String.format("thumb-%s", uploadPO.getName()));
			uploadPO.setInputStream(FileUtils.openInputStream(thumbnail));
			String thumbObjectName = upload2FileServer(uploadPO);
			
			// 保存照片记录
			albumPhoto.setObjectName(objectName);
			albumPhoto.setThumbObjectName(thumbObjectName);
			albumPhoto.setState(EntityState.NORMAL.getCode());
			albumPhoto.setCreateTime(WebworkUtils.nowTime());
			albumPhoto.setUpdateTime(albumPhoto.getCreateTime());
			save(albumPhoto);
			return BeanUtils.toObject(albumPhoto, AlbumPhotoVO.class);
		} finally {
			FileUtils.deleteQuietly(tempFile);
		}
	}
	
	/**
	 * 更新照片，更新照片描述等信息
	 * @param photoPO 照片信息
	 * @return 更新成功返回 true
	 */
	public boolean update(UpdatePhotoPO updatePhotoPO) {
		Validate.notNull(updatePhotoPO, "照片更新参数不能为空");
		Validate.notNull(updatePhotoPO.getId(), "照片ID不能为空");
		
		AlbumPhoto albumPhoto = BeanUtils.toObject(updatePhotoPO, AlbumPhoto.class);
		albumPhoto.setUserId(updatePhotoPO.getUserId());
		albumPhoto.setUserName(updatePhotoPO.getUserName());
		albumPhoto.setUpdateTime(WebworkUtils.nowTime());
		return updateById(albumPhoto);
	}
	
	/**
	 * 删除图片
	 * @param deletePhotoPO 删除图片参数
	 * @return 删除成功返回 true
	 */
	@Transactional
	public boolean delete(DeletePhotoPO deletePhotoPO) {
		// 查询库中照片信息
		AlbumPhoto photo = getById(deletePhotoPO.getId());
		// 删除照片后，更新相册信息
		PlusPhotoCountPO plusPhotoCountPO = PlusPhotoCountPO.builder()
				.id(photo.getAlbumId()).count(-1).build();
		albumService.plusPhotoCount(plusPhotoCountPO);
		PlusUsageSpacePO plusUsageSpacePO = PlusUsageSpacePO.builder()
				.id(photo.getAlbumId()).size(-photo.getPhotoSize())
				.userId(deletePhotoPO.getUserId()).userName(deletePhotoPO.getUserName()).build();
		albumService.plusUsageSpace(plusUsageSpacePO);
		
		// 删除照片
		photo.setState(EntityState.DELETED.getCode());
		photo.setUpdateTime(WebworkUtils.nowTime());
		photo.setUserId(deletePhotoPO.getUserId());
		photo.setUserName(deletePhotoPO.getUserName());
		return updateById(photo);
	}
	
	/**
	 * 查询照片列表
	 * @param queryPO 查询照片参数
	 * @return 照片集合
	 */
	public List<AlbumPhotoVO> listQuery(ListQueryPhotoPO queryPO) {
		List<AlbumPhoto> list = lambdaQuery()
				.eq(AlbumPhoto::getAlbumId, queryPO.getAlbumId())
				.eq(AlbumPhoto::getState, EntityState.NORMAL.getCode()).list();
		return BeanUtils.toList(list, AlbumPhotoVO.class);
	}
}
