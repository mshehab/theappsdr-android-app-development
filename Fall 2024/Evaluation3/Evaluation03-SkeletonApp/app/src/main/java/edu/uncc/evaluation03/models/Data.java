package edu.uncc.evaluation03.models;

import java.sql.Array;
import java.util.ArrayList;

public class Data {

    public static ArrayList<Expense> getTestExpenses(){
        return new ArrayList<Expense>(){{
            //(String name, String category, double amount, Priority priority)
            add(new Expense("Rent", "Housing", 750, new Priority("Very High", "Essential expenses crucial for daily living or urgent needs.")));
            add(new Expense("Car Gas", "Transportation", 220, new Priority("High", "Important expenses that significantly impact well-being or obligations.")));
            add(new Expense("Car Gas", "Housing", 150, new Priority("High", "Important expenses that significantly impact well-being or obligations.")));
        }};
    }

    public static ArrayList<Priority> getPriorities(){
        return new ArrayList<Priority>(){{
            add(new Priority("Very High", "Essential expenses crucial for daily living or urgent needs."));
            add(new Priority("High", "Important expenses that significantly impact well-being or obligations."));
            add(new Priority("Medium", "Necessary but flexible expenses that can be adjusted as needed."));
            add(new Priority("Low", "Non-essential expenses that provide convenience or minor benefits."));
            add(new Priority("Very Low", "Discretionary expenses with minimal impact if postponed or avoided."));
        }};
    }

    public static ArrayList<String> getCategories(){
        return new ArrayList<String>(){{
            add("Housing");
            add("Transportation");
            add("Food & Groceries");
            add("Healthcare");
            add("Entertainment & Leisure");
            add("Savings & Investments");
            add("Other");
        }};
    }
}
