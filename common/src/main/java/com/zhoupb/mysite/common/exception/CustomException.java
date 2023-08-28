package com.zhoupb.mysite.common.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class CustomException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1633238144840274355L;

    private final int code;

    public CustomException(int status, String message) {
        super(message);
        this.code = status;
    }

    public CustomException(HttpStatus status, String message) {
        super(message);
        this.code = status.value();
    }

    public int getCode() {
        return code;
    }

}
