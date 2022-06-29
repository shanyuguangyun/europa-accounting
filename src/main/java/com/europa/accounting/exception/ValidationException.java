package com.europa.accounting.exception;

import lombok.Data;

/**
 * @author fengwen
 * 基本异常
 */
@Data
public class ValidationException extends RuntimeException {

    private Integer code;

    public ValidationException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public ValidationException(String msg) {
        super(msg);
        this.code = 500;
    }

}
