package com.zhoupb.mysite.search.config;

import com.zhoupb.mysite.common.convert.MongoOffsetDateTimeReader;
import com.zhoupb.mysite.common.convert.MongoOffsetDateTimeWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new MongoOffsetDateTimeWriter(),
                new MongoOffsetDateTimeReader()
        ));
    }

}
