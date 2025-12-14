package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.model.Booking;
import com.mindreader007.nsucash.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
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
        updateLabels();
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

    @FXML
    private void onCancelClick() throws SQLException {
        BookingDAO bookingDAO = new BookingDAO();
        try {
            bookingDAO.getBookingsByUser(UserSession.getUser().getUsername()).getFirst();
        }
        catch (Exception e){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("No Booking Found");
            error.setHeaderText(null);
            error.setContentText("Book a ticket first!");
            setAlertIcon(error);
            setAlertCSS(error);
            error.showAndWait();
            return;
        }


        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Cancel Booking");
        confirmAlert.setHeaderText("Are you sure you want to cancel this booking?");
        confirmAlert.setContentText("This action cannot be undone.");
        setAlertIcon(confirmAlert);
        setAlertCSS(confirmAlert);

        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            String enteredPassword = showPasswordDialog();
            if (enteredPassword == null) {
                return;
            }

            if (UserDAO.validateLogin(UserSession.getUser().getUsername(), enteredPassword)) {
                int scheduleId = bookingDAO.getBookingsByUser(UserSession.getUser().getUsername()).getFirst().getScheduleId();
                bookingDAO.removeBooking(UserSession.getUser().getUsername(), bookingDAO.getBookingsByUser(UserSession.getUser().getUsername()).getFirst().getBookingId());
                ScheduleDAO.increaseSeatCount(scheduleId);
                TransactionsDAO.addTransaction(UserSession.getUser().getUsername(), "bus", 100.0);
                AccountsDAO.incrementBalance(UserSession.getUser().getUsername(), 100.0);


                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Success");
                success.setHeaderText(null);
                success.setContentText("Your booking has been cancelled.");
                setAlertIcon(success);
                setAlertCSS(success);
                success.showAndWait();
                updateLabels();

            } else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Wrong Password");
                error.setHeaderText(null);
                error.setContentText("The password you entered is incorrect.");
                setAlertIcon(error);
                setAlertCSS(error);
                error.showAndWait();
            }
        }
    }

    private String showPasswordDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Confirm Action");
        dialog.setHeaderText("Please enter your password to continue.");

        ButtonType confirmBtn = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmBtn, cancelBtn);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        VBox vbox = new VBox(10);
        vbox.getChildren().add(passwordField);
        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(button -> {
            if (button == confirmBtn) {
                return passwordField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private void updateLabels(){
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
            setAlertIcon(alert);
            setAlertCSS(alert);
            alert.showAndWait();
        }
        usernameLabel.setText(UserSession.getUser().getName());
        userIdLabel.setText(String.valueOf(UserSession.getUser().getUserid()));
    }

    private void setAlertIcon(Alert alert){
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(
                new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream("/com/mindreader007/nsucash/image/NSUCash.png")
                        )
                )
        );
    }

    private void setAlertCSS(Alert alert) {
        alert.getDialogPane().getStylesheets().add(
                getClass().getResource("/com/mindreader007/nsucash/css/Alert.css").toExternalForm()
        );
    }
}
