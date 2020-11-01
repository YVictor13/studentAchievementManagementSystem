package com.example.demo.mapper;

import com.example.demo.model.Stucourse;
import com.example.demo.model.StucourseKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StucourseMapper {
    int deleteByPrimaryKey(StucourseKey key);

    int insert(Stucourse record);

    int insertSelective(Stucourse record);

    Stucourse selectByPrimaryKey(StucourseKey key);

    int updateByPrimaryKeySelective(Stucourse record);

    int updateByPrimaryKey(Stucourse record);
}