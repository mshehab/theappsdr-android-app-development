package edu.uncc.gradesapp.models;

public class LetterGrade {
    String letterGrade;
    double numericGrade;

    public LetterGrade() {
    }

    public LetterGrade(String letterGrade, double numericGrade) {
        this.letterGrade = letterGrade;
        this.numericGrade = numericGrade;
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

    @Override
    public String toString() {
        return this.letterGrade;
    }
}
