package com.example.demo.service.Impl;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.mapper.MethodMapper;
import com.example.demo.mapper.StudentsMapper;
import com.example.demo.model.Account;
import com.example.demo.model.Students;
import com.example.demo.service.StudentService;
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    public AccountMapper accountMapper;

    @Resource
    public StudentsMapper studentsMapper;

    @Resource
    public MethodMapper methodMapper;

    @Override
    public List<Students> FindInfoByCCM(String classId, String collegeId, String majorId) {
        return methodMapper.SelectByCCM(classId, collegeId, majorId);
    }

    @Override
    public List<Students> FindAllInfo() {
        return methodMapper.SelectAll();
    }

    @Override
    public int InsertInfo(Students students) {
        try {
            studentsMapper.insert(students);
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public boolean ImportGrade(String fileName, MultipartFile file) throws Exception {
        boolean notNull = false;
        List<Students> studentsList = new ArrayList<Students>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            System.out.println("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null) {
            notNull = true;
        }

        Students students;
        assert sheet != null;
        String studentId = null;

        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            students = new Students();
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            studentId = row.getCell(0).getStringCellValue();
            if (studentId == null || studentId.isEmpty()) {
                return false;
            } else {
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                String password = row.getCell(1).getStringCellValue();
                if (password == null || password.isEmpty()) {
                    return false;
                } else {
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    String name = row.getCell(2).getStringCellValue();
                    if (name == null || name.isEmpty()) {
                        return false;
                    } else {
                        row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                        String sex = row.getCell(3).getStringCellValue();
                        if (sex == null || sex.isEmpty()) {
                            return false;
                        } else {
                            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                            String birthday = row.getCell(4).getStringCellValue();
                            if (birthday == null || birthday.isEmpty()) {
                                return false;
                            } else {
                                row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                                String nation = row.getCell(5).getStringCellValue();
                                if (nation == null || nation.isEmpty()) {
                                    return false;
                                } else {
                                    row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                                    String idNumber = row.getCell(6).getStringCellValue();
                                    if (idNumber == null || idNumber.isEmpty()) {
                                        return false;
                                    } else {
                                        row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                                        String political = row.getCell(7).getStringCellValue();
                                        if (political == null || political.isEmpty()) {
                                            return false;
                                        } else {
                                            row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                                            String classId = row.getCell(8).getStringCellValue();
                                            if (classId == null || classId.isEmpty()) {
                                                return false;
                                            } else {
                                                row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                                                String collegeId = row.getCell(9).getStringCellValue();
                                                if (collegeId == null || collegeId.isEmpty()) {
                                                    return false;
                                                } else {
                                                    row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                                                    String grade = row.getCell(10).getStringCellValue();
                                                    if (grade == null || grade.isEmpty()) {
                                                        return false;
                                                    } else {
                                                        row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                                                        String majorId = row.getCell(11).getStringCellValue();
                                                        if (majorId == null || majorId.isEmpty()) {
                                                            return false;
                                                        } else {
                                                            students.setStudentid(studentId);
                                                            students.setPassword(password);
                                                            students.setName(name);
                                                            students.setSex(sex);
                                                            students.setBirthday(birthday);
                                                            students.setNation(nation);
                                                            students.setIdnumber(idNumber);
                                                            students.setPolitical(political);
                                                            students.setClassid(classId);
                                                            students.setCollegeid(collegeId);
                                                            students.setGrade(grade);
                                                            students.setMajorid(majorId);
                                                            studentsList.add(students);
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for (Students s : studentsList) {
            if (studentsMapper.selectByPrimaryKey(s.getStudentid()) != null&& accountMapper.selectByPrimaryKey(s.getStudentid())!=null) {
                return false;
            }
            Account account = new Account();
            account.setAccountid(s.getStudentid());
            account.setPassword(s.getPassword());
            account.setType("0");
            accountMapper.insert(account);
            studentsMapper.insert(s);
        }
        return true;
    }

    @Override
    public boolean deleteStuInfo(String studentId) {
        try {
            studentsMapper.deleteByPrimaryKey(studentId);
            accountMapper.deleteByPrimaryKey(studentId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Students selectByKey(String studentId) {
        return studentsMapper.selectByPrimaryKey(studentId);
    }

    @Override
    public int UpdateInfo(Students students) {
        Students stu = studentsMapper.selectByPrimaryKey(students.getStudentid());
        if(!students.getStudentid().equals(stu.getStudentid())){
            students.setStudentid(stu.getStudentid());
        }
        if(students.getPassword().equals("")){
            students.setPassword(stu.getPassword());
        }
        if(students.getName().equals("")){
            students.setName(stu.getName());
        }
        if(students.getSex().equals("")){
            students.setSex(stu.getSex());
        }
        if(students.getBirthday().equals("")){
            students.setBirthday(stu.getBirthday());
        }
        if(students.getNation().equals("")){
            students.setNation(stu.getNation());
        }
        if(students.getIdnumber().equals("")){
            students.setIdnumber(stu.getIdnumber());
        }
        if(students.getPolitical().equals("")){
            students.setPolitical(stu.getPolitical());
        }
        if(students.getClassid().equals("")){
            students.setClassid(stu.getClassid());
        }
        if(students.getCollegeid().equals("")){
            students.setCollegeid(stu.getCollegeid());
        }
        if(students.getGrade().equals("")){
            students.setGrade(stu.getGrade());
        }
        if(students.getMajorid().equals("")){
            students.setMajorid(stu.getMajorid());
        }

        try {
            studentsMapper.updateByPrimaryKey(students);
            Account account = new Account();
            account.setPassword(students.getPassword());
            account.setAccountid(students.getStudentid());
            account.setType("0");
            accountMapper.updateByPrimaryKey(account);
            return 1;
        }catch (Exception e){
            return -1;
        }
    }
}