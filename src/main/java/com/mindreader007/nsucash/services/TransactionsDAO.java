package com.mindreader007.nsucash.services;

import com.mindreader007.nsucash.database.Database;
import com.mindreader007.nsucash.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Transaction> getTransactionsByUser(String username) {
        List<Transaction> list = new ArrayList<>();

        String sql = "SELECT * FROM transactions WHERE username = ? ORDER BY date_time DESC";

        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getString("username"),
                        rs.getString("date_time"),
                        rs.getString("type"),
                        rs.getDouble("value")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
