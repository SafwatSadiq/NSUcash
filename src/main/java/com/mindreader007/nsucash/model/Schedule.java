package com.mindreader007.nsucash.model;

public class Schedule {
    private int scheduleId;
    private Route route;
    private String stopTimes;
    private int availableSeat;

    public Schedule(int scheduleId, Route route, String stopTimes, int availableSeat) {
        this.scheduleId = scheduleId;
        this.route = route;
        this.stopTimes = stopTimes;
        this.availableSeat = availableSeat;
    }

    public int getScheduleId() { return scheduleId; }
    public Route getRoute() { return route; }
    public void setRoute(Route route) { this.route = route; }

    public String getStopTimes() { return stopTimes; }
    public int getAvailableSeat() { return availableSeat; }
    public void setAvailableSeat(int availableSeat) { this.availableSeat = availableSeat; }
}
