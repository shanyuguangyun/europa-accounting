package com.europa.accounting.exception;

import com.europa.accounting.common.Result;
import com.europa.accounting.common.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public Result<?> handle(Throwable e) {
        return doHandler(e);
    }

    /**
     * @author fengwen
     * @date 2021-10-14
     * 处理各种突发异常，能预知错误信息就返回，否则返回默认信息
     */
    private Result<?> doHandler(Throwable throwable) {
        if (throwable instanceof ApiException) {
            // 此异常都是手动抛出，应由抛出处记录日志。
            ApiException e = (ApiException) throwable;
            log.error("APIException", e);
            if (e.getCode() != null) {
                return Result.fail(e.getCode(), e.getMessage());
            }
            return Result.fail(ResultEnum.CODER_THROW_ERROR.getCode(), e.getMessage());
        }

        if (throwable instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) throwable;
            BindingResult bindingResult = e.getBindingResult();
            String message = null;
            if (bindingResult.hasErrors()) {
                FieldError fieldError = bindingResult.getFieldError();
                if (fieldError != null) {
                    message = fieldError.getField() + fieldError.getDefaultMessage();
                }
            }
            log.warn("MethodArgumentNotValidException", e);
            return Result.fail(ResultEnum.PARAM_ERROR.getCode(), message);
        }

        if (throwable instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException e = (MethodArgumentTypeMismatchException) throwable;
            log.warn("MethodArgumentTypeMismatchException", e);
            return Result.fail(ResultEnum.PARAM_ERROR.getCode(), ResultEnum.PARAM_ERROR.getMsg());
        }

        if (throwable instanceof BindException) {
            BindException e = (BindException) throwable;
            BindingResult bindingResult = e.getBindingResult();
            String message = null;
            if (bindingResult.hasErrors()) {
                FieldError fieldError = bindingResult.getFieldError();
                if (fieldError != null) {
                    message = fieldError.getField() + fieldError.getDefaultMessage();
                }
            }
            log.warn("BindException", e);
            return Result.fail(ResultEnum.PARAM_ERROR.getCode(), message);
        }

        if (throwable instanceof NullPointerException) {
            NullPointerException e = (NullPointerException) throwable;
            log.warn("NullPointException", e);
            return Result.fail(ResultEnum.INTERNAL_PROCESS_ERROR.getCode(), ResultEnum.INTERNAL_PROCESS_ERROR.getMsg());
        }

        if (throwable instanceof ArithmeticException) {
            ArithmeticException e = (ArithmeticException) throwable;
            log.warn("ArithmeticException", e);
            return Result.fail(ResultEnum.INTERNAL_MATH_ERROR.getCode(), ResultEnum.INTERNAL_MATH_ERROR.getMsg());
        }

        if (throwable instanceof IllegalArgumentException) {
            IllegalArgumentException e = (IllegalArgumentException) throwable;
            log.warn("IllegalArgumentException", e);
            return Result.fail(ResultEnum.INTERNAL_ARGUMENT_ERROR.getCode(), ResultEnum.INTERNAL_ARGUMENT_ERROR.getMsg());
        }

        if (throwable instanceof HttpRequestMethodNotSupportedException) {
            HttpRequestMethodNotSupportedException e = (HttpRequestMethodNotSupportedException) throwable;
            log.warn("HttpRequestMethodNotSupportedException", e);
            return Result.fail(ResultEnum.REQUEST_METHOD_ERROR.getCode(), ResultEnum.REQUEST_METHOD_ERROR.getMsg());
        }

        if (throwable instanceof HttpMediaTypeNotSupportedException) {
            HttpMediaTypeNotSupportedException e = (HttpMediaTypeNotSupportedException) throwable;
            log.warn("HttpMediaTypeNotSupportedException", e);
            return Result.fail(ResultEnum.REQUEST_CONTENT_TYPE_ERROR.getCode(), ResultEnum.REQUEST_CONTENT_TYPE_ERROR.getMsg());
        }

        if (throwable instanceof ValidationException) {
            ValidationException e = (ValidationException) throwable;
            log.warn("ValidationException", e);
            return Result.fail(ResultEnum.PARAM_ERROR.getCode(), e.getMessage());
        }

        log.warn("UnCatchException", throwable);
        return Result.fail(ResultEnum.SYSTEM_ERROR.getCode(), ResultEnum.SYSTEM_ERROR.getMsg());
    }
}
