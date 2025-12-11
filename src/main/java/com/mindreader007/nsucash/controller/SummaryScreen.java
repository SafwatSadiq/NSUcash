package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.model.Transaction;
import com.mindreader007.nsucash.services.TransactionsDAO;
import com.mindreader007.nsucash.services.UserSession;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class SummaryScreen implements Initializable {
    @FXML
    private PieChart pieChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Transaction> transactions = TransactionsDAO.getTransactionsByUser(UserSession.getUser().getUsername());

        Map<String, Double> totalsByType = new HashMap<>();
        for (Transaction t : transactions) {
            totalsByType.merge(t.getType(), t.getValue(), Double::sum);
        }

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : totalsByType.entrySet()) {
            pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        pieChart.setData(pieData);
        pieChart.setTitle("Expense Distribution");
        pieChart.setLabelsVisible(true);
    }


}
