package com.example.demo.controller;

import com.example.demo.mapper.*;
import com.example.demo.model.*;
import com.example.demo.service.CourseService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/index")
public class CourseController {

    @Resource
    public CourseService courseService;
    @Resource
    public StucourseMapper stucourseMapper;

    @Resource
    public StudentsMapper studentsMapper;
    @Resource
    public MethodMapper methodMapper;

    @Resource
    public TeachcourseMapper teachcourseMapper;



    @GetMapping("/courseManagement")
    public String courseManagement(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        List<Courses> allCoursesList = courseService.selectAllCourse();
        model.addAttribute("allCoursesListLength", allCoursesList.size());
        model.addAttribute("allCoursesList", allCoursesList);
        return "courseManagement";
    }

    @PostMapping("/courseManagement")
    public String courseManagement(Model model, String collegeId, String majorId) {
        if (collegeId.equals("0") && majorId.equals("0")) {
            List<Courses> allCoursesList = courseService.selectAllCourse();
            model.addAttribute("allCoursesListLength", allCoursesList.size());
            model.addAttribute("allCoursesList", allCoursesList);
            return "courseManagement";
        }
        List<Courses> coursesList = courseService.FindInfoByCM(collegeId, majorId);
        model.addAttribute("coursesListLength", coursesList.size());
        model.addAttribute("coursesList", coursesList);

        return "courseManagement";
    }

    @PostMapping("/courseManagement/deleteCourse")
    public String deleteCourse(@RequestParam(name = "courseId") String courseId) {
        courseService.deleteStuCourse(courseId);
        courseService.deleteTeachCourse(courseId);
        courseService.deleteCourse(courseId);
        return "redirect:/index/courseManagement";
    }

    @GetMapping("/courseManagement/addCourse")
    public String addCourse(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        List<Teachers> teachersList = methodMapper.SelectAllTeacher();
        model.addAttribute("teachersList", teachersList);
        return "addCourse";
    }

    @PostMapping("/courseManagement/addCourse")
    public String addCourse(Model model, @RequestParam("file") MultipartFile file, String courseId, String collegeId,
                            String majorId, String courseName, String term, String teacherId, String courseDate, String room
    ) {
        String fileName = file.getOriginalFilename();
        if (collegeId.equals("0") || courseId.equals("") || majorId.equals("0") || courseName.equals("") || fileName == null || fileName.isEmpty() || term.equals("") || teacherId.equals("0") || courseDate.equals("") || room.equals("")) {
            model.addAttribute("addCourseCode", 0);
            return "addCourse";
        }
        boolean isSuccess = false;
        try {
            isSuccess = courseService.ImportStudent(fileName, file, courseId, term);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //导入学生失败
        if (!isSuccess) {
            model.addAttribute("addCourseCode", 1);
            return "addCourse";
        }
        Teachcourse teachcourse = new Teachcourse();
        teachcourse.setCourseid(courseId);
        teachcourse.setTeacherid(teacherId);
        teachcourse.setTerm(term);
        teachcourse.setCoursedate(courseDate);
        teachcourse.setCourseroom(room);
        teachcourseMapper.insert(teachcourse);

        Courses courses = new Courses();
        courses.setCourseid(courseId);
        courses.setCoursename(courseName);
        courses.setCollegeid(collegeId);
        courses.setMajorid(majorId);
        courseService.insert(courses);

        return "redirect:/index/courseManagement";
    }

    @GetMapping("/courseManagement/modifyCourseInfo/{courseId}")
    public String modifyCourseInfo(@PathVariable(name = "courseId") String courseId, Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        session.setAttribute("courseId", courseId);
        Courses courses = courseService.selectByKey(courseId);
        List<Stucourse> stuCourseList = courseService.selectStuCourseByCId(courseId);
        List<Students> studentsList = new ArrayList<Students>();
        for (Stucourse stucourse : stuCourseList) {
            studentsList.add(studentsMapper.selectByPrimaryKey(stucourse.getStudentid()));
        }
        List<Teachers> teachersList = methodMapper.SelectAllTeacher();
        model.addAttribute("teachersList", teachersList);
        model.addAttribute("courses", courses);
        model.addAttribute("studentsList", studentsList);
        return "modifyCourseInfo";
    }

    @GetMapping("/courseManagement/modifyCourseInfo")
    public String modifyCourseInfo(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        String courseId = (String) session.getAttribute("courseId");
        Courses courses = courseService.selectByKey(courseId);
        List<Stucourse> stuCourseList = courseService.selectStuCourseByCId(courseId);
        List<Students> studentsList = new ArrayList<Students>();
        for (Stucourse stucourse : stuCourseList) {
            studentsList.add(studentsMapper.selectByPrimaryKey(stucourse.getStudentid()));
        }
        List<Teachers> teachersList = methodMapper.SelectAllTeacher();
        model.addAttribute("teachersList", teachersList);
        model.addAttribute("courses", courses);
        model.addAttribute("studentsList", studentsList);
        return "modifyCourseInfo";
    }

    @PostMapping("/courseManagement/modifyCourseInfo")
    public String modifyCourseInfo(HttpSession session, Model model, @RequestParam("file") MultipartFile file, String courseId, String collegeId,
                                   String majorId, String courseName, String term, String teacherId, String courseDate, String room) {
        String fileName = file.getOriginalFilename();
        String courseId1 = (String) session.getAttribute("courseId");
        if (collegeId.equals("0") && courseId.equals("") && majorId.equals("0") && courseName.equals("") && fileName == null && fileName.isEmpty() && term.equals("")&&teacherId.equals("")&&courseDate.equals("")&&room.equals("")) {
            return "redirect:/index/courseManagement";
        }
        boolean isSuccess = false;
        if (fileName != null && !fileName.isEmpty()) {
            try {
                isSuccess = courseService.ImportStudent(fileName, file, courseId1, term);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //导入学生失败
            if (!isSuccess) {
                model.addAttribute("modifyStuCourseCode", 1);
                return "modifyCourseInfo";
            }
        }

        if (collegeId.equals("0") && courseId.equals("") && majorId.equals("0") && courseName.equals("")&& term.equals("")&&teacherId.equals("")&&courseDate.equals("")&&room.equals("")) {
            return "modifyCourseInfo";
        }

        Teachcourse teachcourse = new Teachcourse();
        teachcourse.setCourseid(courseId1);
        teachcourse.setTeacherid(teacherId);
        teachcourse.setTerm(term);
        teachcourse.setCoursedate(courseDate);
        teachcourse.setCourseroom(room);
        courseService.updateTeachCourseInfo(teachcourse);

        Courses courses = new Courses();
        courses.setCourseid(courseId1);
        courses.setCoursename(courseName);
        courses.setCollegeid(collegeId);
        courses.setMajorid(majorId);
        courseService.updateCourseInfo(courses);


        model.addAttribute("modifyStuCourseCode", 2);
        return "redirect:/index/courseManagement/modifyCourseInfo";
    }


    @PostMapping("/courseManagement/deleteInfo")
    public String deleteInfo(Model model, String studentId, HttpSession session) {
        String courseId = (String) session.getAttribute("courseId");
        StucourseKey stucourseKey = new StucourseKey();
        stucourseKey.setCourseid(courseId);
        stucourseKey.setStudentid(studentId);
        stucourseMapper.deleteByPrimaryKey(stucourseKey);
        model.addAttribute("modifyStuCourseCode", 3);
        return "modifyCourseInfo";
    }

}