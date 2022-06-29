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
@TableName(value = "member")
public class Member implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 姓名 **/
    private String name;

    /** 性别 **/
    private Integer gender;

    /** 备注 **/
    private String mark;

    /** 删除标识 **/
    private Boolean deleted;

    /** 创建时间 **/
    private Date createTime;

    /** 更新时间 **/
    private Date updateTime;
}
