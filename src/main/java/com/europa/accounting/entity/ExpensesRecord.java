package com.europa.accounting.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fengwen
 * @date 2022/6/23
 * @description 消费记录
 **/
@Data
@TableName(value = "expenses_record")
public class ExpensesRecord implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 姓名 **/
    private String name;

    /** 电话 **/
    private BigDecimal amount;

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

    /** 备注 **/
    private String mark;

    /** 1-吃喝;2-玩乐;3-交通;4-其他;5-收入 **/
    private Integer type;
    
    /** 创建时间 **/
    private Date createTime;

    /** 更新时间 **/
    private Date updateTime;
}
