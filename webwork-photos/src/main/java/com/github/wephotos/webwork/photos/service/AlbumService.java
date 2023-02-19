package com.github.wephotos.webwork.photos.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wephotos.webwork.photos.entity.Album;
import com.github.wephotos.webwork.photos.entity.po.AlbumPO;
import com.github.wephotos.webwork.photos.entity.po.CoverPO;
import com.github.wephotos.webwork.photos.entity.po.DeleteAlbumPO;
import com.github.wephotos.webwork.photos.entity.po.ListQueryAlbumPO;
import com.github.wephotos.webwork.photos.entity.po.ListQueryPhotoPO;
import com.github.wephotos.webwork.photos.entity.po.PlusPhotoCountPO;
import com.github.wephotos.webwork.photos.entity.po.PlusUsageSpacePO;
import com.github.wephotos.webwork.photos.entity.vo.AlbumPhotoVO;
import com.github.wephotos.webwork.photos.mapper.AlbumMapper;
import com.github.wephotos.webwork.photos.utils.PhotosStateCode;
import com.github.wephotos.webwork.schema.entity.EntityState;
import com.github.wephotos.webwork.schema.exception.WebworkRuntimeException;
import com.github.wephotos.webwork.utils.BeanUtils;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * 用户相册业务类
 * @author TianQi
 *
 */
@Service
public class AlbumService extends ServiceImpl<AlbumMapper, Album> {
	
	@Resource
	private UserProfileService userProfileService;
	
	@Resource
	private AlbumPhotoService albumPhotoService;
	
	/**
	 * 相册可上传最大图片数量，默认100张
	 */
	@Value("${webwork.photos.album.max-photo-count:100}")
	private Integer maxPhotoCount;
	
	/**
	 * 查询用户相册
	 * @param po 查询参数
	 * @return 用户相册
	 */
	public List<Album> listQuery(ListQueryAlbumPO po) {
		Validate.notNull(po, "参数不能为空");
		Validate.notNull(po.getUserId(), "用户ID不能为空");
		return lambdaQuery().eq(Album::getUserId, po.getUserId())
				.eq(Album::getState, EntityState.NORMAL.getCode()).list();
	}
	
	/**
	 * 保存相册
	 * @param albumPO 相册参数
	 * @return 相册ID
	 */
	@Transactional
	public Integer saveAlbum(AlbumPO albumPO) {
		Validate.notNull(albumPO, "参数不能为空");
		Validate.notNull(albumPO.getUserId(), "用户ID不能为空");
		Validate.notBlank(albumPO.getName(), "相册名称不能为空");
		
		Album album = BeanUtils.toObject(albumPO, Album.class);
		Date nowTime = WebworkUtils.nowTime();
		// 新建相册
		if(album.getId() == null) {
			album.setUsageSpace(0L);
			album.setPhotoCount(0);
			album.setMaxPhotoCount(maxPhotoCount);
			album.setState(EntityState.NORMAL.getCode());
			album.setCreateTime(nowTime);
			
			// 更新用户描述信息
			userProfileService.plusAlbumCount(albumPO.getUserId(), 1);
		}
		album.setUpdateTime(nowTime);
		
		saveOrUpdate(album);
		
		return album.getId();
	}
	
	/**
	 * 删除相册
	 * @param po 删除相册参数
	 * @return
	 */
	@Transactional
	public boolean deleteAlbum(DeleteAlbumPO po) {
		Objects.requireNonNull(po, "删除相册参数不能为空");
		Objects.requireNonNull(po.getId(), "未指定要删除相册的ID");
		// 有照片不允许删除
		ListQueryPhotoPO listQueryPhotoPO = ListQueryPhotoPO.builder()
				.albumId(po.getId()).build();
		List<AlbumPhotoVO> photos = albumPhotoService.listQuery(listQueryPhotoPO);
		if(photos.size() > 0) {
			throw new WebworkRuntimeException(PhotosStateCode.NOT_DELETE_ALBUM_HAS_PHOTO, "相册中存在照片，不能删除");
		}
		Album album = getById(po.getId());
		album.setUserId(po.getUserId());
		album.setUserName(po.getUserName());
		album.setState(EntityState.DELETED.getCode());
		album.setUpdateTime(WebworkUtils.nowTime());
		// 修改用户描述信息
		userProfileService.plusAlbumCount(album.getUserId(), -1);
		userProfileService.plusUsageSpace(po.getUserId(), -album.getUsageSpace());
		return updateById(album);
	}
	
	/**
	 * 增加相册使用空间
	 * @param po 增加相册空间参数
	 */
	public void plusUsageSpace(PlusUsageSpacePO po) {
		Validate.notNull(po, "参数不能为空");
		Validate.notNull(po.getId(), "相册ID不能为空");
		Validate.notNull(po.getSize(), "增加空间不能为空");
		
		Album album = getById(po.getId());
		Long usageSpace = Optional.ofNullable(album.getUsageSpace()).orElse(0L);
		album.setUsageSpace(usageSpace + po.getSize());
		album.setUpdateTime(WebworkUtils.nowTime());
		updateById(album);
		// 修改用户描述信息
		userProfileService.plusUsageSpace(album.getUserId(), po.getSize());
	}
	
	/**
	 * 增加相册图片数量
	 * @param po 增加相册图片数量
	 */
	public void plusPhotoCount(PlusPhotoCountPO po) {
		Validate.notNull(po, "参数不能为空");
		Validate.notNull(po.getId(), "相册ID不能为空");
		Validate.notNull(po.getCount(), "增加照片数量不能为空");
		
		Album album = getById(po.getId());
		
		Integer photoCount = Optional.ofNullable(album.getPhotoCount()).orElse(0);
		album.setPhotoCount(photoCount + po.getCount());
		// 检测相册照片数量是否超出限制
		if(album.getPhotoCount() >= album.getMaxPhotoCount()) {
			throw new WebworkRuntimeException(PhotosStateCode.GREATER_THAN_MAX_PHOTO_COUNT);
		}
		
		album.setUserId(po.getUserId());
		album.setUserName(po.getUserName());
		album.setUpdateTime(WebworkUtils.nowTime());
		updateById(album);
	}

	/**
	 * 设置相册封面照片
	 * @param po 入参
	 * @return 成功返回 true
	 */
	public boolean cover(CoverPO po) {
		Objects.requireNonNull(po.getCover(), "封面照片不能为空");
		Objects.requireNonNull(po.getAlbumId(), "相册ID不能为空");
		Album album = getById(po.getAlbumId());
		if(album == null) {
			throw new IllegalArgumentException("相册ID不存在: " + po.getAlbumId());
		}
		album.setCover(po.getCover());
		album.setUpdateTime(WebworkUtils.nowTime());
		return updateById(album);
	}
	
}
