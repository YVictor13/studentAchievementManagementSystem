package com.example.demo.mapper;

import com.example.demo.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MethodMapper {

    @Select("select * from students")
    List<Students> SelectAll();

    @Select("select * from students where classId=#{classId} or collegeId=#{collegeId} or majorId=#{majorId}")
    List<Students> SelectByCCM(@Param("classId") String classId,@Param("collegeId") String collegeId,@Param("majorId") String majorId);

    @Select("select * from teachers")
    List<Teachers> SelectAllTeacher();

    @Select("select * from teachers where collegeId=#{collegeId}")
    List<Teachers> SelectByC(@Param("collegeId") String collegeId);

    @Select("select * from courses")
    List<Courses> selectAllCourse();

    @Select("select * from courses where collegeId=#{collegeId} or majorId=#{majorId}")
    List<Courses> SelectByCM(@Param("collegeId") String collegeId,@Param("majorId") String majorId);

    @Select("select * from stucourse where courseId=#{courseId}")
    List<Stucourse> selectCourseByCId(@Param("courseId") String courseId);

    @Select("select * from stucourse where studentId=#{studentId}")
    List<Stucourse> SelectByStudentId(@Param("studentId") String studentId);

    @Select("select * from teachcourse where courseId=#{courseId}")
    Teachcourse selectTeachCourse(@Param("courseId") String courseId);

    @Select("select * from stucourse where courseId=#{courseId}")
    List<Stucourse> selectStuCourseById(@Param("courseId") String courseId);

    @Update("update students set password=#{password},political=#{political} where  studentId=#{studentId}")
    void updateStudentInfo(@Param("studentId") String studentId, @Param("password") String password,@Param("political") String political);

    @Update("update account set password=#{password} where  accountId=#{accountId}")
    void updateAccountPassword(@Param("accountId") String accountId, @Param("password") String password);

    @Select("select * from teachcourse where teacherId=#{teacherId}")
    List<Teachcourse> selectTeachCourseList(String teacherId);

    @Update("update teachers set password=#{password},political=#{political} where  teacherId=#{teacherId}")
    void updateTeacherInfo(@Param("teacherId") String teacherId, @Param("password") String password,@Param("political") String political);

    @Update("update stucourse set grade=#{grade}where  studentId=#{studentId} and courseId=#{courseId}")
    void updateStuCourseGrade(String studentId, String courseId, String grade);
}