package com.github.wephotos.webwork.controller;

import com.github.wephotos.FileService;
import com.github.wephotos.WebWorkFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author chengzi
 * @date 2021-03-08 20:38
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file) throws IOException {
        try (InputStream input = file.getInputStream()) {
            WebWorkFile workFile = new WebWorkFile();
            workFile.setObjectName(file.getOriginalFilename());
            workFile.setInputStream(input);
            fileService.storage(workFile);
        }
    }
}
