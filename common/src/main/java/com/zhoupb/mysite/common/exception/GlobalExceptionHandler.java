package com.zhoupb.mysite.common.exception;

import com.zhoupb.mysite.common.JSONResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ConditionalOnClass(HttpServletResponse.class)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public JSONResponse handleCustomized(CustomException exception) {
        System.err.println("Global CustomizedException：" + exception.getMessage());
        return JSONResponse.fail(HttpStatus.valueOf(exception.getCode()), exception.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public JSONResponse handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception) {
        return JSONResponse.fail(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JSONResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return JSONResponse.fail(HttpStatus.BAD_REQUEST, exception.getAllErrors().get(0).getDefaultMessage());
    }

//	@ExceptionHandler(ConstraintViolationException.class)
//	public JSONResponse handleConstraintViolationException(ConstraintViolationException exception) {
//		return JSONResponse.fail(HttpStatus.BAD_REQUEST);
//	}

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public JSONResponse handleJsonParseException(HttpMessageNotReadableException exception) {
        return JSONResponse.fail(HttpStatus.BAD_REQUEST, "json解析失败");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public JSONResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        return JSONResponse.fail(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public JSONResponse handleMissingServletRequestParameterException(
            MissingServletRequestParameterException exception) {
        return JSONResponse.fail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * 最后的底线
     */
    @ExceptionHandler(Exception.class)
    public JSONResponse handleException(Exception exception) {
        System.err.println("Global Exception：");
        exception.printStackTrace(System.err);
        return JSONResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
