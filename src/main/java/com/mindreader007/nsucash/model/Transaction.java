package com.mindreader007.nsucash.model;

public class Transaction {
    private int transaction_id;
    private String username;
    private String date_time;
    private String type;
    private double value;

    public Transaction(int transaction_id, String username, String date_time, String type, double value) {
        this.transaction_id = transaction_id;
        this.username = username;
        this.date_time = date_time;
        this.type = type;
        this.value = value;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public String getUsername() {
        return username;
    }

    public String getDate_time() {
        return date_time;
    }

    public String getType() {
        return type;
    }

    public double getValue() {
        return value;
    }
}
