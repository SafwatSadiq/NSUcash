package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.services.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountScreen implements Initializable {
    @FXML
    private Label accountLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountLabel.setText("Name: " + UserSession.getUser().getName() + "\n" +
                "UserID: " +UserSession.getUser().getUserid() + "\n" +
                "Username: " +UserSession.getUser().getUsername() + "\n" +
                "Department: " +UserSession.getUser().getDepartment() + "\n" +
                "PhoneNumber: " +UserSession.getUser().getPhoneNumber() + "\n");
    }
}
