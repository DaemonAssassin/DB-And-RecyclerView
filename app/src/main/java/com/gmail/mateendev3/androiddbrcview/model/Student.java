package com.gmail.mateendev3.androiddbrcview.model;

//model/pojo/data class
public class Student {

    //declaring members
    private int id;
    private final int rollNo;
    private final String name;

    //public constructor
    public Student(int id, int rollNo, String name) {
        this.id = id;
        this.rollNo = rollNo;
        this.name = name;
    }

    public Student(int rollNo, String name) {
        this.rollNo = rollNo;
        this.name = name;
    }

    //getters
    public int getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
