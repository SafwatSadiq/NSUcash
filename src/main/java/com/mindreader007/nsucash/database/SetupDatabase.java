package com.mindreader007.nsucash.database;

import java.sql.Connection;

public class SetupDatabase {
    public static void init() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE," +
                "password TEXT" +
                ");";

        try (Connection conn = Database.connect();
             var stmt = conn.createStatement()) {

            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
