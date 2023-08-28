package com.zhoupb.mysite.model.blog.entity;

import com.zhoupb.mysite.model.BaseEntity;
import io.mybatis.provider.Entity;
import org.apache.ibatis.type.JdbcType;

@Entity.Table(value = "blog", autoResultMap = true)
public class Blog extends BaseEntity {

    @Entity.Column(id = true, updatable = false)
    protected Long id;

    @Entity.Column
    private String title;

    @Entity.Column
    private Long accountId;

    @Entity.Column
    private String accountNickname;

    @Entity.Column
    private Long categoryId;

    @Entity.Column
    private String categoryName;

    @Entity.Column
    private String cover;

    @Entity.Column(typeHandler = org.apache.ibatis.type.ArrayTypeHandler.class)
    private String[] keywords;

    @Entity.Column
    private Long likes;

    @Entity.Column
    private Long comments;

    @Entity.Column
    private Long views;

    @Entity.Column
    private String summary;

    public Blog() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNickname() {
        return accountNickname;
    }

    public void setAccountNickname(String accountNickname) {
        this.accountNickname = accountNickname;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
