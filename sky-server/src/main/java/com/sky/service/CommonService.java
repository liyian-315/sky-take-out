package com.sky.service;

import org.springframework.web.multipart.MultipartFile;

public interface CommonService {

    /**
     * 文件上传
     * @param file
     */
    void upload(MultipartFile file);
}
