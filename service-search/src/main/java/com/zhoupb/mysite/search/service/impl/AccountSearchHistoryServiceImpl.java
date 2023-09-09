package com.zhoupb.mysite.search.service.impl;

import com.zhoupb.mysite.model.search.entity.AccountSearchHistory;
import com.zhoupb.mysite.search.service.AccountSearchHistoryService;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class AccountSearchHistoryServiceImpl implements AccountSearchHistoryService {

    private final MongoTemplate mongoTemplate;

    public AccountSearchHistoryServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Async
    @Override
    public void saveSearchHistory(long accountId, String keyword) {
        /*
         * 只保留前搜索10条
         */
        // 查询用户是否有这条记录
        Query query = Query.query(Criteria.where("accountId").is(accountId).and("keyword").is(keyword));
        AccountSearchHistory accountSearchHistory = mongoTemplate.findOne(query, AccountSearchHistory.class);
        // 有就更新搜索时间
        if ( accountSearchHistory != null ) {
            accountSearchHistory.setCreateTime(OffsetDateTime.now());
            mongoTemplate.save(accountSearchHistory);
            return;
        }
        // 没有就看有没有10条记录
        accountSearchHistory = new AccountSearchHistory();
        accountSearchHistory.setAccountId(accountId);
        accountSearchHistory.setKeyword(keyword);
        accountSearchHistory.setCreateTime(OffsetDateTime.now());
        // 没超过10条就直接插入
        long count = mongoTemplate.count(Query.query(Criteria.where("accountId").is(accountId)), AccountSearchHistory.class);
        if ( count >= 10 ) {
            // 超过了就删除最早的一条，再插入
            query = Query.query(Criteria.where("accountId").is(accountId)).with(Sort.by(Sort.Direction.ASC, "createTime")).limit(1);
            mongoTemplate.remove(query, AccountSearchHistory.class);
        }
        mongoTemplate.save(accountSearchHistory);
    }

    @Override
    public List<AccountSearchHistory> getByAccountId(long accountId) {
        Query query = Query.query(Criteria.where("accountId").is(accountId)).with(Sort.by(Sort.Direction.DESC, "createTime"));
        return mongoTemplate.find(query, AccountSearchHistory.class);
    }

    @Override
    public boolean deleteByIdAndAccountId(String id, long accountId) {
        return mongoTemplate.remove(Query.query(Criteria.where("id").is(id).and("accountId").is(accountId)), AccountSearchHistory.class).getDeletedCount() > 0;
    }
}
