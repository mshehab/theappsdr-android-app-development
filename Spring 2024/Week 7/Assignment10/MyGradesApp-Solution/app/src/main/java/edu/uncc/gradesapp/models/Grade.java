package edu.uncc.gradesapp.models;

public class Grade {
    String letterGrade;
    double numericGrade;
    String semester;
    String courseName;
    String courseNumber;
    double numberOfCredits;
    String uid;
    String docId;

    public Grade() {
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
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

    public double getNumberOfCredits() {
        return numberOfCredits;
    }

    public void setNumberOfCredits(double numberOfCredits) {
        this.numberOfCredits = numberOfCredits;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
