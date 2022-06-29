package com.europa.accounting.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fengwen
 * @date 2022/6/23
 * @description 寄件人、收件人
 **/
@Data
@TableName(value = "customer")
public class Customer implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 姓名 **/
    private String name;

    /** 手机 **/
    private String mobile;

    /** 电话 **/
    private String phone;

    /** regionCode json **/
    private String regionCode;

    /** 省份、直辖区 **/
    private String province;

    /** 市 **/
    private String city;

    /** 区 **/
    private String area;

    /** 城镇 **/
    private String town;

    /** 详细地址 **/
    private String address;

    /** 公司 **/
    private String company;

    /** 类型 1-寄件人 2-收件人 **/
    private Integer type;

    /** 创建时间 **/
    private Date createTime;

    /** 更新时间 **/
    private Date updateTime;
}
