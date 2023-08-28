package com.zhoupb.mysite.model.blog.entity;

import com.zhoupb.mysite.model.BaseEntity;
import io.mybatis.provider.Entity;

@Entity.Table("blog_category")
public class BlogCategory extends BaseEntity {

    @Entity.Column(id = true, insertable = false, updatable = false)
    protected Long id;

    @Entity.Column
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
