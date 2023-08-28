package com.zhoupb.mysite.serivce.blog.mapper;

import com.zhoupb.mysite.model.blog.entity.BlogConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BlogConfigMapper extends io.mybatis.mapper.Mapper<BlogConfig, Long> {
}
