package edu.uncc.assignment05.models;

import java.util.ArrayList;

public class Data {
    public static final String[] states = {
            "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
            "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
            "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan",
            "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire",
            "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma",
            "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee",
            "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
    };

    public static final String[] genders = {
            "Female", "Male", "Other", "Prefer not to say"
    };

    public static final String[] groups = {
            "Friend", "Family", "Coworker", "Classmate", "Other"
    };

    public static final ArrayList<User> sampleTestUsers = new ArrayList<User>(){{
        this.add(new User("Bob Smith", "b@b.com", "Male", 25, "New Mexico", "Friend"));
        this.add(new User("Alice Green", "a@a.com", "Female", 28, "Washington", "Coworker"));
        this.add(new User("Tom Brown", "t@t.com", "Male", 22, "North Carolina", "Classmate"));
    }};

}
