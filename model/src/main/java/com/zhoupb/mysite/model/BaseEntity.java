package com.zhoupb.mysite.model;

import io.mybatis.provider.Entity;

import java.time.OffsetDateTime;

public class BaseEntity {

    @Entity.Column
    private OffsetDateTime createTime;

    @Entity.Column
    private OffsetDateTime updateTime;

    public OffsetDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(OffsetDateTime createTime) {
        this.createTime = createTime;
    }

    public OffsetDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(OffsetDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
