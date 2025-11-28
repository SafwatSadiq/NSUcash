package com.mindreader007.nsucash.services;

import com.mindreader007.nsucash.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TransactionsDAO {
    public static void addTransaction(String username, String type, double value) {
        String sql = "INSERT INTO transactions (username, date_time, type, value) VALUES (?, datetime('now'), ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, type); // 'canteen', 'bus', 'advising', 'transfer', 'bookshop'
            ps.setDouble(3, value);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
