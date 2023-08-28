package com.zhoupb.mysite.serivce.blog.controller.v1;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.yitter.idgen.YitIdHelper;
import com.zhoupb.mysite.api.account.AccountFeignClient;
import com.zhoupb.mysite.common.JSONPageResponse;
import com.zhoupb.mysite.common.JSONResponse;
import com.zhoupb.mysite.common.util.SensitiveWordFilter;
import com.zhoupb.mysite.common.util.StringUtils;
import com.zhoupb.mysite.model.account.entity.Account;
import com.zhoupb.mysite.model.blog.AuditStatusEnum;
import com.zhoupb.mysite.model.blog.dto.BlogPostDTO;
import com.zhoupb.mysite.model.blog.dto.BlogRespDTO;
import com.zhoupb.mysite.model.blog.entity.Blog;
import com.zhoupb.mysite.model.blog.entity.BlogCategory;
import com.zhoupb.mysite.model.blog.entity.BlogConfig;
import com.zhoupb.mysite.model.blog.entity.BlogContent;
import com.zhoupb.mysite.serivce.blog.mapper.BlogCategoryMapper;
import com.zhoupb.mysite.serivce.blog.mapper.BlogConfigMapper;
import com.zhoupb.mysite.serivce.blog.mapper.BlogContentMapper;
import com.zhoupb.mysite.serivce.blog.mapper.BlogMapper;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.*;

@RestController
@RequestMapping("/v1")
public class BlogController {

    private final BlogMapper blogMapper;

    private final BlogCategoryMapper blogCategoryMapper;

    private final BlogConfigMapper blogConfigMapper;

    private final BlogContentMapper blogContentMapper;

    private final AccountFeignClient accountFeignClient;

    private final SensitiveWordFilter sensitiveWordFilter;

    public BlogController(BlogMapper blogMapper, BlogCategoryMapper blogCategoryMapper, BlogConfigMapper blogConfigMapper, BlogContentMapper blogContentMapper, AccountFeignClient accountFeignClient, SensitiveWordFilter sensitiveWordFilter) {
        this.blogMapper = blogMapper;
        this.blogCategoryMapper = blogCategoryMapper;
        this.blogConfigMapper = blogConfigMapper;
        this.blogContentMapper = blogContentMapper;
        this.accountFeignClient = accountFeignClient;
        this.sensitiveWordFilter = sensitiveWordFilter;
    }

    /**
     * 发布文章：client -> API(信息基本校验 -> 设置信息 -> 保存到数据库 -> 审核(异步) -> 返回)。
     * 更新文章：client -> API(信息基本校验 -> 修改信息、修改状态为审核中 -> 保存到数据库 -> 审核(异步) -> 返回)。
     * @param accountId 用户id
     * @param dto 参数
     * @return 响应
     */
    @PostMapping("/content")
    @Transactional
    public JSONResponse<Void> post(@RequestParam long accountId, @RequestBody BlogPostDTO dto) {
        JSONResponse<Account> resp = accountFeignClient.getAccountById(accountId);
        if ( resp.code() / 100 != 2 )
            return JSONResponse.fail(HttpStatus.valueOf(resp.code()), resp.message());

        Optional<BlogCategory> blogCategoryOptional = blogCategoryMapper.selectByPrimaryKey(dto.categoryId());
        if ( blogCategoryOptional.isEmpty() )
            return JSONResponse.fail(HttpStatus.NOT_FOUND, "找不到该分类");
        BlogCategory blogCategory = blogCategoryOptional.get();

        OffsetDateTime now = OffsetDateTime.now();
        Blog blog = new Blog();
        blog.setId(YitIdHelper.nextId());
        blog.setTitle(dto.title());
        blog.setAccountId(accountId);
        blog.setAccountNickname(resp.data().getNickname());
        blog.setCategoryId(dto.categoryId());
        blog.setCategoryName(blogCategory.getName());
        blog.setCover("cover");
        blog.setKeywords(dto.keywords());
        blog.setLikes(0L);
        blog.setComments(0L);
        blog.setViews(0L);
        blog.setCreateTime(now);
        blog.setUpdateTime(now);
        blog.setSummary(dto.summary());
        blogMapper.insert(blog);

        BlogContent blogContent = new BlogContent();
        blogContent.setId(YitIdHelper.nextId());
        blogContent.setBlogId(blog.getId());
        blogContent.setHtmlContent(dto.htmlContent());
        blogContent.setRawContent(dto.rawContent());
        blogContentMapper.insert(blogContent);

        BlogConfig blogConfig = new BlogConfig();
        blogConfig.setId(YitIdHelper.nextId());
        blogConfig.setBlogId(blog.getId());
        blogConfig.setAllowComment(true);
        blogConfig.setDeleted(false);
        blogConfig.setDisplayable(true);
        blogConfig.setAuditStatus(AuditStatusEnum.AUDITING);

        // TODO 将下面这一坨放到RabbitMQ中
        StringBuilder tmp = new StringBuilder();
        // title
        tmp.append(dto.title());
        tmp.append("--");
        // raw_content
        tmp.append(dto.rawContent());
        tmp.append("--");
        // html_content
        tmp.append(dto.htmlContent());
        tmp.append("--");
        // summary
        tmp.append(dto.summary());
        tmp.append("--");
        // 关键字
        tmp.append(Arrays.toString(dto.keywords()));
        // 审核
        Set<String> sensitiveWords = sensitiveWordFilter.findSensitiveWords(tmp.toString());
        if (sensitiveWords.size() > 0) {
            blogConfig.setAuditStatus(AuditStatusEnum.FAILURE);
            blogConfig.setFailureReason("存在敏感词：" + sensitiveWords);
        }
        else
            blogConfig.setAuditStatus(AuditStatusEnum.SUCCESS);

        blogConfigMapper.insert(blogConfig);
        return JSONResponse.ok(HttpStatus.OK, null);
    }

    @GetMapping("/content/{id}")
    public JSONResponse<BlogRespDTO> getById(@PathVariable long id) {
        Optional<BlogConfig> blogConfigOptional = blogConfigMapper.wrapper()
                .eq(BlogConfig::getBlogId, id)
                .eq(BlogConfig::getAuditStatus, AuditStatusEnum.SUCCESS)
                .eq(BlogConfig::getDisplayable, true)
                .first();

        if ( blogConfigOptional.isEmpty() )
            return JSONResponse.fail(HttpStatus.NOT_FOUND);
        Optional<Blog> blogOptional = blogMapper.selectByPrimaryKey(id);
        Optional<BlogContent> blogContentOptional = blogContentMapper.wrapper().eq(BlogContent::getBlogId, id).first();
        if ( blogOptional.isEmpty() || blogContentOptional.isEmpty() )
            return JSONResponse.fail(HttpStatus.NOT_FOUND);
        BlogRespDTO resp = new BlogRespDTO(blogOptional.get(), blogContentOptional.get());
        return JSONResponse.ok(HttpStatus.OK, resp);
    }

    @GetMapping("/list")
    public JSONPageResponse<Blog> list(@RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "10") int size) {
        List<Long> ids = blogConfigMapper.wrapper()
                .eq(BlogConfig::getAuditStatus, AuditStatusEnum.SUCCESS)
                .eq(BlogConfig::getDisplayable, true)
                .stream()
                .map(BlogConfig::getBlogId)
                .toList();

        Page<Blog> pages = PageHelper.startPage(page, size).doSelectPage(() -> blogMapper.selectByFieldList(Blog::getId, ids));
        return new JSONPageResponse<>(pages);
    }

}
