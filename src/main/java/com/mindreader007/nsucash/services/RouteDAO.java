package com.mindreader007.nsucash.services;

import com.mindreader007.nsucash.database.Database;
import com.mindreader007.nsucash.model.Route;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteDAO {
    public List<Route> getRoutesByBus(int busId) throws SQLException {
        List<Route> routes = new ArrayList<>();
        String sql = "SELECT * FROM routes WHERE bus_id = ?";
        try (Connection conn = Database.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, busId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                routes.add(new Route(rs.getInt("route_id"), rs.getInt("bus_id"), rs.getString("direction")));
            }
        }
        return routes;
    }
}

