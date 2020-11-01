package com.example.demo.service.Impl;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.mapper.MethodMapper;
import com.example.demo.mapper.TeachersMapper;
import com.example.demo.model.Account;
import com.example.demo.model.Teachers;
import com.example.demo.service.TeacherService;
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
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Resource
    public AccountMapper accountMapper;
    @Resource
    public TeachersMapper teachersMapper;
    @Resource
    public MethodMapper methodMapper;

    @Override
    public List<Teachers> FindAllInfo() {
        return methodMapper.SelectAllTeacher();
    }

    @Override
    public List<Teachers> FindInfoByC(String collegeId) {
        return methodMapper.SelectByC(collegeId);
    }

    @Override
    public int InsertInfo(Teachers teachers) {
        try {
            Account account =new Account();
            account.setPassword(teachers.getPassword());
            account.setAccountid(teachers.getTeacherid());
            account.setType("2");
            accountMapper.insert(account);
            teachersMapper.insert(teachers);
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public boolean ImportTeacherInfo(String fileName, MultipartFile file) {
        boolean notNull = false;
        List<Teachers> teachersList = new ArrayList<Teachers>();
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

        Teachers teachers;
        assert sheet != null;
        String teacherId = null;

        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            teachers = new Teachers();
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            teacherId = row.getCell(0).getStringCellValue();
            if (teacherId == null || teacherId.isEmpty()) {
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
                                            String collegeId = row.getCell(8).getStringCellValue();
                                            if (collegeId == null || collegeId.isEmpty()) {
                                                return false;
                                            } else {
                                                teachers.setTeacherid(teacherId);
                                                teachers.setPassword(password);
                                                teachers.setName(name);
                                                teachers.setSex(sex);
                                                teachers.setBrithday(birthday);
                                                teachers.setNation(nation);
                                                teachers.setIdnumber(idNumber);
                                                teachers.setPolitical(political);
                                                teachers.setCollegeid(collegeId);
                                                teachersList.add(teachers);
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
        for (Teachers t : teachersList) {
            if (teachersMapper.selectByPrimaryKey(t.getTeacherid()) != null&&accountMapper.selectByPrimaryKey(t.getTeacherid())!=null) {
                return false;
            }
            Account account =new Account();
            account.setPassword(t.getPassword());
            account.setAccountid(t.getTeacherid());
            account.setType("2");
            accountMapper.insert(account);
            teachersMapper.insert(t);
        }
        return true;
    }

    @Override
    public int deleteTeacherInfo(String teacherId) {
        try {
            teachersMapper.deleteByPrimaryKey(teacherId);
            accountMapper.deleteByPrimaryKey(teacherId);
            return 1;
        }catch (Exception e){
            return -1;
        }

    }

    @Override
    public Teachers selectByKey(String teacherId) {
        return teachersMapper.selectByPrimaryKey(teacherId);
    }

    @Override
    public int UpdateInfo(Teachers teachers) {
        Teachers teach = teachersMapper.selectByPrimaryKey(teachers.getTeacherid());
        if(!teachers.getTeacherid().equals(teach.getTeacherid())){
            teachers.setTeacherid(teach.getTeacherid());
        }
        if(teachers.getPassword().equals("")){
            teachers.setPassword(teach.getPassword());
        }
        if(teachers.getName().equals("")){
            teachers.setName(teach.getName());
        }
        if(teachers.getSex().equals("")){
            teachers.setSex(teach.getSex());
        }
        if(teachers.getBrithday().equals("")){
            teachers.setBrithday(teach.getBrithday());
        }
        if(teachers.getNation().equals("")){
            teachers.setNation(teach.getNation());
        }
        if(teachers.getIdnumber().equals("")){
            teachers.setIdnumber(teach.getIdnumber());
        }
        if(teachers.getPolitical().equals("")){
            teachers.setPolitical(teach.getPolitical());
        }
        if(teachers.getCollegeid().equals("")){
            teachers.setCollegeid(teach.getCollegeid());
        }

        try {
            teachersMapper.updateByPrimaryKey(teachers);
            Account account =new Account();
            account.setPassword(teachers.getPassword());
            account.setAccountid(teachers.getTeacherid());
            account.setType("2");
            accountMapper.updateByPrimaryKey(account);
            return 1;
        }catch (Exception e){
            return -1;
        }
    }
}