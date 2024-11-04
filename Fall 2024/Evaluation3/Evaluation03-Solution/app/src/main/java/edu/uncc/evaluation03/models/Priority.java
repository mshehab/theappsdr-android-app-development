package edu.uncc.evaluation03.models;

import java.io.Serializable;

public class Priority implements Serializable {
    private String name;
    private String description;

    public Priority() {
    }

    public Priority(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
