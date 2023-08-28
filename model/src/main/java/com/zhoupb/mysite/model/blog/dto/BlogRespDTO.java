package com.zhoupb.mysite.model.blog.dto;

import com.zhoupb.mysite.model.blog.entity.Blog;
import com.zhoupb.mysite.model.blog.entity.BlogContent;

import java.time.OffsetDateTime;

public record BlogRespDTO(long id, String title, String accountNickname, String categoryName, String cover, String[] keywords, long likes, long comments, long views, OffsetDateTime createTime, String htmlContent) {

    public BlogRespDTO(Blog blog, BlogContent blogContent) {
        this(blog.getId(), blog.getTitle(), blog.getAccountNickname(), blog.getCategoryName(), blog.getCover(), blog.getKeywords(), blog.getLikes(), blog.getComments(), blog.getViews(), blog.getCreateTime(), blogContent.getHtmlContent());
    }

}
