package edu.charlotte.evaluation03.models;

import java.io.Serializable;

public class Answer implements Serializable {
    //{name:"Java", votes: 0},
    String name;
    int votes;

    public Answer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "name='" + name + '\'' +
                ", votes=" + votes +
                '}';
    }
}
