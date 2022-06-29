package com.europa.accounting.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author fengwen
 * @date 2022/6/23
 * @description 寄件人
 **/
@Data
public class ListMemberVO {

    private Integer id;

    /** 姓名 **/
    private String name;

    /** 备注 **/
    private String mark;

    /** 性别 1-男 0-女 **/
    private Integer gender;

    /** 创建时间 **/
    private Date createTime;

    /** 更新时间 **/
    private Date updateTime;

}
