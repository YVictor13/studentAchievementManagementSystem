package com.example.demo.mapper;

import com.example.demo.model.Courses;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoursesMapper {
    int deleteByPrimaryKey(String courseid);

    int insert(Courses record);

    int insertSelective(Courses record);

    Courses selectByPrimaryKey(String courseid);

    int updateByPrimaryKeySelective(Courses record);

    int updateByPrimaryKey(Courses record);
}