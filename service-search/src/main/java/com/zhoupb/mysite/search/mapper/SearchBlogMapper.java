package com.zhoupb.mysite.search.mapper;

import com.zhoupb.mysite.model.search.entity.SearchBlog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;

@Mapper
public interface SearchBlogMapper {

    @Select("select b.id, b.title, bc.raw_content, b.summary, b.account_id, b.account_nickname, b.category_id, b.category_name, b.cover, b.create_time\n" +
            "from blog b inner join blog_content bc on b.id = bc.blog_id")
    Cursor<SearchBlog> getAll();

}
