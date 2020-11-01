package com.example.demo.mapper;

import com.example.demo.model.Teachers;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeachersMapper {
    int deleteByPrimaryKey(String teacherid);

    int insert(Teachers record);

    int insertSelective(Teachers record);

    Teachers selectByPrimaryKey(String teacherid);

    int updateByPrimaryKeySelective(Teachers record);

    int updateByPrimaryKey(Teachers record);
}