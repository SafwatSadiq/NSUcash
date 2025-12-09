package com.mindreader007.nsucash.database;

import java.sql.Connection;

public class SetupDatabase {
    public static void init() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE," +
                "password TEXT" +
                ");";

        String sqltransaction = "CREATE TABLE IF NOT EXISTS transactions (" +
                "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL," +
                "date_time TEXT NOT NULL," +
                "type TEXT NOT NULL CHECK(type IN ('canteen', 'bus', 'advising', 'transfer', 'bookshop', 'topup'))," +
                "value REAL NOT NULL," +
                "FOREIGN KEY(username) REFERENCES accounts(username)" +
                ");";

        String sqlaccounts = "CREATE TABLE IF NOT EXISTS accounts (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE NOT NULL," +
                "name TEXT NOT NULL," +
                "phone_no TEXT CHECK(phone_no IS NULL OR phone_no GLOB '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')," +
                "age INTEGER CHECK(age > 0)," +
                "department TEXT," +
                "balance REAL DEFAULT 0" +
                ");";

        String buses = "CREATE TABLE IF NOT EXISTS buses (" +
                "bus_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "bus_name TEXT NOT NULL UNIQUE" +
                ");";

        String routes = "CREATE TABLE IF NOT EXISTS routes (" +
                "route_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "bus_id INTEGER NOT NULL," +
                "direction TEXT NOT NULL CHECK(direction IN ('TO_NSU', 'FROM_NSU'))," +
                "FOREIGN KEY (bus_id) REFERENCES buses(bus_id)" +
                ");";

        String route_stops = "CREATE TABLE IF NOT EXISTS route_stops (" +
                "stop_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "route_id INTEGER NOT NULL," +
                "stop_order INTEGER NOT NULL," +
                "stop_name TEXT NOT NULL," +
                "FOREIGN KEY (route_id) REFERENCES routes(route_id)" +
                ");";

        String schedules = "CREATE TABLE IF NOT EXISTS schedules (" +
                "schedule_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "route_id INTEGER NOT NULL," +
                "stop_times TEXT NOT NULL," +
                "available_seat INTEGER NOT NULL," +
                "FOREIGN KEY (route_id) REFERENCES routes(route_id)" +
                ");";

        String bookings = "CREATE TABLE IF NOT EXISTS bookings (" +
                "booking_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "schedule_id INTEGER NOT NULL," +
                "stops TEXT NOT NULL," +
                "booking_time TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY(user_id) REFERENCES users(user_id)," +
                "FOREIGN KEY(schedule_id) REFERENCES schedules(schedule_id)" +
                ");";

        try (Connection conn = Database.connect();
             var stmt = conn.createStatement()) {

            stmt.execute(sql);
            stmt.execute(sqltransaction);
            stmt.execute(sqlaccounts);

            stmt.execute(buses);
            stmt.execute(routes);
            stmt.execute(route_stops);
            stmt.execute(schedules);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
