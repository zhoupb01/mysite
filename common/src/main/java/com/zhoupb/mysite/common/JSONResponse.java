package com.zhoupb.mysite.common;

import org.springframework.http.HttpStatus;

public record JSONResponse<T>(int code, T data, String message) {
	
	public static <T> JSONResponse<T> ok(HttpStatus status, T data, String message) {
		return new JSONResponse<>(status.value(), data, message);
	}
	
	public static <T> JSONResponse<T> ok(HttpStatus status, T data) {
		return new JSONResponse<>(status.value(), data, status.getReasonPhrase());
	}
	
	public static <T> JSONResponse<T> fail(HttpStatus status, String message) {
		return new JSONResponse<>(status.value(), null, message);
	}
	
	public static <T> JSONResponse<T> fail(HttpStatus status) {
		return new JSONResponse<>(status.value(), null, status.getReasonPhrase());
	}

}
