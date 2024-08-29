package com.sky.service.impl;

import com.sky.constant.PathConstant;
import com.sky.constant.StatusConstant;
import com.sky.result.Result;
import com.sky.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService {

    /**
     * 文件上传
     * @param file
     */
    @Override
    public void upload(MultipartFile file) {
        // 获取项目根目录
        String projectPath = System.getProperty("user.dir");

        // 创建上传目录的File对象
        File uploadDir = new File(projectPath + File.separator + PathConstant.UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();  // 如果目录不存在，则创建目录
        }

        // 构建文件保存路径
        String fileName = file.getOriginalFilename();
        File dest = new File(uploadDir + File.separator + fileName);

        try {
            // 保存文件到指定路径
            file.transferTo(dest);
            log.info("文件成功上传到: {}", dest.getAbsolutePath());
        } catch (IOException e) {
            log.error("文件上传失败", e);
        }
    }
}
