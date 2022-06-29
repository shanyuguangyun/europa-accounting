package com.europa.accounting.vo;

import com.europa.accounting.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class LoginVO {

    private Integer id;

    private String username;

//    private String password;

    private String token;

    private Date createTime;

    private Date updateTime;

    public static LoginVO from(User user) {
        LoginVO vo = new LoginVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setCreateTime(user.getCreateTime());
        vo.setUpdateTime(user.getUpdateTime());
        return vo;
    }
}
