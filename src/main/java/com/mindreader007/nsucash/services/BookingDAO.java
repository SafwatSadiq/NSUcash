package com.mindreader007.nsucash.services;

import com.mindreader007.nsucash.database.Database;
import com.mindreader007.nsucash.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    public void addBooking(String username, int scheduleId, String stops) throws SQLException {
        String sql = "INSERT INTO bookings(username, schedule_id, stops) VALUES (?, ?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, scheduleId);
            ps.setString(3, stops);
            ps.executeUpdate();
        }
    }

    public boolean bookingExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM bookings WHERE username = ?";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public List<Booking> getBookingsByUser(String username) throws SQLException {
        List<Booking> bookings = new ArrayList<>();

        String sql =
                "SELECT b.booking_id, b.schedule_id, b.booking_time, b.stops, " +
                        "s.stopTimes, r.direction, bs.busName " +
                        "FROM bookings b " +
                        "JOIN schedules s ON b.schedule_id = s.schedule_id " +
                        "JOIN routes r ON s.route_id = r.route_id " +
                        "JOIN buses bs ON r.bus_id = bs.bus_id " +
                        "WHERE b.username = ?";

        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("booking_id"),
                        username,
                        rs.getInt("schedule_id"),
                        rs.getString("booking_time"),
                        rs.getString("busName"),
                        rs.getString("direction"),
                        rs.getString("stopTimes"),
                        rs.getString("stops")
                ));
            }
        }

        return bookings;
    }

    public void removeBooking(String username, int bookingId) throws SQLException {
        String sql = "DELETE FROM bookings WHERE username = ? AND booking_id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, bookingId);
            ps.executeUpdate();
        }
    }
}
