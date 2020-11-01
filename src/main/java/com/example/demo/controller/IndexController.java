package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.model.Students;
import com.example.demo.model.Teachers;
import com.example.demo.service.LoginService;
import com.example.demo.service.StudentService;
import com.example.demo.service.TeacherService;
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
public class IndexController {

    @Resource
    public LoginService loginService;

    @Resource
    public StudentService studentService;

    @Resource
    public TeacherService teacherService;

    @GetMapping("")
    public String Index(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:login";
        }
        return "index";
    }

    //退出
    @GetMapping("/logOut")
    public String LogOUt(HttpSession session) {
        session.setAttribute("account", null);
        return "redirect:/";
    }

    //学生档案管理
    @GetMapping("/studentInfoManagement")
    public String studentInfoManagement(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        List<Students> studentsList = studentService.FindAllInfo();
        List<String> classIdList = new ArrayList<>();
        List<String> collegeIdList = new ArrayList<>();
        List<String> majorIdList = new ArrayList<>();
        for(Students stu:studentsList){
            if(!classIdList.contains(stu.getClassid())){
                classIdList.add(stu.getClassid());
            }
            if(!collegeIdList.contains(stu.getCollegeid())){
                collegeIdList.add(stu.getCollegeid());
            }
            if(!majorIdList.contains(stu.getMajorid())){
                majorIdList.add(stu.getMajorid());
            }
        }
        model.addAttribute("classIdList", classIdList);
        model.addAttribute("collegeIdList", collegeIdList);
        model.addAttribute("majorIdList", majorIdList);
        model.addAttribute("studentsList", studentsList);
        return "StudentInfoManagement";
    }

    //学生档案管理--管理学生档案
    @PostMapping("/studentInfoManagement")
    public String studentInfoManagement(Model model, String classId, String collegeId, String majorId) {
        List<Students> allStudentsList = studentService.FindAllInfo();
        List<String> classIdList = new ArrayList<>();
        List<String> collegeIdList = new ArrayList<>();
        List<String> majorIdList = new ArrayList<>();
        for(Students stu:allStudentsList){
            if(!classIdList.contains(stu.getClassid())){
                classIdList.add(stu.getClassid());
            }
            if(!collegeIdList.contains(stu.getCollegeid())){
                collegeIdList.add(stu.getCollegeid());
            }
            if(!majorIdList.contains(stu.getMajorid())){
                majorIdList.add(stu.getMajorid());
            }
        }
        model.addAttribute("classIdList", classIdList);
        model.addAttribute("collegeIdList", collegeIdList);
        model.addAttribute("majorIdList", majorIdList);

        if (classId.equals("0") && collegeId.equals("0") && majorId.equals("0")) {
            model.addAttribute("studentsList", allStudentsList);
            return "StudentInfoManagement";
        }

        List<Students> studentsList = studentService.FindInfoByCCM(classId, collegeId, majorId);
        model.addAttribute("studentsList", studentsList);
        return "StudentInfoManagement";
    }

    //添加学生档案信息
    @GetMapping("/studentInfoManagement/addStuInfo")
    public String addStuInfo(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        return "addStuInfo";
    }

    @PostMapping("/studentInfoManagement/addStuInfo")
    public String addStuInfo(Model model, @RequestParam(name = "studentId") String studentId,
                             @RequestParam(name = "password") String password,
                             @RequestParam(name = "classId") String classId,
                             @RequestParam(name = "collegeId") String collegeId,
                             @RequestParam(name = "majorId") String majorId,
                             @RequestParam(name = "sex") String sex,
                             @RequestParam(name = "name") String name,
                             @RequestParam(name = "grade") String grade,
                             @RequestParam(name = "birthday") String birthday,
                             @RequestParam(name = "nation") String nation,
                             @RequestParam(name = "idNumber") String idNumber,
                             @RequestParam(name = "political") String political
    ) {
        if (studentId.equals("") || password.equals("") || classId.equals("") || collegeId.equals("") || majorId.equals("") || sex.equals("") || name.equals("") ||
                grade.equals("") || birthday.equals("") || nation.equals("") || idNumber.equals("") || political.equals("")) {
            model.addAttribute("addStuInfoCode", 0);
            return "addStuInfo";
        }
        Students students = new Students();
        students.setStudentid(studentId);
        students.setPassword(password);
        students.setName(name);
        students.setBirthday(birthday);
        students.setClassid(classId);
        students.setCollegeid(collegeId);
        students.setGrade(grade);
        students.setIdnumber(idNumber);
        students.setMajorid(majorId);
        students.setNation(nation);
        students.setSex(sex);
        students.setPolitical(political);

        Account account = new Account();
        account.setType("0");
        account.setAccountid(studentId);
        account.setPassword(password);
        int code = loginService.addAccount(account);
        if(code!=1){
            return "addStuInfo";
        }
        int addStuInfoCode = studentService.InsertInfo(students);
        if (addStuInfoCode == -1) {
            model.addAttribute("addStuInfoCode", addStuInfoCode);
            return "addStuInfo";
        }
        return "redirect:/index/studentInfoManagement";
    }

    //上传学生档案信息
    @GetMapping("/studentInfoManagement/upLoad")
    public String upLoadFile(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        return "upLoadStuInfo";
    }

    @PostMapping("/studentInfoManagement/upLoad")
    public String upLoadFile(@RequestParam("file") MultipartFile file, Model model) {
        boolean isSuccess = false;
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            model.addAttribute("upLoadCode", 0);
            return "upLoadStuInfo";
        }
        try {
            isSuccess = studentService.ImportGrade(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!isSuccess) {
            model.addAttribute("upLoadCode", 1);
            return "upLoadStuInfo";
        }

        return "redirect:/index/studentInfoManagement";
    }

    @PostMapping("/studentInfoManagement/delete")
    public String DeleteStuInfo(@RequestParam(name = "studentId") String studentId) {
        studentService.deleteStuInfo(studentId);
        return "redirect:/index/studentInfoManagement";
    }

    @GetMapping("/studentInfoManagement/ModifyStuInfo/{studentId}")
    public String ModifyStuInfo(@PathVariable(name = "studentId") String studentId, Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        session.setAttribute("studentId", studentId);
        Students students = studentService.selectByKey(studentId);
        model.addAttribute("students", students);
        return "modifyStuInfo";
    }

    @GetMapping("/studentInfoManagement/ModifyStuInfo")
    public String ModifyStuInfo(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        String studentId = (String) session.getAttribute("studentId");
        Students students = studentService.selectByKey(studentId);
        model.addAttribute("students", students);
        return "modifyStuInfo";
    }


    @PostMapping("/studentInfoManagement/ModifyStuInfo")
    public String ModifyStuInfo(Model model, @RequestParam(name = "studentId") String studentId,
                                @RequestParam(name = "password") String password,
                                @RequestParam(name = "classId") String classId,
                                @RequestParam(name = "collegeId") String collegeId,
                                @RequestParam(name = "majorId") String majorId,
                                @RequestParam(name = "sex") String sex,
                                @RequestParam(name = "name") String name,
                                @RequestParam(name = "grade") String grade,
                                @RequestParam(name = "birthday") String birthday,
                                @RequestParam(name = "nation") String nation,
                                @RequestParam(name = "idNumber") String idNumber,
                                @RequestParam(name = "political") String political, HttpSession session) {

        if (studentId.equals("") && password.equals("") && classId.equals("") && collegeId.equals("") && majorId.equals("") && sex.equals("") && name.equals("") &&
                grade.equals("") && birthday.equals("") && nation.equals("") && idNumber.equals("") && political.equals("")) {
            model.addAttribute("ModifyStuInfoCode", 0);
            return "redirect:/index/studentInfoManagement/ModifyStuInfo";
        }
        String studentId1 = (String) session.getAttribute("studentId");
        Students students = new Students();
        students.setStudentid(studentId1);
        students.setPassword(password);
        students.setName(name);
        students.setBirthday(birthday);
        students.setClassid(classId);
        students.setCollegeid(collegeId);
        students.setGrade(grade);
        students.setIdnumber(idNumber);
        students.setMajorid(majorId);
        students.setNation(nation);
        students.setSex(sex);
        students.setPolitical(political);
        int ModifyStuInfoCode = studentService.UpdateInfo(students);
        if (ModifyStuInfoCode == -1) {
            model.addAttribute("ModifyStuInfoCode", ModifyStuInfoCode);
            return "redirect:/index/studentInfoManagement/ModifyStuInfo";
        }

        return "redirect:/index/studentInfoManagement";
    }


    /**
     * @ 教师信息管理
     */
    @GetMapping("/teacherInfoManagement")
    public String teacherInfoManagement(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        List<Teachers> teachersList = teacherService.FindAllInfo();
        List<String> collegeIdList = new ArrayList<>();
        for(Teachers t:teachersList){
            if(!collegeIdList.contains(t.getCollegeid())){
                collegeIdList.add(t.getCollegeid());
            }
        }
        model.addAttribute("collegeIdList", collegeIdList);
        model.addAttribute("teachersList", teachersList);
        return "teacherInfoManagement";
    }


    @PostMapping("/teacherInfoManagement")
    public String teacherInfoManagement(Model model, String collegeId) {
        List<Teachers> allTeachersList = teacherService.FindAllInfo();
        List<String> collegeIdList = new ArrayList<>();
        for(Teachers t:allTeachersList){
            if(!collegeIdList.contains(t.getCollegeid())){
                collegeIdList.add(t.getCollegeid());
            }
        }
        model.addAttribute("collegeIdList", collegeIdList);
        if(collegeId.equals("0")){
            model.addAttribute("teachersList", allTeachersList);
            return "teacherInfoManagement";
        }

        List<Teachers> teachersList = teacherService.FindInfoByC(collegeId);
        model.addAttribute("teachersList", teachersList);
        return "teacherInfoManagement";
    }


    @GetMapping("/teacherInfoManagement/addTeacherInfo")
    public String addTeacherInfo(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        return "addTeacherInfo";
    }

    @PostMapping("/teacherInfoManagement/addTeacherInfo")
    public String addTeacherInfo(Model model, @RequestParam(name = "teacherId") String teacherId,
                                 @RequestParam(name = "password") String password,
                                 @RequestParam(name = "collegeId") String collegeId,
                                 @RequestParam(name = "sex") String sex,
                                 @RequestParam(name = "name") String name,
                                 @RequestParam(name = "birthday") String birthday,
                                 @RequestParam(name = "nation") String nation,
                                 @RequestParam(name = "idNumber") String idNumber,
                                 @RequestParam(name = "political") String political
    ) {
        if (teacherId.equals("") || password.equals("") || collegeId.equals("") || sex.equals("") || name.equals("") ||
                birthday.equals("") || nation.equals("") || idNumber.equals("") || political.equals("")) {
            model.addAttribute("addStuInfoCode", 0);
            return "addTeacherInfo";
        }

        Teachers teachers = new Teachers();
        teachers.setTeacherid(teacherId);
        teachers.setPassword(password);
        teachers.setName(name);
        teachers.setBrithday(birthday);
        teachers.setCollegeid(collegeId);
        teachers.setIdnumber(idNumber);
        teachers.setNation(nation);
        teachers.setSex(sex);
        teachers.setPolitical(political);

        int addTeacherInfoCode = teacherService.InsertInfo(teachers);
        if (addTeacherInfoCode == -1) {
            model.addAttribute("addTeacherInfoCode", addTeacherInfoCode);
            return "addTeacherInfo";
        }
        return "redirect:/index/teacherInfoManagement";
    }

    @GetMapping("/teacherInfoManagement/upLoadTeacherFile")
    public String upLoadTeacherFile(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        return "upLoadTeacherInfo";
    }

    @PostMapping("/teacherInfoManagement/upLoadTeacherFile")
    public String upLoadTeacherFile(@RequestParam("file") MultipartFile file, Model model) {
        boolean isSuccess = false;
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            model.addAttribute("upLoadCode", 0);
            return "upLoadTeacherInfo";
        }
        try {
            isSuccess = teacherService.ImportTeacherInfo(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!isSuccess) {
            model.addAttribute("upLoadCode", 1);
            return "upLoadTeacherInfo";
        }

        return "redirect:/index/teacherInfoManagement";
    }

    @PostMapping("/teacherInfoManagement/deleteTeacherInfo")
    public String deleteTeacherInfo(@RequestParam(name = "teacherId") String teacherId) {
        teacherService.deleteTeacherInfo(teacherId);
        return "redirect:/index/teacherInfoManagement";
    }


    @GetMapping("/teacherInfoManagement/ModifyTeacherInfo/{teacherId}")
    public String ModifyTeacherInfo(@PathVariable(name = "teacherId") String teacherId, Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        session.setAttribute("teacherId", teacherId);
        Teachers teachers = teacherService.selectByKey(teacherId);
        model.addAttribute("teachers", teachers);
        return "ModifyTeacherInfo";
    }

    @GetMapping("/teacherInfoManagement/ModifyTeacherInfo")
    public String ModifyTeacherInfo(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/";
        }
        String teacherId = (String) session.getAttribute("teacherId");
        Teachers teachers = teacherService.selectByKey(teacherId);
        model.addAttribute("students", teachers);
        return "ModifyTeacherInfo";
    }


    @PostMapping("/teacherInfoManagement/ModifyTeacherInfo")
    public String ModifyTeacherInfo(Model model, @RequestParam(name = "teacherId") String teacherId,
                                @RequestParam(name = "password") String password,
                                @RequestParam(name = "collegeId") String collegeId,
                                @RequestParam(name = "sex") String sex,
                                @RequestParam(name = "name") String name,
                                @RequestParam(name = "birthday") String birthday,
                                @RequestParam(name = "nation") String nation,
                                @RequestParam(name = "idNumber") String idNumber,
                                @RequestParam(name = "political") String political, HttpSession session) {

        if (teacherId.equals("") && password.equals("") && collegeId.equals("") && sex.equals("") && name.equals("") &&
               birthday.equals("") && nation.equals("") && idNumber.equals("") && political.equals("")) {
            model.addAttribute("ModifyTeacherInfoCode", 0);
            return "redirect:/index/teacherInfoManagement/ModifyTeacherInfo";
        }
        String teacherId1 = (String) session.getAttribute("teacherId");
        Teachers teachers = new Teachers();
        teachers.setTeacherid(teacherId1);
        teachers.setPassword(password);
        teachers.setName(name);
        teachers.setBrithday(birthday);
        teachers.setCollegeid(collegeId);
        teachers.setIdnumber(idNumber);
        teachers.setNation(nation);
        teachers.setSex(sex);
        teachers.setPolitical(political);
        int ModifyTeacherInfoCode = teacherService.UpdateInfo(teachers);
        if (ModifyTeacherInfoCode == -1) {
            model.addAttribute("ModifyTeacherInfoCode", ModifyTeacherInfoCode);
            return "redirect:/index/teacherInfoManagement/ModifyTeacherInfo";
        }

        return "redirect:/index/teacherInfoManagement";
    }




}