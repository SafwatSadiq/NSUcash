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
                "type TEXT NOT NULL CHECK(type IN ('food', 'bus', 'advising', 'transfer', 'book'))," +
                "value REAL NOT NULL" +
                ");";

        try (Connection conn = Database.connect();
             var stmt = conn.createStatement()) {

            stmt.execute(sql);
            stmt.execute(sqltransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
