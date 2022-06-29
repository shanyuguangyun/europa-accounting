package com.europa.accounting.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.europa.accounting.exception.ValidationException;
import lombok.Data;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author fengwen
 * @date 2022/6/7
 * @description 帮助校验字段长度等，避免抛出数据库异常给调用方（客户）
 **/
public class ValidatorUtil {

    private ValidatorUtil() {
    }

    /**
     * 验证器
     */
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    /**
     * 校验实体，直接抛出校验异常
     */
    public static <T> void validateAndThrowException(T obj) {
        Set<ConstraintViolation<T>> validateSet = validator.validate(obj, Default.class);
        for (ConstraintViolation<T> constraintViolation : validateSet) {
            // 仅需抛出第一项校验错误
            String message = constraintViolation.getMessage();
            throw new ValidationException(message);
        }
    }

    /**
     * 校验实体，直接抛出校验异常
     */
    public static <T> void validateAndThrowException(T obj, Class<?> groupClazz) {
        Set<ConstraintViolation<T>> validateSet = validator.validate(obj, groupClazz);
        for (ConstraintViolation<T> constraintViolation : validateSet) {
            // 仅需抛出第一项校验错误
            String message = constraintViolation.getMessage();
            throw new ValidationException(message);
        }
    }

    /**
     * 校验实体，返回实体所有属性的校验结果
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> ValidationResult validateEntity(T obj) {
        //解析校验结果
        Set<ConstraintViolation<T>> validateSet = validator.validate(obj, Default.class);
        return buildValidationResult(validateSet);
    }

    /**
     * 校验指定实体的指定属性是否存在异常
     *
     * @param obj
     * @param propertyName
     * @param <T>
     * @return
     */
    public static <T> ValidationResult validateProperty(T obj, String propertyName) {
        Set<ConstraintViolation<T>> validateSet = validator.validateProperty(obj, propertyName, Default.class);
        return buildValidationResult(validateSet);
    }

    /**
     * 将异常结果封装返回
     *
     * @param validateSet
     * @param <T>
     * @return
     */
    private static <T> ValidationResult buildValidationResult(Set<ConstraintViolation<T>> validateSet) {
        ValidationResult validationResult = new ValidationResult();
        if (CollectionUtils.isNotEmpty(validateSet)) {
            validationResult.setHasErrors(true);
            Map<String, String> errorMsgMap = new HashMap<>();
            for (ConstraintViolation<T> constraintViolation : validateSet) {
                errorMsgMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            validationResult.setErrorMsg(errorMsgMap);
        }
        return validationResult;
    }


    @Data
    public static class ValidationResult {

        private Boolean hasErrors;

        private Map<String, String> errorMsg;

    }
}