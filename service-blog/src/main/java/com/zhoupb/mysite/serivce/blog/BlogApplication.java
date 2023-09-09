package com.zhoupb.mysite.serivce.blog;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zhoupb.mysite.api")
@EnableAsync
public class BlogApplication {

    public static void main(String[] args) {
        IdGeneratorOptions options = new IdGeneratorOptions((short) 1);
        options.SeqBitLength = 10;
        YitIdHelper.setIdGenerator(options);
        SpringApplication.run(BlogApplication.class, args);
    }

}
