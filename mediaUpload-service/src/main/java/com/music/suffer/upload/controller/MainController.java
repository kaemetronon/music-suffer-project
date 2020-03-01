package com.music.suffer.upload.controller;

import com.music.suffer.upload.service.implementation.UploadServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/media-upload")
public class MainController {

    private final UploadServiceImpl uploadService;

    public MainController(UploadServiceImpl us) {
        uploadService = us;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public void img(MultipartFile file, String name, String type) {
        uploadService.uploadFile(file, name, type);
    }

}
