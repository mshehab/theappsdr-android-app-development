package edu.uncc.evaluation04.models;
import java.util.ArrayList;

public class DataSource {
    static public ArrayList<Course> getCourses(){
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(new Course("e3a6ef8a-b1a8-4bc2-8f86-fb7f5c276c0b", "Introduction to Computer Science I", "ITSC 1212", 4));
        courses.add(new Course("43a3b7de-91b8-48ba-9cc5-e19f94444d93", "Introduction to Computer Science II", "ITSC 1213", 4));
        courses.add(new Course("2da22a62-b200-47f1-b0c8-8ecadc032256", "Computing Professionals", "ITSC 1600", 2));
        courses.add(new Course("973b1c16-4009-4828-afb2-d149b8395c6d", "Computer Science Program, Identity, Career", "ITSC 2600", 2));
        courses.add(new Course("b1a5330a-ff43-4b10-bbd9-3789c2030b0f", "Logic and Algorithms", "ITSC 2175", 3));
        courses.add(new Course("3bba8780-196d-4173-9c4a-6fa08e54f803", "Introduction to Discrete Structures", "MATH 1165", 3));
        courses.add(new Course("e67afd67-e79c-47ab-9aab-6fa08e54f803", "Introduction to Computer Systems", "ITSC 2181", 4));
        courses.add(new Course("e67afd67-e79c-47ab-9aab-28b0af912cdb", "Data Structures and Algorithms", "ITSC 2214", 3));
        courses.add(new Course("7b01534f-08cd-4879-99fd-bf2a1243bb5d", "Introduction to Operating Systems and Networking", "ITSC 3146", 3));
        courses.add(new Course("2494ad0f-5e68-43d1-8fea-5c7aef9e8e1c", "Software Engineering", "ITSC 3155", 3));
        courses.add(new Course("2b7a1d54-4b65-4b4e-b3f4-dd9ca06e8afa", "Computers and Their Impact on Society", "ITSC 3688", 3));
        courses.add(new Course("1e07e6ed-40f4-44c0-a107-49f5c1521073", "Game Design and Development Studio", "ITCS 4232", 3));
        courses.add(new Course("c2a103ea-9935-4a11-8dc9-867c52fd2c6c", "Interaction Design Studio", "ITIS 4390", 3));
        courses.add(new Course("17f3844c-700c-4697-8e83-d771c122b3a0", "Mobile Application Development", "ITIS 4180", 3));
        courses.add(new Course("60740574-3bb5-4cac-9f07-795d030f6418", "Network-Based Application Development", "ITIS 4166", 3));
        courses.add(new Course("e01e7edb-e848-4223-9c53-1c002fe71aae", "Introduction to Software Testing and Assurance", "ITIS 3320", 3));
        courses.add(new Course("5f5d026a-c2d7-45df-85cf-e6527656e2c9", "Interactive Systems Design and Implementation", "ITIS 4340", 3));
        courses.add(new Course("ec4aa78b-17a9-4476-9c87-3be43b7f0b27", "Human-Centered Design", "ITIS 3130", 3));
        courses.add(new Course("04fdc044-0eeb-4471-9189-2b32824db1b0", "Design and Implementation of Object-Oriented Systems", "ITCS 3112", 3));
        courses.add(new Course("e7772363-6e45-43ab-98b0-87a35e44d5eb", "Secure Programming and Penetration Testing", "ITIS 4221", 3));
        courses.add(new Course("f023f4d6-6301-4088-b7aa-df2f97925428", "Software Architecture and Design", "ITIS 3310", 3));
        courses.add(new Course("a90814bc-2f5c-4c1a-b426-cf0453b26600", "Web-Based Application Design and Development", "ITIS 3135", 3));
        courses.add(new Course("82e30e74-e7c8-4823-91a2-09b4d1bdbf24", "Database Design and Implementation", "ITCS 3160", 3));
        courses.add(new Course("71cd09e7-b163-4f92-81ad-f4d44d45d2c3", "Rapid Prototyping", "ITIS 4350", 3));
        courses.add(new Course("ff843e83-d2d1-4851-ba62-80b53d98f941", "Software Requirements and Project Management", "ITIS 3300", 3));


        return courses;
    }

    static public ArrayList<Semester> getSemesters(){
        ArrayList<Semester> semesters = new ArrayList<>();
        for (int i = 0; i < 6 ; i++) {
            semesters.add(new Semester(2024 - i, 8));
            semesters.add(new Semester(2024 - i, 1));
        }
        return semesters;
    }

    static public ArrayList<LetterGrade> getLetterGrades(){
        ArrayList<LetterGrade> letterGrades = new ArrayList<>();
        letterGrades.add(new LetterGrade("A", 4.0));
        letterGrades.add(new LetterGrade("B", 3.0));
        letterGrades.add(new LetterGrade("C", 2.0));
        letterGrades.add(new LetterGrade("D", 1.0));
        letterGrades.add(new LetterGrade("F", 0.0));
        return letterGrades;
    }
}
