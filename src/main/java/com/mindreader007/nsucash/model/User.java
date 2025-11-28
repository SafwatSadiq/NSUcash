package com.mindreader007.nsucash.model;

public class User {
    private int userid;
    private String username;
    private String name;
    private String phonenumber;
    private int age;
    private String department;
    private double balance;

    public User(int userid, String username, String name, String phonenumber, int age, String department, double balance){
        this.userid = userid;
        this.username = username;
        this.name = name;
        this.phonenumber = phonenumber;
        this.age = age;
        this.department = department;
        this.balance = balance;
    }

    public String getDepartment() {
        return department;
    }

    public int getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPhoneNumber() {
        return phonenumber;
    }
}
