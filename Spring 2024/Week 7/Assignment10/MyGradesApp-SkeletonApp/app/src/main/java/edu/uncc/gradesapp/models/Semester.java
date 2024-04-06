package edu.uncc.gradesapp.models;

public class Semester {
    public String name;
    public int year, month;

    public Semester() {
    }

    public Semester(int year, int month) {
        this.year = year;
        this.month = month;

        if(month == 1) {
            this.name = "Spring " + year;
        } else {
            this.name = "Fall " + year;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return name;
    }
}
