package com.example.demo.controller;

import com.example.demo.mapper.MethodMapper;
import com.example.demo.mapper.StudentsMapper;
import com.example.demo.mapper.TeachersMapper;
import com.example.demo.model.*;
import com.example.demo.service.CourseService;
import com.example.demo.service.GradeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/index")
public class GradeController {
//    @Resource
//    public
    @Resource
    public StudentsMapper studentsMapper;

    @Resource
    public CourseService courseService;

    @Resource
    public TeachersMapper teachersMapper;

    @Resource
    public GradeService gradeService;


    @Resource
    public MethodMapper methodMapper;


    @GetMapping("/gradeManagement")
    public String gradeManagement(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        List<CourseInfo> courseInfoList = new ArrayList<>();
        List<Courses> coursesList = methodMapper.selectAllCourse();
        CourseInfo courseInfo;
        for (Courses c : coursesList) {
            courseInfo = new CourseInfo();
            courseInfo.setCollegeid(c.getCollegeid());
            courseInfo.setMajorid(c.getMajorid());
            courseInfo.setCourseid(c.getCourseid());
            courseInfo.setCoursename(c.getCoursename());
            List<Stucourse> stucourseList = methodMapper.selectStuCourseById(c.getCourseid());
            courseInfo.setSum(String.valueOf(stucourseList.size()));
            courseInfo.setTerm(stucourseList.get(0).getTerm());
            int adopt = 0;
            for (Stucourse s : stucourseList) {
                if (Integer.parseInt(s.getGrade()) > 60) {
                    adopt++;
                }
            }
            courseInfo.setAdopt(String.valueOf(adopt));
            Teachcourse teachcourse = methodMapper.selectTeachCourse(c.getCourseid());
            courseInfo.setCourseDate(teachcourse.getCoursedate());
            courseInfo.setRoom(teachcourse.getCourseroom());
            Teachers teachers = teachersMapper.selectByPrimaryKey(teachcourse.getTeacherid());
            courseInfo.setTeacherName(teachers.getName());
            courseInfoList.add(courseInfo);
        }
        model.addAttribute("courseInfoList1",courseInfoList);
        model.addAttribute("courseInfoList",courseInfoList);
        return "gradeManagement";
    }

    @PostMapping("/gradeManagement")
    public String gradeManagement( Model model,String term,String collegeId,String majorId) {
        List<CourseInfo> courseInfoList = new ArrayList<>();
        List<Courses> coursesList = methodMapper.selectAllCourse();
        CourseInfo courseInfo;
        for (Courses c : coursesList) {
            courseInfo = new CourseInfo();
            courseInfo.setCollegeid(c.getCollegeid());
            courseInfo.setMajorid(c.getMajorid());
            courseInfo.setCourseid(c.getCourseid());
            courseInfo.setCoursename(c.getCoursename());
            List<Stucourse> stucourseList = methodMapper.selectStuCourseById(c.getCourseid());
            courseInfo.setSum(String.valueOf(stucourseList.size()));
            courseInfo.setTerm(stucourseList.get(0).getTerm());
            int adopt = 0;
            for (Stucourse s : stucourseList) {
                if (Integer.parseInt(s.getGrade()) > 60) {
                    adopt++;
                }
            }
            courseInfo.setAdopt(String.valueOf(adopt));
            Teachcourse teachcourse = methodMapper.selectTeachCourse(c.getCourseid());
            courseInfo.setCourseDate(teachcourse.getCoursedate());
            courseInfo.setRoom(teachcourse.getCourseroom());
            Teachers teachers = teachersMapper.selectByPrimaryKey(teachcourse.getTeacherid());
            courseInfo.setTeacherName(teachers.getName());
            courseInfoList.add(courseInfo);
        }
        model.addAttribute("courseInfoList1",courseInfoList);
        List<CourseInfo> courseInfos = new ArrayList<>();
        for(CourseInfo c:courseInfoList){
            if(!c.getCollegeid().equals(collegeId)&&!c.getMajorid().equals(majorId)&&!c.getTerm().equals(term)){
                continue;
            }
            courseInfos.add(c);
        }
        model.addAttribute("courseInfoList",courseInfos);
        return "gradeManagement";
    }

    @GetMapping("/gradeManagement/details/{courseId}")
    public String details(@PathVariable(name = "courseId") String courseId,Model model,HttpSession session){
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        session.setAttribute("courseId", courseId);
        Courses courses = courseService.selectByKey(courseId);
        List<gradeInfo> gradeInfoList = new ArrayList<>();
        List<Stucourse> stuCourseList = courseService.selectStuCourseByCId(courseId);
        gradeInfo gradeInfo;
        for (Stucourse stucourse : stuCourseList) {
            gradeInfo = new gradeInfo();
            gradeInfo.setCourseid(courseId);
            gradeInfo.setCourseName(courses.getCoursename());
            Students students = studentsMapper.selectByPrimaryKey(stucourse.getStudentid());
            gradeInfo.setStudentName(students.getName());
            gradeInfo.setStudentid(students.getStudentid());
            gradeInfo.setTerm(stucourse.getTerm());
            gradeInfo.setGrade(stucourse.getGrade());
            gradeInfoList.add(gradeInfo);
        }
        List<gradeInfo> gradeInfoList1 = gradeService.upGrade(gradeInfoList);
        model.addAttribute("courseId",courseId);
        model.addAttribute("courseName",courses.getCoursename());
        model.addAttribute("gradeInfoList", gradeInfoList1);

        return "details";
    }

    @GetMapping("/gradeManagement/details/{courseId}/{code}")
    public String gradeManagement(HttpSession session, Model model,@PathVariable(name = "courseId") String courseId,@PathVariable(name = "code") String code){
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        session.setAttribute("courseId", courseId);
        Courses courses = courseService.selectByKey(courseId);
        List<gradeInfo> gradeInfoList = new ArrayList<>();
        List<Stucourse> stuCourseList = courseService.selectStuCourseByCId(courseId);
        gradeInfo gradeInfo;
        for (Stucourse stucourse : stuCourseList) {
            gradeInfo = new gradeInfo();
            gradeInfo.setCourseid(courseId);
            gradeInfo.setCourseName(courses.getCoursename());
            Students students = studentsMapper.selectByPrimaryKey(stucourse.getStudentid());
            gradeInfo.setStudentName(students.getName());
            gradeInfo.setStudentid(students.getStudentid());
            gradeInfo.setTerm(stucourse.getTerm());
            gradeInfo.setGrade(stucourse.getGrade());
            gradeInfoList.add(gradeInfo);
        }
        List<gradeInfo> gradeInfoList1 = new ArrayList<>();
        if(code.equals("1")){
            gradeInfoList1 = gradeService.upGrade(gradeInfoList);
        }
        if(code.equals("0")){
            gradeInfoList1 = gradeService.downGrade(gradeInfoList);
        }

        model.addAttribute("courseId",courseId);
        model.addAttribute("courseName",courses.getCoursename());
        model.addAttribute("gradeInfoList", gradeInfoList1);

        return "details";
    }

}