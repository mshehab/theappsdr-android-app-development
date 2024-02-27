package edu.uncc.assignment05.models;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String email;
    private String gender;
    private int age;
    private String state;
    private String group;

    public User() {
    }

    public User(String name, String email, String gender, int age, String state, String group) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.state = state;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", state='" + state + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
