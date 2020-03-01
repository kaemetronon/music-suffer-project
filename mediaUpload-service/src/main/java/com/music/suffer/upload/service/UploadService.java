package com.music.suffer.upload.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    void uploadFile(MultipartFile file, String pathWithUUID, String type);
}
