package edu.uncc.assignment08;

import java.io.Serializable;

public class PhoneNumber implements Serializable {
    public String number;
    public String type;

    public PhoneNumber() {
    }

    public PhoneNumber(String number, String type) {
        this.number = number;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
