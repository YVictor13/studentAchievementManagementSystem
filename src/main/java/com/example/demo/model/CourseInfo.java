package com.example.demo.model;

public class CourseInfo extends Courses{
    String term;
    String teacherName;
    String room;
    String courseDate;
    String sum;
    String adopt;

    public CourseInfo(){}

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getAdopt() {
        return adopt;
    }

    public void setAdopt(String adopt) {
        this.adopt = adopt;
    }
}