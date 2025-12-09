package com.mindreader007.nsucash.model;

public class Schedule {
    private int scheduleId;
    private int routeId;
    private String stopTimes;
    private int availableSeat;

    public Schedule(int scheduleId, int routeId, String stopTimes, int availableSeat) {
        this.scheduleId = scheduleId;
        this.routeId = routeId;
        this.stopTimes = stopTimes;
        this.availableSeat = availableSeat;
    }

    public int getScheduleId() { return scheduleId; }
    public int getRouteId() { return routeId; }
    public String getStopTimes() { return stopTimes; }
    public int getAvailableSeat() { return availableSeat; }
    public void setAvailableSeat(int availableSeat) { this.availableSeat = availableSeat; }
}

