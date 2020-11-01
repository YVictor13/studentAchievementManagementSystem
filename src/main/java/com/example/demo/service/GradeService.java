package com.example.demo.service;

import com.example.demo.model.gradeInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GradeService {

    boolean ImportGrade(String fileName, MultipartFile file, String courseId);

    List<gradeInfo> upGrade(List<gradeInfo> gradeInfoList);

    List<gradeInfo> downGrade(List<gradeInfo> gradeInfoList);
}