package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.model.Food;
import com.mindreader007.nsucash.services.AccountsDAO;
import com.mindreader007.nsucash.services.FoodDAO;
import com.mindreader007.nsucash.services.TransactionsDAO;
import com.mindreader007.nsucash.services.UserSession;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private double totalAmount;
    private final ObservableList<String> selectedFoods = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {
        idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        priceColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        List<Food> allFoods = FoodDAO.getAllFoods();
        availableFoodListTable.setItems(FXCollections.observableArrayList(allFoods));
        selectedItemListView.setItems(selectedFoods);
    }

    @FXML
    private void addSelectedClicked(){
        Food food = availableFoodListTable.getSelectionModel().getSelectedItem();
        if(food == null){
            showAlert(Alert.AlertType.INFORMATION, "Select a food from the table first.");
            return;
        }
        selectedFoods.add(food.getId() + " - " + food.getName() + " - Tk " + food.getPrice());
        totalAmount += food.getPrice();
        totalAmountLabel.setText("Total: " + totalAmount + " BDT");
    }

    @FXML
    private void addRemoveClicked() {
        String selectedItem = selectedItemListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert(Alert.AlertType.INFORMATION, "Select a food to remove first.");
            return;
        }

        try {
            String[] parts = selectedItem.split(" - Tk ");
            double price = Double.parseDouble(parts[1]);

            selectedFoods.remove(selectedItem);
            totalAmount -= price;

            if (totalAmount < 0) {
                totalAmount = 0;
            }

            totalAmountLabel.setText("Total: " + totalAmount + " BDT");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Failed to remove item.");
        }
    }

    @FXML
    private void onPaymentClicked() {
        if (selectedFoods.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No food selected.");
            return;
        }

        if (totalAmount <= 0) {
            showAlert(Alert.AlertType.INFORMATION, "Invalid total amount.");
            return;
        }

        double currentBalance = UserSession.getUser().getBalance();

        if (currentBalance < totalAmount) {
            showAlert(Alert.AlertType.ERROR, "Insufficient balance.");
            return;
        }

        AccountsDAO.decrementBalance(UserSession.getUser().getUsername(), totalAmount);
        TransactionsDAO.addTransaction(UserSession.getUser().getUsername(), "canteen", -totalAmount);

        showAlert(Alert.AlertType.INFORMATION, "Payment successful.");

        selectedFoods.clear();
        totalAmount = 0;
        totalAmountLabel.setText("Total: 0 BDT");

    }

    private void showAlert(Alert.AlertType t, String msg) {
        Alert a = new Alert(t, msg);
        a.showAndWait();
    }
}
