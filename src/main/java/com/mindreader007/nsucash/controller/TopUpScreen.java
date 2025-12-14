package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.services.AccountsDAO;
import com.mindreader007.nsucash.services.TransactionsDAO;
import com.mindreader007.nsucash.services.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class TopUpScreen {
    @FXML private ComboBox<String> amountComboBox;

    @FXML
    public void initialize() {
        amountComboBox.getItems().addAll(
                "20", "50", "100", "200", "500", "1000", "10000", "50000", "100000"
        );
    }

    @FXML
    private void onTopUpClicked(){
        String selectedAmount = amountComboBox.getValue();
        if(selectedAmount == null || selectedAmount.isEmpty()){
            return;
        }

        double amount = Double.parseDouble(selectedAmount);

        AccountsDAO.incrementBalance(UserSession.getUser().getUsername(), amount);
        TransactionsDAO.addTransaction(UserSession.getUser().getUsername(), "topup", amount);
    }

}
