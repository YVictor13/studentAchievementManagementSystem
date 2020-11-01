package com.example.demo.mapper;

import com.example.demo.model.Students;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentsMapper {
    int deleteByPrimaryKey(String studentid);

    int insert(Students record);

    int insertSelective(Students record);

    Students selectByPrimaryKey(String studentid);

    int updateByPrimaryKeySelective(Students record);

    int updateByPrimaryKey(Students record);
}