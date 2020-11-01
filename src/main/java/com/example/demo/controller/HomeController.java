package com.example.demo.controller;

import com.example.demo.mapper.*;
import com.example.demo.model.*;
import com.example.demo.service.CourseService;
import com.example.demo.service.GradeService;
import lombok.extern.flogger.Flogger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.awt.image.FilteredImageSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Resource
    public MethodMapper methodMapper;

    @Resource
    public CoursesMapper coursesMapper;

    @Resource
    public StudentsMapper StudentsMapper;

    @Resource
    public CourseService courseService;

    @Resource
    public StudentsMapper studentsMapper;

    @Resource
    public TeachersMapper teachersMapper;

    @Resource
    public TeachcourseMapper teachcourseMapper;

    @Resource
    public GradeService gradeService;

    @GetMapping("")
    public String Home(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        String type = account.getType();
        int typeCode = Integer.parseInt(type);
        model.addAttribute("typeCode", typeCode);
        //学生
        String studentId = account.getAccountid();
        List<Stucourse> stucourseList = methodMapper.SelectByStudentId(studentId);
        List<Grade> gradeList = new ArrayList<>();
        Grade grade;
        for (Stucourse s : stucourseList) {
            if (s.getGrade().equals("0")) {
                continue;
            }
            grade = new Grade();
            Courses courses = coursesMapper.selectByPrimaryKey(s.getCourseid());
            grade.setCourseId(courses.getCourseid());
            grade.setCourseName(courses.getCoursename());
            Teachcourse teachcourse = methodMapper.selectTeachCourse(s.getCourseid());
            Teachers teachers = teachersMapper.selectByPrimaryKey(teachcourse.getTeacherid());
            grade.setTeacherName(teachers.getName());
            grade.setGrade(s.getGrade());
            gradeList.add(grade);
        }

        model.addAttribute("gradeList", gradeList);
        //教师

        String teacherId = account.getAccountid();
        List<Teachcourse> teachcourseList = methodMapper.selectTeachCourseList(teacherId);
        Teachers teachers = teachersMapper.selectByPrimaryKey(teacherId);
        List<TeacherCourseList> teacherCourseListArrayList = new ArrayList<>();
        for (Teachcourse teachcourse : teachcourseList) {
            TeacherCourseList teacherCourseList = new TeacherCourseList();
            Courses courses = coursesMapper.selectByPrimaryKey(teachcourse.getCourseid());
            teacherCourseList.setCourseid(teachcourse.getCourseid());
            teacherCourseList.setCourseName(courses.getCoursename());
            teacherCourseList.setTeacherid(teachcourse.getTeacherid());
            teacherCourseList.setTeacherName(teachers.getName());
            teacherCourseList.setCourseroom(teachcourse.getCourseroom());
            teacherCourseList.setCoursedate(teachcourse.getCoursedate());
            List<Stucourse> stucourseList1 = methodMapper.selectStuCourseById(teachcourse.getCourseid());
            teacherCourseList.setSum(String.valueOf(stucourseList1.size()));
            teacherCourseList.setTerm(teachcourse.getTerm());
            teacherCourseListArrayList.add(teacherCourseList);
        }
        model.addAttribute("teacherCourseListArrayList", teacherCourseListArrayList);
        return "home";
    }

    //学生
    @GetMapping("/studentInfo")
    public String studentInfo(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        Students students = StudentsMapper.selectByPrimaryKey(account.getAccountid());
        model.addAttribute(students);
        return "studentInfo";

    }

    @PostMapping("/studentInfo")
    public String studentInfo(HttpSession session, Model model, String password, String political) {
        Account account = (Account) session.getAttribute("account");
        String studentId = account.getAccountid();
        Students students = StudentsMapper.selectByPrimaryKey(studentId);
        if (password.equals("") && political.equals("")) {
            methodMapper.updateStudentInfo(studentId, students.getPassword(), students.getPolitical());
        }
        if (political.equals("") && !password.equals("")) {
            methodMapper.updateAccountPassword(studentId, password);
            methodMapper.updateStudentInfo(studentId, password, students.getPolitical());
        }
        if (!political.equals("") && password.equals("")) {
            methodMapper.updateStudentInfo(studentId, students.getPassword(), political);
        }
        if (!political.equals("") && !password.equals("")) {
            methodMapper.updateAccountPassword(studentId, password);
            methodMapper.updateStudentInfo(studentId, password, political);
        }
        model.addAttribute(students);
        return "redirect:/home/studentInfo";
    }

    //教师
    @GetMapping("/couresManagement")
    public String couresManagement(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("account");
        String teacherId = account.getAccountid();
        List<Teachcourse> teachcourseList = methodMapper.selectTeachCourseList(teacherId);
        Teachers teachers = teachersMapper.selectByPrimaryKey(teacherId);
        List<TeacherCourseList> teacherCourseListArrayList = new ArrayList<>();
        for (Teachcourse teachcourse : teachcourseList) {
            TeacherCourseList teacherCourseList = new TeacherCourseList();
            Courses courses = coursesMapper.selectByPrimaryKey(teachcourse.getCourseid());
            teacherCourseList.setCourseid(teachcourse.getCourseid());
            teacherCourseList.setCourseName(courses.getCoursename());
            teacherCourseList.setTeacherid(teachcourse.getTeacherid());
            teacherCourseList.setTeacherName(teachers.getName());
            teacherCourseList.setCourseroom(teachcourse.getCourseroom());
            teacherCourseList.setCoursedate(teachcourse.getCoursedate());
            List<Stucourse> stucourseList1 = methodMapper.selectStuCourseById(teachcourse.getCourseid());
            teacherCourseList.setSum(String.valueOf(stucourseList1.size()));
            teacherCourseList.setTerm(teachcourse.getTerm());
            teacherCourseListArrayList.add(teacherCourseList);
        }
        model.addAttribute("teacherCourseListArrayList", teacherCourseListArrayList);
        return "couresManagement";
    }

    @PostMapping("/couresManagement")
    public String couresManagement(HttpSession session, String term, Model model) {
        Account account = (Account) session.getAttribute("account");
        String teacherId = account.getAccountid();
        List<Teachcourse> teachcourseList = methodMapper.selectTeachCourseList(teacherId);
        Teachers teachers = teachersMapper.selectByPrimaryKey(teacherId);
        List<TeacherCourseList> teacherCourseListArrayList = new ArrayList<>();
        for (Teachcourse teachcourse : teachcourseList) {
            if (teachcourse.getTerm().equals(term)) {
                TeacherCourseList teacherCourseList = new TeacherCourseList();
                Courses courses = coursesMapper.selectByPrimaryKey(teachcourse.getCourseid());
                teacherCourseList.setCourseid(teachcourse.getCourseid());
                teacherCourseList.setCourseName(courses.getCoursename());
                teacherCourseList.setTeacherid(teachcourse.getTeacherid());
                teacherCourseList.setTeacherName(teachers.getName());
                teacherCourseList.setCourseroom(teachcourse.getCourseroom());
                teacherCourseList.setCoursedate(teachcourse.getCoursedate());
                List<Stucourse> stucourseList1 = methodMapper.selectStuCourseById(teachcourse.getCourseid());
                teacherCourseList.setSum(String.valueOf(stucourseList1.size()));
                teacherCourseList.setTerm(teachcourse.getTerm());
                teacherCourseListArrayList.add(teacherCourseList);
            }
        }
        model.addAttribute("teacherCourseListArrayList", teacherCourseListArrayList);
        return "couresManagement";
    }

    @GetMapping("/couresManagement/teacherModifyCourseInfo/{courseId}")
    public String modifyCourseInfo(@PathVariable(name = "courseId") String courseId, Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        session.setAttribute("courseId", courseId);
        Courses courses = courseService.selectByKey(courseId);
        List<Stucourse> stuCourseList = courseService.selectStuCourseByCId(courseId);
        List<Students> studentsList = new ArrayList<>();
        for (Stucourse stucourse : stuCourseList) {
            studentsList.add(studentsMapper.selectByPrimaryKey(stucourse.getStudentid()));
        }
        TeachcourseKey key = new TeachcourseKey();
        key.setCourseid(courseId);
        key.setTeacherid(account.getAccountid());
        Teachcourse teachcourse = teachcourseMapper.selectByPrimaryKey(key);
        model.addAttribute("teachcourse", teachcourse);
        model.addAttribute("courses", courses);
        model.addAttribute("studentsList", studentsList);
        return "teacherModifyCourseInfo";
    }


    @GetMapping("/couresManagement/teacherModifyCourseInfo")
    public String modifyCourseInfo(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        String courseId = (String) session.getAttribute("courseId");
        Courses courses = courseService.selectByKey(courseId);
        List<Stucourse> stuCourseList = courseService.selectStuCourseByCId(courseId);
        List<Students> studentsList = new ArrayList<>();
        for (Stucourse stucourse : stuCourseList) {
            studentsList.add(studentsMapper.selectByPrimaryKey(stucourse.getStudentid()));
        }
        TeachcourseKey key = new TeachcourseKey();
        key.setCourseid(courseId);
        key.setTeacherid(account.getAccountid());
        Teachcourse teachcourse = teachcourseMapper.selectByPrimaryKey(key);
        model.addAttribute("teachcourse", teachcourse);
        model.addAttribute("courses", courses);
        model.addAttribute("studentsList", studentsList);
        return "teacherModifyCourseInfo";
    }

    @PostMapping("/couresManagement/teacherModifyCourseInfo")
    public String modifyCourseInfo(Model model, HttpSession session, @RequestParam("file") MultipartFile file,
                                   String room, String courseDate, String term) {
        String fileName = file.getOriginalFilename();
        String courseId1 = (String) session.getAttribute("courseId");
        Account account = (Account) session.getAttribute("account");
        if (fileName == null && fileName.isEmpty() && courseDate.equals("") && room.equals("")) {
            return "redirect:/home/couresManagement/teacherModifyCourseInfo";
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
                return "teacherModifyCourseInfo";
            }
        }

        if (term.equals("") && courseDate.equals("") && room.equals("")) {
            return "teacherModifyCourseInfo";
        }

        Teachcourse teachcourse = new Teachcourse();
        teachcourse.setCourseid(courseId1);
        teachcourse.setTeacherid(account.getAccountid());
        teachcourse.setTerm(term);
        teachcourse.setCoursedate(courseDate);
        teachcourse.setCourseroom(room);
        courseService.updateTeachCourseInfo(teachcourse);

        model.addAttribute("modifyStuCourseCode", 2);
        return "redirect:/home/couresManagement/teacherModifyCourseInfo";
    }

    @GetMapping("/teacherInfo")
    public String teacherInfo(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        Teachers teachers = teachersMapper.selectByPrimaryKey(account.getAccountid());
        model.addAttribute("teachers", teachers);

        return "teacherInfo";
    }

    @PostMapping("/teacherInfo")
    public String teacherInfo(HttpSession session, Model model, String password, String political) {
        Account account = (Account) session.getAttribute("account");
        String teacherId = account.getAccountid();
        Teachers teachers = teachersMapper.selectByPrimaryKey(teacherId);
        if (password.equals("") && political.equals("")) {
            methodMapper.updateTeacherInfo(teacherId, teachers.getPassword(), teachers.getPolitical());
        }
        if (political.equals("") && !password.equals("")) {
            methodMapper.updateAccountPassword(teacherId, password);
            methodMapper.updateTeacherInfo(teacherId, password, teachers.getPolitical());
        }
        if (!political.equals("") && password.equals("")) {
            methodMapper.updateTeacherInfo(teacherId, teachers.getPassword(), political);
        }
        if (!political.equals("") && !password.equals("")) {
            methodMapper.updateAccountPassword(teacherId, password);
            methodMapper.updateTeacherInfo(teacherId, password, political);
        }
        model.addAttribute("teachers", teachers);
        return "redirect:/home/teacherInfo";
    }

    @GetMapping("/couresManagement/upLoadGrade/{courseId}")
    public String upLoadGrade(HttpSession session, Model model, @PathVariable(name = "courseId") String courseId) {
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
        model.addAttribute("gradeInfoList", gradeInfoList);
        return "upLoadGrade";
    }

    @GetMapping("/couresManagement/upLoadGrade")
    public String upLoadGrade(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        String courseId = (String) session.getAttribute("courseId");
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
        model.addAttribute("gradeInfoList", gradeInfoList);
        return "upLoadGrade";
    }

    @PostMapping("/couresManagement/updateGrade")
    public String updateGrade(HttpSession session, Model model, String studentId, String grade) {
        String courseId = (String) session.getAttribute("courseId");
        if (studentId.equals("") || grade.equals("")) {
            model.addAttribute("updateGradeCode", 0);
            return "upLoadGrade";
        }
        methodMapper.updateStuCourseGrade(studentId, courseId, grade);
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
        model.addAttribute("gradeInfoList", gradeInfoList);
        return "redirect:/home/couresManagement/upLoadGrade";
    }


    @PostMapping("/couresManagement/upLoadGrade")
    public String upLoadGrade(HttpSession session, Model model, @RequestParam("file") MultipartFile file) {
        boolean isSuccess = false;
        String fileName = file.getOriginalFilename();
        String courseId1 = (String) session.getAttribute("courseId");
        if (fileName == null || fileName.isEmpty()|| fileName.equals("")) {
            model.addAttribute("updateGradeCode", 0);
            return "redirect:/home/couresManagement/upLoadGrade";
        }
        try {
            isSuccess = gradeService.ImportGrade(fileName, file, courseId1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //导入学生失败
        if (!isSuccess) {
            model.addAttribute("updateGradeCode", 1);
            return "redirect:/home/couresManagement/upLoadGrade";
        }
        return "redirect:/home/couresManagement/upLoadGrade";
    }


    //成绩管理 --教师
    @GetMapping("/gradeManagement/{courseId}")
    public String gradeManagement(HttpSession session, Model model,@PathVariable(name = "courseId") String courseId){
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
        return "teacherGradeManagement";
    }

    @GetMapping("/gradeManagement/{courseId}/{code}")
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
        return "teacherGradeManagement";
    }


}