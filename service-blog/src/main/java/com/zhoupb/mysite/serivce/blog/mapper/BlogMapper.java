package com.zhoupb.mysite.serivce.blog.mapper;

import com.zhoupb.mysite.model.blog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlogMapper extends io.mybatis.mapper.Mapper<Blog, Long> {

    @Select("select b.* from blog b inner join blog_config bc on b.id = bc.blog_id where bc.audit_status= 'SUCCESS' and bc.displayable = true and bc.deleted = false  order by b.create_time desc")
    List<Blog> selectDisplayable();

}
