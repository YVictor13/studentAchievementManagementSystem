package com.example.demo.service;

import com.example.demo.model.Courses;
import com.example.demo.model.Stucourse;
import com.example.demo.model.Teachcourse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    List<Courses> selectAllCourse();

    List<Courses> FindInfoByCM(String collegeId, String majorId);

    void deleteCourse(String courseId);

    boolean ImportStudent(String fileName, MultipartFile file, String courseId,String term);

    void insert(Courses courses);

    Courses selectByKey(String courseId);

    List<Stucourse> selectStuCourseByCId(String courseId);

    void updateCourseInfo(Courses courses);

    void updateTeachCourseInfo(Teachcourse teachcourse);

    void deleteStuCourse(String courseId);

    void deleteTeachCourse(String courseId);
}
