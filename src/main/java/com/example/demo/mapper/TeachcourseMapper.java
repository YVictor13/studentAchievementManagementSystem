package com.example.demo.mapper;

import com.example.demo.model.Teachcourse;
import com.example.demo.model.TeachcourseKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeachcourseMapper {
    int deleteByPrimaryKey(TeachcourseKey key);

    int insert(Teachcourse record);

    int insertSelective(Teachcourse record);

    Teachcourse selectByPrimaryKey(TeachcourseKey key);

    int updateByPrimaryKeySelective(Teachcourse record);

    int updateByPrimaryKey(Teachcourse record);
}