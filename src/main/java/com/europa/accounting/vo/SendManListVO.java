package com.europa.accounting.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author fengwen
 * @date 2022/6/23
 * @description 寄件人
 **/
@Data
public class SendManListVO {

    private Integer id;

    /** 姓名 **/
    private String name;

    /** 手机 **/
    private String mobile;

    /** 电话 **/
    private String phone;

    /** region json **/
    private RegionCode regionCode;

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

    @Data
    public static class RegionCode {

        private String province;

        private String city;

        private String area;

        private String town;
    }
}
