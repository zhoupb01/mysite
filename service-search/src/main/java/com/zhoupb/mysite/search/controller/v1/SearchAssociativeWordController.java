package com.zhoupb.mysite.search.controller.v1;

import com.zhoupb.mysite.common.JSONResponse;
import com.zhoupb.mysite.model.search.entity.SearchAssociativeWord;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/v1/associative")
public record SearchAssociativeWordController(MongoTemplate mongoTemplate) {

    @GetMapping("/{keyword}")
    public JSONResponse<List<SearchAssociativeWord>> get(@PathVariable String keyword) {
        Pattern pattern = Pattern.compile("^.*" + keyword + ".*$", Pattern.CASE_INSENSITIVE);
        List<SearchAssociativeWord> resp = mongoTemplate.find(Query.query(Criteria.where("word").regex(pattern)).limit(10), SearchAssociativeWord.class);
        return JSONResponse.ok(HttpStatus.OK, resp);
    }
}
