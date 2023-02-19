package com.github.wephotos.webwork.photos.service;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wephotos.webwork.logging.LoggerFactory;
import com.github.wephotos.webwork.photos.entity.UserProfile;
import com.github.wephotos.webwork.photos.mapper.UserProfileMapper;
import com.github.wephotos.webwork.photos.utils.PhotosStateCode;
import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.exception.WebworkRuntimeException;
import com.github.wephotos.webwork.user.client.UserClient;
import com.github.wephotos.webwork.user.client.entity.ro.UserRO;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * 用户描述信息业务
 * @author TQ
 *
 */
@Service
public class UserProfileService extends ServiceImpl<UserProfileMapper, UserProfile> {
	
	//日志
	static final Logger log = LoggerFactory.getLogger(UserProfileService.class);
	
	@Resource
	private UserClient userClient;
	
	@Resource
	private UserProfileMapper userProfileMapper;
	
	/**
	 * 用户总空间，默认 100M
	 */
	@Value("${webwork.photos.profile.total-space:104857600}")
	private Long totalSpace;
	
	/**
	 * 用户可创建的相册数据，默认 10个
	 */
	@Value("${webwork.photos.profile.max-album-count:10}")
	private Integer maxAlbumCount;

	/**
	 * 获取用户描述信息，如果描述信息不存在，会创建一条并返回
	 * @param userId 用户ID
	 * @return 用户描述信息
	 */
    public UserProfile getProfile(Integer userId) {
    	Objects.requireNonNull(userId, "用户ID不能为空");
    	synchronized (userId) {
	    	UserProfile profile = lambdaQuery().eq(UserProfile::getUserId, userId).one();
	    	if(profile == null) {
	    		profile = createDefaultProfile(userId);
	    	}
	    	return profile;
    	}
    }
    
    /**
     * 创建用户描述信息
     * @param userId 用户ID
     * @return 用户描述信息对象
     */
    private UserProfile createDefaultProfile(Integer userId) {
    	UserProfile profile = new UserProfile();
    	profile.setUserId(userId);
    	profile.setAlbumCount(0);
    	profile.setUsageSpace(0L);
    	profile.setTotalSpace(totalSpace);
    	profile.setMaxAlbumCount(maxAlbumCount);
    	profile.setCreateTime(WebworkUtils.nowTime());
    	profile.setUpdateTime(profile.getCreateTime());
    	// 设置用户名
    	Result<UserRO> user = userClient.findUserDetailsById(userId);
    	profile.setUserName(user.getData().getName());
    	save(profile);
    	return profile;
    }
    
    /**
     * 更新描述信息
     * @param profile 描述信息
     * @return 成功返回 true
     */
    public boolean updateProfile(UserProfile profile) {
    	Objects.requireNonNull(profile.getId(), "profile#id不能为空");
    	profile.setUpdateTime(WebworkUtils.nowTime());
    	return updateById(profile);
    }
    
    /**
     * 增加用户相册数量，负数进行减操作
     * @param count 本次变更相册数量
     */
    public void plusAlbumCount(Integer userId, int count) {
    	UserProfile profile = getProfile(userId);
    	// 检测相册是否超出数量限制
    	Integer albumCount = Optional.ofNullable(profile.getAlbumCount()).orElse(0);
    	profile.setAlbumCount(albumCount + count);
    	if(albumCount >= profile.getMaxAlbumCount()) {
    		throw new WebworkRuntimeException(PhotosStateCode.GREATER_THAN_MAX_ALBUM_COUNT);
    	}
    	profile.setUpdateTime(WebworkUtils.nowTime());
    	updateProfile(profile);
    }
    
    /**
     * 增加用户使用空间，负数进行减操作
     * @param size 本次变更空间大小
     */
    public void plusUsageSpace(Integer userId, long size) {
    	UserProfile profile = getProfile(userId);
    	Long usageSpace = Optional.ofNullable(profile.getUsageSpace()).orElse(0L);
    	profile.setUsageSpace(usageSpace + size);
    	// 检测用户空间是否超出限制
    	if(profile.getUsageSpace() >= profile.getTotalSpace()) {
    		throw new WebworkRuntimeException(PhotosStateCode.NOT_ENOUGH_STORAGE_SPACE);
    	}
    	profile.setUpdateTime(WebworkUtils.nowTime());
    	updateProfile(profile);
    }
}
