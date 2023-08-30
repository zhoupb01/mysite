package com.zhoupb.file.service.impl;

import com.zhoupb.file.config.MinioConfig;
import com.zhoupb.file.config.MinioConfigProperties;
import com.zhoupb.file.service.FileStorageService;
import com.zhoupb.mysite.common.exception.CustomException;
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

import java.io.InputStream;

@Import(MinioConfig.class)
public record MinioFileStorageService(MinioClient minioClient,
                                      MinioConfigProperties minioConfigProperties) implements FileStorageService {

    @Override
    public String uploadImage(String path, InputStream inputStream) {
        try {
            BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder()
                    .bucket(minioConfigProperties.getBucket())
                    .build();
            boolean exists = minioClient.bucketExists(bucketExistsArgs);
            if (!exists)
                throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, minioConfigProperties().getBucket() + "不存在");
            // 上传
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(minioConfigProperties.getBucket())
                    .object(path)
                    .contentType("image/jpeg")
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "上传失败");
        }
        return minioConfigProperties.getEndpoint() + "/" + minioConfigProperties.getBucket() + "/" + path;
    }

    @Override
    public void delete(String path) {
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(minioConfigProperties.getBucket())
                .object(path)
                .build();
        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "删除失败");
        }
    }

    @Override
    public String getBucket() {
        return minioConfigProperties.getBucket();
    }
}
