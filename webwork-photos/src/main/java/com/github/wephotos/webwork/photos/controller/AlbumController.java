package com.github.wephotos.webwork.photos.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.photos.entity.Album;
import com.github.wephotos.webwork.photos.entity.po.AlbumPO;
import com.github.wephotos.webwork.photos.entity.po.CoverPO;
import com.github.wephotos.webwork.photos.entity.po.DeleteAlbumPO;
import com.github.wephotos.webwork.photos.entity.po.ListQueryAlbumPO;
import com.github.wephotos.webwork.photos.service.AlbumService;
import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.utils.Results;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.security.utils.SecurityUtils;

/**
 * 相册接口
 * @author TQ
 *
 */
@RestController
@RequestMapping("/album")
public class AlbumController {

	@Resource
	private AlbumService albumService;
	
	/**
	 * 保存相册
	 * @param albumPO 相册参数
	 * @param session 会话对象
	 * @return 相册ID
	 */
	@PostMapping("/save")
	public Result<Integer> save(@RequestBody AlbumPO albumPO, HttpSession session) {
		SecurityUser user = SecurityUtils.getSecurityUser(session);
		albumPO.setUserId(user.getId());
		albumPO.setUserName(user.getName());
		Integer albumId = albumService.saveAlbum(albumPO);
		return Results.newSuccessfullyResult(albumId);
	}
	
	/**
	 * 删除相册
	 * @param albumId 相册ID
	 * @param session 会话对象
	 * @return 删除成功返回 true
	 */
	@GetMapping("/delete/{id}")
	public Result<Boolean> deleteAlbum(@PathVariable("id") Integer albumId, HttpSession session) {
		SecurityUser user = SecurityUtils.getSecurityUser(session);
		DeleteAlbumPO deleteAlbumPO = new DeleteAlbumPO();
		deleteAlbumPO.setId(albumId);
		deleteAlbumPO.setUserId(user.getId());
		deleteAlbumPO.setUserName(user.getName());
		boolean ret = albumService.deleteAlbum(deleteAlbumPO);
		return Results.newSuccessfullyResult(ret);
	}
	
	/**
	 * 用户相册列表查询
	 * @param session 会话对象
	 * @return 用户相册集合
	 */
	@PostMapping("/list-query")
	public Result<List<Album>> listQuery(HttpSession session) {
		SecurityUser user = SecurityUtils.getSecurityUser(session);
		ListQueryAlbumPO listQueryAlbumPO = ListQueryAlbumPO.builder().userId(user.getId()).build();
		List<Album> albums = albumService.listQuery(listQueryAlbumPO);
		return Results.newSuccessfullyResult(albums);
	}
	
	/**
	 * 设置相册封面
	 * @param cover 入参
	 * @return 成功返回 true
	 */
	@PostMapping("/cover")
	public Result<Boolean> cover(@RequestBody CoverPO cover) {
		boolean isSuccess = albumService.cover(cover);
		return Results.newSuccessfullyResult(isSuccess);
	}
}
