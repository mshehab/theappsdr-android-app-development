package edu.uncc.evaluation02.model;

import java.io.Serializable;
import java.util.Date;

public class Bill implements Serializable {
    private String name;
    private Date createdAt;
    private double discount, amount;

    public Bill() {
    }

    public Bill(String name, Date createdAt, double discount, double amount) {
        this.name = name;
        this.createdAt = createdAt;
        this.discount = discount;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
