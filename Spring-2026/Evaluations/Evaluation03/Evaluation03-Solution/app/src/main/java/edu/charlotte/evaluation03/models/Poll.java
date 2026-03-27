package edu.charlotte.evaluation03.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Poll implements Serializable {
    String name;
    String question;
    String creatorId;
    String creatorName;
    String docId;
    ArrayList<Answer> answers;
    ArrayList<String> completedBy;

    public Poll() {
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public ArrayList<String> getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(ArrayList<String> completedBy) {
        this.completedBy = completedBy;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "name='" + name + '\'' +
                ", question='" + question + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", answers=" + answers +
                ", completedBy=" + completedBy +
                '}';
    }
}
