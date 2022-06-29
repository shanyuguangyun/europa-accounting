package com.europa.accounting.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String username;

    private String password;

    private Date createTime;

    private Date updateTime;
}
