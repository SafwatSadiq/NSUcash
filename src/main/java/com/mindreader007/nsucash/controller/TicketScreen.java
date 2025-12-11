package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.model.Booking;
import com.mindreader007.nsucash.services.BookingDAO;
import com.mindreader007.nsucash.services.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TicketScreen implements Initializable {
    private String onHoverStyle = "-fx-background-color: #a3b7ca; -fx-background-radius: 20; -fx-text-fill: #061742;";
    private String defaultStyle = "-fx-background-radius: 20; -fx-background-color: #061742; -fx-text-fill: #d1dbe4;";

    @FXML
    private Label ticketLabel;
    @FXML
    private Label bookingIdLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label userIdLabel;
    @FXML
    private Label scheduleIdLabel;
    @FXML
    private Label bookingTimeLabel;
    @FXML
    private Label busIdLabel;
    @FXML
    private Label stopsLabel;
    @FXML
    private Label stopTimesLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BookingDAO bookingDAO = new BookingDAO();


        try {
            Booking booking = bookingDAO.getBookingsByUser(UserSession.getUser().getUsername()).getFirst();
            bookingIdLabel.setText(String.valueOf(booking.getBookingId()));
            scheduleIdLabel.setText(String.valueOf(booking.getScheduleId()));
            bookingTimeLabel.setText(booking.getBookingTime());
            stopsLabel.setText(booking.getStops());
            stopTimesLabel.setText(booking.getStopTimes());
            busIdLabel.setText(String.valueOf(booking.getBusId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            bookingIdLabel.setText("N/A");
            scheduleIdLabel.setText("N/A");
            bookingTimeLabel.setText("N/A");
            stopsLabel.setText("N/A");
            stopTimesLabel.setText("N/A");
            busIdLabel.setText("N/A");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Booking Exists");
            alert.setHeaderText(null);
            alert.setContentText("Please first book a bus to view ticket");
            alert.showAndWait();
        }
        usernameLabel.setText(UserSession.getUser().getName());
        userIdLabel.setText(String.valueOf(UserSession.getUser().getUserid()));
    }

    public void onHoverButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle(onHoverStyle);
    }

    public void onHoverExitButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle(defaultStyle);
    }

    @FXML
    private void onBackClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mindreader007/nsucash/fxml/MainScreen.fxml"));
            Parent homeRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(homeRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onCancelClick(){

    }
}
