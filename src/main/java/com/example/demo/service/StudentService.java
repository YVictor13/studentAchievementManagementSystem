package com.example.demo.service;

import com.example.demo.model.Students;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {


    List<Students> FindInfoByCCM(String classId, String collegeId, String majorId);

    List<Students> FindAllInfo();

    int InsertInfo(Students students);

    //导入学生信息
    boolean ImportGrade(String fileName, MultipartFile file) throws Exception;

    boolean deleteStuInfo(String studentId);

    Students selectByKey(String studentId);

    int UpdateInfo(Students students);
}