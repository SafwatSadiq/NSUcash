package com.mindreader007.nsucash.model;

public class RouteStop {
    private int stopId;
    private int routeId;
    private int stopOrder;
    private String stopName;

    public RouteStop(int stopId, int routeId, int stopOrder, String stopName) {
        this.stopId = stopId;
        this.routeId = routeId;
        this.stopOrder = stopOrder;
        this.stopName = stopName;
    }

    public int getStopId() { return stopId; }
    public int getRouteId() { return routeId; }
    public int getStopOrder() { return stopOrder; }
    public String getStopName() { return stopName; }
}

