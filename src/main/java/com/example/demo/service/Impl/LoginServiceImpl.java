package com.example.demo.service.Impl;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.Account;
import com.example.demo.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    public AccountMapper accountMapper;

    @Override
    public String isLogin(String accountId, String password) {
        if(accountMapper.selectByPrimaryKey(accountId)!=null&&accountMapper.selectByPrimaryKey(accountId).getPassword().equals(password)){
            return accountMapper.selectByPrimaryKey(accountId).getType();
        }
        return "-1";
    }

    @Override
    public int addAccount(Account account) {
        try {
            accountMapper.insert(account);
            return 1;
        }catch (Exception e){
            System.out.println("此账号已存在！！");
            return 0;
        }

    }
}