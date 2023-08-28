package com.zhoupb.mysite.serivce.blog.config;

import com.zhoupb.mysite.common.util.SensitiveWordFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class Beans {

    @Bean
    public SensitiveWordFilter sensitiveWordUtil() {
        return new SensitiveWordFilter();
    }

}
