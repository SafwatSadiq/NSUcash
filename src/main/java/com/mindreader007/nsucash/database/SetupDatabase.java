package com.mindreader007.nsucash.database;

import java.sql.Connection;

public class SetupDatabase {
    public static void init() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE," +
                "password TEXT" +
                ");";

        String sqltransaction = "CREATE TABLE IF NOT EXISTS transactions (" +
                "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL," +
                "date_time TEXT NOT NULL," +
                "type TEXT NOT NULL CHECK(type IN ('canteen', 'bus', 'advising', 'transfer', 'bookshop', 'topup'))," +
                "value REAL NOT NULL," +
                "FOREIGN KEY(username) REFERENCES accounts(username)" +
                ");";

        String sqlaccounts = "CREATE TABLE IF NOT EXISTS accounts (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE NOT NULL," +
                "name TEXT NOT NULL," +
                "phone_no TEXT CHECK(phone_no IS NULL OR phone_no GLOB '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')," +
                "age INTEGER CHECK(age > 0)," +
                "department TEXT," +
                "balance REAL DEFAULT 0" +
                ");";

        try (Connection conn = Database.connect();
             var stmt = conn.createStatement()) {

            stmt.execute(sql);
            stmt.execute(sqltransaction);
            stmt.execute(sqlaccounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
