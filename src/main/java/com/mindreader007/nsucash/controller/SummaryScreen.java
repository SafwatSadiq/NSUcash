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
import javafx.scene.control.Tooltip;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class SummaryScreen implements Initializable {
    @FXML
    private PieChart expensePieChart;
    @FXML
    private PieChart incomePieChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Transaction> transactions = TransactionsDAO.getTransactionsByUser(UserSession.getUser().getUsername());

        Map<String, Double> negativeTotalsByType = new HashMap<>();
        Map<String, Double> postiveTotalsByType = new HashMap<>();
        for (Transaction t : transactions) {
            double value = t.getValue();
            if (value < 0) {
                negativeTotalsByType.merge(t.getType(), Math.abs(t.getValue()), Double::sum);
            } else if (value > 0) {
                postiveTotalsByType.merge(t.getType(), Math.abs(t.getValue()), Double::sum);
            }
        }

        ObservableList<PieChart.Data> positiveData = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : postiveTotalsByType.entrySet()) {
            positiveData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        ObservableList<PieChart.Data> negativeData = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : negativeTotalsByType.entrySet()) {
            negativeData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        Platform.runLater(() -> {
            incomePieChart.setData(positiveData);
            expensePieChart.setData(negativeData);

            addTooltips(incomePieChart);
            addTooltips(expensePieChart);
        });
    }

    private void addTooltips(PieChart chart) {
        for (PieChart.Data data : chart.getData()) {
            if (data.getNode() != null) {
                Tooltip tooltip = new Tooltip(
                        data.getName() + " : " + String.format("%.2f BDT", data.getPieValue())
                );
                tooltip.setShowDelay(javafx.util.Duration.ZERO);
                tooltip.setHideDelay(javafx.util.Duration.seconds(0.2));
                Tooltip.install(data.getNode(), tooltip);
            }
        }
        chart.setLabelsVisible(true);
    }


}
