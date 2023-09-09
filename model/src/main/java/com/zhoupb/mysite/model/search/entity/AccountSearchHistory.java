package com.zhoupb.mysite.model.search.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Document("account_search_history")
public class AccountSearchHistory {

    private String id;

    private Long accountId;

    private String keyword;

    private OffsetDateTime createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public OffsetDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(OffsetDateTime createTime) {
        this.createTime = createTime;
    }
}
