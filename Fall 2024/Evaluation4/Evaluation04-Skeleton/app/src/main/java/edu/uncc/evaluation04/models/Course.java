package edu.uncc.evaluation04.models;

public class Course {
    String courseId;
    String name;
    String number;
    double hours;

    public Course(String courseId, String name, String number, double hours) {
        this.courseId = courseId;
        this.name = name;
        this.number = number;
        this.hours = hours;
    }

    public Course(){

    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }
}
