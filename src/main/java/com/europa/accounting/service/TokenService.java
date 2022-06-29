package com.europa.accounting.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

    public String genToken(Integer id) {
        return UUID.randomUUID().toString().substring(0, 16) + "$" + id;
    }
}
