package com.mindreader007.nsucash.services;

import com.mindreader007.nsucash.database.Database;
import com.mindreader007.nsucash.model.Food;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodDAO {
    public static List<Food> getAllFoods() throws SQLException {
        List<Food> food_list = new ArrayList<>();
        String sql = "SELECT * FROM foods ORDER BY food_id";

        try (Connection conn = Database.connect(); PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                food_list.add(new Food(rs.getInt("food_id"), rs.getString("food_name"), rs.getDouble("price")));
            }
        }
        return food_list;
    }
}
