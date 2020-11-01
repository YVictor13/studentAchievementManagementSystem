package com.example.demo.service.Impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.demo.mapper.*;
import com.example.demo.model.*;
import com.example.demo.service.CourseService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    public MethodMapper methodMapper;

    @Resource
    public CoursesMapper coursesMapper;

    @Resource
    public StucourseMapper stucourseMapper;

    @Resource
    public StudentsMapper studentsMapper;

    @Resource
    public TeachcourseMapper teachcourseMapper;


    @Override
    public List<Courses> selectAllCourse() {
        return methodMapper.selectAllCourse();
    }

    @Override
    public List<Courses> FindInfoByCM(String collegeId, String majorId) {
        return methodMapper.SelectByCM(collegeId, majorId);
    }

    @Override
    public void deleteCourse(String courseId) {
        try {
            coursesMapper.deleteByPrimaryKey(courseId);
            return;
        } catch (Exception e) {
            System.out.println("删除失败！！");
        }
    }

    @Override
    public boolean ImportStudent(String fileName, MultipartFile file, String courseId,String term) {
        boolean notNull = false;
        List<Stucourse> stuCourseList = new ArrayList<Stucourse>();
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
                stucourse.setStudentid(studentId);
                stucourse.setCourseid(courseId);
                stucourse.setTerm(term);
                stucourse.setGrade("0");
                stuCourseList.add(stucourse);
            }
        }

        for (Stucourse s : stuCourseList) {
            if (studentsMapper.selectByPrimaryKey(s.getStudentid()) == null) {
                return false;
            }
            stucourseMapper.insert(s);
        }
        return true;
    }

    @Override
    public void insert(Courses courses) {
        try {
            coursesMapper.insert(courses);
            return ;
        }catch (Exception e){
            System.out.println("添加课程失败");
        }

    }

    @Override
    public Courses selectByKey(String courseId) {
        return coursesMapper.selectByPrimaryKey(courseId);
    }

    @Override
    public List<Stucourse> selectStuCourseByCId(String courseId) {
        return methodMapper.selectCourseByCId(courseId);
    }

    @Override
    public void updateCourseInfo(Courses courses) {
        Courses courses1 = coursesMapper.selectByPrimaryKey(courses.getCourseid());
        if(courses.getCollegeid().equals("")){
            courses.setCollegeid(courses1.getCollegeid());
        }
        if(courses.getMajorid().equals("")){
            courses.setMajorid(courses1.getCollegeid());
        }
        if(courses.getCoursename().equals("")){
            courses.setCoursename(courses1.getCoursename());
        }
        try{
            coursesMapper.updateByPrimaryKey(courses);
            return ;
        }catch (Exception e){
            System.out.println("更新失败！！");
        }
    }

    @Override
    public void updateTeachCourseInfo(Teachcourse teachcourse) {
        
        Teachcourse teachcourse1 = methodMapper.selectTeachCourse(teachcourse.getCourseid());
        teachcourse.setCourseid(teachcourse1.getCourseid());
        if(teachcourse.getTerm().equals("")){
            teachcourse.setTerm(teachcourse1.getTerm());
        }
        if(teachcourse.getCoursedate().equals("")||teachcourse.getCoursedate()==null){
            teachcourse.setCoursedate(teachcourse1.getCoursedate());
        }
        if(teachcourse.getCourseroom().equals("")||teachcourse.getCourseroom()==null){
            teachcourse.setCourseroom(teachcourse1.getCourseroom());
        }
        teachcourseMapper.updateByPrimaryKey(teachcourse);
    }

    @Override
    public void deleteStuCourse(String courseId) {
        List<Stucourse> stucourseList = methodMapper.selectStuCourseById(courseId);
        for(Stucourse stucourse:stucourseList){
            StucourseKey key = new StucourseKey();
            key.setStudentid(stucourse.getStudentid());
            key.setCourseid(stucourse.getCourseid());
            stucourseMapper.deleteByPrimaryKey(key);
        }
    }

    @Override
    public void deleteTeachCourse(String courseId) {
        Teachcourse teachcourse = methodMapper.selectTeachCourse(courseId);
        TeachcourseKey key = new TeachcourseKey();
        key.setTeacherid(teachcourse.getTeacherid());
        key.setCourseid(teachcourse.getCourseid());
        teachcourseMapper.deleteByPrimaryKey(key);
    }

}