package com.europa.accounting.param;

import cn.hutool.core.bean.BeanUtil;
import com.europa.accounting.entity.Customer;
import com.europa.accounting.entity.Member;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author fengwen
 * @date 2022/6/28
 * @description TODO
 **/
@Data
@ToString
public class AddMemberParam {

    private Integer id;

    /** 姓名 **/
    @NotBlank(message = "姓名不能为空")
    private String name;

    /** 性别 **/
    private Integer gender;

    /** 地址 **/
    private String mark;

    public static Member toCustomer(AddMemberParam param) {
        return BeanUtil.copyProperties(param, Member.class);
    }

}
