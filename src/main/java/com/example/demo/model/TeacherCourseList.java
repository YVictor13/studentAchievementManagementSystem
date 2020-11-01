package com.example.demo.model;

public class TeacherCourseList extends Teachcourse{
    String courseName;
    String teacherName;
    String Sum;

    public TeacherCourseList(){}

    public String getSum() {
        return Sum;
    }

    public void setSum(String sum) {
        Sum = sum;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}