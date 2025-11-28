package com.mindreader007.nsucash.services;

import com.mindreader007.nsucash.database.Database;
import com.mindreader007.nsucash.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountsDAO {
    public static User getUser(String username){
        String query = "SELECT * FROM accounts WHERE username = ?";

        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("phone_no"),
                        rs.getInt("age"),
                        rs.getString("department"),
                        rs.getDouble("balance")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
