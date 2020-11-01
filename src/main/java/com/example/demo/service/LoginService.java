package com.example.demo.service;

import com.example.demo.model.Account;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    //登录操作
    String isLogin(String accountId, String password);

    int addAccount(Account account);
}
