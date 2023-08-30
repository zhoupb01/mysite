package com.zhoupb.file.service;

import java.io.InputStream;

public interface FileStorageService {

    /**
     * 上传图片文件
     *
     * @param path        路径 2023/08/28/xxx.jpg
     * @param inputStream 文件流
     * @return 文件全路径
     */
    String uploadImage(String path, InputStream inputStream);

    /**
     * 删除文件
     *
     * @param path 文件全路径
     */
    void delete(String path);

    /**
     * 获取Bucket
     * @return bucket的名称
     */
    String getBucket();
}
