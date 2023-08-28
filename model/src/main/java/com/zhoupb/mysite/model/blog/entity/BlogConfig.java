package com.zhoupb.mysite.model.blog.entity;

import com.zhoupb.mysite.model.blog.AuditStatusEnum;
import io.mybatis.provider.Entity;

@Entity.Table("blog_config")
public class BlogConfig {

    @Entity.Column(id = true, updatable = false)
    protected Long id;

    @Entity.Column
    private Long blogId;

    @Entity.Column
    private Boolean allowComment;

    @Entity.Column
    private Boolean deleted;

    @Entity.Column
    private Boolean displayable;

    @Entity.Column
    private AuditStatusEnum auditStatus;

    @Entity.Column
    private String failureReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Boolean getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(Boolean allowComment) {
        this.allowComment = allowComment;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getDisplayable() {
        return displayable;
    }

    public void setDisplayable(Boolean displayable) {
        this.displayable = displayable;
    }

    public AuditStatusEnum getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(AuditStatusEnum auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}
