package com.zhoupb.mysite.search.controller.v1;

import com.zhoupb.mysite.common.JSONResponse;
import com.zhoupb.mysite.model.search.entity.AccountSearchHistory;
import com.zhoupb.mysite.search.service.AccountSearchHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/history")
public record SearchHistoryController(AccountSearchHistoryService accountSearchHistoryService) {

    @GetMapping
    public JSONResponse<List<AccountSearchHistory>> get(@RequestHeader long accountId) {
        return JSONResponse.ok(HttpStatus.OK, accountSearchHistoryService.getByAccountId(accountId));
    }

    @DeleteMapping("/{id}")
    public JSONResponse<Void> delete(@RequestHeader long accountId, @PathVariable String id) {
        boolean f = accountSearchHistoryService.deleteByIdAndAccountId(id, accountId);
        if ( !f )
            return JSONResponse.fail(HttpStatus.BAD_REQUEST, "删除失败");
        return JSONResponse.ok(HttpStatus.OK, null, "删除成功");
    }

}
