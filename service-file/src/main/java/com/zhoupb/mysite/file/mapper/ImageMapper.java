package com.zhoupb.mysite.file.mapper;

import com.zhoupb.mysite.model.file.entity.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper extends io.mybatis.mapper.Mapper<Image, Long> {
}
