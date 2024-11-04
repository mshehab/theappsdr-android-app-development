package edu.uncc.evaluation03.models;

import java.io.Serializable;

public class Expense implements Serializable {
    private String name;
    private String category;
    private double amount;
    private Priority priority;

    public Expense(String name, String category, double amount, Priority priority) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.priority = priority;
    }

    public Expense() {
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
