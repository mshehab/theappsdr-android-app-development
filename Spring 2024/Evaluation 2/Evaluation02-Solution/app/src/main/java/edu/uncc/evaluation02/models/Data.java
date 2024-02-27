package edu.uncc.evaluation02.models;

import java.util.ArrayList;

public class Data {
    public static final String[] categories = {
            "Work/Professional",
            "Personal Errands",
            "Home & Family",
            "Health & Fitness",
            "Finance",
            "Education & Learning",
            "Leisure & Social",
            "Projects",
            "Urgent/Important"
    };

    public static final ArrayList<Task> sampleTestTasks = new ArrayList<Task>(){{
        this.add(new Task("Task 1", "Work/Professional", "High", 4));
        this.add(new Task("Task 2", "Personal Errands", "Low", 1));
        this.add(new Task("Task 3", "Finance", "Very High", 5));
    }};

}
