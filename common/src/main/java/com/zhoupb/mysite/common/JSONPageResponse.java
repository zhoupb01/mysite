package com.zhoupb.mysite.common;

import com.github.pagehelper.Page;
import org.springframework.http.HttpStatus;

import java.util.List;

public record JSONPageResponse<T>(int code, List<T> data, String message, long total, int page, int size) {

    public JSONPageResponse(Page<T> page) {
        this(HttpStatus.OK.value(), page.getResult(), null, page.getTotal(), page.getPageNum(), page.getPageSize());
    }

    public JSONPageResponse(HttpStatus status, Page<T> page, String message) {
        this(HttpStatus.OK.value(), page.getResult(), message, page.getTotal(), page.getPageNum(), page.getPageSize());
    }
}
