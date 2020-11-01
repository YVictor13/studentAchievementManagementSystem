package com.example.demo.model;

public class Teachcourse extends TeachcourseKey {
    private String term;

    private String courseroom;

    private String coursedate;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term == null ? null : term.trim();
    }

    public String getCourseroom() {
        return courseroom;
    }

    public void setCourseroom(String courseroom) {
        this.courseroom = courseroom == null ? null : courseroom.trim();
    }

    public String getCoursedate() {
        return coursedate;
    }

    public void setCoursedate(String coursedate) {
        this.coursedate = coursedate == null ? null : coursedate.trim();
    }
}