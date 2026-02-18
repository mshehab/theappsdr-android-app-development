package edu.charlotte.evaluation01;

import java.io.Serializable;

public class Calorie implements Serializable {
    private String gender;
    private double heightFt, heightIn;
    private double weight;
    private double age;
    private String activityLevel;

    public Calorie() {
    }

    public ActivityLevel calculateBMR(){
        double bmr = ((10.0 * weight) / 2.205) + (15.875 * ((heightFt * 12.0) + heightIn)) - (5 * age);
        if(gender.equals("Male")){
            bmr = bmr + 5;
        } else{
            bmr = bmr - 161;
        }

        ActivityLevel activityLevel = new ActivityLevel();
        activityLevel.calculateTDEE(bmr, this.activityLevel);
        return activityLevel;
    }

    public Calorie(String gender, double heightFt, double heightIn, double weight, double age, String activityLevel) {
        this.gender = gender;
        this.heightFt = heightFt;
        this.heightIn = heightIn;
        this.weight = weight;
        this.age = age;
        this.activityLevel = activityLevel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getHeightFt() {
        return heightFt;
    }

    public void setHeightFt(double heightFt) {
        this.heightFt = heightFt;
    }

    public double getHeightIn() {
        return heightIn;
    }

    public void setHeightIn(double heightIn) {
        this.heightIn = heightIn;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public static class ActivityLevel implements Serializable {
        double bmr;
        double tdee;

        public ActivityLevel() {
        }

        public void calculateTDEE(double bmr, String activityLevel) {
            this.bmr = bmr;
            switch (activityLevel) {
                case "Sedentary":
                    tdee = bmr * 1.2;
                    break;
                case "Lightly active":
                    tdee = bmr * 1.375;
                    break;
                case "Moderately active":
                    tdee = bmr * 1.55;
                    break;
                case "Very active":
                    tdee = bmr * 1.725;
                    break;
                case "Super active":
                    tdee = bmr * 1.9;
                    break;
                default:
                    tdee = bmr; // If no activity level is selected, TDEE is equal to BMR
            }
        }

        public ActivityLevel(double bmr, double tdee) {
            this.bmr = bmr;
            this.tdee = tdee;
        }

        public double getBmr() {
            return bmr;
        }

        public void setBmr(double bmr) {
            this.bmr = bmr;
        }

        public double getTdee() {
            return tdee;
        }

        public void setTdee(double tdee) {
            this.tdee = tdee;
        }
    }
}
