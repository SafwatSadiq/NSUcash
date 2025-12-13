package com.mindreader007.nsucash.model;

public class Course {
    private String code;
    private String name;
    private int credits;

    public Course(String code, String name, int credits) {
        this.code = code;
        this.name = name;
        this.credits = credits;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public int getCredits() { return credits; }

    public int getFee() {
        return credits == 3 ? 19500 : 6500;
    }

    @Override
    public String toString() {
        return code + " - " + name + " (" + credits + " cr)";
    }
}

