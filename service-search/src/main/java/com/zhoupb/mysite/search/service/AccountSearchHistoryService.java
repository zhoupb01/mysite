package com.zhoupb.mysite.search.service;

import com.zhoupb.mysite.model.search.entity.AccountSearchHistory;

import java.util.List;

public interface AccountSearchHistoryService {

    void saveSearchHistory(long accountId, String keyword);

    List<AccountSearchHistory> getByAccountId(long accountId);

    /**
     * 根据id和accountId删除
     * 解释一下为什么要两个id：防止给别人的删除别人的
     * @param id id
     * @param accountId accountId
     * @return 是否删除成功
     */
    boolean deleteByIdAndAccountId(String id, long accountId);

}
