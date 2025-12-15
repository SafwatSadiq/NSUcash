package com.mindreader007.nsucash.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class CalculatorScreen {
    private String onHoverStyle = "-fx-background-color: #a3b7ca; -fx-background-radius: 20; -fx-text-fill: #061742;";
    private String defaultStyle = "-fx-background-radius: 20; -fx-background-color: #d1dbe4; -fx-text-fill: #061742;";

    private double FirstNumber;
    private String Operator;
    private boolean startNew;

    @FXML
    private TextField calculateBox;

    public void onHoverButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle(onHoverStyle);
    }

    public void onHoverExitButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle(defaultStyle);
    }

    public String getActionValue(ActionEvent event){
        return ((Button) event.getSource()).getText();
    }

    public void onNumberClick(ActionEvent event){
        if(startNew){
            calculateBox.setText("");
            startNew = false;
        }
        String value = getActionValue(event);
        calculateBox.setText(calculateBox.getText() + value);
    }

    public void onOperatorClick(ActionEvent event){
        String newOperator = getActionValue(event);
        if(Operator != null && isNumeric(calculateBox.getText())){
            FirstNumber = calculate(FirstNumber, Operator);
        }
        else if(isNumeric(calculateBox.getText())){
            FirstNumber = Double.parseDouble(calculateBox.getText());
        }

        calculateBox.setText(newOperator);
        Operator = newOperator;
        startNew = true;
    }

    public void onEqualClick(ActionEvent event) {
        if (isNumeric(calculateBox.getText())) {
            calculateBox.setText(String.valueOf((int) calculate(FirstNumber, Operator)));
            startNew = true;
        }
    }

    public void onCClick(ActionEvent event){
        calculateBox.setText("");
        startNew = true;
    }

    public void onACClick(ActionEvent event){
        calculateBox.clear();
        FirstNumber = 0;
        Operator = null;
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

    public double calculate(double firstNumber, String operator) {
        if (operator == null) return Double.parseDouble(calculateBox.getText());

        double secondNumber = Double.parseDouble(calculateBox.getText());
        double result = 0;
        switch (operator) {
            case "+" -> result = firstNumber + secondNumber;
            case "-" -> result = firstNumber - secondNumber;
            case "X" -> result = firstNumber * secondNumber;
            case "/" -> result = firstNumber / secondNumber;
            case "%" -> result = firstNumber % secondNumber;
        }
        Operator = null;

        return result;
    }

    @FXML
    private void onBackClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mindreader007/nsucash/fxml/MainScreen.fxml"));
            Parent homeRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(homeRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
