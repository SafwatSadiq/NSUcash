package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.model.RouteStop;
import com.mindreader007.nsucash.model.Schedule;
import com.mindreader007.nsucash.services.BusService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;

public class BusScreen{
    @FXML
    private TableView<Schedule> scheduleTable;
    @FXML
    private TableColumn<Schedule, String> busColumn;
    @FXML
    private TableColumn<Schedule, String> directionColumn;
    @FXML
    private TableColumn<Schedule, String> stoptimesColumn;
    @FXML
    private TableColumn<Schedule, Integer> seatsColumn;
    @FXML
    private TableColumn<Schedule, String> stopsColumn;
    @FXML
    private ListView<String> locationListView;
    @FXML
    private CheckBox toNsuCheckBox;
    @FXML
    private CheckBox fromNsuCheckBox;

    private BusService busService = new BusService();


    @FXML
    public void initialize() throws SQLException{
        busColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRoute().getBus().getBusName()));
        directionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRoute().getDirection()));
        stoptimesColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStopTimes()));
        seatsColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAvailableSeat()).asObject());
        stopsColumn.setCellValueFactory(data -> {
            List<RouteStop> stops = data.getValue().getRoute().getStops();
            String stopsString = stops.stream()
                    .map(RouteStop::getStopName)
                    .reduce((a, b) -> a + "-" + b)
                    .orElse("");
            return new SimpleStringProperty(stopsString);
        });


        List<String> stops = busService.getAllStops();
        locationListView.getItems().addAll(stops);
        locationListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        List<Schedule> allSchedules = busService.getAllSchedules();
        scheduleTable.setItems(FXCollections.observableArrayList(allSchedules));
    }

    @FXML
    private void applyFilter() throws SQLException {
        String selectedStop = locationListView.getSelectionModel().getSelectedItem();
        if (selectedStop != null) {
            if(toNsuCheckBox.isSelected() && fromNsuCheckBox.isSelected()){
                List<Schedule> filtered = busService.searchSchedules(selectedStop, null);
                scheduleTable.setItems(FXCollections.observableArrayList(filtered));
            }
            else if(toNsuCheckBox.isSelected()){
                List<Schedule> filtered = busService.searchSchedules(selectedStop, "TO_NSU");
                scheduleTable.setItems(FXCollections.observableArrayList(filtered));
            }
            else if(fromNsuCheckBox.isSelected()){
                List<Schedule> filtered = busService.searchSchedules(selectedStop, "FROM_NSU");
                scheduleTable.setItems(FXCollections.observableArrayList(filtered));
            }
            else{
                List<Schedule> filtered = busService.searchSchedules(selectedStop, null);
                scheduleTable.setItems(FXCollections.observableArrayList(filtered));
            }
        }
        else{
            if(toNsuCheckBox.isSelected() && fromNsuCheckBox.isSelected()){
                List<Schedule> filtered = busService.searchSchedules(null, null);
                scheduleTable.setItems(FXCollections.observableArrayList(filtered));
            }
            else if(toNsuCheckBox.isSelected()){
                List<Schedule> filtered = busService.searchSchedules(null, "TO_NSU");
                scheduleTable.setItems(FXCollections.observableArrayList(filtered));
            }
            else if(fromNsuCheckBox.isSelected()){
                List<Schedule> filtered = busService.searchSchedules(null, "FROM_NSU");
                scheduleTable.setItems(FXCollections.observableArrayList(filtered));
            }
            else{
                List<Schedule> filtered = busService.searchSchedules(null, null);
                scheduleTable.setItems(FXCollections.observableArrayList(filtered));
            }
        }
    }
}
