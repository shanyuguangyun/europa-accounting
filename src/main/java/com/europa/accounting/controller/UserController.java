package com.europa.accounting.controller;


import com.europa.accounting.common.Result;
import com.europa.accounting.entity.User;
import com.europa.accounting.mapper.UserMapper;
import com.europa.accounting.param.LoginParam;
import com.europa.accounting.service.TokenService;
import com.europa.accounting.vo.LoginVO;
import com.europa.accounting.vo.UserRoleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.europa.accounting.common.Result.fail;
import static com.europa.accounting.common.Result.ok;


@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenService tokenService;

    @PostMapping("login")
    public Result<LoginVO> login(@RequestBody LoginParam param) {
        String username = param.getUsername();
        User user = userMapper.findByUsername(username);
        if (user == null) {
            return fail("用户名错误");
        }
        if (user.getPassword().equals(param.getPassword())) {
            LoginVO vo = LoginVO.from(user);
            vo.setToken(tokenService.genToken(user.getId()));
            return ok(vo);
        }
        return fail("密码错误");
    }

    @GetMapping("getByToken")
    public Result<UserRoleVO> getUserByToken(@RequestParam String token) {
        UserRoleVO vo = tokenService.getUserByToken(token);
        return Result.ok(vo);
    }

    @PostMapping("regist")
    public Result<String> regist(@RequestBody LoginParam param) {
        String username = param.getUsername();
        User user = userMapper.findByUsername(username);
        if (user != null) {
            return fail("用户名已存在");
        }
        User iUser = param.toUser();
        int row = userMapper.insert(iUser);
        return ok("注册成功");
    }

    @GetMapping("test")
    public Result<String> test() {
        log.info("测试");
        return ok("ceshi");
    }
}
