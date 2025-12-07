package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.services.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TransferScreen implements Initializable {
    @FXML
    private Label balanceLabel;
    @FXML
    private TextField transferAmountInputField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        balanceLabel.setText(String.valueOf(UserSession.getUser().getBalance()));
    }

    public void calculateBalanceAfterTransaction(){
        if(!isNumeric(transferAmountInputField.getText())) {
            balanceLabel.setText("Only Numbers Are Valid");
            return;
        }

        double balance = UserSession.getUser().getBalance();
        double transferAmount = Double.parseDouble(transferAmountInputField.getText());

        if(transferAmount < 0) {
            balanceLabel.setText("Invalid Amount");
            return;
        }

        double balanceAfterTransaction = balance - transferAmount;
        if(balanceAfterTransaction < 0)
            balanceLabel.setText("Not Enough Balance");
        else
            balanceLabel.setText(String.valueOf(balanceAfterTransaction));
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
