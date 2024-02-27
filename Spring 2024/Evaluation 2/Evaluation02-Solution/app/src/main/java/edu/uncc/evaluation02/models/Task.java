package edu.uncc.evaluation02.models;

import java.io.Serializable;

public class Task implements Serializable {
    String name, category, priorityStr;
    int priority;


    public Task() {
    }

    public Task(String name, String category, String priorityStr, int priority) {
        this.name = name;
        this.category = category;
        this.priorityStr = priorityStr;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriorityStr() {
        return priorityStr;
    }

    public void setPriorityStr(String priorityStr) {
        this.priorityStr = priorityStr;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
