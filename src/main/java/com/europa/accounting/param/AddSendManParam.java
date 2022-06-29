package com.europa.accounting.param;

import cn.hutool.core.bean.BeanUtil;
import com.europa.accounting.entity.Customer;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author fengwen
 * @date 2022/6/22
 * @description 增加寄件人
 **/
@Data
@ToString
public class AddSendManParam {

    private Integer id;

    /** 姓名 **/
    @NotBlank(message = "姓名不能为空")
    private String name;

    /** 手机 **/
    @NotBlank(message = "手机不能为空")
    private String mobile;
    
    /** 电话 **/
    private String phone;

    /** 区域 **/
    private String regionJson;

    /** 地址 **/
    @NotBlank(message = "详细地址不能为空")
    private String address;

    /** 公司 **/
    private String company;

    public static Customer toCustomer(AddSendManParam param) {
        return BeanUtil.copyProperties(param, Customer.class);
    }

}
