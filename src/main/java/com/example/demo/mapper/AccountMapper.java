package com.example.demo.mapper;

import com.example.demo.model.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {
    int deleteByPrimaryKey(String accountid);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(String accountid);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);
}