package com.europa.accounting.common;

import lombok.Data;
import org.slf4j.MDC;

/**
 * @author Administrator
 */
@Data
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    private String traceId;

    public Result() {

    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.traceId = MDC.get("traceId");
    }

    public Result(ResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMsg();
        this.data = data;
        this.traceId = MDC.get("traceId");
    }

    public static<T> Result<T> ok(T t) {
        return new Result<>(ResultEnum.SUCCESS, t);
    }

    public static<T> Result<T> fail() {
        return new Result<>(ResultEnum.INTERNAL_PROCESS_ERROR, null);
    }

    public static<T> Result<T> fail(String message) {
        return new Result<>(ResultEnum.INTERNAL_PROCESS_ERROR.getCode(), message, null);
    }

    public static<T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    public static<T> Result<T> fail(ResultEnum resultEnum) {
        return new Result<>(resultEnum, null);
    }
}
