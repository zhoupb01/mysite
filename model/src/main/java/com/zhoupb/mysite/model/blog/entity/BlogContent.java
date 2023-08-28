package com.zhoupb.mysite.model.blog.entity;

import io.mybatis.provider.Entity;

@Entity.Table("blog_content")
public class BlogContent {

    @Entity.Column(id = true, updatable = false)
    protected Long id;

    @Entity.Column
    private String rawContent;

    @Entity.Column
    private Long blogId;

    @Entity.Column
    private String htmlContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
