package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.model.Course;
import com.mindreader007.nsucash.model.Food;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CanteenScreen {
    @FXML
    private TableView<Food> availableFoodListTable;
    @FXML private TableColumn<Food, Integer> idColumn;
    @FXML private TableColumn<Food, String> nameColumn;
    @FXML private TableColumn<Food, Double> priceColumn;

    @FXML private ListView<String> selectedItemListView;
    @FXML private Label totalAmountLabel;
    @FXML private Button btnAdd;
    @FXML private Button btnRemove;
    @FXML private Button btnPay;
}
