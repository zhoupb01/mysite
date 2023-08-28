package com.zhoupb.mysite.serivce.blog;

import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import org.apache.ibatis.session.RowBounds;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MinioTest {

    public static void main(String[] args)
            throws IOException, NoSuchAlgorithmException {

        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("https://xxx")
                            .credentials("xxx", "xxx")
                            .build();

            // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
            // 'asiatrip'.
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("mysite")
                            .object("20230726150437.png")
                            .filename("C:\\Users\\zhoupb\\Desktop\\20230726150437.png")
                            .build());
            System.out.println("ok");
        } catch (MinioException | InvalidKeyException e) {
            System.out.println("Error occurred: " + e);
        }
    }

}
