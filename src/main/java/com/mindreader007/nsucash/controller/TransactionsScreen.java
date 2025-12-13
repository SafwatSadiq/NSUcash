package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.model.Transaction;
import com.mindreader007.nsucash.services.TransactionsDAO;
import com.mindreader007.nsucash.services.UserSession;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionsScreen {

    @FXML
    private TableView<Transaction> transactionsTable;

    @FXML
    private TableColumn<Transaction, Integer> colId;

    @FXML
    private TableColumn<Transaction, String> colType;

    @FXML
    private TableColumn<Transaction, Double> colValue;

    @FXML
    private TableColumn<Transaction, String> colDate;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("transaction_id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date_time"));

        loadTransactions();
    }

    private void loadTransactions() {
        if (UserSession.getUser() == null) return;
        String username = UserSession.getUser().getUsername();

        List<Transaction> list = TransactionsDAO.getTransactionsByUser(username);

        transactionsTable.setItems(FXCollections.observableArrayList(list));
    }
}

