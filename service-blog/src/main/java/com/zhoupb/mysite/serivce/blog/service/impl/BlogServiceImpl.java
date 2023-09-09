package com.zhoupb.mysite.serivce.blog.service.impl;

import com.zhoupb.mysite.common.util.SensitiveWordFilter;
import com.zhoupb.mysite.model.blog.AuditStatusEnum;
import com.zhoupb.mysite.model.blog.entity.Blog;
import com.zhoupb.mysite.model.blog.entity.BlogConfig;
import com.zhoupb.mysite.model.blog.entity.BlogContent;
import com.zhoupb.mysite.model.blog.entity.SensitiveWord;
import com.zhoupb.mysite.serivce.blog.mapper.BlogConfigMapper;
import com.zhoupb.mysite.serivce.blog.mapper.BlogContentMapper;
import com.zhoupb.mysite.serivce.blog.mapper.BlogMapper;
import com.zhoupb.mysite.serivce.blog.mapper.SensitiveWordMapper;
import com.zhoupb.mysite.serivce.blog.service.BlogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogMapper blogMapper;

    private final BlogContentMapper blogContentMapper;

    private final BlogConfigMapper blogConfigMapper;

    private final SensitiveWordFilter sensitiveWordFilter;

    private final SensitiveWordMapper sensitiveWordMapper;

    public BlogServiceImpl(BlogMapper blogMapper, BlogContentMapper blogContentMapper, BlogConfigMapper blogConfigMapper, SensitiveWordFilter sensitiveWordFilter, SensitiveWordMapper sensitiveWordMapper) {
        this.blogMapper = blogMapper;
        this.blogContentMapper = blogContentMapper;
        this.blogConfigMapper = blogConfigMapper;
        this.sensitiveWordFilter = sensitiveWordFilter;
        this.sensitiveWordMapper = sensitiveWordMapper;
    }

    @Async
    @Override
    public void check(long id) {
        // 查出文章
        Optional<Blog> blog = blogMapper.selectByPrimaryKey(id);
        if (blog.isEmpty())
            return;
        // 查出文章内容
        Optional<BlogContent> blogContent = blogContentMapper.wrapper().eq(BlogContent::getBlogId, id).first();
        if (blogContent.isEmpty())
            return;
        // 查出文章配置
        Optional<BlogConfig> blogConfig = blogConfigMapper.wrapper().eq(BlogConfig::getBlogId, id).first();
        if (blogConfig.isEmpty())
            return;
        // 审核文章
        String tmp = blog.get().getTitle() +
                "--" +
                // raw_content
                blogContent.get().getRawContent() +
                "--" +
                // html_content
                blogContent.get().getHtmlContent() +
                "--" +
                // summary
                blog.get().getSummary() +
                "--" +
                // 关键字
                Arrays.toString(blog.get().getKeywords());
        // 暂时这样初始化敏感词库吧
        sensitiveWordFilter.buildTransitionTable(sensitiveWordMapper.selectList(null).stream().map(SensitiveWord::getWord).collect(Collectors.toSet()));
        Set<String> sensitiveWords = sensitiveWordFilter.findSensitiveWords(tmp);
        if (sensitiveWords.size() > 0) {
            blogConfig.get().setAuditStatus(AuditStatusEnum.FAILURE);
            blogConfig.get().setFailureReason("存在敏感词：" + sensitiveWords);
        } else {
            blogConfig.get().setAuditStatus(AuditStatusEnum.SUCCESS);
            //TODO 审核成功后，放入ElasticSearch中
        }
        blogConfigMapper.updateByPrimaryKeySelective(blogConfig.get());
    }

}
