package com.zhoupb.mysite.serivce.blog.mapper;

import com.zhoupb.mysite.model.blog.entity.BlogCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogCategoryMapper extends io.mybatis.mapper.Mapper<BlogCategory, Long> {
}
