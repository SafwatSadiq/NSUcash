package com.mindreader007.nsucash.services;

import com.mindreader007.nsucash.database.Database;
import com.mindreader007.nsucash.model.Bus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusDAO {
    public Bus getBus(int busId) throws SQLException {
        String sql = "SELECT * FROM buses WHERE bus_id = ?";
        try (Connection conn = Database.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, busId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Bus(rs.getInt("bus_id"), rs.getString("bus_name"));
            }
        }
        return null;
    }

    public List<Bus> getAllBuses() throws SQLException {
        List<Bus> buses = new ArrayList<>();
        String sql = "SELECT * FROM buses";
        try (Connection conn = Database.connect(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                buses.add(new Bus(rs.getInt("bus_id"), rs.getString("bus_name")));
            }
        }
        return buses;
    }
}
