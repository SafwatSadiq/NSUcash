package com.mindreader007.nsucash.services;

import com.mindreader007.nsucash.database.Database;
import com.mindreader007.nsucash.model.Schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    public List<Schedule> getSchedulesByRoute(int routeId) throws SQLException {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM schedules WHERE route_id = ?";
        try (Connection conn = Database.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, routeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                schedules.add(new Schedule(rs.getInt("schedule_id"), null, rs.getString("stop_times"), rs.getInt("available_seat")));
            }
        }
        return schedules;
    }

    public void reduceSeatCount(int scheduleId) throws SQLException {
        String sql = "UPDATE schedules SET available_seat = available_seat - 1 WHERE schedule_id = ? AND available_seat > 0";
        try (Connection conn = Database.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, scheduleId);
            ps.executeUpdate();
        }
    }

    public static void increaseSeatCount(int scheduleId) throws SQLException {
        String sql = "UPDATE schedules SET available_seat = available_seat + 1 WHERE schedule_id = ? AND available_seat > 0";
        try (Connection conn = Database.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, scheduleId);
            ps.executeUpdate();
        }
    }

}

