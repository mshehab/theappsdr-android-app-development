package edu.charlotte.evaluation02.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Employee implements Serializable {
    String name;
    String department;
    ArrayList<Purchase> purchases;

    public Employee() {
    }

    public Employee(String name, String department, ArrayList<Purchase> purchases) {
        this.name = name;
        this.department = department;
        this.purchases = purchases;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public ArrayList<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(ArrayList<Purchase> purchases) {
        this.purchases = purchases;
    }
}