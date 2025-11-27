package com.mindreader007.nsucash.services;

import com.mindreader007.nsucash.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    public static boolean validateLogin(String username, String password) {
        String query = "SELECT password FROM users WHERE username = ?";

        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return false;

            String stored = rs.getString("password");

            return stored.equals(password);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
