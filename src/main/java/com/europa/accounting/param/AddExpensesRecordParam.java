package com.europa.accounting.param;

import cn.hutool.core.bean.BeanUtil;
import com.europa.accounting.entity.Customer;
import com.europa.accounting.entity.ExpensesRecord;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author fengwen
 * @date 2022/6/22
 * @description 增加寄件人
 **/
@Data
@ToString
public class AddExpensesRecordParam {

    private Integer id;

    /** 姓名 **/
    @NotBlank(message = "姓名不能为空")
    private String name;

    /** 消费金额 **/
    private BigDecimal amount;

    /** 区域 **/
    private String regionJson;

    /** 地址 **/
    @NotBlank(message = "详细地址不能为空")
    private String address;

    /** 记录 **/
    private String mark;

    /** 1-吃喝;2-玩乐;3-交通;4-其他;5-收入 **/
    @NotNull(message = "消费类型不能为空")
    private Integer type;

    public static ExpensesRecord toRecord(AddExpensesRecordParam param) {
        return BeanUtil.copyProperties(param, ExpensesRecord.class);
    }

}
