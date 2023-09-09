package com.zhoupb.mysite.serivce.blog.mapper;

import com.zhoupb.mysite.model.blog.entity.SensitiveWord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SensitiveWordMapper extends io.mybatis.mapper.Mapper<SensitiveWord, Long> {
}
