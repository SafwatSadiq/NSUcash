package com.mindreader007.nsucash.services;

import com.mindreader007.nsucash.model.Bus;
import com.mindreader007.nsucash.model.Route;
import com.mindreader007.nsucash.model.Schedule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusService {

    private BusDAO busDAO = new BusDAO();
    private RouteDAO routeDAO = new RouteDAO();
    private RouteStopDAO stopDAO = new RouteStopDAO();
    private ScheduleDAO scheduleDAO = new ScheduleDAO();

    public List<Bus> getAllBusesWithDetails() throws SQLException {
        List<Bus> buses = busDAO.getAllBuses();

        for (Bus bus : buses) {
            List<Route> routes = routeDAO.getRoutesByBus(bus.getBusId());
            for (Route route : routes) {
                route.setStops(stopDAO.getStopsByRoute(route.getRouteId()));
                route.setSchedules(scheduleDAO.getSchedulesByRoute(route.getRouteId()));
            }
            bus.setRoutes(routes);
        }

        return buses;
    }

    public List<Schedule> searchSchedules(String stopName, String direction) throws SQLException {
        List<Schedule> result = new ArrayList<>();

        for (Bus bus : getAllBusesWithDetails()) {
            for (Route route : bus.getRoutes()) {
                if (direction != null && !direction.isEmpty() &&
                        !route.getDirection().equalsIgnoreCase(direction)) {
                    continue;
                }

                boolean hasStop = true;
                if (stopName != null && !stopName.isEmpty()) {
                    hasStop = route.getStops()
                            .stream()
                            .anyMatch(stop -> stop.getStopName().equalsIgnoreCase(stopName));
                }
                if (!hasStop) continue;

                result.addAll(route.getSchedules());
            }
        }

        return result;
    }

    public void reduceSeatCount(int scheduleId) throws SQLException {
        List<Schedule> schedules = new ArrayList<>();
        for (Bus bus : getAllBusesWithDetails()) {
            for (Route route : bus.getRoutes()) {
                for (Schedule schedule : route.getSchedules()) {
                    if (schedule.getScheduleId() == scheduleId) {
                        int seats = schedule.getAvailableSeat();
                        if (seats > 0) {
                            schedule.setAvailableSeat(seats - 1);
                            scheduleDAO.reduceSeatCount(scheduleId);
                        }
                    }
                }
            }
        }
    }
}

