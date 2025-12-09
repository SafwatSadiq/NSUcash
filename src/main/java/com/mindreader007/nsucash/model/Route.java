package com.mindreader007.nsucash.model;

import java.util.List;

public class Route {
    private int routeId;
    private int busId;
    private String direction; // TO_NSU or FROM_NSU
    private List<RouteStop> stops;
    private List<Schedule> schedules;

    public Route(int routeId, int busId, String direction) {
        this.routeId = routeId;
        this.busId = busId;
        this.direction = direction;
    }

    public int getRouteId() { return routeId; }
    public void setRouteId(int routeId) { this.routeId = routeId; }

    public int getBusId() { return busId; }
    public void setBusId(int busId) { this.busId = busId; }

    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }

    public List<RouteStop> getStops() { return stops; }
    public void setStops(List<RouteStop> stops) { this.stops = stops; }

    public List<Schedule> getSchedules() { return schedules; }
    public void setSchedules(List<Schedule> schedules) { this.schedules = schedules; }
}

