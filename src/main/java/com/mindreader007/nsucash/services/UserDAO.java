package com.mindreader007.nsucash.services;

import com.mindreader007.nsucash.database.Database;
import com.mindreader007.nsucash.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    public static List<String> getAllUserNames(){
        List<String> list = new ArrayList<>();
        String sql = "SELECT username FROM users";

        try(Connection conn = Database.connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> searchInDatabase(String query) {

        List<String> list = new ArrayList<>();

        String sql = "SELECT username FROM users WHERE username LIKE ? LIMIT 10";

        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, query + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("username"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean usernameExists(String username){
        String query = "SELECT 1 FROM users WHERE username = ? LIMIT 1";

        try (Connection conn = Database.connect();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public static boolean changePassword(String username, String newPassword){
        String query = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection con = Database.connect();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, newPassword);
            ps.setString(2, username);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
