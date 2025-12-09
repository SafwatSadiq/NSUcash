package com.mindreader007.nsucash.model;

public class Booking {
    private int bookingId;
    private String username;
    private int scheduleId;
    private String bookingTime;
    private String busName;
    private String direction;
    private String stopTimes;
    private String stops;

    public Booking(int bookingId, String username, int scheduleId, String bookingTime,
                   String busName, String direction, String stopTimes, String stops) {
        this.bookingId = bookingId;
        this.username = username;
        this.scheduleId = scheduleId;
        this.bookingTime = bookingTime;
        this.busName = busName;
        this.direction = direction;
        this.stopTimes = stopTimes;
        this.stops = stops;
    }


    public int getBookingId() {
        return bookingId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public String getBusName() {
        return busName;
    }

    public String getDirection() {
        return direction;
    }

    public String getStopTimes() {
        return stopTimes;
    }

    public String getStops() {
        return stops;
    }

    public String getUsername() {
        return username;
    }
}

