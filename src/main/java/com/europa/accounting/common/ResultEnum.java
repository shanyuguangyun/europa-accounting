package com.europa.accounting.common;

import lombok.Getter;

@Getter
public enum ResultEnum {

    INTERNAL_PROCESS_ERROR(500, "内部处理出现了点问题"),
    INTERNAL_MATH_ERROR(501, "内部数值运算出现了点问题"),
    INTERNAL_ARGUMENT_ERROR(502, "内部参数出现了点问题"),
    CODER_THROW_ERROR(503, "业务逻辑异常"),

    SUCCESS(200, "操作成功"),

    // 服务外部异常
    PARAM_ERROR(401, "传递参数出现了点问题"),
    REQUEST_METHOD_ERROR(405, "请求方式有问题，请检查POST/GET"),
    REQUEST_CONTENT_TYPE_ERROR(415, "请求方式有问题，请检查Content-type"),
    TOKEN_ERROR(403, "用户身份出现了点问题"),
    SYSTEM_ERROR(500, "系统内部出错了");


    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
