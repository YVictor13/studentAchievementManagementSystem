package com.example.demo.service.Impl;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.mapper.MethodMapper;
import com.example.demo.mapper.StucourseMapper;
import com.example.demo.mapper.StudentsMapper;
import com.example.demo.model.*;
import com.example.demo.service.GradeService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    @Resource
    public StudentsMapper studentsMapper;

    @Resource
    public AccountMapper accountMapper;

    @Resource
    public StucourseMapper stucourseMapper;

    @Resource
    public MethodMapper methodMapper;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public boolean ImportGrade(String fileName, MultipartFile file, String courseId) {
        boolean notNull = false;
        List<Stucourse> stucourseList = new ArrayList<>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            System.out.println("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Workbook wb = null;
        if (isExcel2003) {
            try {
                wb = new HSSFWorkbook(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                wb = new XSSFWorkbook(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null) {
            notNull = true;
        }

        Stucourse stucourse;
        assert sheet != null;
        String studentId = null;

        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            stucourse = new Stucourse();
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            studentId = row.getCell(0).getStringCellValue();
            if (studentId == null || studentId.isEmpty()) {
                return false;
            } else {
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                String grade = row.getCell(1).getStringCellValue();
                if (grade == null || grade.isEmpty()) {
                    return false;
                } else {
                    stucourse.setStudentid(studentId);
                    stucourse.setCourseid(courseId);
                    stucourse.setGrade(grade);
                    stucourseList.add(stucourse);
                }

            }
        }

        for (Stucourse s : stucourseList) {
            StucourseKey key = new StucourseKey();
            key.setStudentid(s.getStudentid());
            key.setCourseid(s.getCourseid());
            if (studentsMapper.selectByPrimaryKey(s.getStudentid()) == null || accountMapper.selectByPrimaryKey(s.getStudentid()) == null || stucourseMapper.selectByPrimaryKey(key) == null) {
                return false;
            }
            methodMapper.updateStuCourseGrade(s.getStudentid(), s.getCourseid(), s.getGrade());
        }
        return true;
    }

    //升序
    @Override
    public List<gradeInfo> upGrade(List<gradeInfo> gradeInfoList) {
        Collections.sort(gradeInfoList, new Comparator<gradeInfo>() {
            @Override
            public int compare(gradeInfo g1, gradeInfo g2) {
                if (Integer.parseInt(g1.getGrade()) > Integer.parseInt(g2.getGrade())) {
                    return -1;
                }
                if (Integer.parseInt(g1.getGrade()) == Integer.parseInt(g2.getGrade())) {
                    return 0;
                }
                return 1;
            }
        });

        return gradeInfoList;
    }

    @Override
    public List<gradeInfo> downGrade(List<gradeInfo> gradeInfoList) {
        Collections.sort(gradeInfoList, new Comparator<gradeInfo>() {
            @Override
            public int compare(gradeInfo g1, gradeInfo g2) {
                if (Integer.parseInt(g1.getGrade()) > Integer.parseInt(g2.getGrade())) {
                    return 1;
                }
                if (Integer.parseInt(g1.getGrade()) == Integer.parseInt(g2.getGrade())) {
                    return 0;
                }
                return -1;
            }
        });

        return gradeInfoList;
    }
}

