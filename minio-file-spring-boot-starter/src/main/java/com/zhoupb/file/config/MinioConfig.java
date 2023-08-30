package com.zhoupb.file.config;

import com.zhoupb.file.service.FileStorageService;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(FileStorageService.class)
@EnableConfigurationProperties({MinioConfigProperties.class})
public class MinioConfig {

    private final MinioConfigProperties minIOConfigProperties;

    public MinioConfig(MinioConfigProperties minIOConfigProperties) {
        this.minIOConfigProperties = minIOConfigProperties;
    }

    @Bean
    public MinioClient minioClient() {
        return MinioClient
                .builder()
                .credentials(minIOConfigProperties.getAccessKey(), minIOConfigProperties.getSecretKey())
                .endpoint(minIOConfigProperties.getEndpoint())
                .build();
    }

}
