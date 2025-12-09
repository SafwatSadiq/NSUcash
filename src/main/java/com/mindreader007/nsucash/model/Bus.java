package com.mindreader007.nsucash.model;

import java.util.List;

public class Bus {
    private int busId;
    private String busName;
    private List<Route> routes;

    public Bus(int busId, String busName) {
        this.busId = busId;
        this.busName = busName;
    }

    public int getBusId() { return busId; }
    public void setBusId(int busId) { this.busId = busId; }

    public String getBusName() { return busName; }
    public void setBusName(String busName) { this.busName = busName; }

    public List<Route> getRoutes() { return routes; }
    public void setRoutes(List<Route> routes) { this.routes = routes; }
}

