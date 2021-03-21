package com.github.wephotos.webwork.file.controller;

import com.github.wephotos.webwork.file.entity.UploadResult;
import com.github.wephotos.webwork.file.entity.WebworkFile;
import com.github.wephotos.webwork.file.service.FileService;
import com.github.wephotos.webwork.http.RestObject;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.security.storage.SessionUserStorage;
import com.github.wephotos.webwork.utils.ImageUtils;
import com.github.wephotos.webwork.utils.WebworkUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 文件模块HTTP接口
 *
 * @author TQ
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @RequestMapping("index")
    public String index() {
        return "ind";
    }

    @Resource
    private FileService fileService;

    /**
     * 上传文件
     *
     * @return {@link RestObject}
     * @throws IOException IO异常
     */
    @PostMapping("/upload")
    public RestObject upload(@RequestParam("file") MultipartFile file, WebworkFile workFile, HttpSession session) throws IOException {
        User user = SessionUserStorage.get(session);
        try (InputStream input = file.getInputStream()) {
            workFile.setName(file.getOriginalFilename());
            workFile.setInputStream(input);
            workFile.setContentType(file.getContentType());
            workFile.setSize(file.getSize());
            if(user != null) {
            	workFile.setUserId(user.getId());
            	workFile.setUserName(user.getName());
            }
            UploadResult upload = fileService.upload(workFile);
            return RestObject.builder().data(upload).build();
        }
    }

    /**
     * 文件下载
     *
     * @param id       附件id
     * @param request  request
     * @param response response
     * @throws IOException IOException
     */
    @GetMapping("/get/{id}")
    public void get(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebworkFile file = fileService.getFile(id);
        response.reset();
        response.setCharacterEncoding("UTF-8");
        String userAgent = request.getHeader("User-Agent");
        String filename = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8.name());
        if (userAgent != null && userAgent.toLowerCase().contains("firefox")) {
            response.addHeader("Content-Disposition", "attachment;filename*=UTF-8''" + filename);
        } else {
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        }
        response.addHeader("Content-Length", "" + file.getSize());
        response.setContentType(file.getContentType());
        try (InputStream input = file.getInputStream()) {
            IOUtils.copy(input, response.getOutputStream());
        }
    }


    /**
     * 删除附件
     *
     * @param id 附件id
     * @return {@link RestObject}
     * @throws IOException IOException
     */
    @GetMapping("/delete/{id}")
    public RestObject delete(@PathVariable("id") String id) throws IOException {
        int i = fileService.deleteByPrimaryKey(id);
        return RestObject.builder().data(i).build();
    }

    /**
     * 逻辑删除
     *
     * @param id 附件id
     * @return {@link RestObject}
     */
    @GetMapping("/logic-delete/{id}")
    public RestObject logicDelete(@PathVariable("id") String id) {
        int i = fileService.logicDelete(id);
        return RestObject.builder().data(i).build();
    }

    /**
     * 获取所属附件
     *
     * @param owner 附件主体
     * @return RestObject
     */
    @GetMapping("/list/{owner}")
    public RestObject list(@PathVariable("owner") String owner) {
        List<WebworkFile> list = fileService.list(owner);
        return RestObject.builder().data(list).build();
    }

    /**
     * 上传base64编码图片
     *
     * @param owner  归属
     * @param base64 图片BASE64编码
     * @return {@link RestObject}
     * @throws IOException IOException
     */
    @PostMapping("/upload/base64-image")
    public RestObject uploadBase64Image(String owner, String base64, HttpSession session) throws IOException {
        User user = SessionUserStorage.get(session);
        //判断是否存在类似 data:image/png;base64, 的前缀
        String suffix;
        String contentType;
        int comma = base64.indexOf(',');
        if (comma != -1) {
            int slash = base64.indexOf('/');
            int semicolon = base64.indexOf(';');
            suffix = base64.substring(slash + 1, semicolon);
            contentType = base64.substring(5, semicolon);
            base64 = base64.substring(comma + 1);
        } else {
            suffix = "png";
            contentType = "image/png";
        }
        byte[] buf = Base64Utils.decodeFromString(base64);
        WebworkFile file = new WebworkFile();
        file.setOwner(owner);
        file.setInputStream(new ByteArrayInputStream(buf));
        file.setName(WebworkUtils.uuid().concat(".").concat(suffix));
        file.setUserId(user.getId());
        file.setUserName(user.getName());
        file.setSize(buf.length);
        file.setContentType(contentType);
        UploadResult upload = fileService.upload(file);
        return RestObject.builder().data(upload).build();
    }

    /**
     * 获取缩略图
     *
     * @param id       附件id
     * @param request  request
     * @param response response
     * @throws IOException IOException
     */
    @GetMapping("/thumb/get/{id}")
    public void getThumb(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebworkFile file = fileService.getFile(id);
        response.reset();
        response.setCharacterEncoding("UTF-8");
        String userAgent = request.getHeader("User-Agent");
        String filename = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8.name());
        if (userAgent != null && userAgent.toLowerCase().contains("firefox")) {
            response.addHeader("Content-Disposition", "attachment;filename*=UTF-8''" + filename);
        } else {
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        }
        response.setContentType(file.getContentType());
        try (InputStream input = file.getInputStream()) {
            byte[] data = ImageUtils.compressionStream(input, 100, 100, 0.75F);
            response.addHeader("Content-Length", "" + data.length);
            IOUtils.write(data, response.getOutputStream());
        }
    }


}
