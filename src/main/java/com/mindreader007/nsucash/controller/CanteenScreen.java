package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.model.Food;
import com.mindreader007.nsucash.services.FoodDAO;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;

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

    @FXML
    public void initialize() throws SQLException {
        idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        priceColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        List<Food> allFoods = FoodDAO.getAllFoods();
        availableFoodListTable.setItems(FXCollections.observableArrayList(allFoods));
    }
}
