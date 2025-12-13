package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.model.User;
import com.mindreader007.nsucash.services.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountScreen implements Initializable {
    @FXML private Label nameLabel;
    @FXML private Label deptLabel;
    @FXML private Label balanceLabel;
    @FXML private Label usernameLabel;
    @FXML private Label phoneLabel;
    @FXML private Label ageLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(UserSession.getUser().getName());
        deptLabel.setText(UserSession.getUser().getDepartment());
        balanceLabel.setText("Tk " + UserSession.getUser().getBalance());
        usernameLabel.setText(UserSession.getUser().getUsername());
        phoneLabel.setText(UserSession.getUser().getPhoneNumber());
        ageLabel.setText(String.valueOf(UserSession.getUser().getAge()));
    }

    @FXML
    private void onInstagramClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instagram");
        alert.setHeaderText(null);
        alert.setContentText("Instagram ID: @" + UserSession.getUser().getUsername());

        alert.showAndWait();
    }

    @FXML
    private void onFacebookClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Facebook");
        alert.setHeaderText(null);
        alert.setContentText("Facebook ID: @" + UserSession.getUser().getUsername());

        alert.showAndWait();
    }

    @FXML
    private void onTelegramClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Telegram");
        alert.setHeaderText(null);
        alert.setContentText("Telegram ID: @" + UserSession.getUser().getUsername());

        alert.showAndWait();
    }

    @FXML
    private void onEmailClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Email");
        alert.setHeaderText(null);
        alert.setContentText("Email ID: @" + UserSession.getUser().getUsername());

        alert.showAndWait();
    }

    @FXML
    private void onLinkedinClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Linkedin");
        alert.setHeaderText(null);
        alert.setContentText("Linkedin ID: @" + UserSession.getUser().getUsername());

        alert.showAndWait();
    }

}
