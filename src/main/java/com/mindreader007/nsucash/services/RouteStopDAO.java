package com.mindreader007.nsucash.services;

import com.mindreader007.nsucash.database.Database;
import com.mindreader007.nsucash.model.RouteStop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteStopDAO {
    public List<RouteStop> getStopsByRoute(int routeId) throws SQLException {
        List<RouteStop> stops = new ArrayList<>();
        String sql = "SELECT * FROM route_stops WHERE route_id = ? ORDER BY stop_order";
        try (Connection conn = Database.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, routeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                stops.add(new RouteStop(rs.getInt("stop_id"), rs.getInt("route_id"), rs.getInt("stop_order"), rs.getString("stop_name")));
            }
        }
        return stops;
    }
}
