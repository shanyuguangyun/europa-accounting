package com.europa.accounting.exception;

import lombok.Data;

/**
 * @author fengwen
 * 基本异常
 */
@Data
public class ApiException extends RuntimeException {

    private Integer code;

    public ApiException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public ApiException(String msg) {
        super(msg);
        this.code = 500;
    }

}
