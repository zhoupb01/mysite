package com.zhoupb.mysite.serivce.blog.mapper;

import com.zhoupb.mysite.model.blog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogMapper extends io.mybatis.mapper.Mapper<Blog, Long> {
}
