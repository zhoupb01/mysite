package com.zhoupb.mysite.search.controller.v1;

import com.zhoupb.mysite.common.JSONPageResponse;
import com.zhoupb.mysite.model.search.entity.SearchBlog;
import com.zhoupb.mysite.search.service.AccountSearchHistoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.CriteriaQueryBuilder;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightFieldParameters;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
public record SearchController(AccountSearchHistoryService accountSearchHistoryService,
                               ElasticsearchTemplate elasticsearchTemplate) {

    @GetMapping
    public JSONPageResponse<SearchBlog> get(@RequestHeader long accountId, @RequestParam String keyword, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "10") int size) {
        Criteria criteria = new Criteria("title").matches(keyword).or(new Criteria("rawContent").matches(keyword)).or(new Criteria("summary").matches(keyword));

        //高亮查询
        List<HighlightField> highlightFieldList = new ArrayList<>();
        HighlightField highlightField = new HighlightField("title", HighlightFieldParameters.builder().withPreTags("<em>").withPostTags("</em>").build());
        highlightFieldList.add(highlightField);
        highlightField = new HighlightField("summary", HighlightFieldParameters.builder().withPreTags("<em>").withPostTags("</em>").build());
        highlightFieldList.add(highlightField);

        Highlight highlight = new Highlight(highlightFieldList);
        HighlightQuery highlightQuery = new HighlightQuery(highlight, SearchBlog.class);

        //构建查询
        CriteriaQueryBuilder builder = new CriteriaQueryBuilder(criteria)
                .withSort(Sort.by(Sort.Direction.DESC,"createTime"))
                .withHighlightQuery(highlightQuery)
                .withPageable(PageRequest.of(page - 1, size));
        CriteriaQuery query = new CriteriaQuery(builder);

        //通过elasticsearchTemplate查询
        SearchHits<SearchBlog> result = elasticsearchTemplate.search(query, SearchBlog.class);
        //处理结果
        List<SearchHit<SearchBlog>> searchHitList = result.getSearchHits();
        List<SearchBlog> resp = new ArrayList<>();
        for( SearchHit<SearchBlog> hit : searchHitList ){
            SearchBlog blog = hit.getContent();
            //将高亮结果添加到返回的结果类中显示
            List<String> titleHighlight = hit.getHighlightField("title");
            if( titleHighlight.size() != 0 )
                blog.setTitle(titleHighlight.get(0));
            List<String> summaryHighlight = hit.getHighlightField("summary");
            if( summaryHighlight.size() != 0 )
                blog.setSummary(summaryHighlight.get(0));
            blog.setRawContent(null);
            resp.add(blog);
        }
        // 保存搜索历史
        accountSearchHistoryService.saveSearchHistory(accountId, keyword);
        return new JSONPageResponse<>(HttpStatus.OK.value(), resp, null, result.getTotalHits(), page, size);
    }

}
