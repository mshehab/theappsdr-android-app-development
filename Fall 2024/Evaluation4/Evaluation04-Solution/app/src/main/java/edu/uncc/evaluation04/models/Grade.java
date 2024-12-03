package edu.uncc.evaluation04.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "grade")
public class Grade {
    @PrimaryKey(autoGenerate = true)
    public int gid;

    public String letterGrade;
    public double numericGrade;
    public String courseId;
    public String courseName;
    public String courseNumber;
    public double courseHours;
    public String semesterName;

    public Grade() {
    }

    public Grade(Course course, Semester semester, LetterGrade letterGrade) {
        this.letterGrade = letterGrade.letterGrade;
        this.numericGrade = letterGrade.numericGrade;
        this.courseId = course.courseId;
        this.courseName = course.name;
        this.courseNumber = course.number;
        this.courseHours = course.hours;
        this.semesterName = semester.name;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public double getNumericGrade() {
        return numericGrade;
    }

    public void setNumericGrade(double numericGrade) {
        this.numericGrade = numericGrade;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public double getCourseHours() {
        return courseHours;
    }

    public void setCourseHours(double courseHours) {
        this.courseHours = courseHours;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "gid=" + gid +
                ", letterGrade='" + letterGrade + '\'' +
                ", numericGrade=" + numericGrade +
                ", courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseNumber='" + courseNumber + '\'' +
                ", courseHours=" + courseHours +
                ", semesterName='" + semesterName + '\'' +
                '}';
    }
}
