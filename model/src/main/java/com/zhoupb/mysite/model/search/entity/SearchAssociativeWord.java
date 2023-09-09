package com.zhoupb.mysite.model.search.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Document("search_associative_word")
public class SearchAssociativeWord {

    private String id;

    private String word;

    private OffsetDateTime createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public OffsetDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(OffsetDateTime createTime) {
        this.createTime = createTime;
    }
}
