package com.zhoupb.mysite.model.file.entity;

import com.zhoupb.mysite.model.BaseEntity;
import io.mybatis.provider.Entity;

import java.time.OffsetDateTime;

@Entity.Table("image")
public class Image extends BaseEntity {

    @Entity.Column(id = true, updatable = false)
    private Long id;

    @Entity.Column
    private String directory;

    @Entity.Column
    private String name;

    @Entity.Column
    private Long accountId;

    private String bucket;

    public Image() {
    }

    public Image(Long id, String directory, String name, Long accountId, OffsetDateTime createTime, OffsetDateTime updateTime, String bucket) {
        this.id = id;
        this.directory = directory;
        this.name = name;
        this.accountId = accountId;
        setCreateTime(createTime);
        setUpdateTime(updateTime);
        this.bucket = bucket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getPath() {
        return directory + "/" + name;
    }
}
