package com.zhoupb.mysite.model.blog.entity;

import com.zhoupb.mysite.model.BaseEntity;
import io.mybatis.provider.Entity;

@Entity.Table("sensitive_word")
public class SensitiveWord extends BaseEntity {

    @Entity.Column(id = true, updatable = false)
    private Long id;

    @Entity.Column
    private String word;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
