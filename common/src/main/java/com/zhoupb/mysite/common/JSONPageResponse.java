package com.zhoupb.mysite.common;

import java.util.List;

public record JSONPageResponse<T>(int code, List<T> data, String message, long total, int page, int size) {
}
