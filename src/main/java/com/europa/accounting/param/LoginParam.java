package com.europa.accounting.param;

import com.europa.accounting.entity.User;
import lombok.Data;

@Data
public class LoginParam {

    private String username;

    private String password;

    private Boolean rememberMe;

    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}
