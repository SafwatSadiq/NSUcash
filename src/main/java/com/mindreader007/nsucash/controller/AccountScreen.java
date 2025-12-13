package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.services.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        balanceLabel.setText("Tk " + String.valueOf(UserSession.getUser().getBalance()));
        usernameLabel.setText(UserSession.getUser().getUsername());
        phoneLabel.setText(UserSession.getUser().getPhoneNumber());
        ageLabel.setText(String.valueOf(UserSession.getUser().getAge()));
    }
}
