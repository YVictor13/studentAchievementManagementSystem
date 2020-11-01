package com.example.demo.service;

import com.example.demo.model.Teachers;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TeacherService {
    List<Teachers> FindAllInfo();

    List<Teachers> FindInfoByC(String collegeId);

    int InsertInfo(Teachers teachers);

    boolean ImportTeacherInfo(String fileName, MultipartFile file);

    int deleteTeacherInfo(String teacherId);

    Teachers selectByKey(String teacherId);

    int UpdateInfo(Teachers teachers);
}
