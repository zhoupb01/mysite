package com.zhoupb.mysite.serivce.blog.service;

public interface BlogService {

    /**
     * 异步审核文章
     * 根据文章id审核
     * @param id 文章id
     */
    void check(long id);

}
