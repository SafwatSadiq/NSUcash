package com.mindreader007.nsucash.controller;

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

}
