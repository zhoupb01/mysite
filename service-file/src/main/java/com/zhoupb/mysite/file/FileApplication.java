package com.zhoupb.mysite.file;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FileApplication {

    public static void main(String[] args) {
        IdGeneratorOptions options = new IdGeneratorOptions((short) 2);
        options.SeqBitLength = 10;
        YitIdHelper.setIdGenerator(options);
        SpringApplication.run(FileApplication.class, args);
    }

}
