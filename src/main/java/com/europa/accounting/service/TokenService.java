package com.europa.accounting.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.europa.accounting.exception.ApiException;
import com.europa.accounting.vo.UserRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class TokenService {

    private final static Integer TOKEN_EXPIRED_TIMING = 60;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String genToken(Integer id) {
        String token = UUID.randomUUID().toString().substring(0, 16).replaceAll("-", "") + "$" + id;
        Boolean result = redisTemplate.opsForValue().setIfAbsent(token,
                String.valueOf(id),
                Duration.ofMinutes(TOKEN_EXPIRED_TIMING));
        if (result == null || !result) {
            throw new ApiException("获取token错误，请重试");
        }
        return token;
    }

    public UserRoleVO getUserByToken(String token) {
        String id = redisTemplate.opsForValue().get(token);
        if (StringUtils.isEmpty(id)) {
            throw new ApiException("获取用户信息失败");
        }
        UserRoleVO vo = new UserRoleVO();
        vo.setId(Integer.parseInt(id));
        vo.setRole("1".equals(id) ? "admin" : "user");
        return vo;
    }
}
